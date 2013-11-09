package huard.website.baseDb;
import java.sql.*;

public class ManagedConnection {
	private Connection connection;
	private int connectionCounter;
	private String database;
	private String priviliges;


	public ManagedConnection(Connection connection, String database, String privileges){
		this.connection = connection;
		this.database = database;
		this.priviliges = privileges;
		connectionCounter = 0;
	}

	public ManagedConnection duplicate(){
		ManagedConnection managedConnection = new ManagedConnection(
				this.connection, this.database, this.priviliges);
		return managedConnection;
	}

	public Statement createStatement() throws SQLException{
		return this.connection.createStatement();
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


}
