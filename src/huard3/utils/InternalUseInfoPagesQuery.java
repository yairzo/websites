package huard3.utils;
import huard3.actions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class InternalUseInfoPagesQuery extends InfoPagesQuery{

	public InternalUseInfoPagesQuery(){
		super();
	}

    public InfoPage [] getInfoPagesByDesk(String deskId, String orderBy){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    	    String query = "SELECT * FROM InfoPages "+ (deskId.equals("ALL") ? "" : " WHERE deskId=\""+deskId+"\"")+" ORDER BY isDeleted ASC, "+orderBy;
    	    ResultSet resultSet = statement.executeQuery(query);
    	    return moveFromResultSetToInfoPage(resultSet);
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		InfoPage [] infoPages = new InfoPage [0];
    		infoPages[0]=new InfoPage();
    		return infoPages;
    	}
    }

	public InfoPage [] getOldInfoPagesSortedByArdNum(String deskId){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM InfoPages WHERE ArdNum>=10000"+ (deskId.equals("ALL") ? "" : " AND deskId=\""+deskId+"\"")+" ORDER BY ardNum";
			ResultSet resultSet = statement.executeQuery(query);
			return moveFromResultSetToInfoPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			InfoPage [] infoPages = new InfoPage [0];
			infoPages[0]=new InfoPage();
			return infoPages;
		}
	}

    public InfoPage [] getInfoPagesSortedByTitle(String deskId){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    	    String query = "SELECT * FROM InfoPages WHERE ardNum<10000" + (deskId.equals("ALL") ? "" : " AND deskId=\""+deskId+"\"")+" ORDER BY title";
    	    ResultSet resultSet = statement.executeQuery(query);
    	    return moveFromResultSetToInfoPage(resultSet);
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		InfoPage [] infoPages = new InfoPage [0];
    		infoPages[0] = new InfoPage();
    		return infoPages;
    	}
    }

	public InfoPage [] getOldInfoPagesSortedByTitle(String deskId){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM InfoPages WHERE ardNum>=10000" + (deskId.equals("ALL") ? "" : " AND deskId=\""+deskId+"\"")+" ORDER BY title";
			ResultSet resultSet = statement.executeQuery(query);
			return moveFromResultSetToInfoPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			InfoPage [] infoPages = new InfoPage [0];
			infoPages[0] = new InfoPage();
			return infoPages;
		}
	}






   	public int getNumOfResearchField(String researchField){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
            String researchFieldNumQuery="SELECT * FROM ResearchFields WHERE researchField=\""+researchField+"\"";
     		ResultSet resultSet = statement.executeQuery(researchFieldNumQuery);
     		return resultSet.getInt("num");
    	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    	    return 0;
    	}
    }

    public String getResearchFieldShortNameByNum(int num){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
            String researchFieldQuery="SELECT * FROM ResearchFields WHERE num=\""+num+"\"";
     		ResultSet resultSet = statement.executeQuery(researchFieldQuery);
     		resultSet.next();
     		return resultSet.getString("researchFieldShort");
    	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    	    return "";
    	}
    }



    	public String[] getAllDocTypes(){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
  	        String docTypesQuery = "SELECT * FROM DocTypes";
   	        ResultSet resultSet = statement.executeQuery(docTypesQuery);
   			String [] docTypesArray = new String [getRowsCount("DocTypes")];
   			int i=0;
   			while(resultSet.next()){
   				docTypesArray[i] = resultSet.getString("docType");
      			i++;
   			}
   			return docTypesArray;
   		}
   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		String[] errStr = new String[0];
    		errStr[0]="Problem";
    		return errStr;
        }

   	}





}
