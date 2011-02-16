package huard.iws.model;

public class AListDesign {
	private int id;
	private int listId;
	private int parentListId;
	private String displayNameAlignment;
	private int bottomPadding;
	private boolean showTableHeader;

	public boolean isShowTableHeader() {
		return showTableHeader;
	}
	public void setShowTableHeader(boolean showTableHeader) {
		this.showTableHeader = showTableHeader;
	}
	public int getBottomPadding() {
		return bottomPadding;
	}
	public void setBottomPadding(int bottomPadding) {
		this.bottomPadding = bottomPadding;
	}
	public String getDisplayNameAlignment() {
		return displayNameAlignment;
	}
	public void setDisplayNameAlignment(String displayNameAlignment) {
		this.displayNameAlignment = displayNameAlignment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public int getParentListId() {
		return parentListId;
	}
	public void setParentListId(int parentListId) {
		this.parentListId = parentListId;
	}

}
