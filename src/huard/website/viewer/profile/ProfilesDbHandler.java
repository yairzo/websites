package huard.website.viewer.profile;

import huard.website.baseDb.DbHandler;
import huard.website.baseDb.ManagedConnection;
import huard.website.model.ComposedPatternedPage;
import huard.website.model.Fund;
import huard.website.model.MultipleOptionsPatternedPage;
import huard.website.model.PubPage;
import huard.website.model.ResearchField;
import huard.website.model.TabledInfoPage;
import huard.website.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class ProfilesDbHandler extends DbHandler {
	private long now;

	private static final Logger logger = Logger
			.getLogger(ProfilesDbHandler.class);

	public ProfilesDbHandler() {
		super();
		now = new Date().getTime();
	}

	public String getWorkersTableLastUpdate() {
		String lastUpdate = Utils.getWebsiteOnlineDate();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT MAX(creationTime) FROM UserActivityLog"
					+ " WHERE `database`=\""
					+ Utils.getWebsiteDatabaseName()
					+ "\" AND `table`=\"Workers\"" + " AND action!=\"\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				lastUpdate = Utils.getFormatedDate(resultSet.getLong(1));
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.DbHandler.getWorkersTableLastUpdate(): "
							+ e);
		}
		archiveConection(connection);
		return lastUpdate;
	}

	public String getInfoPagesLastUpdate() {
		String lastUpdateDate = Utils.getWebsiteOnlineDate();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT MAX(date) FROM InfoPagesLastUpdates";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			lastUpdateDate = Utils.getFormatedDate(resultSet.getLong(1));
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.DbHandler.getInfoPagesLastUpdate(): "
							+ e);
		}
		archiveConection(connection);
		return lastUpdateDate;
	}

	public TabledInfoPage[] getNewInfoPages(int daysAsNew) {
		TabledInfoPage[] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			long timeAsNew = (long) daysAsNew * 86400000; // translate from
															// days to
			long now = new java.util.Date().getTime();
			String query = "SELECT * FROM InfoPages,TabledInfoPages WHERE InfoPages.ardNum=TabledInfoPages.ardNum"
					+ " AND InfoPages.isDeleted=0 AND InfoPages.hasTabledVersion=1 AND pubDate>"
					+ (now - timeAsNew)
					+ " ORDER BY pubDate DESC,title";							// millisecends
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			infoPages = moveResultSetToTabledInfoPages(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.DbHandler.getNewInfoPages(): "
							+ e);
		}
		if (infoPages == null) {
			infoPages = new TabledInfoPage[1];
			infoPages[0] = new TabledInfoPage();
			infoPages[0].setTitle("No results");
		}
		archiveConection(connection);
		return infoPages;
	}

	public TabledInfoPage[] getInfoPagesToRollingMessages() {
		TabledInfoPage[] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "(SELECT * FROM TabledInfoPages,InfoPages"
					+ " WHERE TabledInfoPages.ardNum=InfoPages.ardNum AND InfoPages.KeepInRollingMessages=1 "
					+ " AND InfoPages.isDeleted=0 AND InfoPages.hasTabledVersion=1 ORDER BY PubDate DESC)"
					+ " UNION (SELECT * FROM TabledInfoPages,InfoPages WHERE"
					+ " TabledInfoPages.ardNum = InfoPages.ardNum AND InfoPages.isDeleted=0 AND InfoPages.hasTabledVersion=1 "
					+ " ORDER BY pubDate DESC,title LIMIT "
					+ Utils.getNumOfLastPublishedInfoPagesToRoll() + ") ;";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			infoPages = moveResultSetToTabledInfoPages(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.DbHandler.getInfoPagesToRollingMessages(): "
							+ e);
		}
		if (infoPages == null) {
			infoPages = new TabledInfoPage[1];
			infoPages[0] = new TabledInfoPage();
			infoPages[0].setTitle("No results");
		}
		archiveConection(connection);
		return infoPages;
	}

	public ComposedPatternedPage getComposedPatternedPageByProfileName(
			String profileName) {
		ComposedPatternedPage page = new ComposedPatternedPage();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT title FROM ComposedPatternedPages WHERE profileName=\""
					+ profileName + "\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				page.setTitle(resultSet.getString("title"));
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.DbHandler.getComposedPatternedPageByProfileName(): "
							+ e);
		}
		archiveConection(connection);
		return page;
	}

	public ComposedPatternedPage getComposedPatternedPageByProfileNameAndDocType(
			String profileName, String docType) {
		ComposedPatternedPage page = new ComposedPatternedPage();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT title FROM ComposedPatternedPages WHERE profileName=\""
					+ profileName + "\" AND docType=\"" + docType + "\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				page.setTitle(resultSet.getString("title"));
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.DbHandler.getComposedPatternedPageByProfileName(): "
							+ e);
		}
		archiveConection(connection);
		return page;
	}

	public MultipleOptionsPatternedPage getMultipleOptionsPatternedPageByProfileName(
			String profileName) {
		MultipleOptionsPatternedPage page = new MultipleOptionsPatternedPage();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT title FROM MultipleOptionsPatternedPages WHERE profileName=\""
					+ profileName + "\";";
			System.out.println(query);
			// System.out.println("huardSiteViewer..profiles.ParofilesDbHandler.getComposedPatternedPageByProfileName():
			// query: "+query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				page.setTitle(resultSet.getString("title"));
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.DbHandler.getMultipleOptionsPatternedPageByProfileName(): "
							+ e);
		}
		archiveConection(connection);
		return page;
	}

	public PubPage[] getMessagesByDocType(String docType) {
		PubPage[] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE docType=\""
					+ docType
					+ "\" AND message=1 AND isDeleted=0 ORDER BY pubDate DESC";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			pubPages = moveFromResultSetToPubPage(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if (pubPages == null) {
			pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
		}
		archiveConection(connection);
		return pubPages;
	}

	public PubPage[] getTopPriorityMessage() {
		PubPage[] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE  message=1 AND isDeleted=0 AND topPriority=1 ORDER BY pubDate DESC LIMIT 1";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			pubPages = moveFromResultSetToPubPage(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if (pubPages == null) {
			pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
		}
		archiveConection(connection);
		return pubPages;
	}

	public PubPage[] getNewMessagesByDocType(int daysAsNew, String docType) {
		PubPage[] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			long timeAsNew = (long) (daysAsNew * 86400000); // translate from
															// days to
															// millisecends
			long now = new java.util.Date().getTime();
			String query = "SELECT * FROM PubPages WHERE docType=\""
					+ docType
					+ "\" AND isDeleted=0 AND message=1 and onSite=1 AND "
					+ "pubDate>" + (now - timeAsNew)
					+ " ORDER BY pubDate DESC,title";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			statement.close();
			return moveFromResultSetToPubPage(resultSet);
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getNewMessagesByDocType(): "
							+ e);
		}
		if (pubPages == null) {
			pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
			pubPages[0].setTitle("No results");
		}
		archiveConection(connection);
		return pubPages;
	}

	public PubPage[] getMessagesByDocType(int daysAsNew, String docType) {
		PubPage[] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE docType=\""
					+ docType
					+ "\" AND isDeleted=0 AND message=1 and onSite=1 ORDER BY pubDate DESC,title";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			pubPages = moveFromResultSetToPubPage(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getMessagesByDocType(): "
							+ e);
		}
		if (pubPages == null) {
			pubPages = new PubPage[0];
			pubPages[0] = new PubPage();
			pubPages[0].setTitle("No results");
		}
		archiveConection(connection);
		return pubPages;
	}

	public String getMessagesLastUpdate(String docType) {
		String lastUpdateDate = Utils.getWebsiteOnlineDate();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT MAX(date) FROM PubPagesLastUpdates LEFT JOIN PubPages ON PubPagesLastUpdates.ardNum=PubPages.ardNum"
					+ " WHERE PubPages.message=1 AND docType=\""
					+ docType
					+ "\"";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			lastUpdateDate = Utils.getFormatedDate(resultSet.getLong(1));
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		archiveConection(connection);
		return lastUpdateDate;
	}

	public PubPage[] getToRollingMessages() {
		PubPage [] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE toRollingMessages=1 AND isDeleted=0";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			pubPages = moveFromResultSetToPubPage(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out.println("getToRollingMessages" + e);
		}
		if (pubPages == null){
			pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
		}
		archiveConection(connection);
		return pubPages;
	}

	public static boolean isHebrew(String text) {
		char[] charArray = text.toCharArray();
		boolean foundHebrewChar = false;
		int i = 0;
		while (foundHebrewChar == false && i < charArray.length) {
			int charIntValue = (int) text.charAt(i);
			foundHebrewChar = ((charIntValue >= 1488 && charIntValue <= 1514) || (charIntValue >= 224 && charIntValue <= 250));

			i++;
		}
		return foundHebrewChar;
	}

	public TabledInfoPage[] getInfoPagesByRangeOfSubDates(long fromSubDate,
			long untilSubDate, boolean fullList) {
		TabledInfoPage [] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			boolean allYear = (fromSubDate == 0 && untilSubDate == 0);
			String query = "SELECT * FROM InfoPages INNER JOIN TabledInfoPages USING (ardNum)"
				+ " LEFT JOIN (SELECT ardNum, subDate FROM AdditionalSubDates UNION"
				+ " SELECT ardNum, 0 FROM AdditionalSubDates ORDER BY subDate)"
				+	" AS AdditionalSubDates USING(ardNum) WHERE InfoPages.isDeleted = 0"
				+ " AND InfoPages.hasTabledVersion = 1 AND ((InfoPages.subDate >= " + fromSubDate
				+ " AND InfoPages.subDate <= " + untilSubDate + ")";
			if (!allYear)
				query += "OR (AdditionalSubDates.subDate >= " + fromSubDate
					+ " AND AdditionalSubDates.subDate <= " + untilSubDate + ")";
			query += ") GROUP BY InfoPages.ardNum ORDER BY InfoPages.subDate";
			//logger.info(query);
			System.out.println(query);
			ResultSet r = statement.executeQuery(query);
			List<TabledInfoPage> infoPagesList = new ArrayList<TabledInfoPage>();
			while (r.next()){
				TabledInfoPage tabledInfoPage = readTabledInfoPage(r);
				long additionalSubDate = r.getLong("AdditionalSubDates.subDate");
				if (additionalSubDate > 0)
					tabledInfoPage.setSubDate(additionalSubDate);
				if (validateCalendarInfoPages(tabledInfoPage, fullList)){
					infoPagesList.add(tabledInfoPage);
				}
			}
			statement.close();
			infoPages = Utils.getTabledInfoPagesArrayFromTabledInfoPagesList(infoPagesList);
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getInfoPagesByRangeOfSubDates(): "
							+ e);
		}
		if (infoPages == null)
			infoPages = new TabledInfoPage[0];
		archiveConection(connection);
		return infoPages;
	}


	private boolean validateCalendarInfoPages(TabledInfoPage tabledInfoPage, boolean fullList){
		if (fullList)
			return true;
		return tabledInfoPage.getSubDate() > System.currentTimeMillis() - Utils.DAY;
	}


	public List<TabledInfoPage> searchForInfoPages(
			List<String> originalKeywords, List<String> handledKeywords,
			boolean fullList, String orderBy) {
		List<TabledInfoPage> infoPages = null;
		long time = System.currentTimeMillis();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "CREATE TEMPORARY TABLE SearchResultInfoPages_"
					+ time
					+ " (ardNum smallint(6), word varchar(255))  CHARACTER SET utf8;";
			System.out.println(query);
			statement
					.executeUpdate(query);
			for (String keyword : handledKeywords) {
				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultInfoPages_"
						+ time
						+ " SELECT ardNum, word FROM InfoPagesIndex WHERE word=\""
						+ keyword + "\";";
				System.out.println(query);
				statement.executeUpdate(query);				
			}
			for (String keyword : originalKeywords) {

				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultInfoPages_"
						+ time
						+ " SELECT InfoPages.ardNum, keyword from InfoPages,InfoPagesKeywords WHERE InfoPages.ardNum=InfoPagesKeywords.ardNum AND keyword=\""
						+ keyword + "\" AND isDeleted=0;";
				System.out.println(query);
				statement
						.executeUpdate(query);
			}
			query = "SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive,"
					+ " hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress, word, description, isDeleted, subDateFund, subSite"
					+ " FROM InfoPages, TabledInfoPages, SearchResultInfoPages_"
					+ time
					+ " WHERE (InfoPages.ardNum=TabledInfoPages.ardNum) AND (SearchResultInfoPages_"
					+ time
					+ ".ardNum=InfoPages.ardNum) AND (InfoPages.hasTabledVersion=1) "
					+ (fullList ? ""
							: " AND ((InfoPages.subDate+86400000)>"
									+ time
									+ " OR (subSite='ARD' AND  (InfoPages.subDate+(30*86400000))>"
									+ time + " OR subDate=0))")
					+ " ORDER BY " + orderBy + ";";

			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			infoPages = moveBaseParmsAndFoundBySearchWordsFromResultSetToInfoPage(resultSet);
			statement.close();

			for (int i = 0; i < infoPages.size(); i++) {
				TabledInfoPage infoPage1 = (TabledInfoPage) infoPages.get(i);
				for (int j = i + 1; j < infoPages.size(); j++) {
					TabledInfoPage infoPage2 = (TabledInfoPage) infoPages
							.get(j);
					if (infoPage1.getArdNum() == infoPage2.getArdNum()) {
						infoPage1.setFoundBySearchWords(infoPage1
								.getFoundBySearchWords()
								+ "," + infoPage2.getFoundBySearchWords());
						infoPages.set(i, infoPage1);
						infoPages.remove(j);
						j--;
					}
				}
			}
		}
		catch (SQLException e) {
			logger.info(e);

		}
		archiveConection(connection);
		return infoPages;
	}

	public List<TabledInfoPage> moveBaseParmsAndFoundBySearchWordsFromResultSetToInfoPage(
			ResultSet resultSet) {
		try {
			List<TabledInfoPage> infoPagesList = new ArrayList<TabledInfoPage>();
			TabledInfoPage infoPage;
			while (resultSet.next()) {
				infoPage = new TabledInfoPage();
				infoPage.setArdNum(resultSet.getInt("ardNum"));
				infoPage.setTitle(resultSet.getString("title"));
				infoPage.setTitleHighlighted(resultSet.getString("title"));
				infoPage.setSubDate(resultSet.getLong("subDate"));
				infoPage.setDocType(resultSet.getString("docType"));
				infoPage.setRepetitive(resultSet.getBoolean("repetitive"));
				infoPage.setHasAdditionalSubDates(resultSet
						.getBoolean("hasAdditionalSubDates"));
				infoPage.setHasLocalWebPage(resultSet
						.getBoolean("hasLocalWebPage"));
				infoPage.setHasTabledVersion(resultSet
						.getBoolean("hasTabledVersion"));
				infoPage.setPageWebAddress(resultSet
						.getString("pageWebAddress"));
				infoPage.setFoundBySearchWords(resultSet.getString("word"));
				infoPage.setDescription(resultSet.getString("description"));
				infoPage.setDeleted(resultSet.getBoolean("isDeleted"));
				infoPage.setSubDateFund(resultSet.getString("subDateFund"));
				infoPage.setSubSite(resultSet.getString("subSite"));
				infoPagesList.add(infoPage);
			}
			return infoPagesList;
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.moveBaseParmsAndFoundBySearchWordsFromResultSetToInfoPage(): "
							+ e);
			List<TabledInfoPage> infoPagesList = new ArrayList<TabledInfoPage>();
			TabledInfoPage infoPage = new TabledInfoPage();
			infoPagesList.add(infoPage);
			return infoPagesList;
		}
	}

	public List<Integer> uniqueArdNumsList(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (((Integer) list.get(i)).intValue() == ((Integer) list
						.get(j)).intValue()) {
					list.remove(j);
					j--;
				}
			}
		}
		return list;
	}

	public List<MultipleOptionsPatternedPage> searchForMultipleOptionsPatternedPages(
			List<String> originalKeywords, List<String> handledKeywords,
			String docType) {
		List<MultipleOptionsPatternedPage> patternedPagesList = null;
		long time = System.currentTimeMillis();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "CREATE TEMPORARY TABLE SearchResultMultipleOptionsPatternedPages_"
					+ time
					+ " (ardNum smallint(6), word1 varchar(255), word2 varchar(255))  CHARACTER SET utf8";
			System.out.println(query);
			statement
					.executeUpdate(query);

			for (String keyword : handledKeywords) {
				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultMultipleOptionsPatternedPages_"
						+ time
						+ " SELECT ardNum, word, word FROM MultipleOptionsPatternedPagesIndex WHERE word=\""
						+ keyword + "\"";
				
				System.out.println(query);
				statement
						.executeUpdate(query);
			}
			query = "SELECT DISTINCT MultipleOptionsPatternedPages.ardNum, contentOptionsTable, fileName, docType, word1, word2"
					+ " FROM SearchResultMultipleOptionsPatternedPages_"
					+ time
					+ ",MultipleOptionsPatternedPages WHERE (SearchResultMultipleOptionsPatternedPages_"
					+ time
					+ ".ardNum=MultipleOptionsPatternedPages.ardNum)"
					+ " AND docType=\""
					+ docType
					+ "\" GROUP BY word1, word2";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			patternedPagesList = new ArrayList<MultipleOptionsPatternedPage>();
			while (resultSet.next()) {
				MultipleOptionsPatternedPage patternedPage = new MultipleOptionsPatternedPage();
				patternedPage.setArdNum(resultSet.getInt(1));
				patternedPage.setContentOptionsTable(resultSet.getString(2));
				patternedPage.setFileName(resultSet.getString(3));
				patternedPage.setFoundBySearchWords(resultSet.getString(5)
						+ "," + resultSet.getString(6));
				patternedPagesList.add(patternedPage);
			}

			for (int i = 0; i < patternedPagesList.size(); i++) {
				MultipleOptionsPatternedPage patternedPage1 = (MultipleOptionsPatternedPage) patternedPagesList
						.get(i);
				for (int j = i + 1; j < patternedPagesList.size(); j++) {
					MultipleOptionsPatternedPage patternedPage2 = (MultipleOptionsPatternedPage) patternedPagesList
							.get(j);
					if (patternedPage1.getArdNum() == patternedPage2
							.getArdNum()) {
						patternedPage1.setFoundBySearchWords(patternedPage1
								.getFoundBySearchWords()
								+ "," + patternedPage2.getFoundBySearchWords());
						patternedPagesList.set(i, patternedPage1);
						patternedPagesList.remove(j);
						j--;
					}
				}
			}
		}
		catch (SQLException e) {
			System.out.println("Exception while querying data: " + e);
		}
		archiveConection(connection);
		return patternedPagesList;
	}

	public List<ComposedPatternedPage> searchForComposedPatternedPages(
			List<String> originalKeywords, List<String> handledKeywords,
			String docType) {
		List<ComposedPatternedPage> patternedPagesList = null;
		long time = System.currentTimeMillis();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "CREATE TEMPORARY TABLE SearchResultComposedPatternedPages_"
					+ time
					+ " (ardNum smallint(6), word1 varchar(255), word2 varchar(255))  CHARACTER SET utf8";
			System.out.println(query);
			statement
					.executeUpdate(query);

			for (String keyword : handledKeywords) {
				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultComposedPatternedPages_"
						+ time
						+ " SELECT ardNum, word, word FROM ComposedPatternedPagesIndex WHERE word=\""
						+ keyword + "\"";
				System.out.println(query);
				statement
						.executeUpdate(query);
			}
			query = "SELECT DISTINCT ComposedPatternedPages.ardNum, title, fileName, word1, word2"
					+ " FROM SearchResultComposedPatternedPages_"
					+ time
					+ ",ComposedPatternedPages WHERE (SearchResultComposedPatternedPages_"
					+ time
					+ ".ardNum=ComposedPatternedPages.ardNum)"
					+ " AND docType=\""
					+ docType
					+ "\" GROUP BY word1, word2";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			patternedPagesList = new ArrayList<ComposedPatternedPage>();
			while (resultSet.next()) {
				ComposedPatternedPage patternedPage = new ComposedPatternedPage();
				patternedPage.setArdNum(resultSet.getInt(1));
				patternedPage.setTitle(resultSet.getString(2));
				patternedPage.setFileName(resultSet.getString(3));
				patternedPage.setFoundBySearchWords(resultSet.getString(4)
						+ "," + resultSet.getString(5));
				patternedPagesList.add(patternedPage);
			}
			statement.close();
			for (int i = 0; i < patternedPagesList.size(); i++) {
				ComposedPatternedPage patternedPage1 = (ComposedPatternedPage) patternedPagesList
						.get(i);
				for (int j = i + 1; j < patternedPagesList.size(); j++) {
					ComposedPatternedPage patternedPage2 = (ComposedPatternedPage) patternedPagesList
							.get(j);
					if (patternedPage1.getArdNum() == patternedPage2
							.getArdNum()) {
						patternedPage1.setFoundBySearchWords(patternedPage1
								.getFoundBySearchWords()
								+ "," + patternedPage2.getFoundBySearchWords());
						patternedPagesList.set(i, patternedPage1);
						patternedPagesList.remove(j);
						j--;
					}
				}
			}
		}
		catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getComposedPatternedPagesByKeywordsAndIndexedTextualSearch(): "
							+ e);
		}
		archiveConection(connection);
		return patternedPagesList;
	}

	public List<PubPage> searchForPubPages(List<String> originalKeywords,
			List<String> handledKeywords) {
		List<PubPage> pubPagesList = null;
		long time = System.currentTimeMillis();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "CREATE TEMPORARY TABLE SearchResultPubPages_"
					+ time
					+ " (ardNum smallint(6), word varchar(255)) CHARACTER SET utf8";
			System.out.println(query);
			statement
					.executeUpdate(query);
			for (String keyword : handledKeywords) {
				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultPubPages_"
						+ time
						+ " SELECT ardNum, word FROM PubPagesIndex WHERE word=\""
						+ keyword + "\";";
				System.out.println(query);
				statement.executeUpdate(query);
			}
			for (String keyword : originalKeywords) {
				keyword = keyword.replaceAll("\"", "\\\\\"");
				query = "INSERT SearchResultPubPages_"
						+ time
						+ " SELECT PubPagesKeywords.ardNum, keyword FROM PubPagesKeywords, PubPages WHERE PubPagesKeywords.ardNum=PubPages.ardNum AND keyword=\""
						+ keyword
						+ "\" AND PubPages.isDeleted=0 AND onSite=1";
				System.out.println(query);
				statement
						.executeUpdate(query);
			}
			query = "SELECT DISTINCT PubPages.ardNum, title, html, fileRepresentation, link, onSite, word, wraper, sourceToWrap"
					+ " FROM PubPages, SearchResultPubPages_"
					+ time
					+ " WHERE (SearchResultPubPages_"
					+ time
					+ ".ardNum=PubPages.ardNum) "
					+ "ORDER By title";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			pubPagesList = moveBaseParamsAndFoundBySearchWordsFromResultSetToPubPagesArray(resultSet);
			statement.close();
			for (int i = 0; i < pubPagesList.size(); i++) {
				PubPage pubPage1 = (PubPage) pubPagesList.get(i);
				for (int j = i + 1; j < pubPagesList.size(); j++) {
					PubPage pubPage2 = (PubPage) pubPagesList.get(j);
					if (pubPage1.getArdNum() == pubPage2.getArdNum()) {
						pubPage1.setFoundBySearchWords(pubPage1
								.getFoundBySearchWords()
								+ "," + pubPage2.getFoundBySearchWords());
						pubPagesList.set(i, pubPage1);
						pubPagesList.remove(j);
						j--;
					}
				}
			}
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getPubPagesByKeywordsAndIndexedTextualSearch(): "
							+ e);
		}
		archiveConection(connection);
		return pubPagesList;
	}

	public List<PubPage> moveBaseParamsAndFoundBySearchWordsFromResultSetToPubPagesArray(
			ResultSet resultSet) {
		List<PubPage> pubPagesList = new ArrayList<PubPage>();
		try {
			while (resultSet.next()) {
				PubPage pubPage = new PubPage();
				pubPage.setArdNum(resultSet.getInt("ardNum"));
				pubPage.setTitle(resultSet.getString("title"));
				pubPage.setTitleHighlighted(resultSet.getString("title"));
				pubPage.setFileRepresentation(resultSet
						.getBoolean("fileRepresentation"));
				pubPage.setLink(resultSet.getString("link"));
				pubPage.setHtml(resultSet.getString("html"));
				pubPage.setFoundBySearchWords(resultSet.getString("word"));
				pubPage.setWraper(resultSet.getBoolean("wraper"));
				pubPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
				pubPagesList.add(pubPage);
			}
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.moveBaseParamsAndFoundBySearchWordsFromResultSetToPubPagesArray(): "
							+ e);
		}
		return pubPagesList;
	}

	public String[] getDesksIds() {
		String[] desksShortNames = new String[]{""};
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT deskId FROM Desks ORDER BY appearence";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			statement.close();
			List<String> desksShortNamesList = new ArrayList<String>();
			while (resultSet.next()) {
				desksShortNamesList.add(resultSet.getString("deskId"));
			}
			desksShortNames = new String[desksShortNamesList
					.size()];
			for (int i = 0; i < desksShortNames.length; i++) {
				desksShortNames[i] = (String) desksShortNamesList.get(i);
			}

		} catch (SQLException e) {
			System.out.println("Exception while querying DesksIds: " + e);
		}
		if (desksShortNames[0].isEmpty())
			desksShortNames[0] = "No Results";
		archiveConection(connection);
		return desksShortNames;
	}

	public TabledInfoPage[] getInfoPagesByDocType(String docTypeName,
			boolean fullList, String orderBy, int from) {
		TabledInfoPage[] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet;
			String query = "";
			if (docTypeName != null) {
				 query = "SELECT * FROM InfoPages, TabledInfoPages WHERE "
						+ " InfoPages.isDeleted=0 AND InfoPages.ardNum=TabledInfoPages.ardNum AND InfoPages.hasTabledVersion=1 AND docType='"
						+ docTypeName + "'"
						+ (fullList ? ""
								: " AND ((subDate+86400000)>"
										+ now
										+ " OR (subSite='ARD' AND  (subDate+(30*86400000))>"
										+ now + " OR subDate=0))")

						+ " ORDER BY "
						+ orderBy
						+ ",title LIMIT "
						+ from
						+ "," + Utils.getResultsInPage() + ";";
			} else
				query = "SELECT * FROM InfoPages,TabledInfoPages WHERE"
								+ " InfoPages.isDeleted=0 AND InfoPages.ardNum=TabledInfoPages.ardNum ORDER BY docType";
			System.out.println(query);
			resultSet = statement.executeQuery(query);
			infoPages = moveResultSetToTabledInfoPages(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getInfoPagesByDocType(): "
							+ e);
		}
		if (infoPages == null){
			infoPages = new TabledInfoPage[1];
			infoPages[0] = new TabledInfoPage();
			infoPages[0].setTitle("No Results");
		}
		archiveConection(connection);
		return infoPages;
	}

	public String getDocTypeLastUpdate(String docType) {
		String docTypeLastUpdateDate = Utils.getWebsiteOnlineDate();
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT lastUpdate FROM DocTypes WHERE docType=\""
					+ docType + "\"";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			resultSet.next();
			docTypeLastUpdateDate = Utils.getFormatedDate(resultSet
					.getLong("lastUpdate"));
			statement.close();

		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getDocTypeLastUpdate(): "
							+ e);
		}
		archiveConection(connection);
		return docTypeLastUpdateDate;
	}

	public String getHebrewDocType(String docType) {
		String hebrewDocType = "";
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT docTypeHebrew FROM DocTypes WHERE docType=\""
					+ docType + "\"";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			if (resultSet.next())
				hebrewDocType = resultSet.getString(1);
			statement.close();
		} catch (SQLException e){
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getHebrewDocType(): "
							+ e);
		}
		archiveConection(connection);
		return hebrewDocType;
	}

	public TabledInfoPage[] getInfoPagesSortedByFundsFullNameThenByTitle() {
		TabledInfoPage [] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM InfoPages,TabledInfoPages,Funds WHERE "
					+ "InfoPages.ardNum=TabledInfoPages.ardNum AND InfoPages.fundNum=Funds.fundNum AND InfoPages.isDeleted=0 AND InfoPages.hasTabledVersion=1 ORDER BY fullName,subDate DESC;";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			infoPages = moveResultSetToTabledInfoPages(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getInfoPagesSortedByFundsFullNameThenByTitle(): "
							+ e);
		}
		if (infoPages == null){
			infoPages = new TabledInfoPage[1];
			infoPages[0] = new TabledInfoPage();
			infoPages[0].setTitle("No results");
		}
		archiveConection(connection);
		return infoPages;
	}

	public Fund[] getFundsSortedByFullName() {
		Fund [] funds = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM Funds ORDER BY fullName";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			funds =  moveFromResultSetToFund(resultSet);
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getFundsSortedByFullName(): "
							+ e);
		}
		if (funds == null){
			funds = new Fund[0];
			funds[0] = new Fund();
		}
		archiveConection(connection);
		return funds;
	}

	public TabledInfoPage[] getInfoPagesByResearchField(int researchFieldNum,
			boolean fullList, String orderBy) {
		TabledInfoPage [] infoPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			int numOfResearchFields = getRowsCount(Utils
					.getWebsiteDatabaseName(), "ResearchFields");
			if ((researchFieldNum > 0)
					&& (researchFieldNum <= numOfResearchFields)) {

				/*
				 * An example can explain this method easily: if we are looking
				 * for documents that are medicine related (research field
				 * number 1) so we are looking for documents with researchField
				 * value of 1****.... (number of digits depands on number of
				 * research fields) if we have 5 research fields : 5-1+1=5,
				 * 10pow5=100000 if (the value in researchField column for this
				 * document)%100000>=100000/10 then this document is medicine
				 * related.
				 */

				int i = (numOfResearchFields - researchFieldNum) + 1;
				int j = (int) Math.pow(10, i);
				int k = 1 * j / 10;
				String query = "SELECT * FROM InfoPages, TabledInfoPages WHERE InfoPages.isDeleted=0 AND InfoPages.hasTabledVersion=1"
					+ " AND InfoPages.ardNum=TabledInfoPages.ardNum AND researchFields%"
					+ j
					+ ">="
					+ k
					+ (fullList ? ""
							: " AND ((subDate+86400000)>"
									+ now
									+ " OR (subSite='ARD' AND  (subDate+(30*86400000))>"
									+ now + " OR subDate=0))")

					+ " ORDER BY " + orderBy + ",title";
				System.out.println(query);
				ResultSet resultSet = statement
						.executeQuery(query);
				//logger.info(query);
				infoPages = moveResultSetToTabledInfoPages(resultSet);
				statement.close();
			}
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getInfoPagesByResearchField(): "
							+ e);
		}
		if (infoPages == null){
			infoPages = new TabledInfoPage[0];
		}
		archiveConection(connection);
		return infoPages;
	}

	public String getResearchFieldLastUpdate(int researchFieldNum) {
		String researchFieldLastUpdateDate = "";
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT lastUpdate FROM ResearchFields WHERE num="
					+ researchFieldNum;
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);
			if (resultSet.next())
				researchFieldLastUpdateDate = Utils
					.getFormatedDate(resultSet.getLong("lastUpdate"));

			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getResearchFieldLastUpdate(): "
							+ e);
		}
		archiveConection(connection);
		return researchFieldLastUpdateDate;
	}

	public int getResearchFieldNumByResearchFieldName(String researchField) {
		int researchFieldNum = 0;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT num FROM ResearchFields WHERE researchField=\""
					+ researchField;
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);

			if (resultSet.next())
				researchFieldNum = resultSet.getInt("num");
			statement.close();
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getResearchFieldNumByResearchFieldName(): "
							+ e);
		}
		archiveConection(connection);
		return researchFieldNum;
	}

	public ResearchField[] getResearchFields() {
		ResearchField [] researchFields = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM ResearchFields ORDER BY num;";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<ResearchField> researchFieldsList = new ArrayList<ResearchField>();
			while (resultSet.next()) {
				ResearchField researchField = new ResearchField();
				researchField.setEnglishName(resultSet
						.getString("researchField"));
				researchField.setShortName(resultSet
						.getString("researchFieldShort"));
				researchField.setHebrewName(resultSet
						.getString("researchFieldHebrew"));
				researchField.setExperimental(resultSet
						.getBoolean("experimental"));
				researchFieldsList.add(researchField);
			}
			statement.close();
			researchFields = new ResearchField[researchFieldsList.size()];
			for (int i = 0; i < researchFields.length; i++) {
				researchFields[i] = (ResearchField) researchFieldsList.get(i);
			}
		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.profiles.ProfilesDbHandler.getResearchFields(): "
							+ e);
		}
		if (researchFields == null){
			researchFields = new ResearchField[1];
			researchFields[0] = new ResearchField();
		}
		archiveConection(connection);
		return researchFields;
	}
}
