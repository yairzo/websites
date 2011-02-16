package huard.iws.service;

import huard.iws.bean.AListBean;
import huard.iws.bean.AListColumnInstructionBean;
import huard.iws.constant.Constants;
import huard.iws.db.ListDao;
import huard.iws.model.AList;
import huard.iws.model.AListDesign;
import huard.iws.model.AListInstruction;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ListServiceImpl implements ListService {

	//private static final Logger logger = Logger.getLogger(ListServiceImpl.class);

	public AList getList(String type, int id, String username){
		return getList(id);
	}

	public AList getList(int id){
		return listDao.getList(id);
	}

	public void updateList(AList aList){
		listDao.updateList(aList);
	}

	public int insertList(AList aList){
		int r = listDao.insertList(aList);
		return r;
	}

	/*public int getListIdByListName(String listName){
		return listDao.getListIdByListName(listName);
	}*/

	public void deleteList(int id){
		listDao.deleteList(id);
	}

	public List<AListBean.ListType> getListTypes(RequestWrapper request){
		List<AListBean.ListType> listTypes = new ArrayList<AListBean.ListType>();
		for (Map.Entry<Integer, String> entry: Constants.getListTypesMap().entrySet()){
			AListBean listBean = new AListBean(request);
			AListBean.ListType listType = listBean.new ListType();
			listType.setId(entry.getKey());
			listType.setDisplay(entry.getValue());
			listTypes.add(listType);
		}
		return listTypes;
	}

	public int getListTypeInv(String display){
		return Constants.getListTypesInv().get(display);
	}

	public void moveSublistUp (AListBean aListBean, int sublistPos){
		if (! aListBean.isCompound()) return;
		List<AList> sublists = aListBean.getSublists();
		if (sublistPos == 0) return;
		int previousSublistLocation = sublists.get(sublistPos - 1).getLocation();
		sublists.get(sublistPos - 1).setLocation(sublists.get(sublistPos).getLocation());
		sublists.get(sublistPos).setLocation(previousSublistLocation);
	}

	public void moveSublistDown (AListBean aListBean, int sublistPos){
		if (! aListBean.isCompound()) return;
		List<AList> sublists = aListBean.getSublists();
		if (sublistPos == sublists.size() -1) return;
		int nextSublistLocation = sublists.get(sublistPos + 1).getLocation();
		sublists.get(sublistPos+1).setLocation(sublists.get(sublistPos).getLocation());
		sublists.get(sublistPos).setLocation(nextSublistLocation);
	}

	public void moveSublistToLast (AListBean aListBean, int sublistPos){
		int sublistsNum = aListBean.getSublists().size();
		for (int i=sublistPos; i<sublistsNum; i++){
			moveSublistDown(aListBean, i);
			Collections.sort(aListBean.getSublists(),
					new Comparator<AList>()
			        {
			            public int compare( AList l1, AList l2 )
			            {
			                if (l1.getLocation() == l2.getLocation())
			                    return 0;
			                if (l1.getLocation() < l2.getLocation())
			                    return -1;
			                else
			                    return +1;
			            }
			        } );
		}
	}

	public void deleteLastSublist (AListBean aListBean){
		int lastSublistIndex = 0;
		int location = 0;
		int i = 0;
		for (AList sublist: aListBean.getSublists()){
			if (sublist.getLocation() > location){
				location = sublist.getLocation();
				lastSublistIndex = i;
			}
			i++;
		}
		aListBean.getSublists().remove(lastSublistIndex);
	}

	public void deleteSublist(AListBean aListBean, int sublistIndex, RequestWrapper request){
		listColumnInstructionService.deleteListColumnDesign(aListBean.getSublists().get(sublistIndex).getId(),
				aListBean.getId(), request);
		this.deleteListDesign(aListBean.getSublists().get(sublistIndex).getId(), aListBean.getId());
		moveSublistToLast(aListBean, sublistIndex);
		deleteLastSublist(aListBean);
	}

	public void deleteListDesign(int listId, int parentListId){
		listDao.deleteListDesign(listId, parentListId);
	}

	public void insertListDesign(int listId, int parentListId){
		listDao.insertListDesign(listId, parentListId);
	}

	public AListDesign getListDesign (int listId, int parentListId){
		return listDao.getListDesign(listId, parentListId);
	}

	public void updateListDesign (AListDesign aListDesign){
		listDao.updateListDesign(aListDesign);
	}

	public AList getSublist (int listId, int sublistId){
		return listDao.getSublist(listId, sublistId);
	}

	public void deleteSublist (int listId, int sublistId){
		listDao.deleteSublist(listId, sublistId);
	}


	public int copyList (int sourceListId, RequestWrapper request){
		AList sourceList = this.getList(sourceListId);
		sourceList.setName(sourceList.getName() + " - copy");
		int newListId = this.insertList(sourceList);
		AListBean newListBean = new AListBean(sourceList, request);
		newListBean.setId(newListId);
		AListInstruction newListInstruction = listInstructionService.getMasterListInstruction(sourceList.getId());
		newListInstruction.setListId(newListId);
		int newListInstructionId =
			listInstructionService.insertListInstruction(newListInstruction);
		newListBean.setListInstructionId(newListInstructionId);
		for (AListColumnInstructionBean columnBean: newListBean.getColumnBeans()){
			columnBean.setListId(newListId);
			listColumnInstructionService.insertListColumnInstruction(columnBean.toAListColumnInstruction());
		}
		this.insertListDesign(newListBean.getId(), 0);
		return newListId;
	}


	private ListDao listDao;

	public void setListDao(ListDao listDao) {
		this.listDao = listDao;
	}

	private ListColumnInstructionService listColumnInstructionService;

	public void setListColumnInstructionService(
			ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}

	private ListInstructionService listInstructionService;

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}





}
