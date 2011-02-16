package huard.iws.constant;

import java.util.HashMap;
import java.util.Map;

public class PersonProposalTypes {

	private static Map<String, Integer> personProposalTypes = new HashMap<String, Integer>();

	static{
		personProposalTypes.put("MAIN_RESEARCHER", 0);
		personProposalTypes.put("RESEARCHER", 1);
		personProposalTypes.put("MOP_DESK", 2);
		personProposalTypes.put("DEAN", 4);
		personProposalTypes.put("BUDGET_OFFICER", 5);
		personProposalTypes.put("ARCHIVER", 6);
		personProposalTypes.put("YISSUM", 7);
	}

	public static Map<String, Integer> getPersonProposalTypes(){
		return personProposalTypes;
	}

	public static int getTypeId(String typeName){
		return personProposalTypes.get(typeName);
	}

	public static boolean isResearcher(int typeId){
		return typeId <= 1;
	}

}
