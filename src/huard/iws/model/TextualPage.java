package huard.iws.model;


public class TextualPage {
	private int id;
	private String title;
	private int creatorId;
	private long creationTime;
	private int deskId;
	private boolean requireLogin;
	private String html;
	private String description;
	private boolean showImage;
	private String imageUrl;
	private boolean showFile;
	private String fileUrl;
	private boolean wrapExternalPage;
	private String externalPageUrl;
	private int categoryId;
	private Attachment attachment;

	public TextualPage(){
		this.id = 0;
		this.title = "";
		this.creatorId = 0;
		this.creationTime = 0;
		this.deskId=0;
		this.requireLogin=false;
		this.html="";
		this.description="";
		this.showImage=false;
		this.imageUrl="";
		this.showFile=false;
		this.fileUrl="";
		this.wrapExternalPage=false;
		this.externalPageUrl="";
		this.categoryId=0;
		this.attachment=new Attachment();
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

	public boolean getShowImage() {
		return showImage;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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


}
