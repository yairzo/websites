package huard3.utils;
//import huard2.utils.*;
import java.util.*;
import java.io.*;
//import java.sql.SQLException;
import huard3.actions.*;


public class ChekDatabaseForExpiredInfoPagesOld {

        private InfoPagesUpdate updateDatabase;
        private InfoPage[] infoPages;
        private Date now;
      	private InfoPageDeleter infoPageDeleter;



    	public ChekDatabaseForExpiredInfoPagesOld(){
    	updateDatabase = new InfoPagesUpdate();
	    infoPages = new InfoPagesQuery().getInfoPages();
		now = new Date();
        infoPageDeleter = new InfoPageDeleter();

	    }

	    public void checkAllInfoPages(){
	    	try{
	    		BufferedWriter writeExpired;
	    		writeExpired = new BufferedWriter(new FileWriter("/home/huard/checkDatabase/expired.txt"));
                writeExpired.write("yair~checkDatabase worked");
                writeExpired.newLine();
                writeExpired.flush();
			
		
	        	for (int i=0; i<infoPages.length; i++){
				if (infoPages[i].getArdNum() < 10000){
			 	   	if (infoPages[i].getSubDate()!=0){
                                                                                                                         //one day
	            		if ((now.getTime()-infoPages[i].getSubDate()>0)&&(now.getTime()-infoPages[i].getSubDate()<86400000)&&(! infoPages[i].isHasAdditionalSubDates())){

	            			String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
	        			       "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
	        			       " has just expired, the Document is "+(infoPages[i].isRepetitive() ? "repetative I'm not doing anything about it" : "not repetative so it will be deleted in two weeks");

	        	    		writeExpired.write(expiredInfoPageDetailsString);
	            			writeExpired.newLine();
	            			writeExpired.flush();
	            		}
	            	    if ((! infoPages[i].isHasAdditionalSubDates()) && (! infoPages[i].isRepetitive()) && (now.getTime()-infoPages[i].getSubDate()>1209600000)){//14 days
	            			String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
	            		     	"~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
	            		     	" has expired  14 days ago, the Document is not repeatative so I'm moving it to old document's table";
	            			writeExpired.write(expiredInfoPageDetailsString);
	            			writeExpired.newLine();
	            			writeExpired.flush();
	            			infoPageDeleter.setArdNum(""+infoPages[i].getArdNum());
	            			infoPageDeleter.deleteInfoPage("I");
	            		}
             														//2 days
	            	    if ((now.getTime()-infoPages[i].getSubDate()>172800000)&&(infoPages[i].isHasAdditionalSubDates())){
	            	    	String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
	            	    	    "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
	        	        	    " has additional submission dates. It's 1st submission date has expired"+
	        	        	    " two days ago so I'm deleting it and moving 2nd submission date to be the 1st";
	        	        	writeExpired.write(expiredInfoPageDetailsString);
	            			writeExpired.newLine();
	            			writeExpired.flush();
	            	    	updateDatabase.moveSubDatesOneStepForward(infoPages[i].getArdNum());
	        	    }
                                                                                                    //~10 months                                       //10 months & 4 days
                            if ((infoPages[i].isRepetitive())&&
                               ((now.getTime()-infoPages[i].getSubDate())/1000>25920000)&&
                               (((now.getTime()-infoPages[i].getSubDate())/1000-25920000)%1209600>0)&&
                               (((now.getTime()-infoPages[i].getSubDate())/1000-25920000)%1209600<86400)&&
                               (!infoPages[i].isHasAdditionalSubDates())){
                                 String expiredInfoPageDetailsString = infoPages[i].getDocOwner().toLowerCase()+
                                      "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
                                      " is an expired repetative that his submition month is 2 months from now"+
                                      " please check if you have an exact submission date for it.";
                                 writeExpired.write(expiredInfoPageDetailsString);
                                 writeExpired.newLine();
                                 writeExpired.flush();                                 
                            }

	             	  }

                                     //if its in the range of 1 day from its birthday
	            	  else if ((now.getTime()-infoPages[i].getPubDate())/1000%31536000<86400 && (now.getTime()-infoPages[i].getPubDate())/1000>86400){
	            	  	String oldAllYearDocumentString = infoPages[i].getDocOwner().toLowerCase() +
	        	     	    "~Document number "+infoPages[i].getArdNum()+": "+infoPages[i].getTitle()+
	        	     	    " is celebrating it's 1st birthday (1 year), please check it is still up to date "+
	        	     	    " it's just a reminder i'm not doing anything";
	        	     	writeExpired.write(oldAllYearDocumentString);
	        	        writeExpired.newLine();
	        	        writeExpired.flush();
	        	      }
			}
	           	}
			writeExpired.close();


	    	}


	    	catch(IOException ioe){
	    		System.out.println("Couldn't write to file "+ioe);
	    	}
	    }


	    public static void main(String [] args){
	    	ChekDatabaseForExpiredInfoPagesOld check = new ChekDatabaseForExpiredInfoPagesOld();
	    	check.checkAllInfoPages();
	    }







	

}

