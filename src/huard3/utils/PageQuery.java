package huard3.utils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;



public class PageQuery extends QueryDatabase {

	public PageQuery (){
		super();
	}

	public int getRowsCount(String tableName){
        try{
        	Statement statement = getCurrentConnection().createStatement();
  	        String query = "SELECT COUNT(*) FROM "+tableName;
   	        ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return (resultSet.getInt("COUNT(*)"));
        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return 0;
        }


   	}

   	public String[] getAllDeskIds(){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
  	        String deskIdsQuery = "SELECT * FROM Desks";
   	        ResultSet resultSet = statement.executeQuery(deskIdsQuery);
   			String [] deskIdsArray = new String [getRowsCount("Desks")];
   			int i=0;
   			while(resultSet.next()){
   				deskIdsArray[i] = resultSet.getString("deskId");
      			i++;
   			}
   			return deskIdsArray;
   		}
   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		String[] errStr = new String[0];
    		errStr[0]="Problem";
    		return errStr;
        }

   	}

   	public String[] getAllUsersFirstLetterUpperCased(){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
  	        ResultSet resultSet = statement.executeQuery("SELECT username FROM Users;");
   			List usersList = new ArrayList();
   			while(resultSet.next()){
   				 usersList.add(resultSet.getString("username"));
      		}
   			String [] usersArray = new String [usersList.size()];
   			for (int i=0; i< usersArray.length; i++){
   				usersArray[i] = (String)usersList.get(i);
   				usersArray[i] = usersArray[i].toUpperCase().substring(0,1) + usersArray[i].substring(1);
   			}
   			return usersArray;
   		}
   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		String[] errStr = new String[0];
    		errStr[0]="Problem";
    		return errStr;
        }

   	}

   	public boolean isUserAuthorized(String username, String password){
   	try{
     	Statement statement = getCurrentConnection().createStatement();
  	    ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
  	    while (resultSet.next()){
  	    	if ((resultSet.getString("username").equals(username))&&
   	    	       (resultSet.getString("password").equals(password))) return true;
  	    }
  	    return false;
   	}
   	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return false;
    }
   }

   public String getUsersDeskIdByUsername(String username){
   	try{
     	Statement statement = getCurrentConnection().createStatement();
  	    ResultSet resultSet = statement.executeQuery("SELECT deskId FROM Users WHERE username=\""+username+"\";");
  	    resultSet.next();
  	    return resultSet.getString("deskId");
   	}
   	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return "";
    }
   }

   public int getFirstFreeArdNum(String tableName){
		   try{
			   Statement statement = getCurrentConnection().createStatement();
			   ResultSet resultSet = statement.executeQuery("SELECT ardNum FROM "+tableName+" WHERE ardNum<10000 ORDER BY ardNum;");
			   int freeArdNum=100;

			   while (resultSet.next()){
				   if (freeArdNum!=resultSet.getInt("ardNum")){
					   if (!ProtectRecordsInUse.getProtector().isInfoPageBusy(freeArdNum)) return freeArdNum;
					   else {
					   //	resultSet.previous();
						   freeArdNum=resultSet.getInt("ardNum")+1;
					   }
				   }
				   else freeArdNum++;

			   }
			   return freeArdNum;
		   }
		   catch (SQLException e){
			   System.out.println("Exception while querying data: " + e);
			   return -1;
		   }
	   }



	public int getFirstOldsFreeArdNum(String tableName){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT ardNum FROM "+tableName+" WHERE ardNum>=10000 ORDER BY ardNum;");
			int freeArdNum=10000;
    		while (resultSet.next()){
				if (freeArdNum!=resultSet.getInt("ardNum")){
					if (!ProtectRecordsInUse.getProtector().isInfoPageBusy(freeArdNum)) return freeArdNum;
					else {
						freeArdNum=resultSet.getInt("ardNum")+1;
					}
				}
				else freeArdNum++;
    		}
			return freeArdNum;
		}
		catch (SQLException e){
			System.out.println("Exception while querying data: " + e);
			return -1;
		}
	}




}
