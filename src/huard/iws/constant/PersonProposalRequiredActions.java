package huard.iws.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class PersonProposalRequiredActions {

	private static Map<String, Integer> personProposalRequiredActions = new HashMap<String,Integer>();

	static{
		// For a main proposer
		personProposalRequiredActions.put("FILL_BASIC_DETAILS", 0);

		// For a secondary proposer

		personProposalRequiredActions.put("UPDATE_BASIC_DETAILS", 1);

//		 For all researchers

		personProposalRequiredActions.put("HANDLE_SECONDARY_RESEARCHERS", 2);
		personProposalRequiredActions.put("HANDLE_PARTNERS", 3);
		personProposalRequiredActions.put("APPROVE", 4);
		personProposalRequiredActions.put("ADD_EXPERIMENT_APPROVALS", 5);
		personProposalRequiredActions.put("SEND_DEAN", 6);


		personProposalRequiredActions.put("WAIT_FUND_RESPONSE", 8);
		personProposalRequiredActions.put("NOTHING", 9);
		personProposalRequiredActions.put("WAIT_DEAN_RESPONSE", 12);
		personProposalRequiredActions.put("WAIT_MOP_OPEN_BUDGET", 14);
		personProposalRequiredActions.put("WAIT_RESEARCHER_APPROVAL", 15);

		// For a mop person

		personProposalRequiredActions.put("FILL_FUND_RESPONSE", 10);
		personProposalRequiredActions.put("PASS_BUDGET_OFFICER", 13);


		// For dean

		personProposalRequiredActions.put("FILL_DEAN_RESPONSE", 11);

		// For budget officer

		personProposalRequiredActions.put("OPEN_BUDGET", 16);

		// For archive

		personProposalRequiredActions.put("ARCHIVE", 17);

	}

	public static Map<String, Integer> getPersonProposalRequiredActions(){
		return personProposalRequiredActions;
	}

	public static int getRequiredActionId(String requiredAction){
		return personProposalRequiredActions.get(requiredAction);
	}

	public static List<Integer> getRequiredActionsIds(String requiredActions){
		StringTokenizer st = new StringTokenizer(requiredActions, ",");
		List<Integer> requiredActionsIds = new ArrayList<Integer>();
		while (st.hasMoreTokens()){
			requiredActionsIds.add(getRequiredActionId(st.nextToken()));
		}
		return requiredActionsIds;
	}

}
