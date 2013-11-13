package huard3.utils;
import huard3.actions.*;
import java.sql.*;
import java.util.*;

public class WorkersQuery extends QueryDatabase {
	
	public WorkersQuery(){
		super();
	}

	public String [] getDesksIds(){
			try{
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT deskId FROM Desks ORDER BY appearence;");
				List desksShortNamesList = new ArrayList();
				while (resultSet.next()){
					desksShortNamesList.add(resultSet.getString("deskId"));
				}
				String [] desksShortNamesArray = new String[desksShortNamesList.size()];
				for (int i=0; i<desksShortNamesArray.length; i++){
					desksShortNamesArray[i]=(String)desksShortNamesList.get(i);
				}
				return desksShortNamesArray;
			}
			catch (SQLException e){
				System.out.println("Exception while querying DesksIds: " + e);
				String[] noFunds = new String[1];
				noFunds[0]= "No desksIds";
				return noFunds; 
			}
		}
	
	public String [] getDesksFullNameEnglish(){
				try{
					Statement statement = getCurrentConnection().createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT desksFullNameEnglish FROM Desks ORDER BY appearence;");
					List desksFullNameEnglishList = new ArrayList();
					while (resultSet.next()){
						desksFullNameEnglishList.add(resultSet.getString("desksFullNameEnglish"));
					}
					String [] desksFullNameEnglishArray = new String[desksFullNameEnglishList.size()];
					for (int i=0; i<desksFullNameEnglishArray.length; i++){
						desksFullNameEnglishArray[i]=(String)desksFullNameEnglishList.get(i);
					}
					return desksFullNameEnglishArray;
				}
				catch (SQLException e){
					System.out.println("Exception while querying DesksIds: " + e);
					String[] noFunds = new String[1];
					noFunds[0]= "No desksFullNameEnglish";
					return noFunds; 
				}
			}
	
	public String [] getDesksFullNameHebrew(){
					try{
						Statement statement = getCurrentConnection().createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT desksFullNameHebrew FROM Desks ORDER BY appearence;");
						List desksFullNameHebrewList = new ArrayList();
						while (resultSet.next()){
							desksFullNameHebrewList.add(resultSet.getString("desksFullNameHebrew"));
						}
						String [] desksFullNameHebrewArray = new String[desksFullNameHebrewList.size()];
						for (int i=0; i<desksFullNameHebrewArray.length; i++){
							desksFullNameHebrewArray[i]=(String)desksFullNameHebrewList.get(i);
						}
						return desksFullNameHebrewArray;
					}
					catch (SQLException e){
						System.out.println("Exception while querying DesksIds: " + e);
						String[] noFunds = new String[1];
						noFunds[0]= "No desksFullNameHebrew";
						return noFunds; 
					}
				}
	
	
	
	public Worker [] getWorkers(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers ORDER BY englishName;");
			return moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			Worker [] workers = new Worker[1];
			workers[0] = new Worker();
			return workers;
		}
		
				
	}
	
	public Worker [] getWorkersByTitle(String title){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE englishTitle LIKE '%"+title+"%' ORDER BY englishName;");
			return moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			Worker [] workers = new Worker[1];
			workers[0] = new Worker();
			return workers;
		}
	}
	
	public Worker [] getWorkersByDeskId(String deskId){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE (englishTitle NOT LIKE '%Chairman%' AND englishTitle NOT LIKE '%Coordinator%' AND englishTitle NOT LIKE '%Director%' AND englishTitle NOT LIKE '%Assistant%') AND deskId LIKE '%"+ deskId +"%' ORDER BY hebrewName;");
			return moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			Worker [] workers = new Worker[0];
			return workers;
		}
	}
	
	public Worker [] getAllWorkersByDeskId(String deskId){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE deskId=\""+deskId+"\";");
			return moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			Worker [] workers = new Worker[1];
			workers[0] = new Worker();
			return workers;
		}
	}
		
	public Worker [] getManagersByDeskId(String deskId){
				try{
					Statement statement = getCurrentConnection().createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE (englishTitle LIKE '%Chairman%' OR englishTitle LIKE '%Coordinator%' OR englishTitle LIKE '%Director%') AND deskId LIKE '%"+ deskId +"%' ORDER BY hebrewName;");
					return moveFromResultSetToWorkers(resultSet);
				}
				catch(SQLException e){
					System.out.println(e);
					Worker [] workers = new Worker[0];
					return workers;
				}
			}
			
			
			
	public Worker [] getAssistantsByDeskId(String deskId){
					try{
						Statement statement = getCurrentConnection().createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE englishTitle LIKE '%Assistant%' AND deskId LIKE '%"+ deskId +"%' ORDER BY hebrewName;");
						return moveFromResultSetToWorkers(resultSet);
					}
					catch(SQLException e){
						System.out.println(e);
						Worker [] workers = new Worker[0];
						return workers;
					}
				}
		
	public Worker [] getWorkersByDeskIdEnglish(String deskId){
				try{
					Statement statement = getCurrentConnection().createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE (englishTitle NOT LIKE '%Chairman%' AND englishTitle NOT LIKE '%Coordinator%' AND englishTitle NOT LIKE '%Director%' AND englishTitle NOT LIKE '%Assistant%') AND deskId LIKE '%"+ deskId +"%' ORDER BY englishName;");
					return moveFromResultSetToWorkers(resultSet);
				}
				catch(SQLException e){
					System.out.println(e);
					Worker [] workers = new Worker[1];
					workers[0] = new Worker();
					return workers;
				}
			}
	
	
	
	
	public Worker [] getWorkersByPhraseFromTitleByDesk(String phraseFromTitle, String deskId){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE englishTitle LIKE '%"+ phraseFromTitle +"%' AND " +
				"deskId=\"" + deskId + "\" ORDER BY englishName;");
			return moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			Worker [] workers = new Worker[1];
			workers[0] = new Worker();
			return workers;
		}
		
	}
	
	public Worker getWorkerByEnglishName(String englishName){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers WHERE englishName=\"" + englishName +"\";");
			return moveFromResultSetToWorkers(resultSet)[0];
			
		}
		catch(SQLException e){
			System.out.println(e);
			return new Worker();
		}
	}
	
	
	public Worker [] moveFromResultSetToWorkers(ResultSet resultSet){
		List workersList = new ArrayList();
		try{
			while (resultSet.next()){
				Worker worker = new Worker();
				worker.setEnglishPre(resultSet.getString("englishPre"));
				worker.setEnglishName(resultSet.getString("englishName"));
				worker.setHebrewPre(resultSet.getString("hebrewPre"));
				worker.setHebrewName(resultSet.getString("hebrewName"));
				worker.setEnglishTitle(resultSet.getString("englishTitle"));
				worker.setHebrewTitle(resultSet.getString("hebrewTitle"));
				worker.setPhone(resultSet.getString("phone"));
				worker.setDeskId(resultSet.getString("deskId"));
			  	workersList.add(worker);
			}  
		}
		catch (SQLException e){
			System.out.println(e);
		}
		Worker [] workersArray;
		if (workersList.size()>0){
			workersArray = new Worker[workersList.size()];
			for (int i=0; i<workersArray.length; i++){
				workersArray[i] = (Worker)(workersList.get(i));
			}
		}
		else {
			workersArray = new Worker [1];
			workersArray[0] = new Worker();
		}
		/*for(int j=0; j<workersArray.length;j++){
		if ((workersArray[j].getEnglishTitle().indexOf("Coordinator",0)!=-1) || (workersArray[j].getEnglishTitle().indexOf("Chairman",0)!=-1) ||  (workersArray[j].getEnglishTitle().indexOf("Director",0)!=-1)){
		Worker tempWorker = new Worker();
		
		tempWorker.setEnglishPre(workersArray[j].getEnglishPre());
		tempWorker.setEnglishName(workersArray[j].getEnglishName());
		tempWorker.setHebrewPre(workersArray[j].getHebrewPre());
		tempWorker.setHebrewName(workersArray[j].getHebrewName());
		tempWorker.setEnglishTitle(workersArray[j].getEnglishTitle());
		tempWorker.setHebrewTitle(workersArray[j].getHebrewTitle());
		tempWorker.setPhone(workersArray[j].getPhone());
		tempWorker.setDeskId(workersArray[j].getDeskId());
		
		workersArray[j].setEnglishPre(workersArray[0].getEnglishPre());
		workersArray[j].setEnglishName(workersArray[0].getEnglishName());				
		workersArray[j].setHebrewPre(workersArray[0].getHebrewPre());
		workersArray[j].setHebrewName(workersArray[0].getHebrewName());
		workersArray[j].setEnglishTitle(workersArray[0].getEnglishTitle());
		workersArray[j].setHebrewTitle(workersArray[0].getHebrewTitle());
		workersArray[j].setPhone(workersArray[0].getPhone());
		workersArray[j].setDeskId(workersArray[0].getDeskId());
		
		workersArray[0].setEnglishPre(tempWorker.getEnglishPre());
		workersArray[0].setEnglishName(tempWorker.getEnglishName());				
		workersArray[0].setHebrewPre(tempWorker.getHebrewPre());
		workersArray[0].setHebrewName(tempWorker.getHebrewName());
		workersArray[0].setEnglishTitle(tempWorker.getEnglishTitle());
		workersArray[0].setHebrewTitle(tempWorker.getHebrewTitle());
		workersArray[0].setPhone(tempWorker.getPhone());
		workersArray[0].setDeskId(tempWorker.getDeskId());
		
		}
		}*/
		return workersArray;
		
	}
	
	public String[] getWorkersFullNameByUsernameParts(String firstNamePart, String lastNamePart){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT englishName FROM Workers WHERE englishName LIKE '"+firstNamePart+" "+lastNamePart+"%';");
			String [] fullName = new String[2];
			fullName[0]="";
			fullName[1]="";
			if (resultSet.next()){
				String str = resultSet.getString(1);
				fullName[0] = str.substring(0,str.indexOf(" ")).toLowerCase();
				fullName[1] = str.substring(str.indexOf(" ")+1).toLowerCase();
			}
			return fullName;
		}
		catch(SQLException e){
			System.out.println(e);
			String [] str  = new String[2];
			str[0] = "";
			str[1] = "";
			return str;
		}
	}
	
	
}
