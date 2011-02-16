package huard.iws.constant;

import java.util.HashMap;
import java.util.Map;

public class PersonProposalStates {

	private static Map<String, Integer> personProposalStates = new HashMap<String,Integer>();

	static{
		// For a main proposer
		personProposalStates.put("MAIN_YET_APPROVED", 0);

		// For a secondary proposer

		personProposalStates.put("ADDED_NOT_ACKN", 1);
		personProposalStates.put("ADDED_ACKN", 2);
		personProposalStates.put("WATCHED", 3);
		personProposalStates.put("APPROVAL_REQUEST", 4);

		// For all researchers

		personProposalStates.put("APPROVED", 5);

		// For a mop person


		personProposalStates.put("WAIT_FUND_RESPONSE", 6);
		personProposalStates.put("FILLED_FUND_REPONSE", 7);

		// For a huji approver

		personProposalStates.put("WAIT_DEAN_APPROVAL", 8);

		personProposalStates.put("DEAN_APPROVAL", 9);

		//General

		personProposalStates.put("ADDED", 10);




	}

	public static Map<String, Integer> getPersonProposalStates(){
		return personProposalStates;
	}

	public static int getStateId(String state){
		return personProposalStates.get(state);
	}

}
