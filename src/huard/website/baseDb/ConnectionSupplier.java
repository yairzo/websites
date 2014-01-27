package huard.website.baseDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionSupplier {
	private Map<String, List<ManagedConnection>> managedConnectionsListsMap;
	private static final String DB_USERNAME = "mop", DB_PASSWORD = "hilazon48";
	// 3 types of connections * minimum of 8 for each type;
	private static final int MIN_CONNECTIONS_LIST_SIZE = 8;
	private static ConnectionSupplier connectionSupplier = null;
	private static final int MAX_NUM_RETRIES = 3;
	//private static final Logger logger = Logger
	//		.getLogger(ConnectionSupplier.class);

	
	
	private ConnectionSupplier() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			managedConnectionsListsMap = new HashMap<String, List<ManagedConnection>>();
			managedConnectionsListsMap.put("HUARD",
					new ArrayList<ManagedConnection>());
			managedConnectionsListsMap.put("MENUS",
					new ArrayList<ManagedConnection>());
			managedConnectionsListsMap.put("MAILER",
					new ArrayList<ManagedConnection>());
			managedConnectionsListsMap.put("LOGGER",
					new ArrayList<ManagedConnection>());

		} catch (ClassNotFoundException cnfe) {
			System.out
					.println("Excption while initializing the Database: Driver was not found"
							+ cnfe);
		}
	}

	public static synchronized ConnectionSupplier getConnectionSupplier() {
		// System.out.println("huard.utils.ConnectionSupplier.getConnectionSupplier(): worked");
		if (connectionSupplier == null)
			connectionSupplier = new ConnectionSupplier();
		return connectionSupplier;
	}

	public synchronized ManagedConnection getConnection(String database,
			String privileges) {
		int numRetries = 0;
		long uniqueId = System.currentTimeMillis();
		return getConnection(database, privileges, numRetries, uniqueId);
	}

	public synchronized ManagedConnection getConnection(String database,
			String privileges, int numRetries, long uniqueId) {
		// logger.info(uniqueId + ": Asked for a connection for database " +
		// database);
		if (numRetries > MAX_NUM_RETRIES) {
			// logger.info(uniqueId +
			// ": Num retries exceeded maximum. creating new connection and returning it");
			return addManagedConnection(database, "UPDATE");
		}
		numRetries++;
		List<ManagedConnection> managedConnectionsList = managedConnectionsListsMap
				.get(database);
		ManagedConnection managedConnection = null;
		try {
			if (managedConnectionsList.size() < MIN_CONNECTIONS_LIST_SIZE) {
				// logger.info(uniqueId +
				// ": Lists are not full will return a new connection");
				synchronized (managedConnectionsList) {
					return addManagedConnection(database, "UPDATE");
				}
			}
			synchronized (managedConnectionsList) {
				// logger.info(uniqueId +
				// ": take a random connection from the list");
				int index = (int) (Math.random() * managedConnectionsList
						.size());
				managedConnection = managedConnectionsList.remove(index);
			}
			// Sanity: shoudn't happen
			if (!managedConnection.getDatabase().equals(database)) {
				// logger.info(uniqueId +
				// ": A connection to the wrong database came up. let's try again");
				return getConnection(database, "UPDATE", numRetries, uniqueId);
			}

			if (managedConnection.getConnection() == null
					|| managedConnection.getConnection().isClosed()
					|| managedConnection.getConnectionCounter() >= 200
					|| !connectionValid(managedConnection.getConnection())) {
				// logger.info(uniqueId +
				// ": a null/closed/exshousted/non-valid connection came up. let's try again");
				return getConnection(database, "UPDATE", numRetries, uniqueId);
			} else {
				// logger.info(uniqueId +
				// ": connection seems to be good. let's return it");
				managedConnection.increaseConnectionCounterByOne();
				return managedConnection;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			synchronized (managedConnectionsList) {
				managedConnectionsList.remove(managedConnection);
			}
			// logger.info(uniqueId +
			// ": Trying again after exception. removed connection from the list");
			return getConnection(database, "UPDATE", numRetries, uniqueId);
		}
	}

	private ManagedConnection addManagedConnection(String database,
			String privileges) {
		Connection connection = createConnection(database, privileges);
		ManagedConnection managedConnection = new ManagedConnection(connection,
				database, privileges);
		managedConnection.increaseConnectionCounterByOne();
		// managedConnectionsListsMap.get(database).add(managedConnection);
		// We return a connection without archiving it. We trust that the user
		// of the connection
		// will finally archive it
		return managedConnection;
	}
		
	private Connection createConnection(String database, String privileges) {
		try {
			String url = "jdbc:mysql://localhost:3306/" + database
					+ "?characterEncoding=UTF-8&characterSetResults=UTF-8";
			Connection connection = DriverManager.getConnection(url,
					DB_USERNAME, DB_PASSWORD);
			// add the possibility for different username and password by
			// database and privileges
			return connection;
		} catch (SQLException e) {
			System.out
					.println("huard.website.baseDb.ConnectionSupplier.createConnection():"
							+ e);
			return null;
		}
	}	
	
	public boolean connectionValid(Connection connection) throws SQLException {
		boolean success = false;
		Statement statement = connection.createStatement();
		ResultSet r = statement.executeQuery("SELECT 0");
		if (r.next())
			success = true;
		statement.close();
		return success;
	}

	public void archiveConnection(ManagedConnection managedConnection,
			String database) {
		if (managedConnection != null)
			managedConnectionsListsMap.get(database).add(managedConnection);
	}

}