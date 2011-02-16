package huard.iws.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PersonProposal  {
	private int id;
	private int personId;
	private int proposalId;
	private int typeId;
	private Integer stateId;
	private List<Integer> requiredActionsIds;
	private boolean approval;
	private String title;

	public PersonProposal(){
		this.personId = 0;
		this.proposalId = 0;
		this.typeId = 0;
		this.stateId = 0;
		this.requiredActionsIds = new ArrayList<Integer>();
		this.approval = false;
		this.title = "";
	}

	public PersonProposal(int personId, int proposalId){
		this.personId = personId;
		this.proposalId = proposalId;
		this.typeId = 0;
		this.stateId = 0;
		this.requiredActionsIds = new ArrayList<Integer>();
		this.approval = false;
		this.title = "";
	}

	public PersonProposal(int personId, int proposalId, int typeId, int stateId, List<Integer> requiredActionsIds, String title){
		this.personId = personId;
		this.proposalId = proposalId;
		this.typeId = typeId;
		this.stateId = stateId;
		this.requiredActionsIds = requiredActionsIds;
		this.approval = false;
		this.title = title;
	}


	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public List<Integer> getRequiredActionsIds() {
		return requiredActionsIds;
	}
	public String getRequiredActionsIdsString(){
		StringBuilder sb = new StringBuilder();
		for (int requiredActionId: requiredActionsIds){
			sb.append(""+requiredActionId+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public void setRequiredActionsIds(List<Integer> requiredActionsIds) {
		this.requiredActionsIds = requiredActionsIds;
	}
	public void setRequiredActionsIds(String requiredActionsIdsString) {
		StringTokenizer st = new StringTokenizer(requiredActionsIdsString, ",");
		requiredActionsIds.clear();
		while (st.hasMoreTokens()){
			requiredActionsIds.add(Integer.parseInt(st.nextToken()));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
