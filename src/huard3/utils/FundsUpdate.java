package huard3.utils;

import huard3.actions.Fund;

import java.sql.*;




public class FundsUpdate extends UpdateDatabase{
	
	public FundsUpdate(){
		super();
	}
	
	 public boolean updateFundDetailsInDatabase(Fund fund, boolean aNewFund){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();
    		String updateString = getFullFundUpdateString(fund, aNewFund);
    		System.out.println("FundsUpdate.updateFundDetailsInDatabase(): updateString: "+updateString);
    		updateStatement.executeUpdate(updateString);
    		return true;    		
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return false;
    	}
    }
    
    public String getFullFundUpdateString(Fund fund,boolean aNewFund){
    	if (!aNewFund){
    		String updateString="UPDATE Funds SET fundNum=\""+fund.getFundNum()+
    		"\",shortName=\""+fund.getShortName()+
    		"\",fullName=\""+fund.getFullName()+
    		//"\",hasForms=\""+(fund.isHasForms() ? 1:0)+
    		"\",webAddress=\""+fund.getWebAddress()+
    		"\",phoneNum=\""+fund.getPhoneNum()+
    		"\",fax=\""+fund.getFax()+
    		"\",contact=\""+fund.getContact()+
    		"\",mailAddress=\""+fund.getMailAddress()+
    		"\",docOwner=\""+fund.getDocOwner()+
    		"\",deskId=\""+fund.getDeskId()+
			"\",financialReporter=\""+fund.getFinancialReporter()+
			"\",budgetOfficer=\""+fund.getBudgetOfficer()+
//			"\",hasWebsite=\""+(fund.isHasWebsite() ? 1:0)+
			"\",html=\""+fund.getHtml()+
			"\",restricted="+(fund.isRestricted() ? 1:0)+
			",hasLocalPage="+(fund.isHasLocalPage() ? 1:0)+
    		" WHERE fundNum=\""+fund.getFundNum()+"\";";
    		return updateString;
    	}
    	else{
    		String updateString = "INSERT INTO Funds SET fundNum=("+fund.getFundNum()+
    		"),shortName=(\""+fund.getShortName()+
    		"\"),fullName=(\""+fund.getFullName()+
    		//"\"),hasForms=("+(fund.isHasForms() ? 1:0)+
    		"\"),webAddress=(\""+fund.getWebAddress()+
    		"\"),phoneNum=(\""+fund.getPhoneNum()+
    		"\"),fax=(\""+fund.getFax()+
    		"\"),contact=(\""+fund.getContact()+
    		"\"),mailAddress=(\""+fund.getMailAddress()+
    		"\"),docOwner=(\""+fund.getDocOwner()+
    		"\"),deskId=(\""+fund.getDeskId()+
			"\"),financialReporter=(\""+fund.getFinancialReporter()+
			"\"),budgetOfficer=(\""+fund.getBudgetOfficer()+
//			"\"),hasWebsite=(\""+(fund.isHasWebsite() ? 1:0)+
			"\"),html=(\""+fund.getHtml()+
			"\"),restricted=("+(fund.isRestricted() ? 1:0)+
			"),hasLocalPage=("+(fund.isHasLocalPage() ? 1:0)+
			");";
    		return updateString;
    	}
    }
    
    public boolean insertFundKeywordsIntoDatabase(Fund fund, String[] keywords){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();
    		
   	    	updateStatement.executeUpdate("DELETE FROM FundsKeywords WHERE fundNum="+fund.getFundNum()+";");
    	    for(int i=0;i<keywords.length;i++){
    	    	
    	    	String updateString = "INSERT INTO FundsKeywords (fundNum,keyword) VALUES ('"+fund.getFundNum()+"','"+keywords[i]+"');" ;
    	    	updateStatement.executeUpdate( updateString );
    	    }
    	    return true;
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return false;
    	}
    }
    
    public void deleteFundByFundNum(String fundNum){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();
   	    	updateStatement.executeUpdate("DELETE FROM Funds WHERE fundNum=\""+fundNum+"\";");
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		
    	}
    }
    
    public void cleanHasInfoPagesField(){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    		statement.executeUpdate("UPDATE Funds SET hasAliveInfoPages=0");
    	}
    	catch (SQLException e){
    		System.out.println(e);
    	}
    }
    
    
    public void markFundsThatHasInfoPages(int fundNum){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    		ResultSet resultSet = statement.executeQuery("SELECT ardNum FROM InfoPages WHERE fundNum="+fundNum+" AND ardNum<10000");
    		if (resultSet.next()) statement.executeUpdate("UPDATE Funds SET hasAliveInfoPages=1 WHERE fundNum="+fundNum+";");
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
    }
}
