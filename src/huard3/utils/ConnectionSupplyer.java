package huard3.utils;
import java.sql.*;

public class ConnectionSupplyer {
	private static Connection currentQueryConnection, currentUpdateConnection;
	private static int queryConnectionCounter, updateConnectionCounter;
	private static final String QUERY_DB_USERNAME="mop", QUERY_DB_PASSWORD="hilazon48";
	private static final String UPDATE_DB_USERNAME="mop", UPDATE_DB_PASSWORD="hilazon48";
	private static ConnectionSupplyer connectionSupplyer=null;

	private ConnectionSupplyer(){
		try{
			initDatabase();
			queryConnectionCounter=0;
			updateConnectionCounter=0;
		}
		catch (ClassNotFoundException cnfe){
			System.out.println("Excption while initializing the Database: Driver was not found"+cnfe);
		}
	}

	private void initDatabase() throws ClassNotFoundException{
		Class.forName("org.gjt.mm.mysql.Driver");
	}


	public static synchronized ConnectionSupplyer getConnectionSupplyer(){
		if (connectionSupplyer==null) connectionSupplyer = new ConnectionSupplyer();
		return connectionSupplyer;

	}

	public synchronized Connection getCurrentQueryConnection(){
		try{
			if ((currentQueryConnection == null) || (isConnectionClosed(currentQueryConnection))){
				String url="jdbc:mysql://localhost:3306/"+Utils.getDatabaseName()+"?characterEncoding=UTF-8&characterSetResults=UTF-8";
				System.out.println(url);
				currentQueryConnection = DriverManager.getConnection(url,QUERY_DB_USERNAME,QUERY_DB_PASSWORD);
				queryConnectionCounter = 0;
			}
			if (queryConnectionCounter>200){
			   currentQueryConnection.close();
			   String url= "jdbc:mysql://localhost:3306/"+Utils.getDatabaseName()+"?characterEncoding=UTF-8&characterSetResults=UTF-8";
			   currentQueryConnection = DriverManager.getConnection(url,QUERY_DB_USERNAME,QUERY_DB_PASSWORD);
			   queryConnectionCounter = 0;
			}
		}
		catch (SQLException e){
			System.out.println("Exception while querying data: " + e);
		}
		queryConnectionCounter ++;
		return currentQueryConnection;
	}

	public synchronized Connection getCurrentUpdateConnection(){
		try{
			if ((currentUpdateConnection == null) || (isConnectionClosed(currentUpdateConnection))){
				String url="jdbc:mysql://localhost:3306/"+Utils.getDatabaseName()+"?characterEncoding=UTF-8&characterSetResults=UTF-8";
				currentUpdateConnection = DriverManager.getConnection(url,UPDATE_DB_USERNAME,UPDATE_DB_PASSWORD);
				updateConnectionCounter = 0;
			}
			if (updateConnectionCounter>200){
			   currentUpdateConnection.close();
			   String url= "jdbc:mysql://localhost:3306/"+Utils.getDatabaseName()+"?characterEncoding=UTF-8&characterSetResults=UTF-8";
			   currentUpdateConnection = DriverManager.getConnection(url,UPDATE_DB_USERNAME,UPDATE_DB_PASSWORD);
			   updateConnectionCounter = 0;
			}
		}
		catch (SQLException e){
			System.out.println("Exception while querying data: " + e);
		}
		updateConnectionCounter ++;
		//System.out.println("Update Connection counter: "+updateConnectionCounter);
		return currentUpdateConnection;
	}



	public boolean isConnectionClosed(Connection currentConnection){
		try{
			Statement statement = currentConnection.createStatement();
			statement.executeQuery("SELECT 1");
			return false;
		}
		catch (SQLException e){
			System.out.println("Connection seems to be closed: "+e);
			return true;
		}
	}

}
