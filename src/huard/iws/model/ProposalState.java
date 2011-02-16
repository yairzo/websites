package huard.iws.model;

import java.sql.Timestamp;

public class ProposalState {

	private int id;
	private int stateId;
	private int actionId;
	private int proposalId;
	private int personId;
	private String personRole;
	private String stateDetails;
	private String actionDetails;
	private Timestamp timeLogged;


	public String getActionDetails() {
		return actionDetails;
	}

	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}

	public String getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(String stateDetails) {
		this.stateDetails = stateDetails;
	}

	public ProposalState(){

	}

	public ProposalState(int stateId, int actionId, int proposalId, int personId,
			String personRole, String stateDetails, String actionDetails){
		this.proposalId = proposalId;
		this.personId = personId;
		this.personRole = personRole;
		this.stateDetails = stateDetails;
		this.actionDetails = actionDetails;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPersonRole() {
		return personRole;
	}

	public void setPersonRole(String personRole) {
		this.personRole = personRole;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public Timestamp getTimeLogged() {
		return timeLogged;
	}

	public void setTimeLogged(Timestamp timeLogged) {
		this.timeLogged = timeLogged;
	}

}
