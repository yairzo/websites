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
	private int level;
	private boolean isLink;
	private String textualPageUrlTitle;
	

	public ImageGalleryItemBean( ImageGalleryItem imageGalleryItem){
		this.id = imageGalleryItem.getId();
		this.parentId = imageGalleryItem.getParentId();
		this.url = imageGalleryItem.getUrl();
		this.title = imageGalleryItem.getTitle();
		this.subtitle = imageGalleryItem.getSubtitle();
		this.text = imageGalleryItem.getText();
		this.place = imageGalleryItem.getPlace();
		this.level = imageGalleryItem.getLevel();
		this.isLink = imageGalleryItem.isLink();
		this.textualPageUrlTitle = imageGalleryItem.getTextualPageUrlTitle();
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
		imageGalleryItem.setLevel(level);
		imageGalleryItem.setIsLink(isLink);
		imageGalleryItem.setTextualPageUrlTitle(textualPageUrlTitle);
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

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLink() {
		return isLink;
	}
	public void setIsLink(boolean isLink) {
		this.isLink = isLink;
	}
	
	public String getTextualPageUrlTitle() {
		return textualPageUrlTitle;
	}
	public void setTextualPageUrlTitle(String textualPageUrlTitle) {
		this.textualPageUrlTitle = textualPageUrlTitle;
	}
}
