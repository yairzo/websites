package huard.iws.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import huard.iws.bean.PersonBean;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

public class PersonPrivilege {
	private int id;
	private int personId;
	private String password;
	private String privilege;
	private boolean enabled;
	private String subscriptionMd5;
	private String subscriptionInitPage;
	private long lastAction;

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
	
	public String getSubscriptionInitPage() {
		return subscriptionInitPage;
	}
	public void setSubscriptionInitPage(String subscriptionInitPage) {
		this.subscriptionInitPage = subscriptionInitPage;
	}
	public long getLastAction() {
		return lastAction;
	}
	public void setLastAction(long lastAction) {
		this.lastAction = lastAction;
	}
	public String getFullName() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean person = new PersonBean(
				personService.getPerson(this.personId));
		return person.getDegreeFullNameHebrew();
	}

		public String getFormattedLastAction() {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date lastAction = new Date(this.lastAction);
			return formatter.format(lastAction);
		}

}
