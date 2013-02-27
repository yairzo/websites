package huard.iws.model;


public class TextualPageOld {
	private int id;
	private String html;
	private String title;
	private boolean wraper;
	private String sourceToWrap;
	private long pubDate;
	private String docType;
	private String deskId;
	private boolean restricted;
	private boolean message;
	private long stopRollingDate;
	private boolean fileRepresentation;
	private String link;
	private String internalUseDescription;
	private boolean onSite;
	private String category;
	private long updateTime;
	
	
	public TextualPageOld(){
		this.id = 0;
		this.html = "";
		this.title="";
		this.wraper = false;
		this.sourceToWrap="";
		this.pubDate=0;
		this.docType = "";
		this.deskId ="";
		this.restricted =false;
		this.message =false;
		this.stopRollingDate =0;
		this.fileRepresentation=false;
		this.link="";
		this.internalUseDescription="";
		this.onSite=false;
		this.category="";
		this.updateTime=0;
	}
	
	public String toString(){
			StringBuffer text = new StringBuffer();
			text.append(html + " * ");
			text.append(title + " * ");
			return text.toString();
	}

	public boolean isWraper() {
		return wraper;
	}
	public boolean getWraper() {
		return wraper;
	}
	public void setWraper(boolean wraper) {
		this.wraper = wraper;
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getSourceToWrap() {
		return sourceToWrap;
	}
	public void setSourceToWrap(String sourceToWrap) {
		this.sourceToWrap = sourceToWrap;
	}

	public long getPubDate() {
		return pubDate;
	}
	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
	}

	public long getStopRollingDate() {
		return stopRollingDate;
	}
	public void setStopRollingDate(long stopRollingDate) {
		this.stopRollingDate = stopRollingDate;
	}

	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDeskId() {
		return deskId;
	}
	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getInternalUseDescription() {
		return internalUseDescription;
	}
	public void setInternalUseDescription(String internalUseDescription) {
		this.internalUseDescription = internalUseDescription;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public boolean getRestricted() {
		return restricted;
	}
	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public boolean getMessage() {
		return message;
	}
	public void setMessage(boolean message) {
		this.message = message;
	}

	public boolean getFileRepresentation() {
		return fileRepresentation;
	}
	public void setFileRepresentation(boolean fileRepresentation) {
		this.fileRepresentation = fileRepresentation;
	}

	public boolean getOnSite() {
		return onSite;
	}
	public void setOnSite(boolean onSite) {
		this.onSite = onSite;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}



}
