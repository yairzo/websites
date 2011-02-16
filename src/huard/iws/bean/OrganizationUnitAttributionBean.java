package huard.iws.bean;

import huard.iws.model.AList;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.service.ListService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.util.ApplicationContextProvider;

import java.sql.Timestamp;

public class OrganizationUnitAttributionBean {
	private int id;
	private int organizationUnitId;
	private int listId;
	private Timestamp lastUpdateTime;

	private AList list;
	private OrganizationUnit organizationUnit;

	private ListService listService = (ListService) ApplicationContextProvider.getContext().getBean("listService");

	private OrganizationUnitService organizationUnitService = (OrganizationUnitService) ApplicationContextProvider.getContext().getBean("organizationUnitService");

	public OrganizationUnitAttributionBean(){

	}

	public OrganizationUnitAttributionBean (OrganizationUnitAttribution organizationUnitAttribution){
		this.id = organizationUnitAttribution.getId();
		this.organizationUnitId = organizationUnitAttribution.getOrganizationUnitId();
		this.listId = organizationUnitAttribution.getListId();
		this.lastUpdateTime = organizationUnitAttribution.getLastUpdateTime();
		init();
	}

	public OrganizationUnitAttribution toOrganizationUnitAttribution(){
		OrganizationUnitAttribution organizationUnitAttribution = new OrganizationUnitAttribution();
		organizationUnitAttribution.setId(id);
		organizationUnitAttribution.setOrganizationUnitId(organizationUnitId);
		organizationUnitAttribution.setListId(listId);
		organizationUnitAttribution.setLastUpdateTime(lastUpdateTime);
		return organizationUnitAttribution;
	}

	public void init(){
		if (listId > 0)
			list = listService.getList(listId);
		if (organizationUnitId > 0)
			organizationUnit = organizationUnitService.getOrganizationUnit(organizationUnitId);
	}

	public AList getList() {
		return list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public int getOrganizationUnitId() {
		return organizationUnitId;
	}
	public void setOrganizationUnitId(int organizationUnitId) {
		this.organizationUnitId = organizationUnitId;
	}

	public OrganizationUnit getOrganizationUnit() {
		return organizationUnit;
	}
}
