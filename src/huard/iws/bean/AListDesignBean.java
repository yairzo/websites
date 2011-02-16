package huard.iws.bean;

import huard.iws.model.AListDesign;

public class AListDesignBean {
	private int id;
	private int listId;
	private int parentListId;
	private String displayNameAlignment;
	private int bottomPadding;
	private boolean showTableHeader;

	private final String  [] alignmentModes = {"right", "center", "left"};


	public AListDesignBean(){

	}

	public AListDesignBean(AListDesign aListDesign){
		this.id = aListDesign.getId();
		this.listId = aListDesign.getListId();
		this.parentListId = aListDesign.getParentListId();
		this.displayNameAlignment = aListDesign.getDisplayNameAlignment();
		this.bottomPadding = aListDesign.getBottomPadding();
		this.showTableHeader = aListDesign.isShowTableHeader();
	}

	public AListDesign toListDesign (){
		AListDesign aListDesign = new AListDesign();
		aListDesign.setId(id);
		aListDesign.setListId(listId);
		aListDesign.setParentListId(parentListId);
		aListDesign.setDisplayNameAlignment(displayNameAlignment);
		aListDesign.setBottomPadding(bottomPadding);
		aListDesign.setShowTableHeader(showTableHeader);
		return aListDesign;
	}

	public void advanceNextAlignmentMode(){
		String alignment = this.getDisplayNameAlignment();
		if (alignment.equals("")){
			this.setDisplayNameAlignment(alignmentModes[0]);
			return;
		}
		for (int i = 0 ; i <  alignmentModes.length ; i++){
			if (alignmentModes[i].equals(alignment)){
				if (i < alignmentModes.length - 1)
					this.setDisplayNameAlignment(alignmentModes [ i+1 ]);
				else
					this.setDisplayNameAlignment(alignmentModes [ 0 ]);
				return;
			}
		}
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

	public boolean isShowTableHeader() {
		return showTableHeader;
	}

	public void setShowTableHeader(boolean showTableHeader) {
		this.showTableHeader = showTableHeader;
	}

}
