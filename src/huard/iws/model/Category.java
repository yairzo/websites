package huard.iws.model;

public class Category {
	private int id;
	private String name;
	private int parentId;
	private int categoryOrder;


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

}
