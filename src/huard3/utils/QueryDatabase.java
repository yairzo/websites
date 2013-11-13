package huard3.utils;
import java.sql.Connection;
import java.sql.*;




public class QueryDatabase {
	
	
	//protected static int connectionCounter;
	//protected final String DB_USERNAME="annonymous", DB_PASSWORD="shumklum";
	
	
	/*public QueryDatabase(){
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
       
	
	
	public synchronized Connection getCurrentConnection(String whoCalled){
		try{
			if ((currentConnection == null) || (currentConnection.isClosed())){
				String url="jdbc:mysql://"+Utils.getServerName()+":3306/"+Utils.getDatabaseName();
				currentConnection = DriverManager.getConnection(url,DB_USERNAME,DB_PASSWORD);
				connectionCounter = 0;
			}
			if (connectionCounter>200){
			   currentConnection.close();
			   String url= "jdbc:mysql://"+Utils.getServerName()+":3306/"+Utils.getDatabaseName();
			   currentConnection = DriverManager.getConnection(url,DB_USERNAME,DB_PASSWORD);
			   connectionCounter = 0;
			}
		}
		catch (SQLException e){
			System.out.println("Exception while querying data: " + e);
		} 
        connectionCounter ++;
        return currentConnection;
	}*/
	
	public QueryDatabase(){
		
	}
	
	public Connection getCurrentConnection(){
		return ConnectionSupplyer.getConnectionSupplyer().getCurrentQueryConnection();
	}
	
	public boolean isRecoredExistsInTable(String ardNum, String table){
			try{
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT ardNum FROM "+table+" WHERE ardNum="+ardNum+";");
				return resultSet.next();
			}
			catch(SQLException e){
				System.out.println(e);
				return true;
			}
		}
	
	
            
                      
        
   
		
}
