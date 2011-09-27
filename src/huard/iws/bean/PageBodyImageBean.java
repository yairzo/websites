package huard.iws.bean;

import huard.iws.model.*;
import huard.iws.util.*;

public class PageBodyImageBean {
	private int id;
	private String name;
	private byte [] image;
	private int uploaderPersonId;


	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PageBodyImageBean(){

	}

	public PageBodyImageBean( PageBodyImage pageBodyImage){
		this.id = pageBodyImage.getId();
		this.name = pageBodyImage.getName();
		this.image = pageBodyImage.getImage();
		this.uploaderPersonId = pageBodyImage.getUploaderPersonId();
	}

	public PageBodyImage toPageBodyImage(){
		PageBodyImage pageBodyImage = new PageBodyImage();
		pageBodyImage.setId(id);
		pageBodyImage.setName(name);
		pageBodyImage.setImage(image);
		pageBodyImage.setUploaderPersonId(uploaderPersonId);
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
	public int getUploaderPersonId() {
		return uploaderPersonId;
	}
	public void setUploaderPersonId(int uploaderPersonId) {
		this.uploaderPersonId = uploaderPersonId;
	}



}
