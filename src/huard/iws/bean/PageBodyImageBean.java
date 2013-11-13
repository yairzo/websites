package huard.iws.bean;

import huard.iws.model.*;
import huard.iws.util.*;

public class PageBodyImageBean {
	private int id;
	private String name;
	private String captionHebrew;
	private String captionEnglish;
	private byte [] image;
	private int uploaderPersonId;
	private int approved;
	private String url;

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PageBodyImageBean(){
		this.id = 0;
		this.name="";
		this.captionHebrew="";
		this.captionEnglish="";
		this.image=new byte[0];
		this.uploaderPersonId=0;
		this.approved=0;
		this.url="";
		this.title="";
	}

	public PageBodyImageBean( PageBodyImage pageBodyImage){
		this.id = pageBodyImage.getId();
		this.name = pageBodyImage.getName();
		this.captionHebrew = pageBodyImage.getCaptionHebrew();
		this.captionEnglish = pageBodyImage.getCaptionEnglish();
		this.image = pageBodyImage.getImage();
		this.uploaderPersonId = pageBodyImage.getUploaderPersonId();
		this.approved = pageBodyImage.getApproved();
		this.url = pageBodyImage.getUrl();
		this.title = pageBodyImage.getTitle();
	}

	public PageBodyImage toPageBodyImage(){
		PageBodyImage pageBodyImage = new PageBodyImage();
		pageBodyImage.setId(id);
		pageBodyImage.setName(name);
		pageBodyImage.setCaptionHebrew(captionHebrew);
		pageBodyImage.setCaptionEnglish(captionEnglish);
		pageBodyImage.setImage(image);
		pageBodyImage.setUploaderPersonId(uploaderPersonId);
		pageBodyImage.setApproved(approved);
		pageBodyImage.setUrl(url);
		pageBodyImage.setTitle(title);

		return pageBodyImage;
	}

	public int getWidth(){
		return ImageHandler.getWidth(this.image);
	}

	public int getHeight(){
		return ImageHandler.getHeight(this.image);
	}

	public int getRelationalWidth( int height){
		float divider = (float) (getHeight() / height);
		return Math.round(getWidth() / divider);
	}


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
