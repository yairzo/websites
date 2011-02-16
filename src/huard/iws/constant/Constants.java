package huard.iws.constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/*private static final int proposalApproversListId = 72;
	private static final int yissumListId = 74;
	private static final int postSendersListId = 75;
	private static final int admins = 76;*/

	/*private static Map<Integer,Integer> mopDesksListIds = new HashMap<Integer,Integer>();*/
	private static Map<String, Integer> mopTitlesIds = new HashMap<String,Integer>();
	private static Map<Integer, String> listTypes = new HashMap<Integer, String>();
	private static Map<String, Integer> listTypesInv = new HashMap<String, Integer>();
	private static Map<Integer,String> tabs = new HashMap<Integer,String>();
	private static Map<String,Integer> tabsInv = new HashMap<String, Integer>();
	private static Map<Integer, String> postTypes = new HashMap<Integer, String>();
	private static Map<Integer, String> usersRoles = new HashMap<Integer, String>();


	static{
		/*mopDesksListIds.put(12, 73);*/
		mopTitlesIds.put("COORDINATOR",  3);
		mopTitlesIds.put("COORDINATOR_HELPER",  4);
		mopTitlesIds.put("BUDGET_OFFICER",  5);
		mopTitlesIds.put("ARCHIVER", 6);
		mopTitlesIds.put("YISSUM", 7);

		listTypes.put(1, "person");
		listTypes.put(2, "organization unit");

		for (Map.Entry<Integer,String> entry: listTypes.entrySet()){
			listTypesInv.put(entry.getValue(), entry.getKey());
		}

		tabs.put(1, "proposalDetails");
		tabs.put(2, "proposers");
		tabs.put(3, "proposalFiles");
		tabs.put(4, "partners");

		for (Map.Entry<Integer,String> entry: tabs.entrySet()){
			tabsInv.put(entry.getValue(), entry.getKey());
		}

		postTypes.put(1, "message");
		postTypes.put(2, "callOfProposal");

		usersRoles.put(1, "ROLE_POST_READER");

	}

	/*public static int getMopDeskListId(int mopDeskId){
		return mopDesksListIds.get(mopDeskId);
	}*/

	public static int getMopTitleId(String title){
		return mopTitlesIds.get(title);
	}

	public static Map<String,Integer> getMopTitlesIds(){
		return mopTitlesIds;
	}

	public static Map<Integer, String> getListTypesMap(){
		return listTypes;
	}

	public static Map<String, Integer> getListTypesInv() {
		return listTypesInv;
	}

	public static Map<Integer, String> getTabs() {
		return tabs;
	}

	public static Map<String, Integer> getTabsInv() {
		return tabsInv;
	}

	public static Map<Integer, String> getPostTypes() {
		return postTypes;
	}

	public static Map<Integer, String> getUsersRoles() {
		return usersRoles;
	}



	/*public static Map<Integer,Integer> getMopDesksListIds(){
		return mopDesksListIds;
	}*/
}
