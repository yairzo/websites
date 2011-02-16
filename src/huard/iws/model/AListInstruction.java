package huard.iws.model;

public class AListInstruction {
	private int id;
	private int listId;
	private String selectsFrom;
	private String subTables;
	private String columnsSelection;
	private String  defaultOrderByColumn;
	private String defaultOrderDirection;
	private String partialViewConditions;
	private boolean master;

	public AListInstruction(){
		this.id = 0;
		this.listId = 0;
		this.selectsFrom = "";
		this.subTables = "";
		this.columnsSelection = "";
		this.defaultOrderByColumn = "";
		this.defaultOrderDirection = "";
		this.partialViewConditions = "";
		this.master = false;
	}


	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getColumnsSelection() {
		return columnsSelection;
	}

	public void setColumnsSelection(String columnsSelection) {
		this.columnsSelection = columnsSelection;
	}

	public String getDefaultOrderByColumn() {
		return defaultOrderByColumn;
	}

	public void setDefaultOrderByColumn(String defaultOrderByColumn) {
		this.defaultOrderByColumn = defaultOrderByColumn;
	}

	public String getDefaultOrderDirection() {
		return defaultOrderDirection;
	}

	public void setDefaultOrderDirection(String defaultOrderDirection) {
		this.defaultOrderDirection = defaultOrderDirection;
	}

	public String getPartialViewConditions() {
		return partialViewConditions;
	}

	public void setPartialViewConditions(String partialViewConditions) {
		this.partialViewConditions = partialViewConditions;
	}

	public String getSelectsFrom() {
		return selectsFrom;
	}

	public void setSelectsFrom(String selectsFrom) {
		this.selectsFrom = selectsFrom;
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

	public String getSubTables() {
		return subTables;
	}

	public void setSubTables(String subTables) {
		this.subTables = subTables;
	}



}
