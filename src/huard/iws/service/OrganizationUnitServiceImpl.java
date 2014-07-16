package huard.iws.service;

import huard.iws.bean.OrganizationUnitBean;
import huard.iws.db.OrganizationUnitDao;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.AListInstruction;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.OrganizationUnit.OrganizationUnitType;
import huard.iws.util.ListPaginator;
import huard.iws.util.ListView;
import huard.iws.util.NearPage;
import huard.iws.util.SQLUtils;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrganizationUnitServiceImpl implements OrganizationUnitService{

	//private static final Logger logger = Logger.getLogger(OrganizationUnitServiceImpl.class);

	public List<OrganizationUnit> getOrganizationUnitsPage(ListView lv, SearchCreteria search, int itemsInPage) {
		ListPaginator lp = new ListPaginator(getOrganizationUnits(lv, search), itemsInPage);
		List l = lp.getPage(lv.getPage());
		List<OrganizationUnit> organizationUnitsPage = new ArrayList<OrganizationUnit>();
		for (Object o : l){
			OrganizationUnit organizationUnit = (OrganizationUnit) o;
			organizationUnitsPage.add(organizationUnit);
		}
		return organizationUnitsPage;
	}

	public boolean isLastPage (ListView lv, SearchCreteria search, int itemsInPage){
		ListPaginator lp = new ListPaginator(getOrganizationUnits(lv, search), itemsInPage);
		return lp.getNumOfPages() == lv.getPage();
	}

	public List<NearPage> getNearPageNums(ListView lv, SearchCreteria search, int itemsInPage){
		ListPaginator lp = new ListPaginator(getOrganizationUnits(lv, search), itemsInPage);
		return lp.getNearPages(lv.getPage());
	}

	public int getLastPageNum(ListView lv, SearchCreteria search, int itemsInPage){
		ListPaginator lp = new ListPaginator(getOrganizationUnits(lv, search), itemsInPage);
		return lp.getNumOfPages();
	}


	public List<OrganizationUnit> getOrganizationUnits(ListView lv, SearchCreteria search) {
		return organizationUnitDao.getOrganizationUnits(lv, search);
	}


	public List<OrganizationUnit> getOrganizationUnits(){
		return organizationUnitDao.getOrganizationUnits();
	}

	public List<OrganizationUnit> getOrganizationUnits( int listId,String filter ){
		AListInstruction listInstruction =  listInstructionService.getMasterListInstruction(listId);
		String order = listInstruction.getDefaultOrderByColumn() + " "+listInstruction.getDefaultOrderDirection();
		if (! SQLUtils.isNormalOrderStatement(order))
			order = "1 ASC";
		return organizationUnitDao.getOrganizationUnits(listId, order,filter);
	}

	public List<OrganizationUnit> getOrganizationUnits(int listId, int orderColumn,String filter){
		final AListInstruction listInstruction = listInstructionService.getMasterListInstruction(listId);
		List <AListColumnInstruction> columnInstructions = listColumnInstructionListService.getListColumnInstructions(listId);
		String order = "";
		final AListColumnInstruction orderColumnInstruction = columnInstructions.get(orderColumn);
		if (orderColumn > -1)
			order = orderColumnInstruction.getOrderBy();
		if (! SQLUtils.isNormalOrderStatement(order))
			order = listInstruction.getDefaultOrderByColumn() + " "+listInstruction.getDefaultOrderDirection();
		if (! SQLUtils.isNormalOrderStatement(order))
			order = "1 ASC";
		List<OrganizationUnit> organizationUnits =
			organizationUnitDao.getOrganizationUnits(listId, order,filter);

		// If the order column uses a helper table we have to order the personAttributions by comparing the helper table
		// values. For that we create a helperTableMap to translate from the ids holded by the personListAttribution
		// to real values. For accessing the ids holded by personListAttribution we have to build a fiedValueMap.

		if (orderColumnInstruction != null && orderColumnInstruction.isUseHelperTable()){
			final Map<String, String> helperMap =
				helperTableService.getDisplayNamesMap(orderColumnInstruction.getHelperTableName()
						, orderColumnInstruction.getHelperTableDisplayColumnName());
			Comparator<OrganizationUnit> helperTableOrganizationUnitComparator
				= new Comparator<OrganizationUnit>(){
				public int compare (OrganizationUnit ou1, OrganizationUnit ou2){
					OrganizationUnitBean oub1 = new OrganizationUnitBean(ou1);
					OrganizationUnitBean oub2 = new OrganizationUnitBean(ou2);

					String s2 = helperMap.get(oub2.getFieldValueMap().get(orderColumnInstruction.getColumnName()));
					String s1 = helperMap.get(oub1.getFieldValueMap().get(orderColumnInstruction.getColumnName()));
					int compareResult = s1.compareTo(s2);
					if (compareResult == 0){
						compareResult = oub1.getFieldValueMap().get(listInstruction.getDefaultOrderByColumn())
							.compareTo(oub2.getFieldValueMap().get(listInstruction.getDefaultOrderByColumn()));
					}
					return compareResult;
				}
			};
			Collections.sort(organizationUnits, helperTableOrganizationUnitComparator);
		}
		return organizationUnits;
	}

	public void prepareListView(ListView lv, SearchCreteria search, int itemsInPage){
		ListPaginator lp = new ListPaginator(getOrganizationUnits(lv, search), itemsInPage);
		lv.setLastPage(lp.getNumOfPages());
		lv.setNearPages(lp.getNearPages(lv.getPage()));
	}

	public OrganizationUnit getOrganizationUnit (int id){
		return organizationUnitDao.getOrganizationUnit(id);
	}

	public OrganizationUnit getOrganizationUnit (String name){
		return organizationUnitDao.getOrganizationUnit(name);
	}

	public void updateOrganizationUnit(OrganizationUnit organizationUnit){
		organizationUnitDao.updateOrganizationUnit(organizationUnit);
	}

	public int insertOrganizationUnit (){
		return organizationUnitDao.insertOrganizationUnit();
	}

	public void deleteOrganizationUnit(int id){
		organizationUnitDao.deleteOrganizationUnit(id);
	}

	public List<OrganizationUnitType> getOrganizationUnitTypes(){
		return organizationUnitDao.getOrganizationUnitTypes();
	}

	public List<OrganizationUnitAttribution> getOrganizationUnitAttributions(int organizationUnitId){
		return organizationUnitDao.getOrganizationUnitAttributions(organizationUnitId);
	}

	public void deleteOrganizationUnitAttribution (int organizationUnitAttributionId){
		organizationUnitDao.deleteOrganizationUnitAttribution (organizationUnitAttributionId);
	}

	public void insertOrganizationUnitAttribution (OrganizationUnitAttribution organizationUnitAttribution){
		organizationUnitDao.insertOrganizationUnitAttribution(organizationUnitAttribution);
	}



	private ListInstructionService listInstructionService;

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}

	private OrganizationUnitDao organizationUnitDao;

	public void setOrganizationUnitDao(OrganizationUnitDao organizationUnitDao) {
		this.organizationUnitDao = organizationUnitDao;
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


}
