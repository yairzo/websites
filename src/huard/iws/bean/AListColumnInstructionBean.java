package huard.iws.bean;

import huard.iws.model.AListColumnInstruction;

public class AListColumnInstructionBean {

	private int id;
	private int listId;
	private String columnName;
	private String columnDisplayName;
	private String orderBy;
	private boolean hidden;
	private int width;
	private boolean bold;
	private String align;
	private boolean noBr;
	private boolean mailAddress;
	private boolean webAddress;
	private boolean image;
	private String linkTargetFromColumn;
	private boolean useHelperTable;
	private String helperTableName;
	private String helperTableDisplayColumnName;
	private boolean sortable;
	private boolean manuallyEdited;

	private boolean actual;
	private final String  [] alignmentModes = {"right", "center", "left"};

	public AListColumnInstructionBean (){
		this.id = 0;
		this.listId = 0;
		this.columnName = "";
		this.columnDisplayName = "";
		this.orderBy = "";
		this.hidden = false;
		this.width = 100;
		this.bold = false;
		this.align = "";
		this.noBr = false;
		this.mailAddress = false;
		this.webAddress = false;
		this.image = false;
		this.linkTargetFromColumn = "";
		this.useHelperTable = false;
		this.helperTableName = "";
		this.helperTableDisplayColumnName = "";
		this.sortable = false;
		this.manuallyEdited = false;
	}


	public AListColumnInstructionBean(AListColumnInstruction aListColumnInstruction){
		this.id = aListColumnInstruction.getId();
		this.listId = aListColumnInstruction.getListId();
		this.columnName = aListColumnInstruction.getColumnName();
		this.columnDisplayName = aListColumnInstruction.getColumnDisplayName();
		this.orderBy = aListColumnInstruction.getOrderBy();
		this.hidden = aListColumnInstruction.isHidden();
		this.width = aListColumnInstruction.getWidth();
		this.bold = aListColumnInstruction.isBold();
		this.align = aListColumnInstruction.getAlign();
		this.noBr = aListColumnInstruction.isNoBr();
		this.mailAddress = aListColumnInstruction.isMailAddress();
		this.webAddress = aListColumnInstruction.isWebAddress();
		this.image = aListColumnInstruction.isImage();
		this.linkTargetFromColumn = aListColumnInstruction.getLinkTargetFromColumn();
		this.useHelperTable = aListColumnInstruction.isUseHelperTable();
		this.helperTableName = aListColumnInstruction.getHelperTableName();
		this.helperTableDisplayColumnName = aListColumnInstruction.getHelperTableDisplayColumnName();
		this.sortable = aListColumnInstruction.isSortable();
		this.manuallyEdited = aListColumnInstruction.isManuallyEdited();
	}


	public AListColumnInstruction toAListColumnInstruction(){
		AListColumnInstruction aListColumnInstruction = new AListColumnInstruction();
		aListColumnInstruction.setId(id);
		aListColumnInstruction.setListId(listId);
		aListColumnInstruction.setColumnName(columnName);
		aListColumnInstruction.setColumnDisplayName(columnDisplayName);
		aListColumnInstruction.setOrderBy(orderBy);
		aListColumnInstruction.setHidden(hidden);
		aListColumnInstruction.setWidth(width);
		aListColumnInstruction.setBold(bold);
		aListColumnInstruction.setAlign(align);
		aListColumnInstruction.setNoBr(noBr);
		aListColumnInstruction.setMailAddress(mailAddress);
		aListColumnInstruction.setWebAddress(webAddress);
		aListColumnInstruction.setImage(image);
		aListColumnInstruction.setLinkTargetFromColumn(linkTargetFromColumn);
		aListColumnInstruction.setUseHelperTable(useHelperTable);
		aListColumnInstruction.setHelperTableName(helperTableName);
		aListColumnInstruction.setHelperTableDisplayColumnName(helperTableDisplayColumnName);
		aListColumnInstruction.setSortable(sortable);
		aListColumnInstruction.setManuallyEdited(manuallyEdited);
		return aListColumnInstruction;
	}

	public void advanceNextAlignmentMode(){
		String alignment = this.getAlign();
		if (alignment.equals("")){
			this.setAlign(alignmentModes[0]);
			return;
		}
		for (int i = 0 ; i <  alignmentModes.length ; i++){
			if (alignmentModes[i].equals(alignment)){
				if (i < alignmentModes.length - 1)
					this.setAlign(alignmentModes [ i+1 ]);
				else
					this.setAlign(alignmentModes [ 0 ]);
				return;
			}
		}
	}

	public String getAlignSign(){
		if (this.getAlign().length() == 0) return "";
		return this.getAlign().substring(0,1);
	}


	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}

	public String getColumnDisplayName() {
		return columnDisplayName;
	}

	public void setColumnDisplayName(String columnDisplayName) {
		this.columnDisplayName = columnDisplayName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLinkTargetFromColumn() {
		return linkTargetFromColumn;
	}

	public void setLinkTargetFromColumn(String linkTargetFromColumn) {
		this.linkTargetFromColumn = linkTargetFromColumn;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public boolean isMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(boolean mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean isImage() {
		return image;
	}
	
	public void setImage(boolean image) {
		this.image = image;
	}
	
	public boolean isManuallyEdited() {
		return manuallyEdited;
	}

	public void setManuallyEdited(boolean manuallyEdited) {
		this.manuallyEdited = manuallyEdited;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isWebAddress() {
		return webAddress;
	}

	public void setWebAddress(boolean webAddress) {
		this.webAddress = webAddress;
	}

	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public boolean isUseHelperTable() {
		return useHelperTable;
	}


	public void setUseHelperTable(boolean useHelperTable) {
		this.useHelperTable = useHelperTable;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public boolean isBold() {
		return bold;
	}


	public void setBold(boolean bold) {
		this.bold = bold;
	}


	public String getAlign() {
		return align;
	}


	public void setAlign(String align) {
		this.align = align;
	}


	public String getHelperTableName() {
		return helperTableName;
	}


	public void setHelperTableName(String helperTableName) {
		this.helperTableName = helperTableName;
	}


	public String getHelperTableDisplayColumnName() {
		return helperTableDisplayColumnName;
	}


	public void setHelperTableDisplayColumnName(String helperTableDisplayColumnName) {
		this.helperTableDisplayColumnName = helperTableDisplayColumnName;
	}


	public boolean isNoBr() {
		return noBr;
	}


	public void setNoBr(boolean noBr) {
		this.noBr = noBr;
	}

}