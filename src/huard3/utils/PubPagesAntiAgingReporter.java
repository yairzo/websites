
package huard3.utils;
import huard3.actions.*;
import java.io.*;
import java.util.*;

public class PubPagesAntiAgingReporter {
	private PubPagesQuery pubPagesQuery;
	private PubPage [] pubPages;
	private long now;
	
	public PubPagesAntiAgingReporter(){
		now = new Date().getTime();
		pubPages = pubPagesQuery.getPubPagesNotUpdatedForOneYear(now);		
	}
	
	public void reportOnPubPagesNotUpdatedForOneYear(){
		try{
			BufferedWriter writeExpired;
			writeExpired = new BufferedWriter(new FileWriter(Utils.getHomeDirPath()+"checkDatabase/expired.txt"));
			for (int i=0; i<pubPages.length; i++){
				writeExpired.write(pubPages[i].getDocOwner()+"~ Inforamation Page: "+pubPages[i].getArdNum()+" Title: "+pubPages[i].getTitle()+" Hasn't been updated in the past year."+
					" This message will repeat until you read it, to check it's still updated, edit it (even without changing anythig, just open it for editing), and press the update button");
			}
			writeExpired.close();			
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	
	

}
