package huard.iws.service;

import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.OrganizationUnit.OrganizationUnitType;
import huard.iws.util.ListView;
import huard.iws.util.NearPage;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface OrganizationUnitService {

	public List<OrganizationUnit> getOrganizationUnitsPage(ListView lv, SearchCreteria search, int itemsInPage);

	public boolean isLastPage (ListView lv, SearchCreteria search, int itemsInPage);

	public List<NearPage> getNearPageNums(ListView lv, SearchCreteria search, int itemsInPage);

	public int getLastPageNum(ListView lv, SearchCreteria search, int itemsInPage);

	public List<OrganizationUnit> getOrganizationUnits(ListView lv, SearchCreteria search);

	public List<OrganizationUnit>getOrganizationUnits();

	public List<OrganizationUnit>getOrganizationUnits(int listId, int orderColumn);

	public List<OrganizationUnit>getOrganizationUnits(int listId);

	public void prepareListView(ListView lv, SearchCreteria search, int itemsInPage);

	public OrganizationUnit getOrganizationUnit (int id);

	public OrganizationUnit getOrganizationUnit (String name);

	public void updateOrganizationUnit(OrganizationUnit organizationUnit);

	public int insertOrganizationUnit ();

	public void deleteOrganizationUnit(int id);

	public List<OrganizationUnitType> getOrganizationUnitTypes();

	public List<OrganizationUnitAttribution> getOrganizationUnitAttributions(int organizationUnitId);

	public void deleteOrganizationUnitAttribution (int organizationUnitAttributionId);

	public void insertOrganizationUnitAttribution (OrganizationUnitAttribution organizationUnitAttribution);

}
