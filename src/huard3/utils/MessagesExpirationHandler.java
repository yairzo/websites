
package huard3.utils;
import huard3.actions.*;
import java.util.*;
import java.io.*;

public class MessagesExpirationHandler {
	private PubPageDeleter pubPageDeleter;
	private PubPagesUpdate pubPagesUpdate;
	private PubPage [] pubPages;
	private long now;
	
	
	public MessagesExpirationHandler(){
		pubPageDeleter = new PubPageDeleter();
		pubPagesUpdate = new PubPagesUpdate();
		pubPages = new PubPagesQuery().getMessages();
		now = new Date().getTime();
	}
	
	public void hadleMessagesExpirations(){
		try{
			BufferedWriter writeExpired;
			writeExpired = new BufferedWriter(new FileWriter("/home/huard/checkDatabase/expired.txt"));
			for (int i=0; i<pubPages.length; i++){
				if (pubPages[i].isToRollingMessages())
					if (pubPages[i].getStopRollingDate()<now) {
					pubPagesUpdate.stopMessageFromRolling(pubPages[i].getArdNum());
					writeExpired.write(pubPages[i].getDocOwner()+"~ Message number:  "+pubPages[i].getArdNum()+" Title: "+pubPages[i].getTitle()+" has stopped rolling, it remains a static state for now");
					}
				if (pubPages[i].getExpDate()<now){
					pubPageDeleter.setArdNum(pubPages[i].getArdNum());
					pubPageDeleter.deletePubPage("I");
					writeExpired.write(pubPages[i].getDocOwner()+"~ Message number:  "+pubPages[i].getArdNum()+" with the Title: "+pubPages[i].getTitle()+" has expired, so I moved it to the olds table");
				}
			}
			writeExpired.close();	
		}			
		catch(IOException e){
			System.out.println(e);			
		}
					
						
	}
	
	
	
	

}
