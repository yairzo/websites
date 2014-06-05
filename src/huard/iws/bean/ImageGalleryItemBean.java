package huard.iws.bean;

import huard.iws.model.ImageGalleryItem;

public class ImageGalleryItemBean {

	private int id;
	private int parentId;
	private String url;
	private String title;
	private String subtitle;
	private String text;
	private int place;


	

	public ImageGalleryItemBean( ImageGalleryItem imageGalleryItem){
		this.id = imageGalleryItem.getId();
		this.parentId = imageGalleryItem.getParentId();
		this.url = imageGalleryItem.getUrl();
		this.title = imageGalleryItem.getTitle();
		this.subtitle = imageGalleryItem.getSubtitle();
		this.text = imageGalleryItem.getText();
		this.place = imageGalleryItem.getPlace();
	}

	public ImageGalleryItem toGalleryItem(){
		ImageGalleryItem imageGalleryItem = new ImageGalleryItem();
		imageGalleryItem.setId(id);
		imageGalleryItem.setParentId(parentId);
		imageGalleryItem.setUrl(url);
		imageGalleryItem.setTitle(title);
		imageGalleryItem.setSubtitle(subtitle);
		imageGalleryItem.setText(text);
		imageGalleryItem.setPlace(place);
		return imageGalleryItem;
	}
	
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}



}
