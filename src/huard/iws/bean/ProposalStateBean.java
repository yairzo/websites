package huard.iws.bean;

import huard.iws.model.ProposalState;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;

import java.sql.Timestamp;

public class ProposalStateBean {
	private int id;
	private int stateId;
	private int actionId;
	private int proposalId;
	private int personId;
	private String personRole;
	private String stateDetails;
	private String actionDetails;
	private Timestamp timeLogged;

	private PersonBean personBean;
	private PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");



	private void init(){
		personBean = new PersonBean( personService.getPerson(personId));
	}

	public String getProposalStateHistoryDescription(){
		StringBuilder s = new StringBuilder();
		s.append(DateUtils.formatDate(timeLogged)).append(" "+personBean.getDegreeFullNameHebrew());
		s.append(" "+actionDetails).append(" "+stateDetails);
		return s.toString();
	}

	public String getFormattedTimeLogged(){
		return DateUtils.formatDate(timeLogged);
	}

	public String getActionDetails() {
		return actionDetails;
	}

	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(String stateDetails) {
		this.stateDetails = stateDetails;
	}

	public ProposalStateBean (){

	}

	public ProposalStateBean(ProposalState proposalState){
		this.id = proposalState.getId();
		this.stateId = proposalState.getStateId();
		this.actionId = proposalState.getActionId();
		this.personId = proposalState.getPersonId();
		this.proposalId = proposalState.getProposalId();
		this.stateDetails = proposalState.getStateDetails();
		this.actionDetails = proposalState.getActionDetails();
		this.timeLogged = proposalState.getTimeLogged();
		init();
	}

	public ProposalState toProposalState(){
		ProposalState proposalState = new ProposalState();
		proposalState.setId(id);
		proposalState.setStateId(stateId);
		proposalState.setActionId(actionId);
		proposalState.setPersonId(personId);
		proposalState.setProposalId(proposalId);
		proposalState.setStateDetails(stateDetails);
		proposalState.setActionDetails(actionDetails);
		proposalState.setTimeLogged(timeLogged);
		return proposalState;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public Timestamp getTimeLogged() {
		return timeLogged;
	}

	public void setTimeLogged(Timestamp timeLogged) {
		this.timeLogged = timeLogged;
	}

	public PersonBean getPersonBean() {
		return personBean;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}



}
