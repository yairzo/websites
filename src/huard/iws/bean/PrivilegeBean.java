package huard.iws.bean;

import huard.iws.model.Privilege;

public class PrivilegeBean {

	private int id;
	private String privilege;

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public PrivilegeBean(){
		this.id = 0;
		this.privilege = "";
	}

	public PrivilegeBean(Privilege privilege){
		this.id = privilege.getId();
		this.privilege = privilege.getPrivilege();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}



}
