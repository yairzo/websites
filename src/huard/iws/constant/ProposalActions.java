package huard.iws.constant;

import java.util.HashMap;
import java.util.Map;

public class ProposalActions {

	private static Map<String, Integer> proposalActions = new HashMap<String,Integer>();

	static{
		proposalActions.put("OPEN",0);
		proposalActions.put("ADD_RESEARCHER",1);
		proposalActions.put("REMOVE_RESEARCHER",2);
		proposalActions.put("APPROVED",3);
		proposalActions.put("FUND_RESPONSE",4);
		proposalActions.put("EXPERIMENT_APPROVAL",5);
		proposalActions.put("DEAN_SEND",6);
		proposalActions.put("DEAN_RESPONSE",7);
		proposalActions.put("MOP_APPROVAL",8);
		proposalActions.put("ADD_PARTNER", 9);
		proposalActions.put("REMOVE_PARTNER", 10);
		proposalActions.put("REMOVE_PARTNER", 10);
		proposalActions.put("PASSED_MOP_BUDGET_OFFICER", 11);
		proposalActions.put("OPEN_BUDGET", 12);
		proposalActions.put("SEND_YISSUM", 13);
		proposalActions.put("ARCHIVE", 13);
	}

	public static Map<String, Integer> getProposalActions(){
		return proposalActions;
	}

	public static int getProposalActionId( String action){
		return proposalActions.get(action);
	}

}
