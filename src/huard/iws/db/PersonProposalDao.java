package huard.iws.db;

import huard.iws.model.PersonProposal;

import java.util.List;

public interface PersonProposalDao {

	public void insertPersonProposal (PersonProposal personProposal);

	public void updatePersonProposal (PersonProposal personProposal);

	public void deletePersonProposal (PersonProposal personProposal);

	public List<PersonProposal> getPersonProposals(int proposalId);

	public PersonProposal getPersonProposal(int personId, int proposalId);

	public boolean isExists(PersonProposal personProposal);

}
