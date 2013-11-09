package huard.website.baseDb;

import huard.website.model.Desk;
import huard.website.model.Fund;
import huard.website.model.PubPage;
import huard.website.model.ResearchField;
import huard.website.model.TabledInfoPage;
import huard.website.model.Worker;
import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

	private String websiteDatabase;

	public DbHandler (){
		websiteDatabase = Utils.getWebsiteDatabaseName();
	}

	protected ManagedConnection getConnection(){
		return getConnection(websiteDatabase);
	}

	protected void archiveConection(ManagedConnection connection){
		archiveConection(websiteDatabase, connection);
	}

	protected ManagedConnection getConnection(String database){
		return ConnectionSupplier.getConnectionSupplier().getConnection(database,"UPDATE");
	}

	protected void archiveConection(String database, ManagedConnection connection){
		ConnectionSupplier.getConnectionSupplier().archiveConnection(connection, database);
	}

	public int getRowsCount(String database, String table){
		ManagedConnection connection = getConnection(database);
		int r = 0;
		try{
        	Statement statement = connection.createStatement();
  	        String query = "SELECT COUNT(*) FROM "+table+";";
   	        ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            r = resultSet.getInt(1);
            statement.close();
        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    	}
        archiveConection(database, connection);
        return r;
   	}

	public String getTablesContentByColumnsComposition(String columnsComposition, String selectiveIndexingCondition){
		if (columnsComposition.length()>0){
			ManagedConnection connection = getConnection();
			try{
				Statement statement = connection.createStatement();
				String tablesComposition = "";
				WordsTokenizer wt = new WordsTokenizer(",");
				List<String> list = wt.getSubstringsList(columnsComposition);
				for (int i=0; i<list.size(); i++){
					String table = (String)list.get(i);
					if (table!=null && table.indexOf(".")!=-1){
						table = table.substring(0,table.lastIndexOf("."));
						tablesComposition = tablesComposition.concat(table+",");
					}
					else{
						statement.close();
						archiveConection(connection);
						return "";
					}
				}
				tablesComposition = uniqueColonSeparatedList(tablesComposition);
				if (tablesComposition!=null && tablesComposition.indexOf(",")!=-1)
					tablesComposition = tablesComposition.substring(0,tablesComposition.lastIndexOf(","));
				ResultSet resultSet = statement.executeQuery("SELECT "+columnsComposition+" FROM "+tablesComposition
						+(selectiveIndexingCondition!=null || ! selectiveIndexingCondition.equals("")  ? " WHERE "+selectiveIndexingCondition : "")+";");
				int columnCount = resultSet.getMetaData().getColumnCount();
				StringBuffer sb = new StringBuffer();
				while (resultSet.next()){
					for (int i=1; i <= columnCount; i++){
						if (resultSet.getMetaData().getColumnTypeName(i).equals("TEXT") || resultSet.getMetaData().getColumnTypeName(i).equals("VARCHAR"))
							sb.append(resultSet.getString(i)+" ");
					}
				}
				statement.close();
				archiveConection(connection);
				return sb.toString();
			}
			catch(SQLException e){
				System.out.println(e);
				if (connection != null)
					archiveConection (connection);
				return "";
			}
		}
		return "";
	}

	public String uniqueColonSeparatedList(String tablesComposition){
		WordsTokenizer wt = new WordsTokenizer(",");
		List<String> list = wt.getSubstringsList(tablesComposition);
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

	public PubPage[] moveFromResultSetToPubPage(ResultSet resultSet) throws SQLException{
		List<PubPage> pubPagesList = new ArrayList<PubPage>();
		while (resultSet.next()){
			PubPage pubPage = new PubPage();
			pubPage.setArdNum(resultSet.getInt("ardNum"));
			pubPage.setTitle(resultSet.getString("title"));
			pubPage.setTitleHighlighted(resultSet.getString("title"));
			pubPage.setHtml(resultSet.getString("html"));
			pubPage.setDocType(resultSet.getString("docType"));
			pubPage.setPubDate(resultSet.getLong("pubDate"));
			pubPage.setDeskId(resultSet.getString("deskId"));
			pubPage.setDocOwner(resultSet.getString("docOwner"));
			pubPage.setRestricted(resultSet.getBoolean("restricted"));
			pubPage.setMessage(resultSet.getInt("message")==1);
			pubPage.setExpDate(resultSet.getLong("expDate"));
			pubPage.setToRollingMessages(resultSet.getInt("toRollingMessages")==1);
			pubPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
			pubPage.setHasImage(resultSet.getBoolean("hasImage"));
			pubPage.setImageFilename(resultSet.getString("imageFilename"));
			pubPage.setFileRepresentation(resultSet.getBoolean("fileRepresentation"));
			pubPage.setLink(resultSet.getString("link"));
			pubPage.setReferenceFile(resultSet.getString("referenceFile"));
			pubPage.setSource(resultSet.getString("source"));
			pubPage.setInternalUseDescription(resultSet.getString("internalUseDescription"));
			pubPage.setOnSite(resultSet.getBoolean("onSite"));
			pubPage.setCategory(resultSet.getString("category"));
			pubPage.setWraper(resultSet.getBoolean("wraper"));
			pubPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
			pubPage.setWrappedElementHeight(resultSet.getInt("wrappedElementHeight"));
			pubPage.setParentPageTitle(resultSet.getString("parentPageTitle"));
			pubPagesList.add(pubPage);
		}

		PubPage[] pubPagesArray = new PubPage[pubPagesList.size()>0 ? pubPagesList.size() : 1];
		for (int i=0;i<pubPagesList.size();i++){
			pubPagesArray[i]=(PubPage)pubPagesList.get(i);
		}
		if (pubPagesArray.length==1 && pubPagesArray[0]==null){
			pubPagesArray[0] = new PubPage();
			pubPagesArray[0].setTitle("No Results");
		}
		return pubPagesArray;
	}

	public String getPubPagesKeywordsByArdNum(String ardNum){
		ManagedConnection connection = getConnection();
		StringBuffer keywordsSB = new StringBuffer();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPagesKeywords WHERE ardNum=\""+ardNum+"\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				 keywordsSB.append(resultSet.getString("keyword")+",");
			}
			if (keywordsSB.length()>0) keywordsSB.delete(keywordsSB.length()-1,keywordsSB.length());
			statement.close();

		}
		catch(SQLException e){
			System.out.println("huard.utils.DbHandler.getPubPagesKeywordsByArdNum(): " + e);
			keywordsSB.append("No Keywords");
		}
		archiveConection(connection);
		return keywordsSB.toString();
	}

	public String getHebrewDocTypeByEnglishDocType(String docType){
		ManagedConnection connection = getConnection();
		String hebrewDocType = "";
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT docTypeHebrew FROM DocTypes WHERE docType=\""+docType+"\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()){
				hebrewDocType = resultSet.getString(1);
			}
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huard.utils.DbHandler.getHebrewDocTypeByEnglishDOcType(): " + e);
		}
		archiveConection(connection);
		return hebrewDocType;
	}

	public TabledInfoPage getTabledInfoPageDetailsByArdNum(String ardNum){
		TabledInfoPage  tabledInfoPage = new TabledInfoPage();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String mainQuery = "SELECT * FROM TabledInfoPages LEFT JOIN InfoPages ON TabledInfoPages.ardNum = InfoPages.ardNum WHERE TabledInfoPages.ardNum=\""+ardNum+"\"";
			ResultSet resultSet = statement.executeQuery(mainQuery);
			tabledInfoPage = moveResultSetToTabledInfoPages(resultSet)[0];
			statement.close();
		}
		catch(SQLException e){
			System.out.println("InfoPagesQuery.getTabledInfoPageDetailsByArdNum(String ardNum): " + e);
		}
		archiveConection(connection);
		return tabledInfoPage;
	}

	public TabledInfoPage [] moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
		List<TabledInfoPage> tabledInfoPagesList = new ArrayList<TabledInfoPage>();
		while (resultSet.next()){
			TabledInfoPage tabledInfoPage = readTabledInfoPage(resultSet);
			tabledInfoPagesList.add(tabledInfoPage);
		}
		TabledInfoPage [] tabledInfoPagesArray = new TabledInfoPage [tabledInfoPagesList.size()>0 ? tabledInfoPagesList.size() : 1];
		for (int i=0; i<tabledInfoPagesList.size(); i++){
			tabledInfoPagesArray[i] = (TabledInfoPage)tabledInfoPagesList.get(i);
		}
		if (tabledInfoPagesArray.length==1 && tabledInfoPagesArray[0]==null){
			tabledInfoPagesArray[0] = new TabledInfoPage();
			tabledInfoPagesArray[0].setTitle("No Results");
		}
		return tabledInfoPagesArray;
	}


	public TabledInfoPage readTabledInfoPage(ResultSet resultSet) throws SQLException{
		TabledInfoPage tabledInfoPage = new TabledInfoPage();
		tabledInfoPage.setArdNum(resultSet.getInt("ardNum"));
		tabledInfoPage.setTitle(resultSet.getString("title"));
		tabledInfoPage.setTitleHighlighted(resultSet.getString("title"));
		tabledInfoPage.setPubDate(resultSet.getLong("pubDate"));
		tabledInfoPage.setFundNum(resultSet.getInt("fundNum"));
		tabledInfoPage.setFundShortName(resultSet.getString("fundShortName"));
		tabledInfoPage.setSubDate(resultSet.getLong("subDate"));

		tabledInfoPage.setDocType(resultSet.getString("docType"));
		tabledInfoPage.setDeskId(resultSet.getString("deskId"));
		tabledInfoPage.setResearchFields(resultSet.getInt("researchFields"));
		tabledInfoPage.setHasTabledVersion(resultSet.getBoolean("hasTabledVersion"));
		tabledInfoPage.setRepetitive(resultSet.getBoolean("repetitive"));
		tabledInfoPage.setDocOwner(resultSet.getString("docOwner"));
		tabledInfoPage.setRestricted(resultSet.getBoolean("restricted"));
		tabledInfoPage.setHasAdditionalSubDates(resultSet.getBoolean("hasAdditionalSubDates"));
		tabledInfoPage.setHasLocalWebPage(resultSet.getBoolean("hasLocalWebPage"));
		tabledInfoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
		tabledInfoPage.setDescriptionOnly(resultSet.getBoolean("descriptionOnly"));
		tabledInfoPage.setDeleted(resultSet.getBoolean("isDeleted"));

		//tabledInfoPage.setKeywords(getInfoPagesKeywordsByArdNum(""+tabledInfoPage.getArdNum()));
		tabledInfoPage.setSubSite(resultSet.getString("subSite"));
		tabledInfoPage.setSubDateArd(resultSet.getString("subDateArd"));
		tabledInfoPage.setSubDateFund(resultSet.getString("subDateFund"));
		tabledInfoPage.setSubDateDetails(resultSet.getString("subDateDetails"));
		tabledInfoPage.setDeskAndContact(resultSet.getString("deskAndContact"));
		tabledInfoPage.setForms(resultSet.getString("forms"));
		tabledInfoPage.setDescription(resultSet.getString("description"));
		tabledInfoPage.setMaxFundingPeriod(resultSet.getString("maxFundingPeriod"));
		tabledInfoPage.setAmountOfGrant(resultSet.getString("amountOfGrant"));
		tabledInfoPage.setEligibilityRequirements(resultSet.getString("eligibilityRequirements"));
		tabledInfoPage.setActivityLocation(resultSet.getString("activityLocation"));
		tabledInfoPage.setPossibleCollaboration(resultSet.getString("possibleCollaboration"));
		tabledInfoPage.setBudgetDetails(resultSet.getString("budgetDetails"));
		tabledInfoPage.setNumOfCopies(resultSet.getInt("numOfCopies"));
		tabledInfoPage.setAdditionalInformation(resultSet.getString("additionalInformation"));
		tabledInfoPage.setSendDigitalCopy(resultSet.getBoolean("sendDigitalCopy"));
		tabledInfoPage.setAppendBudgetOfficeLine(resultSet.getBoolean("appendBudgetOfficerLine"));
		return tabledInfoPage;
	}

	public Fund getFundByFundNum(String fundNum){
    	Fund fund = new Fund();
    	ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
            String fundQuery="SELECT * FROM Funds WHERE fundNum=\""+fundNum+"\"";
     		ResultSet resultSet = statement.executeQuery(fundQuery);
     		fund = moveFromResultSetToFund(resultSet)[0];
     		statement.close();
      	}
    	catch (SQLException e){
    		System.out.println("huard1.utils.DbHandler.getFundByFundNum(): " + e);
    	}
    	archiveConection(connection);
    	return fund;
    }

	public Fund[] moveFromResultSetToFund(ResultSet resultSet) throws SQLException {
   		List<Fund> fundsList = new ArrayList<Fund>();
   		while(resultSet.next()){
   		   Fund fund = new Fund();
   		   fund.setFundNum(resultSet.getInt("fundNum"));
           fund.setShortName(resultSet.getString("shortName"));
           fund.setFullName(resultSet.getString("fullName"));
           fund.setKsfNum(resultSet.getInt("ksfNum"));
           fund.setParentFund(resultSet.getString("parentFund"));
           fund.setWebAddress(resultSet.getString("webAddress"));
           fund.setPhoneNum(resultSet.getString("phoneNum"));
           fund.setFax(resultSet.getString("fax"));
           fund.setContact(resultSet.getString("contact"));
           fund.setMailAddress(resultSet.getString("mailAddress"));
           fund.setDocOwner(resultSet.getString("docOwner"));
           fund.setDeskId(resultSet.getString("deskId"));
           fund.setFinancialReporter(resultSet.getString("financialReporter"));
           fund.setBudgetOfficer(resultSet.getString("budgetOfficer"));
           fund.setRestricted(resultSet.getBoolean("restricted"));
           fund.setHasLocalPage(resultSet.getBoolean("hasLocalPage"));
           fund.setHtml(resultSet.getString("html"));
           fund.setPubDate(resultSet.getLong("pubDate"));
           fund.setHasAliveInfoPages(resultSet.getBoolean("hasAliveInfoPages"));
   	       fundsList.add(fund);
   		}
   		Fund[] funds = new Fund[fundsList.size()>0 ? fundsList.size() : 1];
        funds[0] = new Fund();
        for (int i=0; i<fundsList.size(); i++) funds[i] = (Fund)fundsList.get(i);
        return funds;
   	}

	public ResearchField getResearchFieldByNum(int num){
		ResearchField researchField = new ResearchField();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String researchFieldQuery="SELECT * FROM ResearchFields WHERE num=\""+num+"\"";
			ResultSet resultSet = statement.executeQuery(researchFieldQuery);
			if (resultSet.next()){
				researchField.setNum(num);
				researchField.setEnglishName(resultSet.getString("researchField"));
				researchField.setHebrewName(resultSet.getString("researchFIeldHebrew"));
				researchField.setShortName(resultSet.getString("researchFieldShort"));
				researchField.setExperimental(resultSet.getBoolean("experimental"));
			}
			statement.close();
		}
		catch (SQLException e){
			System.out.println("huard1.utils.DbHandler.getResearchFieldByNum(): " + e);

		}
		archiveConection(connection);
		return researchField;
	}

	public long[] getAdditionalSubDates(String ardNum){
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
     		ResultSet resultSet = statement.executeQuery("SELECT * FROM AdditionalSubDates WHERE ardNum="+ardNum+" ORDER BY subDate;");
     		List<Long> additionalSubDatesList = new ArrayList<Long>();
     		while(resultSet.next()){
     			Long additionalSubDate = new Long(resultSet.getLong("subDate"));
     			additionalSubDatesList.add(additionalSubDate);
     		}
     		long [] additionalSubDates = new long[additionalSubDatesList.size()];
     		for (int i=0; i<additionalSubDatesList.size(); i++){
     			additionalSubDates[i] = ((Long)additionalSubDatesList.get(i)).longValue();
     		}
     		statement.close();
     		archiveConection(connection);
     		return additionalSubDates;
   		}

   		catch(SQLException e){
    		System.out.println("huard1.utils.DbHandler.getAditionalSubDates(): " + e);
    		long[] noAdditionalSubDates = new long[3];
    		if (connection != null)
    			archiveConection(connection);
    		return noAdditionalSubDates;
      	}
   	}

	public List<String> getContentOptions(String contentOptionsTable){
		ManagedConnection connection = getConnection();
		List<String> contentOptionsList = new ArrayList<String>();
		try{
			Statement statement = connection.createStatement();
			String optionsColumn = contentOptionsTable.substring(0,1).toLowerCase()+contentOptionsTable.substring(1, contentOptionsTable.lastIndexOf("s"));
			ResultSet resultSet = statement.executeQuery("SELECT "+optionsColumn+", "+optionsColumn+"Hebrew FROM "+contentOptionsTable+";");
			while (resultSet.next()){
				contentOptionsList.add(resultSet.getString(optionsColumn));
				contentOptionsList.add(resultSet.getString(optionsColumn+"Hebrew"));
			}
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		archiveConection(connection);
		return contentOptionsList;
	}

	public String getHebrewContentOptionByRessemblanceToWordsList (String contentOptionsTable, List wordsList, String contentOptionsName, boolean optionalPluralForm){
		ManagedConnection connection = getConnection();
		String result = "";
		try{
			Statement statement = connection.createStatement();
			for (int i=0; i<wordsList.size(); i++){
				String word = (String)wordsList.get(i);
				ResultSet resultSet = statement.executeQuery("SELECT "+contentOptionsName+
						"Hebrew, appearInPluralForm FROM " +contentOptionsTable+
						" WHERE "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '%"+word.trim()+" %'"+
						" OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '% "+word.trim()+"%'"+
						" OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+" LIKE '%"+word.trim()+"%';");
				List<String> list = new ArrayList<String>();
				while (resultSet.next()){
					list.add(resultSet.getString(1));
				}

				if (list.size()==1) {
					resultSet.first();
					result = ((String)list.get(0));
				}
			}
			statement.close();
			if (result.isEmpty())
				result = "Couldn't descide";
		}
		catch (SQLException e){
			System.out.println(e);
		}
		archiveConection(connection);
		return result;
	}

	public String getEnglishContentOptionByRessemblanceToWordsList (String contentOptionsTable, List wordsList, String contentOptionsName, boolean optionalPluralForm){
		ManagedConnection connection = getConnection();
		String result = "";
		try{
			Statement statement = connection.createStatement();
			for (int i=0; i<wordsList.size(); i++){
				String word = (String)wordsList.get(i);
				String query = "SELECT "+contentOptionsName+", appearInPluralForm FROM "+
				contentOptionsTable+" WHERE "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '%"+word.trim()+" %' OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '% "+word.trim()+"%' OR "+contentOptionsName+ (Utils.isHebrew(word) ? "Hebrew" : "")+
				" LIKE '%"+word.trim()+"%';";
				System.out.println(query);
				//System.out.println("huard1.utils.DbHandler.getEnglishContentOptionByRessemblanceToWordsList (): query: "+query);
				ResultSet resultSet = statement.executeQuery(query);
				statement.close();
				List<String> list = new ArrayList<String>();
				while (resultSet.next()){
					list.add(resultSet.getString(1));
				}

				if (list.size()==1) {
					resultSet.first();
					result = ((String)list.get(0) + (optionalPluralForm && resultSet.getBoolean(2) ? "s" : ""));
				}
			}
			if (result.isEmpty())
				result =  "Couldn't descide";
			statement.close();
		}
		catch (SQLException e){
			System.out.println(e);
		}
		archiveConection(connection);
		return result;
	}

	public Desk getDesk (String deskId){
		Desk desk = new Desk();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Desks WHERE deskId=\""+deskId+"\";");
			if (resultSet.next()){
				desk.setEnglishName(resultSet.getString("desksFullNameEnglish"));
				desk.setHebrewName(resultSet.getString("desksFullNameHebrew"));
				desk.setEnglishListId(resultSet.getInt("listIdEnglish"));
				desk.setHebrewListId(resultSet.getInt("listIdHebrew"));
			}
			statement.close();
		}
		catch (SQLException e){
			System.out.println("huard1.utils.getDeskEnglishName(): " + e);
		}
		archiveConection(connection);
		return desk;
	}

	public String getDeskEnglishName(String deskId){
		ManagedConnection connection = getConnection();
		String result = "";
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT desksFullNameEnglish FROM Desks WHERE deskId=\""+deskId+"\";");
			if (resultSet.next()){
				result = resultSet.getString("desksFullNameEnglish");
			}
			statement.close();
		}
		catch (SQLException e){
			System.out.println("huard1.utils.getDeskEnglishName(): " + e);
		}
		archiveConection(connection);
		return result;
	}

	public String getDeskHebrewName(String deskId){
		ManagedConnection connection = getConnection();
		String result = "";
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT desksFullNameHebrew FROM Desks WHERE deskId=\""+deskId+"\";");
			if (resultSet.next()){
				result = resultSet.getString("desksFullNameHebrew");
			}
			statement.close();
		}
		catch (SQLException e){
			System.out.println("huard1.utils.getDeskEnglishName(): " + e);
		}
		archiveConection(connection);
		return result;
	}

	public Worker [] getWorkersByDeskId(String deskId, boolean heb){
		ManagedConnection connection = getConnection();
		Worker [] workers = null;
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers,WorkersTitles WHERE "+
					" Workers.shortTitle=WorkersTitles.title AND deskId=\""+deskId+"\""+
					" ORDER BY WorkersTitles.num, SUBSTRING(`"+(heb ? "hebrewName" : "englishName")+
					"`,LOCATE(' ',`"+(heb ? "hebrewName" : "englishName")+"`)+1);");

			workers = moveFromResultSetToWorkers(resultSet);
		}
		catch(SQLException e){
			System.out.println("huard1.utils.getWorkersByDeskId(): "+e);
			workers = new Worker[1];
			workers[0] = new Worker();
		}
		archiveConection(connection);
		return workers;
	}

	public Worker [] moveFromResultSetToWorkers(ResultSet resultSet) throws SQLException{
		List<Worker> workersList = new ArrayList<Worker>();
		while (resultSet.next()){
				Worker worker = new Worker();
				worker.setEnglishPre(resultSet.getString("englishPre"));
				worker.setEnglishName(resultSet.getString("englishName"));
				worker.setHebrewPre(resultSet.getString("hebrewPre"));
				worker.setHebrewName(resultSet.getString("hebrewName"));
				worker.setEnglishTitle(resultSet.getString("englishTitle"));
				worker.setHebrewTitle(resultSet.getString("hebrewTitle"));
				worker.setPhone(resultSet.getString("phone"));
				worker.setDeskId(resultSet.getString("deskId"));
				workersList.add(worker);
		}
		Worker [] workersArray;
		if (workersList.size()>0){
			workersArray = new Worker[workersList.size()];
			for (int i=0; i<workersArray.length; i++){
				workersArray[i] = (Worker)(workersList.get(i));
			}
		}
		else {
			workersArray = null;
		}
		return workersArray;
	}

	public List<String> getInfoPageKeywords(int ardNum){
		List<String> keywords = new ArrayList<String>();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPagesKeywords WHERE ardNum="+ardNum+";");
			while (resultSet.next()){
				keywords.add(resultSet.getString("keyword"));
			}
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huard.website.baseDb.DbHandler: "+e);
		}
		archiveConection(connection);
		return keywords;
	}
}