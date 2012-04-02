package huard.iws.bean;

import huard.iws.model.PersonPrivilege;


public class PersonPrivilegeBean{
	
	private int id;
	private int personId;
	private String password;
	private String privilege;
	private boolean enabled;
	private String subscriptionMd5;
	
	public PersonPrivilegeBean(PersonPrivilege personPrivilege){
		this.id= personPrivilege.getId();
		this.personId = personPrivilege.getPersonId();
		this.password = personPrivilege.getPassword();
		this.privilege = personPrivilege.getPrivilege();
		this.enabled = personPrivilege.isEnabled();
		this.subscriptionMd5 = personPrivilege.getSubscriptionMd5();
		
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getSubscriptionMd5() {
		return subscriptionMd5;
	}
	public void setSubscriptionMd5(String subscriptionMd5) {
		this.subscriptionMd5 = subscriptionMd5;
	}


}
