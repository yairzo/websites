package huard.iws.model;

import huard.iws.util.TextUtils;

public class Attachment {
	private int id;
	private int parentId;
	private String title;
	private byte [] file;
	private String contentType;
	private String filename;
	
	
	public String getIconTitle(){
		String extension = filename.substring(filename.lastIndexOf(".")+1);
		return TextUtils.getIcon(extension) + title;
	}
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCleanFilename() {
		String cleanFileName=filename;
		if(cleanFileName.indexOf(".")>-1)
			cleanFileName=cleanFileName.substring(0,filename.indexOf("."));
		return cleanFileName.replace("_", " ");
	}
	private int place;

	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


}
