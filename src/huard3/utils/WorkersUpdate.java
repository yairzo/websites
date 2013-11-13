package huard3.utils;
import huard3.actions.*;
import java.sql.*;

public class WorkersUpdate extends UpdateDatabase {
	
	public WorkersUpdate (){
		super();
	}
	
	public void insertWorkerToDatabase(Worker worker){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM Workers WHERE englishName=\"" + worker.getEnglishName() +"\"");
			String updateString = "INSERT INTO Workers SET "+
					"englishPre=\"" + worker.getEnglishPre() + "\"" +
					",englishName=\"" + worker.getEnglishName() + "\"" +
					",hebrewPre=\"" + worker.getHebrewPre() + "\"" +
					",hebrewName=\"" + worker.getHebrewName() + "\"" +
					",englishTitle = \"" + worker.getEnglishTitle() + "\"" +
					",hebrewTitle =\"" + worker.getHebrewTitle() + "\"" +
					",phone = \"" + worker.getPhone() + "\"" +
					",deskId = \"" + worker.getDeskId() + "\";";
			statement.executeUpdate(updateString);					
		}
		catch (SQLException e){	
			System.out.println(e);		
		}
	}

}
