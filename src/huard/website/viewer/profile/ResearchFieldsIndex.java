package huard.website.viewer.profile;
import huard.website.model.*;

public class ResearchFieldsIndex extends Profile{

	private final String title = "Calls Sorted by Research Fields_קולות קוראים לפי תחומי מחקר";
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";
	private ResearchField [] researchFields;

	public ResearchFieldsIndex(){
		super();
	}


	public ResearchField [] getResearchFields(){
		if (researchFields == null){
			researchFields = dbHandler.getResearchFields();
		}
		return researchFields;
	}


	public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}


	public String getTitle(boolean heb) {
		if (heb) return title.substring(title.indexOf("_")+1);
		else return title.substring(0,title.indexOf("_"));
	}





}
