package huard.iws.service;

import huard.iws.bean.AListBean;
import huard.iws.model.AList;
import huard.iws.model.AListDesign;
import huard.iws.util.RequestWrapper;

import java.util.List;

public interface ListService {

	public AList getList(String type, int id, String username);

	public AList getList(int id);

	public void updateList (AList aList);

	public int insertList (AList aList);

	//public int getListIdByListName(String listName);

	public void deleteList(int id);

	public List<AListBean.ListType> getListTypes(RequestWrapper request);

	public int getListTypeInv(String listTypeDisplay);

	public AList getSublist (int listId, int sublistId);

	public void moveSublistUp (AListBean aListBean, int sublistId);

	public void moveSublistDown (AListBean aListBean, int sublistId);

	public void moveSublistToLast (AListBean aListBean, int sublistId);

	public void deleteLastSublist (AListBean aListBean);

	public void deleteSublist(AListBean aListBean, int sublistIndex, RequestWrapper request);

	public AListDesign getListDesign (int listId, int parentListId);

	public void updateListDesign (AListDesign aListDesign);

	public void deleteListDesign (int listId, int parentListId);

	public void insertListDesign (int listId, int parentListId);

	public int copyList (int sourceListId, RequestWrapper request);
	
}
