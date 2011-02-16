package huard.iws.service;

import huard.iws.bean.ProposalBean;
import huard.iws.model.PersonProposal;

import java.util.List;

public interface PersonProposalService {

	public PersonProposal insertPersonProposal( int personId, ProposalBean proposalBean, int typeId, boolean informed);

	public void updatePersonProposal(PersonProposal personProposal);

	public PersonProposal updatePersonProposal(int personId, int proposalId, int stateId);

	public void updatePersonProposal (int proposalId, int typeId, List<Integer> requiredActions);

	public void updatePersonProposal (int proposalId, int typeId, int stateId, List<Integer> requiredActions);

	public void updatePersonProposals(int proposalId, List<Integer> requiredActions);

	public void updateResearchersPersonProposals(int proposalId, List<Integer> requiredActions);

	public void updateResearchersPersonProposals(int proposalId, int stateId, List<Integer> requiredActions);

	public void updateMainResearcherPersonProposal(int proposalId, List<Integer> requiredActions);

	public void updateSecondaryResearchersPersonProposals(int proposalId, List<Integer> requiredActions);

	public void updateSecondaryResearchersPersonProposals(int proposalId, int stateId, List<Integer> requiredActions);

	public void updateSecondaryResearchersPersonProposals(ProposalBean proposalBean, int stateId, List<Integer> requiredActions);

	public void deletePersonProposal( PersonProposal personProposal);

	public void deletePersonProposal( int personId, int proposalId);

	public List<PersonProposal> getPersonProposals( int proposalId);

	public List<PersonProposal> getResearchersPersonProposals(int proposalId);

	public PersonProposal getPersonProposal(int personId, int proposalId);

	public PersonProposal getPersonProposalByType(int proposalId, int typeId);

	public boolean isExists(PersonProposal personProposal);

	public boolean isExists(int personId, int proposalId);

	public boolean isExistsByType (int proposaId, int typeId);

	public boolean isLastToApprove(int proposalId, PersonProposal personProposal);

}
