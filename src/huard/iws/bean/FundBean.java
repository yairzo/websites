package huard.iws.bean;

import huard.iws.model.Fund;

import java.sql.Timestamp;

public class FundBean {

	private int id;
	private String name;
	private String shortName;
	private int deskId;
	private boolean temporary;
	private int financialId;

	private int hujiId;
	private int parentId;
//	private boolean hasForms;
	private String webAddress;
	private String phone;
	private String fax;
	private String contact;
	private String mailAddress;
	private String keywords;
	private int creatorId;

	private int budgetOfficerId;
	private int financialReporterId;

	private String html;
	private Timestamp creationTime;
	private Timestamp updateTime;




	public FundBean(){
		this.id=0;
		this.name = "";
		this.shortName = "";
		this.deskId = 0;
		this.temporary = false;
		this.financialId=0;
		this.hujiId = 0;
		this.parentId = 0;
//		this.hasForms = "";
		this.webAddress = "";
		this.phone = "";
		this.fax = "";
		this.contact = "";
		this.mailAddress = "";
		this.keywords = "";
		this.creatorId = 0;

		this.budgetOfficerId = 0;
		this.financialReporterId = 0;

		this.html = "";
		this.creationTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}

	public FundBean (Fund fund){
		this.id = fund.getId();
		this.name = fund.getName();
		this.shortName = fund.getShortName();
		this.deskId = fund.getDeskId();
		this.temporary = fund.isTemporary();
		this.financialId=fund.getFinancialId();
		this.hujiId = fund.getHujiId();
		this.parentId = fund.getParentId();
//		this.hasForms = fund.gethasForms();
		this.webAddress = fund.getWebAddress();
		this.phone = fund.getPhone();
		this.fax = fund.getFax();
		this.contact = fund.getContact();
		this.mailAddress = fund.getMailAddress();
		this.keywords = fund.getKeywords();
		this.creatorId = fund.getCreatorId();

		this.budgetOfficerId = fund.getBudgetOfficerId();
		this.financialReporterId = fund.getFinancialReporterId();

		this.html = fund.getHtml();
		this.creationTime = fund.getCreationTime();
		this.updateTime = fund.getUpdateTime();
	}

	public Fund toFund(){
		Fund fund = new Fund();
		fund.setId(id);
		fund.setName(name);
		fund.setShortName(shortName);
		fund.setDeskId(deskId);
		fund.setTemporary(temporary);
		fund.setFinancialId(financialId);
		fund.setHujiId(hujiId);
		fund.setParentId(parentId);
//		fund.sethasForms();
		fund.setWebAddress(webAddress);
		fund.setPhone(phone);
		fund.setFax(fax);
		fund.setContact(contact);
		fund.setMailAddress(mailAddress);
		fund.setKeywords(keywords);
		fund.setCreatorId(creatorId);

		fund.setBudgetOfficerId(budgetOfficerId);
		fund.setFinancialReporterId(financialReporterId);

		fund.setHtml(html);
		fund.setCreationTime(creationTime);
		fund.setUpdateTime(updateTime);
		return fund;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public int getDeskId() {
		return deskId;
	}
	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getFinancialId() {
		return financialId;
	}
	public void setFinancialId(int financialId) {
		this.financialId = financialId;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
}
