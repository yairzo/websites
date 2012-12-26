package huard.iws.db;

import huard.iws.model.ManagedConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ArdConnectionSupplier {

	private List<ManagedConnection> managedConnectionsList = new ArrayList<ManagedConnection>();
	private static final String DB_USERNAME="mop", DB_PASSWORD="hilazon48";
	private static ArdConnectionSupplier connectionSupplier=null;

	private ArdConnectionSupplier(){
		try{
			initDatabase();
		}
		catch (ClassNotFoundException cnfe){
			System.out.println("Excption while initializing the Database: Driver was not found"+cnfe);
		}
	}

	private void initDatabase() throws ClassNotFoundException{
		Class.forName("org.gjt.mm.mysql.Driver");
	}


	public static synchronized ArdConnectionSupplier getConnectionSupplier(){
		if (connectionSupplier==null)
			connectionSupplier = new ArdConnectionSupplier();
		return connectionSupplier;
	}


	public synchronized Connection getConnection(String database, String privileges, String server)  throws SQLException {

		List<ManagedConnection> closedConnections = new ArrayList<ManagedConnection>();
		for (ManagedConnection managedConnection: managedConnectionsList){
			if ( managedConnection.getServer().equals(server) && managedConnection.getDatabase().equals(database) && managedConnection.getPriviliges().equals(privileges)){
				if(managedConnection.getConnection() != null && ! managedConnection.getConnection().isClosed()){
					managedConnection.increaseConnectionCounterByOne();
					if (managedConnection.getConnectionCounter()>=200)
						managedConnectionsList.remove(managedConnection);
					return managedConnection.getConnection();
				}
				else {
					closedConnections.add(managedConnection);
				}
			}
		}
		managedConnectionsList.removeAll(closedConnections);
		Connection connection = createConnection(database, privileges, server);
		ManagedConnection managedConnection = new ManagedConnection(connection, server, database, privileges);
		managedConnection.increaseConnectionCounterByOne();
		managedConnectionsList.add(managedConnection);
		return managedConnection.getConnection();
	}

	private Connection createConnection(String database, String privileges, String server){
		try{
			String url="jdbc:mysql://"+server+":3306/"+database+"?characterEncoding=UTF-8&characterSetResults=UTF-8";
			System.out.println(url);
			Connection connection = DriverManager.getConnection(url,DB_USERNAME,DB_PASSWORD);
			// add the possibility for different username and password by database and privileges
			return connection;
		}
		catch (SQLException e){
			System.out.println("huard.website.baeDb.ConnectionSupplier.createConnection():" + e);
			return null;
		}
	}
}
