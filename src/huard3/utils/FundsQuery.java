package huard3.utils;
import huard3.actions.Fund;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class FundsQuery extends PageQuery{
	
	public FundsQuery(){
		super();
	}
	
	public int getFirstFreeFundNum(){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    	    ResultSet resultSet = statement.executeQuery("SELECT fundNum FROM Funds ORDER BY fundNum;");
    	    int freeFundNum=1;
    	    while (resultSet.next()){
    	    	if (freeFundNum!=resultSet.getInt("fundNum"))
    	    	 	if( ! ProtectRecordsInUse.getProtector().isFundBusy(freeFundNum))    	    		    	            
    	            	return freeFundNum;
    	            else freeFundNum=resultSet.getInt("fundNum");    	            	
    	    	
    	  		freeFundNum++;
    	    	
    	    }
    	    return freeFundNum;
    	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return -1;
    	}
    }
    
    public Fund[] getFundsSortedByFullName(){
    	try{
    		
    		Statement statement = getCurrentConnection().createStatement();
     		ResultSet resultSet = statement.executeQuery("SELECT * FROM Funds ORDER BY fullName;");
            return moveFromResultSetToFund(resultSet);     		
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying Funds sorted by Fund Name: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds;
    	}
    }
    
    public String [] getFundsShortNamesSortedByShortName(){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
     		ResultSet resultSet = statement.executeQuery("SELECT shortName FROM Funds ORDER BY shortName;");
            List fundsShortNamesList = new ArrayList();
            while (resultSet.next()){
            	fundsShortNamesList.add(resultSet.getString("shortName"));
            }
            String [] fundsShortNamesArray = new String[fundsShortNamesList.size()];
            for (int i=0; i<fundsShortNamesArray.length; i++){
            	fundsShortNamesArray[i]=(String)fundsShortNamesList.get(i);
            }
            return fundsShortNamesArray;
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying FundsShortNames sorted by shortName: " + e);
    		String[] noFunds = new String[1];
    		noFunds[0]= "No Funds";
    		return noFunds; 
    	}
    }
    	
    
    public Fund[] getFundsByDeskIdSortedByFullName(String deskId){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    		String queryString;
    		if (deskId!=null && ! deskId.equals("MOP")) queryString = "SELECT * FROM Funds WHERE deskId=\""+deskId+"\""+" ORDER BY fullName;";
     		else queryString = "SELECT * FROM Funds ORDER BY fullName;";
        	
        	ResultSet resultSet = statement.executeQuery(queryString);
            return moveFromResultSetToFund(resultSet);     		
    		
       	}
    	catch (SQLException e){
    		System.out.println("Exception while querying Funds sorted by Fund Name: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds;
    	}
    }
    public Fund[] getFundsSortedByFundNum(){
    	try{
    		
    		Statement statement = getCurrentConnection().createStatement();
     		ResultSet resultSet = statement.executeQuery("SELECT * FROM Funds ORDER BY fundNum;");
            return moveFromResultSetToFund(resultSet);     		
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds;
    	}
    }
    
    
    public Fund getFundByFundNum(String fundNum){
    	try{
            Statement statement = getCurrentConnection().createStatement();
            String fundQuery="SELECT * FROM Funds WHERE fundNum=\""+fundNum+"\"";
     		ResultSet resultSet = statement.executeQuery(fundQuery);
            return moveResultSetAndKeywordsToFund(resultSet)[0];     		
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds[0];
    	}
    }
    
    public Fund getFundByShortName(String shortName){
    	try{
            Statement statement = getCurrentConnection().createStatement();
            String fundQuery="SELECT * FROM Funds WHERE shortName=\""+shortName+"\"";
     		ResultSet resultSet = statement.executeQuery(fundQuery);
            return moveFromResultSetToFund(resultSet)[0];     		
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds[0];
    	}
    }
    
    
    
    public Fund[] moveResultSetAndKeywordsToFund(ResultSet resultSet){
    	Fund [] funds = moveFromResultSetToFund(resultSet);
    	for (int i=0; i<funds.length; i++) funds[i].setKeywords(""+funds[i].getFundNum());
    	return funds;
    }
    	
    
    
    public Fund[] moveFromResultSetToFund(ResultSet resultSet){
   		try{	   		      
   		      List fundsList = new ArrayList();
   		      while(resultSet.next()){
   		      	 Fund fund = new Fund();
   		      	 fund.setFundNum(resultSet.getInt("fundNum"));
                 fund.setShortName(resultSet.getString("shortName"));
                 fund.setFullName(resultSet.getString("fullName"));
                 fund.setKsfNum(resultSet.getInt("ksfNum"));
                 fund.setParentFund(resultSet.getString("parentFund"));
                 //fund.setHasForms(resultSet.getBoolean("hasForms"));
                 fund.setWebAddress(resultSet.getString("webAddress"));
                 fund.setPhoneNum(resultSet.getString("phoneNum"));
                 fund.setFax(resultSet.getString("fax"));
                 fund.setContact(resultSet.getString("contact"));
                 fund.setMailAddress(resultSet.getString("mailAddress"));
                 fund.setDocOwner(resultSet.getString("docOwner"));
                 fund.setDeskId(resultSet.getString("deskId"));
                 fund.setFinancialReporter(resultSet.getString("financialReporter"));
				 fund.setBudgetOfficer(resultSet.getString("budgetOfficer"));
				 fund.setRestricted(resultSet.getBoolean("restricted"));
				 fund.setHasLocalPage(resultSet.getBoolean("hasLocalPage"));
				 fund.setHtml(resultSet.getString("html"));
				 fund.setPubDate(resultSet.getLong("pubDate"));
				 fund.setHasAliveInfoPages(resultSet.getBoolean("hasAliveInfoPages"));
   	             fundsList.add(fund); 	         
   		      }
   		      
          Fund[] funds = new Fund[fundsList.size()>0 ? fundsList.size() : 1];
          funds[0] = new Fund();
          for (int i=0; i<fundsList.size(); i++) funds[i] = (Fund)fundsList.get(i);
          	
          return funds; 
   		}
   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
            Fund[] funds = new Fund[0];
            funds[0] = new Fund();
            return funds;
        }
   				
   	}
   	
   		  
   	
    public String getFundsKeywordsByFundNum(String fundNum){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT * FROM FundsKeywords WHERE ardNum=\""+fundNum+"\";");
        	StringBuffer keywordsSB = new StringBuffer();
         	while(resultSet.next()){
        		 keywordsSB.append(resultSet.getString("keyword")+",");
        	}
        	keywordsSB.delete(keywordsSB.length()-1,keywordsSB.length());
        	return keywordsSB.toString();
        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return "No Keywords";
    	}
    }
    
	public String[] getFundsInfoPagesArdNums (String fundNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT ardNum FROM InfoPages WHERE fundNum="+fundNum+" ORDER BY ardNum;");
			List ardNumsList = new ArrayList();
			while (resultSet.next()){
				ardNumsList.add(new Integer(resultSet.getInt("ardNum")));
			}
			String [] ardNumsArray = new String[ardNumsList.size()];
			for (int i=0; i<ardNumsArray.length;i++){
				ardNumsArray[i] = ""+((Integer)ardNumsList.get(i)).intValue();
			}
			return ardNumsArray;			
		}
		catch(SQLException e){
			System.out.println(e);
			return new String[0];
		}
	}
	
	public Fund[] getAllFunds(){
    	try{
            Statement statement = getCurrentConnection().createStatement();
            String fundQuery="SELECT * FROM Funds;";
     		ResultSet resultSet = statement.executeQuery(fundQuery);
            return moveResultSetAndKeywordsToFund(resultSet);     		
      	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		Fund [] funds = new Fund[0];
    		funds[0] =new Fund();
    		return funds;
    	}
    }
    
    
 

}
