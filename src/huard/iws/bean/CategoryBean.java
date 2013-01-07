package huard.iws.bean;

import java.util.ArrayList;
import java.util.List;

import huard.iws.model.Category;


public class CategoryBean {
	private int id;
	private String name;
	private int parentId;
	private int categoryOrder;
	private List<Category> subCategories;
	private String url;
	
	public CategoryBean(){
		this.id = 0;
		this.name = "";
		this.parentId = 0;
		this.categoryOrder = 0;
		this.subCategories=new ArrayList<Category>();
		this.url="";
	}


	public CategoryBean(Category category){
		this.id = category.getId();
		this.name = category.getName();
		this.parentId = category.getParentId();
		this.categoryOrder = category.getCategoryOrder();
		this.subCategories = category.getSubCategories();
		this.url = category.getUrl();
	}

	public Category toCategory(){
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setParentId(parentId);
		category.setCategoryOrder(categoryOrder);
		category.setSubCategories(subCategories);
		category.setUrl(url);
		return category;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}
	public List<Category> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
