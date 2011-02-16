package huard.iws.model;

public class PersonPrivilege {
	private int id;
	private int personId;
	private String password;
	private String privilege;
	private boolean enabled;
	private String subscriptionMd5;

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
