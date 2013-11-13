package huard3.utils;
import java.sql.*;


public class AuthorizationTableHandler extends UpdateDatabase{
	public AuthorizationTableHandler(){
		super();
	}
	
	
	public boolean isAuthorized(String mailAddress, String password){
		try {
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM AuthorizedOutsideHuji WHERE " +
			   				"mailAddress=\""+mailAddress+"\" AND password=\""+password+"\";");
				return resultSet.next();			   				
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}		
	}
	
	public boolean isAuthorityIp(String ip){
		try {
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM AuthorityIp WHERE ip=\""+
						ip+"\";");
				return resultSet.next();			   				
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}		
	}
	
	public void insertUserToAuthorizedOutsideHujiTable(String mailAddress, String password){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("INSERT AuthorizedOutsideHuji SET mailAddress=\""+mailAddress+"\", password=\""+password+"\";");
		}
		catch (SQLException e){
			System.out.println(e);
		}
		
	}

}
