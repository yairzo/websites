package huard.iws.model;

public class AListColumnInstruction {
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

	private String linkTargetFromColumn;
	private boolean useHelperTable;
	private String helperTableName;
	private String helperTableDisplayColumnName;
	private boolean sortable;
	private boolean manuallyEdited;

	public AListColumnInstruction (){
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
		this.linkTargetFromColumn = "";
		this.useHelperTable = false;
		this.helperTableName = "";
		this.helperTableDisplayColumnName = "";
		this.sortable = false;
		this.manuallyEdited = false;
	}

	public boolean isManuallyEdited() {
		return manuallyEdited;
	}
	public void setManuallyEdited(boolean manuallyEdited) {
		this.manuallyEdited = manuallyEdited;
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

	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
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
