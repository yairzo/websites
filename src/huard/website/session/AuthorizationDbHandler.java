package huard.website.session;
import huard.website.util.*;
import huard.website.baseDb.*;
import java.sql.*;


public class AuthorizationDbHandler {
	public AuthorizationDbHandler(){
		super();
	}


	public boolean isAuthorized(String mailAddress, String password){
		try {
				Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
				String query = "SELECT * FROM AuthorizedOutsideHuji WHERE " +
   				"mailAddress=\""+mailAddress+"\" AND password=\""+password+"\";";
				System.out.println(query);
				ResultSet resultSet = statement.executeQuery(query);
				return resultSet.next();
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

	public boolean isAuthorized(String md5){
		try {
				Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
				String query = "SELECT * FROM AuthorizedMD5 WHERE " +
   				" md5 = '"+md5 + "';" ;
				System.out.println(query);
				ResultSet resultSet = statement.executeQuery(query);
				return resultSet.next();
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

	public boolean isNonHujiAuthorized(String mailAddress){
		try {
				Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
				String query = "SELECT * FROM InfoPagesMailURLs WHERE " +
   				"mailAddress=\""+mailAddress+"\" UNION SELECT * FROM PubPagesMailURLs WHERE " +
   				"mailAddress=\""+mailAddress+"\" ;";
				System.out.println(query);
				ResultSet resultSet = statement.executeQuery(query);
				return resultSet.next();
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

	public String getPasswordByMailAddress(String mailAddress){
		try {
			Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM AuthorizedOutsideHuji WHERE " +
		   				"mailAddress=\""+mailAddress+"\";");
			if (resultSet.next()){
				return resultSet.getString("password");
			}
			else return "";
		}
		catch (SQLException e){
			System.out.println(e);
			return "";
		}
	}

	public void insertUserToAuthorizedOutsideHujiTable(String mailAddress, String password){
		try{
			Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
			statement.executeUpdate("INSERT IGNORE AuthorizedOutsideHuji SET mailAddress=\""+mailAddress+"\", password=\""+password+"\";");
		}
		catch (SQLException e){
			System.out.println(e);
		}

	}

	public boolean isMopIp( String ip) {
		try{
			Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"SELECT").createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM AuthorityIp WHERE ip='"+ip+"';");
			rs.next();
			return (rs.getInt(1) > 0);
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

}
