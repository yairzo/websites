package huard.website.viewer.profile;

import huard.website.model.*;
import huard.website.util.*;



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Profile {

	protected ProfilesDbHandler dbHandler;
	protected TabledInfoPage [] infoPages;

	protected String foundBySearchWords;

	public Profile(){
		dbHandler = new ProfilesDbHandler();
	}

/*	public String[] getAdditionalSubDates(int ardNum){
    	if (additionalSubDatesStringArray==null){
    		long[] additionalSubDates = dbHandler.getAdditionalSubDates(""+ardNum);
	    	additionalSubDatesStringArray = new String[additionalSubDates.length];
    		for (int i=0; i<additionalSubDates.length; i++){
    			additionalSubDatesStringArray[i] = Utils.getFormatedDate(additionalSubDates[i]);
	    	}
    	}
    	return additionalSubDatesStringArray;
    }
*/

	public long[] getAdditionalSubDates(int ardNum){
    	return dbHandler.getAdditionalSubDates(""+ardNum);
    }

    public String getSubmissionExpressionByInfoPage(InfoPage infoPage){
       	if (infoPage.getSubDate()==0) return "All Year";
    	else if (isHasExpired(infoPage)){
    		if (infoPage.isRepetitive()){
    			Calendar calendar = new GregorianCalendar();
    			calendar.setTime(new Date(infoPage.getSubDate()));
    			return Utils.getMonthName(calendar.get(Calendar.MONTH));
    		}
    		else return "Expired";
    	}
    	else return getFormatedDate(infoPage.getSubDate());
    }

    public static boolean isHasExpired(InfoPage infoPage){
    	if (infoPage.getSubDate()< new Date().getTime()) return true;
    	else return false;
    }

    public void nullifyInfoPages(){
    	infoPages=null;
    }

    public String getFormatedDate(long date){
    	return Utils.getFormatedDate(date);
    }

    public String getVersionName(){
    	return Utils.getVersionName();
    }

    public String moveHebrewCharsFromAsciiToHebrewCharset(String text){
    	return Utils.moveHebrewCharsFromAsciiToHebrewCharset(text);
    }

    public String getLastUpdate(){
		return dbHandler.getInfoPagesLastUpdate();
	}
/*	public String getSiteLastUpdate(){
		return dbHandler.getSiteLastUpdate();
	}
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

}
