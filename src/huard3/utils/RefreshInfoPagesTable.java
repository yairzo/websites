package huard3.utils;

//import huard2.utils.*;
import huard3.actions.InfoPage;
import java.util.*;
import java.io.*;
//import huard2.actions.*;

public class RefreshInfoPagesTable {
//	private InfoPagesUpdate updateDatabase;
	private InfoPagesQuery infoPagesQuery;
	private InfoPage[] infoPages;
	private Date now;
//	private InfoPageDeleter infoPageDeleter;
	private BufferedWriter writeExpired;
	private final long milisInDay = 86400000;
	private final long meanMilisInMonth = 1000 * 2628000;
	
	public RefreshInfoPagesTable(){
//		updateDatabase = new InfoPagesUpdate();
		infoPagesQuery = new InfoPagesQuery();
		now = new Date();
//		infoPageDeleter = new InfoPageDeleter();
		try {
			BufferedWriter writeExpired;
			writeExpired = new BufferedWriter(new FileWriter("/home/huard/checkDatabase/expired.txt"));
			writeExpired.write("yair~checkDatabase worked");
			writeExpired.newLine();
			writeExpired.flush();
		}
		catch (IOException ioe){
			System.out.println(ioe);		
		}
	}
	
	public void checkForNonRepetitiveWithNoAdditionalSubDatesExpiredInfoPages(){
		try {
			
		
			infoPages = infoPagesQuery.getNonRepetitiveWithNoAdditionalSubDatesExpiredInfoPages(now);
		
			for (int i=0; i<infoPages.length; i++){
				if (now.getTime()-infoPages[i].getSubDate()< (1 * milisInDay)){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
						"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
						" has just expired, the Document is "+(infoPages[i].isRepetitive() ? "repetative I'm not doing anything about it" : "not repetative so it will be deleted in two weeks");
					
					writeExpired.write ( Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString) );
					writeExpired.newLine();
					writeExpired.flush();
				}
				if (now.getTime()-infoPages[i].getSubDate()>(14 * milisInDay)){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
						"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
						" has expired  14 days ago, the Document is not repeatative so I'm moving it to old document's table";
					writeExpired.write(Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString));
					writeExpired.newLine();
					writeExpired.flush();
					//infoPageDeleter.setArdNum(""+infoPages[i].getArdNum());
					//infoPageDeleter.deleteInfoPage("I");
				}
			}
						
			
		}
		catch (IOException ioe){
			System.out.println(ioe);
		}
	}
		
	public void checkForRepetitive(){
		try {
			infoPages = infoPagesQuery.getRepetitiveInfoPages();
			for (int i=0; i< infoPages.length; i++){
				if (now.getTime()-infoPages[i].getSubDate()< (1 * milisInDay)){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
						"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
						" <br> has just expired, the Document is repetative so I'm not deleting it" +
						" <br> It will appear with a general submssion month until you set a new submission date.";
					writeExpired.write ( Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString) );
					writeExpired.newLine();
					writeExpired.flush();
								}
				
				
				if ((now.getTime() - infoPages[i].getSubDate()) > (10 * meanMilisInMonth) && (now.getTime() - infoPages[i].getSubDate()) < (10 * meanMilisInMonth + 2 * milisInDay)){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
				 	  "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
				 	  " is an expired repetative that his submition month is 2 months from now"+
				 	  " please check if you have an exact submission date for it.";
					writeExpired.write(Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString));
					writeExpired.newLine();
					writeExpired.flush();                                 
				}
				if ((now.getTime() - infoPages[i].getSubDate()) > (12 * meanMilisInMonth) ){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
					  "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
					  " is an expired repetative that his submition month has passed exactly"+
					  " one year ago. <br> I'm starting not to belive in it's repetitiveness. I won't delete it,"+
					  " but I'll keep remind you of it, until you do something ";
					writeExpired.write(Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString));
					writeExpired.newLine();
					writeExpired.flush();                                 
				}		
			}
		}
		catch (IOException ioe){
			System.out.println(ioe);
		}
	}
	
	
	public void checkForExpiredInfoPagesWithAdditionalSubDates(){
		try {
			infoPages = infoPagesQuery.getExpiredInfoPagesWithAdditionalSubDates(now);
			for (int i=0; i< infoPages.length; i++){
				if (now.getTime()-infoPages[i].getSubDate()> 2 * milisInDay){
					String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
						"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
						" has additional submission dates. It's 1st submission date has expired"+
						" two days ago so I'm deleting it and moving 2nd submission date to be the 1st";
					writeExpired.write(Utils.checkForHebrewCharsAndChangeThemToAscii(expiredInfoPageDetailsString));
					writeExpired.newLine();
					writeExpired.flush();
					//updateDatabase.moveSubDatesOneStepForward(infoPages[i].getArdNum());
				}
			}
		}
		catch (IOException ioe){
			System.out.println(ioe);
		}
	}
	
	public void checkForAllYear(){
		try{
			infoPages = infoPagesQuery.getAllYearInfoPages();
			for (int i=0; i< infoPages.length; i++){
				if (now.getTime() - infoPages[i].getPubDate() > 12 * meanMilisInMonth) {
					String oldAllYearDocumentString = infoPages[i].getDocOwner().toLowerCase() +
						"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
						"<br> is celebrating it's 1st birthday (1 year), please check it is still up to date "+
						"<br> it's just a reminder i'm not doing anything, but i'll keep reminding you"+ 
						"<br> until you change it's publication date";
					writeExpired.write(Utils.checkForHebrewCharsAndChangeThemToAscii(oldAllYearDocumentString));
					writeExpired.newLine();
					writeExpired.flush();
				}
			}
		}
		catch (IOException ioe){
			System.out.println(ioe);
		}
	}
	
	public static void main (String[] args){
		RefreshInfoPagesTable ript = new RefreshInfoPagesTable();
		ript.checkForNonRepetitiveWithNoAdditionalSubDatesExpiredInfoPages();
		ript.checkForExpiredInfoPagesWithAdditionalSubDates();
		ript.checkForRepetitive();
	}
	
		 
	

}
