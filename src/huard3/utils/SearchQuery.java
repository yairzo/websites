package huard3.utils;
import huard3.actions.*;
import java.sql.*;
import java.util.*;


public class SearchQuery extends PageQuery {
	
	public SearchQuery(){
		super();
	}

	public TabledInfoPage[] getTabledInfoPages(){
		try{
			Statement statement  = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT InfoPages.ardNum,title,description FROM TabledInfoPages LEFT JOIN InfoPages ON TabledInfoPages.ardNum = InfoPages.ardNum");
			return moveResultSetToTabledInfoPages(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			TabledInfoPage [] tabledInfoPages = new TabledInfoPage[1];
			tabledInfoPages[0] = new TabledInfoPage();
			return tabledInfoPages;
		}
	}
	
	public TabledInfoPage [] moveResultSetToTabledInfoPages(ResultSet resultSet){
		try{
			
			List tabledInfoPagesList = new ArrayList();
			while (resultSet.next()){
				TabledInfoPage tabledInfoPage = new TabledInfoPage();
				tabledInfoPage.setArdNum(resultSet.getInt("ardNum"));
				tabledInfoPage.setTitle(resultSet.getString("title"));
				tabledInfoPage.setDescription(resultSet.getString("description"));
				tabledInfoPagesList.add(tabledInfoPage);				
			}
			TabledInfoPage [] tabledInfoPagesArray = new TabledInfoPage [tabledInfoPagesList.size()];
			for (int i=0; i<tabledInfoPagesArray.length; i++){
				tabledInfoPagesArray[i] = (TabledInfoPage)tabledInfoPagesList.get(i);
			}
			return tabledInfoPagesArray;
		}
		catch(SQLException e){
			System.out.println(e);
			TabledInfoPage [] tabledInfoPages = new TabledInfoPage[1];
			tabledInfoPages[0] = new TabledInfoPage();
			return tabledInfoPages;
		}
		
	}
	
	public double getOccuranceFrequency(String word, String table){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT occurances FROM "+table+" WHERE word=\""+word+"\";");
			float occurances=0;
			if (resultSet.next()) occurances = resultSet.getInt("occurances");
			int totalOccurancesCount = getOccurancesCount(table);
			return (occurances/totalOccurancesCount);
		}
		catch(SQLException e){
			System.out.println(e);
			return 0;
		}
			
	}
	
	public int getOccurancesCount(String table){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT occurances from "+table+";");
			int wordsCount=0;
			while (resultSet.next()){
				 wordsCount+=resultSet.getInt("occurances");
			}
			return wordsCount;
		}
		catch (SQLException e){
			System.out.println(e);
			return 1;
		}
	}
	
	public boolean isInBlackList(String word){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT word FROM BlackListWords WHERE word=\""+word+"\";");
			return resultSet.next();
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}
	
	public List getWordsListFromArdPagesWordsOccurancesTable(int numOfWordsToReturn){
		try{
			Statement statement = getCurrentConnection().createStatement();
			int occurancesCount = getOccurancesCount("ArdPagesWordsOccurances"); 
			ResultSet resultSet = statement.executeQuery("SELECT word FROM ArdPagesWordsOccurances"+
				" WHERE occurances/"+occurancesCount+"<"+Utils.getKeywordsOccurancesFrequencyThreshold());
			List wordsList = new ArrayList();
			int i=0;
			while (resultSet.next() && i<=numOfWordsToReturn-1){
				wordsList.add(resultSet.getString("word"));
				i++;
			}
			return wordsList;
		}
		catch (SQLException e){
			System.out.println(e);
			return new ArrayList();
		}
	}
	
	public List getKeywordsList (int numOfKeywordsToReturn){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT keyword from InfoPagesKeywords WHERE ardNum<10000");// ORDER BY keyword DESC");
			List keywordsList = new ArrayList();
			int i=0;
			while (resultSet.next() && i<numOfKeywordsToReturn){
				keywordsList.add(resultSet.getString("keyword"));
				i++;
			}
			return keywordsList;
		}
		catch(SQLException e){
			System.out.println(e);
			return new ArrayList();
		}
	}
	
	
}
