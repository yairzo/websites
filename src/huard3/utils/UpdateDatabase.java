package huard3.utils;
import java.sql.Connection;



public class UpdateDatabase {
	
	
	
	public UpdateDatabase(){
		
	}
	
	public Connection getCurrentConnection(){
		return ConnectionSupplyer.getConnectionSupplyer().getCurrentUpdateConnection();
	}
	
	
	/*protected static int connectionCounter;
	protected final String DB_USERNAME="yair", DB_PASSWORD="ohmerd";
    
    public UpdateDatabase(){
    	try{		
			initDatabase();
		}
		catch (ClassNotFoundException cnfe){
        	System.out.println("Excption while initializing the Database: Driver was not found"+cnfe);
        }
    }
    
    
    public void initDatabase() throws ClassNotFoundException{
                Class.forName("org.gjt.mm.mysql.Driver");
    }
    
    public Connection getCurrentConnection(){
		try{
			if ((currentConnection == null) || (currentConnection.isClosed())){
				String url="jdbc:mysql://"+Utils.getServerName()+":3306/"+Utils.getDatabaseName();
				currentConnection = DriverManager.getConnection(url,DB_USERNAME,DB_PASSWORD);
				connectionCounter = 0;
			}
			if (connectionCounter>100){
			   currentConnection.close();
			   String url= "jdbc:mysql://"+Utils.getServerName()+":3306/"+Utils.getDatabaseName();
               currentConnection = DriverManager.getConnection(url,DB_USERNAME,DB_PASSWORD);
			}
		}
        catch (SQLException e){
            System.out.println("Exception while querying data: " + e);
        } 
        connectionCounter++;
        return currentConnection;
	}*/
	
	
	
	

}
