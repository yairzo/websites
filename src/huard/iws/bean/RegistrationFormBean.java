package huard.iws.bean;

import huard.iws.model.Abstract;
import huard.iws.model.RegistrationForm;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFormBean {

	private int id;
	private String title;
	private String firstName;
	private String lastName;
	private String department;
	private String superviser;
	private String phone;
	private String mobile;
	private String fax;
	private String contactEmail;
	private boolean oneDay;
	private boolean needAccomodation;
	private boolean bus;
	private boolean returnBus;
	private String firstRoommate;
	private String secondRoommate;
	private boolean roomTypeDouble;
	private long updateTime;

	private List<Abstract> attachments;

	public RegistrationFormBean(){
		this.id = 0;
		this.title = "";
		this.firstName = "";
		this.lastName = "";
		this.department = "";
		this.superviser = "";
		this.phone = "";
		this.mobile ="";
		this.fax = "";
		this.contactEmail="";
		this.oneDay=false;
		this.needAccomodation=false;
		this.bus=false;
		this.returnBus=false;
		this.firstRoommate="";
		this.secondRoommate="";
		this.roomTypeDouble=false;
		this.updateTime=0;
		this.attachments=new ArrayList<Abstract>();
	}
	
	public RegistrationFormBean(RegistrationForm registrationForm){
		this.id = registrationForm.getId();
		this.title = registrationForm.getTitle();
		this.firstName = registrationForm.getFirstName();
		this.lastName = registrationForm.getLastName();
		this.department = registrationForm.getDepartment();
		this.superviser = registrationForm.getSuperviser();
		this.phone = registrationForm.getPhone();
		this.mobile = registrationForm.getMobile();
		this.fax = registrationForm.getFax();
		this.contactEmail= registrationForm.getContactEmail();
		this.oneDay=registrationForm.isOneDay();
		this.needAccomodation=registrationForm.isNeedAccomodation();
		this.bus=registrationForm.isBus();
		this.returnBus=registrationForm.isReturnBus();
		this.firstRoommate=registrationForm.getFirstRoommate();
		this.secondRoommate=registrationForm.getSecondRoommate();
		this.roomTypeDouble=registrationForm.isRoomTypeDouble();
		this.updateTime=registrationForm.getUpdateTime();
		this.attachments=registrationForm.getAttachments();
	}
	
	public RegistrationForm toRegistrationForm(){
		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setId(id);
		registrationForm.setTitle(title);
		registrationForm.setFirstName(firstName);
		registrationForm.setLastName(lastName);
		registrationForm.setDepartment(department);
		registrationForm.setSuperviser(superviser);
		registrationForm.setPhone(phone);
		registrationForm.setMobile(mobile);
		registrationForm.setFax(fax);
		registrationForm.setContactEmail(contactEmail);
		registrationForm.setOneDay(oneDay);
		registrationForm.setNeedAccomodation(needAccomodation);
		registrationForm.setBus(bus);
		registrationForm.setReturnBus(returnBus);
		registrationForm.setFirstRoommate(firstRoommate);
		registrationForm.setSecondRoommate(secondRoommate);
		registrationForm.setRoomTypeDouble(roomTypeDouble);
		registrationForm.setUpdateTime(updateTime);
		registrationForm.setAttachments(attachments);
		return registrationForm;
	}
		
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getSuperviser() {
		return superviser;
	}
	public void setSuperviser(String superviser) {
		this.superviser = superviser;
	}
	

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
 
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public boolean isOneDay() {
		return oneDay;
	}
	public void setOneDay(boolean oneDay) {
		this.oneDay = oneDay;
	}
	
	public boolean isNeedAccomodation() {
		return needAccomodation;
	}
	public void setNeedAccomodation(boolean needAccomodation) {
		this.needAccomodation = needAccomodation;
	}
	
	public boolean isBus() {
		return bus;
	}
	public void setBus(boolean bus) {
		this.bus = bus;
	}

	public boolean isReturnBus() {
		return returnBus;
	}
	public void setReturnBus(boolean returnBus) {
		this.returnBus = returnBus;
	}
	
	public String getFirstRoommate() {
		return firstRoommate;
	}
	public void setFirstRoommate(String firstRoommate) {
		this.firstRoommate = firstRoommate;
	}
	
	public String getSecondRoommate() {
		return secondRoommate;
	}
	public void setSecondRoommate(String secondRoommate) {
		this.secondRoommate = secondRoommate;
	}

	public boolean isRoomTypeDouble() {
		return roomTypeDouble;
	}
	public void setRoomTypeDouble(boolean roomTypeDouble) {
		this.roomTypeDouble = roomTypeDouble;
	}
	
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	

	public List<Abstract> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Abstract> attachments) {
		this.attachments = attachments;
	}
	

}
