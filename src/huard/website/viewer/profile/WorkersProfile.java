package huard.website.viewer.profile;
import huard.website.model.*;
import huard.website.menu.*;
import huard.website.util.*;

public class WorkersProfile extends Profile{
		private ProfilesDbHandler dbHandler;
		private Desk [] desks;
		private boolean putAnchor;
		private final String category = "The Authority_הרשות למופ";
		private ComposedPatternedPage composedPatternedPage;
		private final String profileName="Workers";
		private String categoryColor;


		public WorkersProfile() {
			dbHandler = new ProfilesDbHandler();
			composedPatternedPage = dbHandler.getComposedPatternedPageByProfileName(profileName);
			categoryColor = new MenusDbHandler().getCategoryByCategoryName( category.substring( 0,category.indexOf("_") ), "Eng" ).getBgcolor();
		}

		public void logAccessToPage(boolean heb, String ip){
			PageAccessLog.logAccesToPage("ComposedPatternedPages",100,getTitle(true),heb, ip);
		}

		public Desk [] getDesks(){
			if (desks==null){
				String [] deskIds = dbHandler.getDesksIds();
				desks = new Desk[deskIds.length];
				for (int i=0 ;i<desks.length; i++){
					desks[i] = new Desk(deskIds[i]);
				}
			}
			return desks;
		}

		public String getTitle(boolean heb){
	 		if (heb) return composedPatternedPage.getHebrewTitle();
	 		else return composedPatternedPage.getEnglishTitle();
	 	}

		public String getLastUpdate(){
			return dbHandler.getWorkersTableLastUpdate();
		}



		/**
		 * @param query
		 */




		/**
		 * @return
		 */
		public String getFoundBySearchWords() {
			return foundBySearchWords;
		}

		/**
		 * @param string
		 */
		public void setFoundBySearchWords(String string) {
			foundBySearchWords = string;
		}

		/**
		 * @return
		 */
		public boolean isPutAnchor() {
			return putAnchor;
		}

		/**
		 * @param b
		 */
		public void setPutAnchor(boolean b) {
			putAnchor = b;
		}

		public String getCategory(boolean heb){
			if (heb) return category.substring(category.indexOf("_")+1);
			else return category.substring(0,category.indexOf("_"));
		}
		public String getCategoryColor() {
			return categoryColor;
		}

}
