package huard.iws.service;

import huard.iws.bean.PersonListAttributionBean;
import huard.iws.db.PersonAttributionDao;
import huard.iws.db.PersonAttributionListDao;
import huard.iws.model.AList;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.AListInstruction;
import huard.iws.model.PersonListAttribution;
import huard.iws.util.SQLUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PersonAttributionListServiceImpl implements PersonAttributionListService {

	//private static final Logger logger = Logger.getLogger(PersonAttributionListServiceImpl.class);

	public List<PersonListAttribution> getPersonAttributionsByPersonId( int personId){
		return personAttributionListDao.getPersonAttributions(personId);
	}

	public List<PersonListAttribution> getPersonAttributions( int listId){
		List<PersonListAttribution> personListAttributions = new ArrayList<PersonListAttribution>();
		AList list = listService.getList(listId);
		if (list.isCompound()){
			for (AList sublist: list.getSublists())
				personListAttributions.addAll(getPersonAttributionsByListId(sublist.getId()));
		}
		else{
			personListAttributions.addAll(getPersonAttributionsByListId(listId));
		}
		return personListAttributions;
	}


	public List<PersonListAttribution> getPersonAttributionsByListId( int listId){
		AListInstruction listInstruction =  listInstructionService.getMasterListInstruction(listId);
		String order = listInstruction.getDefaultOrderByColumn() + " "+listInstruction.getDefaultOrderDirection();
		if (! SQLUtils.isNormalOrderStatement(order))
			order = "1 ASC";
		return personAttributionListDao.getPersonAttributionsByListId(listId, order);
	}

	public List<PersonListAttribution> getPersonAttributionsByListId(int listId, int orderColumn){
		final AListInstruction listInstruction =  listInstructionService.getMasterListInstruction(listId);
		List <AListColumnInstruction> columnInstructions = listColumnInstructionListService.getListColumnInstructions(listId);
		String order = "";
		final AListColumnInstruction orderColumnInstruction = columnInstructions.get(orderColumn);
		if (orderColumn > -1){
			order = orderColumnInstruction.getOrderBy();
		}
		if (! SQLUtils.isNormalOrderStatement(order))
			order = listInstruction.getDefaultOrderByColumn() + " "+listInstruction.getDefaultOrderDirection();
		if (! SQLUtils.isNormalOrderStatement(order))
			order = "1 ASC";
		List<PersonListAttribution> personsAttributions =
			personAttributionListDao.getPersonAttributionsByListId(listId, order);

		// If the order column uses a helper table we have to order the personAttributions by comparing the helper table
		// values. For that we create a helperTableMap to translate from the ids holded by the personListAttribution
		// to real values. For accessing the ids holded by personListAttribution we have to build a fiedValueMap.

		if (orderColumnInstruction != null && orderColumnInstruction.isUseHelperTable()){
			final Map<String, String> helperMap =
				helperTableService.getDisplayNamesMap(orderColumnInstruction.getHelperTableName()
						, orderColumnInstruction.getHelperTableDisplayColumnName());
			Comparator<PersonListAttribution> helperTablePersonListAttributionComparator
				= new Comparator<PersonListAttribution>(){
				public int compare (PersonListAttribution pla1, PersonListAttribution pla2){
					PersonListAttributionBean plab1 = new PersonListAttributionBean(pla1);
					PersonListAttributionBean plab2 = new PersonListAttributionBean(pla2);

					String s2 = helperMap.get(plab2.getFieldValueMap().get(orderColumnInstruction.getColumnName()));
					String s1 = helperMap.get(plab1.getFieldValueMap().get(orderColumnInstruction.getColumnName()));
					int compareResult = s1.compareTo(s2);
					if (compareResult == 0){
						compareResult = plab1.getFieldValueMap().get(listInstruction.getDefaultOrderByColumn())
							.compareTo(plab2.getFieldValueMap().get(listInstruction.getDefaultOrderByColumn()));
					}
					return compareResult;
				}
			};
			Collections.sort(personsAttributions, helperTablePersonListAttributionComparator);
		}
		return personsAttributions;
	}

	public List<PersonListAttribution> getPersonAttributionsByListId( int listId, String order){
		return personAttributionListDao.getPersonAttributionsByListId(listId, order);
	}

	public void updateConnectedDetailsPersonAttributions(int personId, PersonListAttribution personAttribution){
		List<PersonListAttribution> personAttributions = getPersonAttributionsByPersonId(personId);
		for (PersonListAttribution aPersonAttribution : personAttributions){
			if (aPersonAttribution.isConnectDetails()){
				personAttribution.setId(aPersonAttribution.getId());
				personAttribution.setPersonId(aPersonAttribution.getPersonId());
				personAttribution.setListId(aPersonAttribution.getListId());
				personAttribution.setTitle(aPersonAttribution.getTitle());
				personAttribution.setTitleId(aPersonAttribution.getTitleId());
				personAttribution.setPlaceInList(aPersonAttribution.getPlaceInList());
				personAttribution.setConnectDetails(aPersonAttribution.isConnectDetails());
				personAttributionDao.updatePersonAttribution(personAttribution);
			}
		}
	}


	PersonAttributionListDao personAttributionListDao;

	public void setPersonAttributionListDao(PersonAttributionListDao personAttributionListDao) {
		this.personAttributionListDao = personAttributionListDao;
	}

	PersonAttributionDao personAttributionDao;

	public void setPersonAttributionDao(
			PersonAttributionDao personAttributionDao) {
		this.personAttributionDao = personAttributionDao;
	}

	private ListInstructionService listInstructionService;

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}

	private ListColumnInstructionListService listColumnInstructionListService;

	public void setListColumnInstructionListService(
			ListColumnInstructionListService listColumnInstructionListService) {
		this.listColumnInstructionListService = listColumnInstructionListService;
	}

	private HelperTableService helperTableService;

	public void setHelperTableService(HelperTableService helperTableService) {
		this.helperTableService = helperTableService;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}


}
