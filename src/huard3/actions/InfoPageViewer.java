package huard3.actions;

import huard3.utils.InternalUseInfoPagesQuery;
import huard3.utils.ResearchField;
import huard3.utils.Utils;


public class InfoPageViewer {
	private InfoPage infoPage;
	private InternalUseInfoPagesQuery infoPagesQuery;
	private String ardNum;
	
	private ResearchField[] allResearchFields;
	private long[] additionalSubDates;
	private final int MAX_ADDITIONAL_SUB_DATES=3;
	
	public InfoPageViewer(){
		infoPagesQuery = new InternalUseInfoPagesQuery();
	}
	
	public InfoPage getInfoPage(){
		if (infoPage==null) infoPage = infoPagesQuery.getInfoPageDetailsByArdNum(ardNum);
		return infoPage;
	}
	
	public String getFormatedPubDate(){
		return Utils.getFormatedDate(infoPage.getPubDate());
	}
	
	public String getFormatedSubDate(){
		return Utils.getFormatedDate(infoPage.getSubDate());
	}
	
	public int[] getResearchFieldsIntArray(){
		int numOfResearchFields=infoPagesQuery.getRowsCount("ResearchFields");
		int[] researchFields = new int[numOfResearchFields];
		int j=infoPage.getResearchFields();
		for (int i=numOfResearchFields-1;i>=0;i--){
			
			if (j%2==1){
				researchFields[i]=1;
				j=j-1;
			}
			else researchFields[i]=0;
			j=j/10;
		}
		return researchFields;
	}
	
	public ResearchField[] getAllResearchFieldsOrderdByNum(){
		if (allResearchFields==null) allResearchFields=infoPagesQuery.getAllResearchFieldsOrderdByNum();
		return allResearchFields;
	}
	
	public String[] getFormatedAdditionalSubDates(){
		if (additionalSubDates==null){
			additionalSubDates=infoPagesQuery.getAdditionalSubDates(ardNum);
		}
		String [] formatedAdditionalSubDates = new String[MAX_ADDITIONAL_SUB_DATES];
		for (int i=0; i<formatedAdditionalSubDates.length; i++){
			if (i<additionalSubDates.length) formatedAdditionalSubDates[i] = Utils.getFormatedDate(additionalSubDates[i]);
			else formatedAdditionalSubDates[i]="";
			
		}
		return formatedAdditionalSubDates;
	}
	
	
	
	public String getArdNum() {
		return ardNum;
	}

	

	
	public void setArdNum(String ardNum) {
		this.ardNum = ardNum;
	}

	

}
