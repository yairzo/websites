package huard.iws.model;

import java.io.Serializable;

public class PageBodyImage implements Serializable{
	public static final long serialVersionUID = 1323243434;

	private int id;
	private String name;
	private String captionHebrew;
	private String captionEnglish;
	private byte [] image;
	private int uploaderPersonId;
	private int approved;
	private String url;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaptionHebrew() {
		return captionHebrew;
	}
	public void setCaptionHebrew(String captionHebrew) {
		this.captionHebrew = captionHebrew;
	}
	public String getCaptionEnglish() {
		return captionEnglish;
	}
	public void setCaptionEnglish(String captionEnglish) {
		this.captionEnglish = captionEnglish;
	}
	public int getUploaderPersonId() {
		return uploaderPersonId;
	}
	public void setUploaderPersonId(int uploaderPersonId) {
		this.uploaderPersonId = uploaderPersonId;
	}
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
