package huard3.utils;
import java.sql.*;

public class MiscellaneousUpdates extends UpdateDatabase {
	
	public MiscellaneousUpdates(){
		super();
	}
	
	
	public void deleteFromMultipleOptionsPatternedPagesIndexTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM MultipleOptionsPatternedPagesIndex;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	public void deleteFromComposedPatternedPagesIndexTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM ComposedPatternedPagesIndex;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	public void insertWordToMultipleOptionsPatternedPagesIndexTable(String word, int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("INSERT MultipleOptionsPatternedPagesIndex SET word=\""+word+"\", ardNum="+ardNum+";");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	public void insertWordToComposedPatternedPagesIndexTable(String word, int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("INSERT ComposedPatternedPagesIndex SET word=\""+word+"\", ardNum="+ardNum+";");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	
		
}
