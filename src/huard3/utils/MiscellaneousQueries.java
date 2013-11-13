package huard3.utils;
import java.util.*;
import java.sql.*;
import huard3.actions.*;


public class MiscellaneousQueries extends QueryDatabase{
	
	public MiscellaneousQueries(){
		super();
	}
	
	/*public AccessLogEntery[] getAccessLogEnteriesByTimePeriod(long [] [] ipsRanges, boolean queryIpsInRange, long startTime, long endTime){
		    StringBuffer querySB;
			if (queryIpsInRange){
				querySB = new StringBuffer();
				for (int i=0; i<ipsRanges.length; i++){
					querySB.append("SELECT * FROM AccessLog WHERE ipAsLong>"+ipsRanges [i] [0] +" AND ipAsLong <"+ipsRanges [i] [1]+" AND time>"+startTime+" AND time<"+endTime+" UNION ");
				}			
				querySB.delete(querySB.length()-7, querySB.length()-1);
				querySB.append(";");
				
			}
			else {
				querySB = new StringBuffer("SELECT * FROM AccessLog WHERE ");
				for (int i=0; i<ipsRanges.length; i++){
					querySB.append("(ipAsLong<"+ipsRanges[i] [0]+" OR ipAsLong>"+ipsRanges[i] [1]+") AND ");
				}			
				querySB.delete(querySB.length()-4, querySB.length()-1);
				querySB.append(" AND time>"+startTime+" AND time<"+endTime+";");
				
				
			}
			
	//		System.out.println(querySB.toString());
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(querySB.toString());
			return moveFromResultSetToAccessLogEntery(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			AccessLogEntery [] accessLogEnteries = new AccessLogEntery[0];
			return accessLogEnteries;
		}
		
	}
	public AccessLogEntery [] moveFromResultSetToAccessLogEntery(ResultSet resultSet){
		try{
			List accessLogEnteriesList  = new ArrayList();
			int counter=0;
			while (resultSet.next()){
				counter++;
				AccessLogEntery accessLogEntery = new AccessLogEntery();
				accessLogEntery.setIp(resultSet.getString("ip"));
				accessLogEntery.setIpAsLong(resultSet.getLong("ipAsLong"));
				accessLogEntery.setTime(resultSet.getLong("time"));
				accessLogEntery.setFile(resultSet.getString("file"));
				//System.out.println(accessLogEntery.getFile()+" "+counter);
				accessLogEntery.setStatus(resultSet.getString("status"));
				accessLogEntery.setReferer(resultSet.getString("referer"));
				accessLogEntery.setBrowser(resultSet.getString("browser"));
				//accessLogEntery.setTitle(getTitleByArdNumAndDocumentMetaType(accessLogEntery));
				accessLogEnteriesList.add(accessLogEntery);
			}
			AccessLogEntery [] accessLogEnteriesArray = new AccessLogEntery[ accessLogEnteriesList.size()];
			for (int i=0; i< accessLogEnteriesArray.length; i++){
				accessLogEnteriesArray[i] = (AccessLogEntery)accessLogEnteriesList.get(i);
			}
			return accessLogEnteriesArray;			 	
		}
		catch (SQLException e){
			System.out.println("moveFromResultSetToAccessLogEntery: "+e);
			AccessLogEntery [] accessLogEnteries = new AccessLogEntery[0];
			return accessLogEnteries;
		}
	}*/
	
	public boolean isMailAddressOnPage(String mailAddress, String ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPagesMailURLs WHERE " +
					"URL=\""+mailAddress+"\" AND ardNum=\""+ardNum+"\";");
			boolean inTable =  resultSet.next();
			System.out.println(resultSet.getString(2));
			return inTable;			   				
			}
			catch (SQLException e){
				System.out.println(e);
				return false;
			}		
		
	}
	
	public List getContentOptions(String contentOptionsTable){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String optionsColumn = contentOptionsTable.substring(0,1).toLowerCase()+contentOptionsTable.substring(1, contentOptionsTable.lastIndexOf("s"));
			ResultSet resultSet = statement.executeQuery("SELECT "+optionsColumn+", "+optionsColumn+"Hebrew FROM "+contentOptionsTable+";");
			List contentOptionsList = new ArrayList();
			while (resultSet.next()){
				contentOptionsList.add(resultSet.getString(optionsColumn));
				contentOptionsList.add(resultSet.getString(optionsColumn+"Hebrew"));
			}
			return contentOptionsList;			
		}
		catch(SQLException e){
			System.out.println(e);
			return new ArrayList();
		}		
	}
	
	
	
	
	
	public String getHebrewContentOptionByRessemblanceToWordsList (String contentOptionsTable, List wordsList, String contentOptionsName, boolean optionalPluralForm){
		try{
			Statement statement = getCurrentConnection().createStatement();
			
			for (int i=0; i<wordsList.size(); i++){
				String word = (String)wordsList.get(i);
				ResultSet resultSet = statement.executeQuery("SELECT "+contentOptionsName+ 
						"Hebrew, appearInPluralForm FROM " +contentOptionsTable+
						" WHERE "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '%"+word.trim()+" %'"+
						" OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '% "+word.trim()+"%'"+
						" OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '%"+word.trim()+"%';");
				List list = new ArrayList();
				while (resultSet.next()){
					list.add(resultSet.getString(1));
				}
					
				if (list.size()==1) {
					resultSet.first();
					return ((String)list.get(0));
				}				
			}
			return "Couldn't descide";			
		}
		catch (SQLException e){
			System.out.println(e);
			return "";
		}
	}
	
	public String getEnglishContentOptionByRessemblanceToWordsList (String contentOptionsTable, List wordsList, String contentOptionsName, boolean optionalPluralForm){
		try{
			Statement statement = getCurrentConnection().createStatement();
			for (int i=0; i<wordsList.size(); i++){
				String word = (String)wordsList.get(i);
				String queryString = "SELECT "+contentOptionsName+", appearInPluralForm FROM "+
				contentOptionsTable+" WHERE "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '%"+word.trim()+" %' OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '% "+word.trim()+"%' OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '%"+word.trim()+"%';";
				System.out.println("huard3.utils.MiscellaneousQueries.getEnglishContentOptionByRessemblanceToWordsList (): queryString: "+queryString);
				ResultSet resultSet = statement.executeQuery(queryString);
				List list = new ArrayList();
				while (resultSet.next()){
					list.add(resultSet.getString(1));
				}
					
				if (list.size()==1) {
					resultSet.first();
					return ((String)list.get(0) + (optionalPluralForm && resultSet.getBoolean(2) ? "s" : ""));
				}							 
			}
			return "Couldn't descide";			
		}
		catch (SQLException e){
			System.out.println(e);
			return "";
		}
	}
	
	public String getTablesContentByColumnsComposition(String columnsComposition, String selectiveIndexingCondition){
		if (columnsComposition!=null && ! columnsComposition.equals("")){
		try{
			Statement statement = getCurrentConnection().createStatement();	
			String tablesComposition = "";
			WordsTokenizer wt = new WordsTokenizer(",");
			List list = wt.getSubstringsList(columnsComposition);
			for (int i=0; i<list.size(); i++){
				String table = (String)list.get(i);
				if (table!=null && table.indexOf(".")!=-1){
					table = table.substring(0,table.lastIndexOf("."));
					tablesComposition = tablesComposition.concat(table+",");
				}
				else return "";		
			}
			tablesComposition = uniqueColonSeparatedList(tablesComposition);
			tablesComposition = tablesComposition.substring(0,tablesComposition.lastIndexOf(","));			
			ResultSet resultSet = statement.executeQuery("SELECT "+columnsComposition+" FROM "+tablesComposition
					+(selectiveIndexingCondition!=null || ! selectiveIndexingCondition.equals("")  ? " WHERE "+selectiveIndexingCondition : "")+";");
			int columnCount = resultSet.getMetaData().getColumnCount();
			StringBuffer sb = new StringBuffer();
			while (resultSet.next()){
				for (int i=1; i <= columnCount; i++){
					if (resultSet.getMetaData().getColumnTypeName(i).equals("TEXT") || resultSet.getMetaData().getColumnTypeName(i).equals("VARCHAR"))
						sb.append(resultSet.getString(i)+" * ");
				}
			}
			return sb.toString();
			
		}
		catch(SQLException e){
			System.out.println(e);
			return "";
		}
		}
		else return "";
		
	}
		
	public String uniqueColonSeparatedList(String tablesComposition){
		WordsTokenizer wt = new WordsTokenizer(",");
		List list = wt.getSubstringsList(tablesComposition);
		for (int i=0; i<list.size(); i++){
			for (int j=i+1; j<list.size(); j++){
				if (list.get(i).equals(list.get(j))) {
					list.remove(j);
					j--;
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<list.size(); i++){
			sb.append( (String) list.get(i) + ",");
		}
		return sb.toString();
	}
	
	
		
	
}
