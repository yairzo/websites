package huard3.utils;

import huard3.actions.*;
import java.sql.*;

public class LogFileHandler {

	public LogFileHandler (){
	}

	/*public LogRecored [] getLogFile(String username){
		try{
			BufferedReader logFileBR = new BufferedReader (new FileReader(Utils.getLogDir()+username+".log"));
	        String line;
     	    List logRecoredList = new ArrayList();
        	while ((line=logFileBR.readLine())!=null){
        		WordsTokenizer wt = new WordsTokenizer("~");
     			List fieldsList = wt.getSubstringsList(line);
     			if (fieldsList.size()==7){
     				LogRecored logRecored = new LogRecored();
     				logRecored.setUsername((String)fieldsList.get(0));
					logRecored.setAction((String)fieldsList.get(1));
					logRecored.setArdNum((String)fieldsList.get(2));
					logRecored.setTitle(Utils.moveHebrewCharsFromAsciiToHebrewCharset((String)fieldsList.get(3)));
					logRecored.setDocType((String)fieldsList.get(4));
					logRecored.setDate((String)fieldsList.get(5));
					logRecored.setNewArdNum((String)fieldsList.get(6));
     				logRecoredList.add(logRecored);
     			}
     			else System.out.println("Problem reading line number "+ logRecoredList.size() + "number of fields in the log file line else 7");
	        }
	        LogRecored [] logRecoredArray = new LogRecored [logRecoredList.size()];
	        for (int i=0; i<logRecoredArray.length; i++){
	        	logRecoredArray[i]=(LogRecored)logRecoredList.get(i);
	        }
	        return logRecoredArray;
		}
		catch (IOException ioe){
			System.out.println("Couldn't open log file: "+ioe);
			LogRecored [] errorRecored = new LogRecored[0];
			return errorRecored;
		}
	}*/


	/*public void addCreateOrUpdateLineToLogFile(String action, String username, InfoPage infoPage){
		try{
			BufferedWriter logFileBW = new BufferedWriter(new FileWriter(Utils.getLogDir()+infoPage.getDocOwner().toLowerCase()+".log",true));
            String line = username+"~"+action+"~"+infoPage.getArdNum()+"~"+infoPage.getTitle().replaceAll("~","")+
			  "~"+infoPage.getDocType()+ "~"+Utils.getFormatedDate(new Date().getTime())+"~No Change ";
			logFileBW.write(line);
       		logFileBW.newLine();
	        logFileBW.close();
		}
		catch (IOException ioe){
			System.out.println("Couldn't write to log file: "+ioe);
		}
	}

	public void addCreateOrUpdateLineToLogFile(String action, String username, PubPage pubPage){
		try{
			BufferedWriter logFileBW = new BufferedWriter(new FileWriter(Utils.getLogDir()+pubPage.getDocOwner().toLowerCase()+".log",true));
			String line = username+"~"+action+"~"+pubPage.getArdNum()+"~"+pubPage.getTitle().replaceAll("~","")+
			  "~ "+"~"+Utils.getFormatedDate(new Date().getTime())+"~No Change ";
			logFileBW.write(line);
			logFileBW.newLine();
			logFileBW.close();
		}
		catch (IOException ioe){
			System.out.println("Couldn't write to log file: "+ioe);
		}
	}*/

	/*public void addDeleteOrReviveLineToLogFile(String action, String username, InfoPage infoPage, String newArdNum){
		try{
			BufferedWriter logFileBW = new BufferedWriter(new FileWriter(Utils.getLogDir()+infoPage.getDocOwner().toLowerCase()+".log",true));
			String line = username+"~"+action+"~"+infoPage.getArdNum()+"~"+infoPage.getTitle()+
						  "~"+infoPage.getDocType()+"~"+Utils.getFormatedDate(new Date().getTime())+"~"+newArdNum;
			logFileBW.write(line);
       		logFileBW.newLine();
	        logFileBW.close();
		}
		catch (IOException ioe){
			System.out.println("Couldn't write to log file: "+ioe);
		}
	}*/

	public static void writeToUserLog(String action, String username, InfoPage infoPage, String details){
		try{
			Connection c = ConnectionSupplyer.getConnectionSupplyer().getCurrentUpdateConnection();
			PreparedStatement ps = c.prepareStatement("insert UserLog set username = ?, action = ?, ardNum = ?," +
					" title = ?, type = ?, owner = ?, desk = ?,  date = now(), details = ? ;");
			ps.setString(1, username);
			ps.setString(2, action);
			ps.setInt(3, infoPage.getArdNum());
			ps.setString(4, infoPage.getTitle());
			ps.setString(5, infoPage.getDocType());
			ps.setString(6, infoPage.getDocOwner());
			ps.setString(7, infoPage.getDeskId());
			ps.setString(8, details);
			ps.executeUpdate();
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}

	public static void writeToUserLog(String action, String username, PubPage pubPage, String details){
		try{
			Connection c = ConnectionSupplyer.getConnectionSupplyer().getCurrentUpdateConnection();
			PreparedStatement ps = c.prepareStatement("insert UserLog set username = ?, action = ?, ardNum = ?," +
					" title = ?, type = 'free text', owner = ?, desk = ?,  date = now(), details = ? ;");
			ps.setString(1, username);
			ps.setString(2, action);
			ps.setInt(3, pubPage.getArdNum());
			ps.setString(4, pubPage.getTitle());
			ps.setString(5, pubPage.getDocOwner());
			ps.setString(6, pubPage.getDeskId());
			ps.setString(7, details);
			ps.executeUpdate();
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}


}
