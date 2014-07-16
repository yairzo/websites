package huard.iws.db;

import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.OrganizationUnit.OrganizationUnitType;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface OrganizationUnitDao {

	public List<OrganizationUnit> getOrganizationUnits();

	public List<OrganizationUnit> getOrganizationUnits(int listId, String orderStatement, String filter);

	public List<OrganizationUnit> getOrganizationUnits(ListView lv, SearchCreteria search);

	public OrganizationUnit getOrganizationUnit (int id);

	public OrganizationUnit getOrganizationUnit (String name);

	public void updateOrganizationUnit (OrganizationUnit organizationUnit);

	public int insertOrganizationUnit();

	public void deleteOrganizationUnit(int id);

	public List<OrganizationUnitType> getOrganizationUnitTypes();

	public List<OrganizationUnitAttribution> getOrganizationUnitAttributions(int organizationUnitId);

	public void deleteOrganizationUnitAttribution (int organizationUnitAttributionId);

	public void insertOrganizationUnitAttribution (OrganizationUnitAttribution organizationUnitAttribution);

}
