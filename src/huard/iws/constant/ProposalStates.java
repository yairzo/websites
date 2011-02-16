package huard.iws.constant;

import java.util.HashMap;
import java.util.Map;

public class ProposalStates {

	private static Map<String, Integer> proposalStates = new HashMap<String,Integer>();

	static{
		proposalStates.put("DRAFT",0);
		proposalStates.put("APPROVED_BY_MAIN",1);
		proposalStates.put("APPROVED_BY_ALL",2);
		proposalStates.put("FUND_REFUSED",3);
		proposalStates.put("FUND_APPROVED",4);
		proposalStates.put("EXPERIMENT_APPROVED",5);
		proposalStates.put("DEAN_WAIT",6);
		proposalStates.put("DEAN_REFUSED",7);
		proposalStates.put("DEAN_APPROVED",8);
		proposalStates.put("PASSED_MOP_BUDGET_OFFICER",9);
		proposalStates.put("MOP_BUDGET_OPENED",10);
	}

	public static Map<String, Integer> getProposalStates(){
		return proposalStates;
	}

	public static int getProposalStateId( String state){
		return proposalStates.get(state);
	}













}
