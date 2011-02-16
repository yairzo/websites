package huard.iws.bean;

import huard.iws.model.AList;
import huard.iws.model.AListInstruction;

public class AListInstructionBean {

	private int id;
	private int listId;
	private String selectsFrom;
	private String subTables;
	private String columnsSelection;
	private String  defaultOrderByColumn;
	private String defaultOrderDirection;
	private String partialViewConditions;
	private boolean master;

	private AList list;



	public AListInstructionBean(){
		this.id = 0;
		this.listId = 0;
		this.selectsFrom = "";
		this.subTables = "";
		this.columnsSelection = "";
		this. defaultOrderByColumn = "";
		this.defaultOrderDirection = "";
		this.partialViewConditions = "";
		this.master = false;
	}

	public AListInstructionBean(AListInstruction aListInstruction){
		this.id = aListInstruction.getId();
		this.listId = aListInstruction.getListId();
		this.selectsFrom = aListInstruction.getSelectsFrom();
		this.subTables = aListInstruction.getSubTables();
		this.columnsSelection = aListInstruction.getColumnsSelection();
		this. defaultOrderByColumn = aListInstruction.getDefaultOrderByColumn();
		this.defaultOrderDirection = aListInstruction.getDefaultOrderDirection();
		this.partialViewConditions = aListInstruction.getPartialViewConditions();
		this.master = aListInstruction.isMaster();
	}

	public AListInstruction toAListInstruction(){
		AListInstruction aListInstruction = new AListInstruction();
		aListInstruction.setId(id);
		aListInstruction.setListId(listId);
		aListInstruction.setSelectsFrom(selectsFrom);
		aListInstruction.setSubTables(subTables);
		aListInstruction.setColumnsSelection(columnsSelection);
		aListInstruction.setDefaultOrderByColumn(defaultOrderByColumn);
		aListInstruction.setDefaultOrderDirection(defaultOrderDirection);
		aListInstruction.setPartialViewConditions(partialViewConditions);
		aListInstruction.setMaster(master);
		return aListInstruction;
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

	public String getSubTables() {
		return subTables;
	}

	public void setSubTables(String subTables) {
		this.subTables = subTables;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public AList getList() {
		return list;
	}

	public void setList(AList list) {
		this.list = list;
	}

}
