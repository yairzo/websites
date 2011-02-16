package huard.iws.model;
import java.sql.*;

public class ManagedConnection {
	private Connection connection;
	private int connectionCounter;
	private String server;
	private String database;
	private String priviliges;


	public ManagedConnection(Connection connection, String server, String database, String privileges){
		this.connection = connection;
		this.server = server;
		this.database = database;
		this.priviliges = privileges;
		connectionCounter = 0;
	}

	public String getDatabase() {
		return database;
	}

	public void increaseConnectionCounterByOne(){
		this.connectionCounter++;
	}

	public int getConnectionCounter() {
		return connectionCounter;
	}

	public void setConnectionCounter(int connectionCounter) {
		this.connectionCounter = connectionCounter;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getPriviliges() {
		return priviliges;
	}

	public boolean isConnectionClosed(){
		try{
			Statement statement = connection.createStatement();
			statement.executeQuery("SELECT 1;");
			return false;
		}
		catch (SQLException e){
			System.out.println("Connection seems to be closed: "+e);
			return true;
		}
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}


}
