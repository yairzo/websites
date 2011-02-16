package huard.iws.model;

import java.sql.Timestamp;

public class OrganizationUnitAttribution {
	private int id;
	private int organizationUnitId;
	private int listId;
	private Timestamp lastUpdateTime;


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


}
