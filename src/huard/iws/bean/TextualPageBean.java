package huard.iws.bean;

import java.util.List;

import huard.iws.model.TextualPage;
import huard.iws.model.Attachment;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

public class TextualPageBean {
	private int id;
	private String title;
	private int creatorId;
	private long creationTime;
	private int deskId;
	private boolean requireLogin;
	private String html;
	private String description;
	private boolean showFile;
	private String fileUrl;
	private boolean wrapExternalPage;
	private String externalPageUrl;
	private int categoryId;
	private Attachment attachment;
	private boolean isMessage;
	private long keepInRollingMessagesExpiryTime;
	private long updateTime;

	
	public TextualPageBean(){
		this.id = 0;
		this.title = "";
		this.creatorId = 0;
		this.creationTime = 0;
		this.deskId=0;
		this.requireLogin=false;
		this.html="";
		this.description="";
		this.showFile=false;
		this.fileUrl="";
		this.wrapExternalPage=false;
		this.externalPageUrl="";
		this.categoryId=0;
		this.attachment=new Attachment();
		this.isMessage=false;
		this.keepInRollingMessagesExpiryTime=0;
		this.updateTime=0;
	}


	public TextualPageBean(TextualPage textualPage){
		this.id = textualPage.getId();
		this.title = textualPage.getTitle();
		this.creatorId = textualPage.getCreatorId();
		this.creationTime = textualPage.getCreationTime();
		this.deskId = textualPage.getDeskId();
		this.requireLogin=textualPage.getRequireLogin();
		this.html=textualPage.getHtml();
		this.description=textualPage.getDescription();
		this.showFile=textualPage.getShowFile();
		this.fileUrl=textualPage.getFileUrl();
		this.wrapExternalPage=textualPage.getWrapExternalPage();
		this.externalPageUrl=textualPage.getExternalPageUrl();
		this.categoryId=textualPage.getCategoryId();
		this.attachment=textualPage.getAttachment();
		this.isMessage = textualPage.getIsMessage();
		this.keepInRollingMessagesExpiryTime = textualPage.getKeepInRollingMessagesExpiryTime();
		this.updateTime = textualPage.getUpdateTime();
	}

	public TextualPage toTextualPage(){
		TextualPage textualPage = new TextualPage();
		textualPage.setId(id);
		textualPage.setTitle(title);
		textualPage.setCreatorId(creatorId);
		textualPage.setCreationTime(creationTime);
		textualPage.setDeskId(deskId);
		textualPage.setRequireLogin(requireLogin);
		textualPage.setHtml(html);
		textualPage.setDescription(description);
		textualPage.setShowFile(showFile);
		textualPage.setFileUrl(fileUrl);
		textualPage.setWrapExternalPage(wrapExternalPage);
		textualPage.setExternalPageUrl(externalPageUrl);
		textualPage.setCategoryId(categoryId);
		textualPage.setAttachment(attachment);
		textualPage.setIsMessage(isMessage);
		textualPage.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
		textualPage.setUpdateTime(updateTime);
		return textualPage;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}


	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}

	public boolean getRequireLogin() {
		return requireLogin;
	}
	public void setRequireLogin(boolean requireLogin) {
		this.requireLogin = requireLogin;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getShowFile() {
		return showFile;
	}

	public void setShowFile(boolean showFile) {
		this.showFile = showFile;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public boolean getWrapExternalPage() {
		return wrapExternalPage;
	}

	public void setWrapExternalPage(boolean wrapExternalPage) {
		this.wrapExternalPage = wrapExternalPage;
	}

	public String getExternalPageUrl() {
		return externalPageUrl;
	}

	public void setExternalPageUrl(String externalPageUrl) {
		this.externalPageUrl = externalPageUrl;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	public boolean getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(boolean isMessage) {
		this.isMessage = isMessage;
	}

	public long getKeepInRollingMessagesExpiryTime() {
		return keepInRollingMessagesExpiryTime;
	}
	
	public void setKeepInRollingMessagesExpiryTime(long keepInRollingMessagesExpiryTime) {
		this.keepInRollingMessagesExpiryTime=keepInRollingMessagesExpiryTime;
	}

	public PersonBean getCreator() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean creator = new PersonBean(
				personService.getPerson(this.creatorId));
		return creator;
	}

	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

}
