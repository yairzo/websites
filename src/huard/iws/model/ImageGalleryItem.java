package huard.iws.model;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryItem {

	private int id;
	private int parentId;
	private String url;
	private String title;
	private String subtitle;
	private String text;
	private int place;
	private int level;
	private boolean isLink;

	private List<ImageGalleryItem> subItems;
	
	public ImageGalleryItem(){
		this.id = 0;
		this.parentId = 0;
		this.url = "";
		this.title = "";
		this.subtitle = "";
		this.text = "";
		this.place = 0;
		this.level = 0;
		this.isLink = false;
		this.subItems = new ArrayList<ImageGalleryItem>();
	}
	

	public String getSubtitle() {
		return subtitle;
	}


	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}


	public List<ImageGalleryItem> getSubItems() {
		return subItems;
	}
	public void setSubItems(List<ImageGalleryItem> subItems) {
		this.subItems = subItems;
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

}
