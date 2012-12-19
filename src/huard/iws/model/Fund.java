package huard.iws.model;

import java.sql.Timestamp;

public class Fund {

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


	public Fund(){
		this.id=0;
		this.name = "";
		this.shortName = "";
		this.deskId = 0;
		this.temporary = false;
		this.financialId=0;
		this.hujiId = 0;
		this.parentId = 0;
//		this.hasForms;
		this.webAddress = "";
		this.phone = "";
		this.fax = "";
		this.contact = "";
		this.mailAddress = "";
		this.keywords ="";
		this.creatorId = 0;

		this.budgetOfficerId = 0;
		this.financialReporterId = 0;

		this.html = "";
		this.creationTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());

	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}
	public boolean getTemporary() {
		return temporary;
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

	public int getBudgetOfficerId() {
		return budgetOfficerId;
	}

	public void setBudgetOfficerId(int budgetOfficerId) {
		this.budgetOfficerId = budgetOfficerId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getFinancialReporterId() {
		return financialReporterId;
	}

	public void setFinancialReporterId(int financialReporterId) {
		this.financialReporterId = financialReporterId;
	}

	public int getHujiId() {
		return hujiId;
	}

	public void setHujiId(int hujiId) {
		this.hujiId = hujiId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}



	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
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
