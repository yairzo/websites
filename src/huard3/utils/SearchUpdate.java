package huard3.utils;
import java.sql.*;
import java.util.*;

public class SearchUpdate extends UpdateDatabase {
	
	public SearchUpdate(){
		super();
	}

	public void updateOccurancesTable(String word, String table){
		try{
			Statement statement = getCurrentConnection().createStatement();
			int i= statement.executeUpdate("UPDATE "+table+" SET occurances=occurances+1 WHERE word=\""+handleHebrew(word)+"\"");
			if (i==0) {
				statement.executeUpdate("INSERT "+table+" SET word=\""+word+"\", occurances=1 ;");
			}
			
		}
		
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public String handleHebrew(String word){
		return Utils.checkForHebrewCharsAndChangeThemToAscii(word);
	}
	
	public void clearArdPagesWordsOccurancesTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM ArdPagesWordsOccurances");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public void clearSearchResultsWordsOccurancesTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM SearchResultsWordsOccurances");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public void insertBlackListWords(String blackListWords){
		WordsTokenizer wt = new WordsTokenizer(" ");
		List blackList = wt.getSubstringsList(blackListWords);
		try{
			Statement statement = getCurrentConnection().createStatement();
			for (int i=0; i<blackList.size(); i++){
				ResultSet resultSet = statement.executeQuery("SELECT word FROM BlackListWords WHERE word=\""+(String)blackList.get(i)+"\";");
				if (! resultSet.next() ) statement.executeUpdate("INSERT BlackListWords SET word=\""+(String)blackList.get(i)+"\";");
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

}