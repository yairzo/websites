package huard3.utils;
import huard3.actions.*;
import java.sql.*;

public class InternalUsePubPagesQuery extends PubPagesQuery{

	public InternalUsePubPagesQuery(){
		super();
	}

	public PubPage [] getPubPagesSortedByArdNum(boolean message){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM PubPages WHERE message="+(message==false ? 0 :1 )+" ORDER BY onSite DESC, isDeleted ASC, ardNum;";
			ResultSet resultSet = statement.executeQuery(query);
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			PubPage [] pubPages = new PubPage [0];
			pubPages[0]=new PubPage();
			return pubPages;
		}

	}

	public PubPage [] getOldPubPagesSortedByArdNum(boolean message){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM PubPages WHERE ardNum>=10000 AND message="+(message==false ? 0 :1 )+"  ORDER BY ardNum;";
			ResultSet resultSet = statement.executeQuery(query);
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			PubPage [] pubPages = new PubPage [0];
			pubPages[0]=new PubPage();
			return pubPages;
		}

	}

	public PubPage [] getOldPubPagesSortedByTitle(boolean message){
			try{
				Statement statement = getCurrentConnection().createStatement();
				String query = "SELECT * FROM PubPages WHERE ardNum>=10000 AND message="+(message==false ? 0 :1 )+"  ORDER BY title;";
				ResultSet resultSet = statement.executeQuery(query);
				return moveFromResultSetToPubPage(resultSet);
			}
			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				PubPage [] pubPages = new PubPage [0];
				pubPages[0]=new PubPage();
				return pubPages;
			}

		}

	public PubPage [] getPubPagesSortedByTitle(boolean message){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM PubPages WHERE ardNum<10000 AND message="+(message==false ? 0 :1 )+"  ORDER BY title;";
			ResultSet resultSet = statement.executeQuery(query);
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			PubPage [] pubPages = new PubPage [0];
			pubPages[0]=new PubPage();
			return pubPages;
		}

	}



}
