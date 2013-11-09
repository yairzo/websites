package huard.website.viewer.profile;

import huard.website.model.ComposedPatternedPage;
import huard.website.model.TabledInfoPage;
import huard.website.util.PageAccessLog;
import huard.website.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarProfile extends Profile {

	private final String profileName="Calendar";
	private String [] allSubDatesExceptThisString;
	private boolean fullList;
	private String foundBySearchWords;
	private boolean putAnchor;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";
	private ComposedPatternedPage composedPatternedPage;
	private int month;
	private int num;
	//private static final Logger logger = Logger.getLogger(CalendarProfile.class);


	public CalendarProfile(){
	 	super();
	 	fullList=false;
	 	composedPatternedPage = dbHandler.getComposedPatternedPageByProfileName(profileName);
	 	month=-1;
	 }

	public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("ComposedPatternedPages",102,getTitle(true),heb, ip);
	}

     public String getTitle(boolean heb){
 		if (heb) return composedPatternedPage.getHebrewTitle();
 		else return composedPatternedPage.getEnglishTitle();
 	 }

     public TabledInfoPage[] getInfoPagesByMonth(int monthNum){
    	if (infoPages==null){
   	    	if (monthNum==-1)
   	    		infoPages = dbHandler.getInfoPagesByRangeOfSubDates(0,0,true);
     		else{
   	    		int lastYear = (new GregorianCalendar().get(Calendar.YEAR))-1;
                List<TabledInfoPage> infoPagesList = new ArrayList<TabledInfoPage>();
            	//iterate over the years last year --> this year --> next year
                for (int i=lastYear; i<=lastYear+2; i++){
    	         	TabledInfoPage[] infoPagesArray;
            		Calendar startOfFirstDayOfMonth = new GregorianCalendar (i,monthNum,1,0,0);

     	     		Calendar endOfLastDayOfMonth = new GregorianCalendar (i,monthNum,startOfFirstDayOfMonth.getActualMaximum(Calendar.DATE),23,59);

     	     		infoPagesArray = dbHandler.getInfoPagesByRangeOfSubDates(startOfFirstDayOfMonth.getTimeInMillis(),endOfLastDayOfMonth.getTimeInMillis(),fullList);

     	     		for (int j=0; j<infoPagesArray.length; j++){
                   		if (! infoPagesArray[j].getTitle().equals("No Results")){
                   			infoPagesList.add(infoPagesArray[j]);
                   		}
                   	}

            	}
            	infoPages = Utils.getTabledInfoPagesArrayFromTabledInfoPagesList(infoPagesList);
            	if (infoPages == null)
            		infoPages = new TabledInfoPage[0];
         	}
     	}
       return infoPages;
    }

   public String getMonthNameByMonthNum(int monthNum){
   	if (monthNum>=0&&monthNum<=11)return Utils.getMonthName(monthNum);
   	else if (monthNum==-1) return Utils.getMonthName(12);
   	else return "No Such Month";
   }

   public String getMonthFullNameByMonthNum(int monthNum, boolean heb){
	   if (monthNum>=0&&monthNum<=11) return Utils.getMonthFullName(monthNum, heb);
	   else if (monthNum==-1) return Utils.getMonthFullName(12, heb);
	   else return "No Such Month";
   }


   public String[] getAllSubDatesExceptThisSubDate(int ardNum, long thisSubDate){
   	if (allSubDatesExceptThisString==null){
   		long[] allSubDates = getAllSubDates(ardNum);
	   	List<Long> allSubDatesExceptThisList = new ArrayList<Long>();
	   	for (int i=0; i<allSubDates.length; i++){
	   		if(allSubDates[i]!=thisSubDate) allSubDatesExceptThisList.add(new Long(allSubDates[i]));
	    }
	    allSubDatesExceptThisString = new String[allSubDatesExceptThisList.size()];

   	   for (int i=0; i<allSubDatesExceptThisList.size(); i++){
    		//Calendar calendar = new GregorianCalendar();
   		 	//calendar.setTime(new Date(((Long)allSubDatesExceptThisList.get(i)).longValue()));
   		 	allSubDatesExceptThisString[i] = Utils.getFormatedDate( ((Long)allSubDatesExceptThisList.get(i)).longValue());
   	   }
   	}
   return allSubDatesExceptThisString;

   }

   public void nullifyAllSubDatesExceptThisString(){
   	allSubDatesExceptThisString=null;
   }

   public long[] getAllSubDates(int ardNum){
   		List<Long> allSubDatesList = new ArrayList<Long>();
   		allSubDatesList.add(new Long(dbHandler.getTabledInfoPageDetailsByArdNum(""+ardNum).getSubDate()));
   		long[] additionalSubDates = dbHandler.getAdditionalSubDates(""+ardNum);
   		for (int i=0; i<additionalSubDates.length; i++){
   			allSubDatesList.add(new Long(additionalSubDates[i]));
   		}
        long[] allSubDates = new long[allSubDatesList.size()];
        for (int i=0; i<allSubDates.length; i++){
        	allSubDates[i]=((Long)allSubDatesList.get(i)).longValue();
        }
        return allSubDates;
   	}







	/**
	 * @return
	 */
	public boolean isFullList() {
		return fullList;
	}

	/**
	 * @param b
	 */
	public void setFullList(boolean b) {
		fullList = b;
	}

	/**
	 * @return
	 */
	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	/**
	 * @return
	 */
	public boolean isPutAnchor() {
		return putAnchor;
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
	}

	/**
	 * @param b
	 */
	public void setPutAnchor(boolean b) {
		putAnchor = b;
	}

	
	public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}


}
