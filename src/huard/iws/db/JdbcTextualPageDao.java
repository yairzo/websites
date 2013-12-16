package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.model.TextualPageOld;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.TextualPageSearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcTextualPageDao extends SimpleJdbcDaoSupport implements TextualPageDao {

	public TextualPage getTextualPage(int id){
		try{
			String query = "select * from textualPageDraft where id =?";
			TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			getTextualPageFile(textualPage);
			return textualPage;	
		}
		catch(Exception e){
			return new TextualPage();
		}
	}
	
	public TextualPage getTextualPageOnline(int id){
		try{
			String query = "select * from textualPage where id =?";
			TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			getTextualPageFile(textualPage);
			return 	textualPage;	
		}
		catch(Exception e){
			return new TextualPage();
		}
	}

	public TextualPage getTextualPageOnline(String urlTitle){
		String query = "select * from textualPage where urlTitle = ?";
		logger.info(query + " urlTitle: " + urlTitle);
		
		TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, urlTitle);
		getTextualPageFile(textualPage);
		return 	textualPage;	
	}
	
	public String getTextualPageUrlTitleByArdNum(int ardNum){
		String query = "select urlTitle from textualPage inner join textualPageHistoryId on" +
				" textualPage.id=textualPageHistoryId.textualPageId where textualPageHistoryId.textualPageHistoryId =?";
		try{
			return getSimpleJdbcTemplate().queryForObject(query, String.class, ardNum);
		}
		catch(Exception e){
			return "";
		}
	}

	private void getTextualPageFile(TextualPage textualPage){
		String query = "select * from textualPageFile where pageId = ?";
		try{
		Attachment attachment =  getSimpleJdbcTemplate().queryForObject(query, JdbcFilesDao.attachmentRowMapper, textualPage.getId());
		if(attachment!=null)
			textualPage.setAttachment(attachment);
		}
		catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	
	
	public boolean existsTextualPageOnline(int id){
		String query = "select * from textualPage where id =?";
		try{
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public TextualPage getTextualPage(String title){
		String query = "select * from textualPageDraft where title =?";
		TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, title);
		getTextualPageFile(textualPage);
		return 	textualPage;	
	}

	public int insertTextualPage(TextualPage textualPage){
		if(textualPage.getTitle().isEmpty())
			textualPage.setTitle("###" + new java.util.Date().getTime() + "###");
		if(textualPage.getUrlTitle().isEmpty())
			textualPage.setUrlTitle("###" + textualPage.getTitle() + "###");

		final String query = "insert ignore textualPageDraft set title='" + textualPage.getTitle() + "' ,urlTitle=?, creatorId = ?, html='', description='', updateTime=now(), localeId=?;";
		//logger.info(query);
		final String urlTitle= textualPage.getUrlTitle();
		final int creatorId= textualPage.getCreatorId();
		final String localeId= textualPage.getLocaleId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						ps.setString(1, urlTitle);
						ps.setInt(2, creatorId);
						ps.setString(3, localeId);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey()==null?0:keyHolder.getKey().intValue();
	}
	
	public void insertTextualPageOnline(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(textualPage.getKeepInRollingMessagesExpiryTime());
		final String query = "insert textualPage set id = ?"+
				", title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?" +
				", categoryId = ?" +
				", isMessage = ?" +
				", messageType = ?" +
				", keepInRollingMessagesExpiryTime = ?" +
				", neverExpires= ?" +
				", updateTime = now()"+
				", isDeleted = ?"+
				", localeId = ?";
		//logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getId(),
				textualPage.getTitle(),
				textualPage.getUrlTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl(),
	    		textualPage.getCategoryId(),
	    		textualPage.getIsMessage(),
	    		textualPage.getMessageType(),
	    		keepInRollingMessagesExpiryTime,
	    		textualPage.getNeverExpires(),
	    		textualPage.getIsDeleted(),
	    		textualPage.getLocaleId());
	}
	
	public void updateTextualPage(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(textualPage.getKeepInRollingMessagesExpiryTime());
		String query = "update textualPageDraft set " +
				" title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?" +
				", categoryId = ?" +
				", isMessage = ?" +
				", messageType = ?" +
				", keepInRollingMessagesExpiryTime = ?" +
				", neverExpires= ?" +
				", updateTime = now()" +
				", isDeleted = ?" +
				", localeId = ?" +
			" where id = ?;";
		//logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
				textualPage.getUrlTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl(),
	    		textualPage.getCategoryId(),
	    		textualPage.getIsMessage(),
	    		textualPage.getMessageType(),
	    		keepInRollingMessagesExpiryTime,
	    		textualPage.getNeverExpires(),
	    		textualPage.getIsDeleted(),
	    		textualPage.getLocaleId(),
	    		textualPage.getId());
		
		if (textualPage.getAttachment() != null && textualPage.getAttachment().getFile()!=null){
			query = "delete from textualPageFile where pageId = ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId());
			query  = "insert textualPageFile set pageId = ?, attachment = ?, attachmentContentType = ?, title = ?, filename = ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId(), textualPage.getAttachment().getFile(),
					textualPage.getAttachment().getContentType(), textualPage.getAttachment().getTitle(),
					textualPage.getAttachment().getFilename());
		}
	}
	
	public void updateTextualPageOnline(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(textualPage.getKeepInRollingMessagesExpiryTime());
		String query = "update textualPage set " +
				" title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?" +
				", categoryId = ?" +
				", isMessage = ?" +
				", messageType = ?" +
				", keepInRollingMessagesExpiryTime = ?" +
				", neverExpires= ?" +
				", updateTime = now()" +
				", isDeleted = ?" +
				", localeId = ?" +
				" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
				textualPage.getUrlTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl(),
	    		textualPage.getCategoryId(),
	    		textualPage.getIsMessage(),
	    		textualPage.getMessageType(),
	    		keepInRollingMessagesExpiryTime,
	    		textualPage.getNeverExpires(),
	    		textualPage.getIsDeleted(),
	    		textualPage.getLocaleId(),
	    		textualPage.getId());
	}	
	
	public void removeTextualPageOnline(int id){
		String query = "delete from textualPage where id= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<TextualPage> getTextualPages(ListView lv,TextualPageSearchCreteria searchCreteria){
		String query = "select * from textualPageDraft";
		query += getWhereClause(searchCreteria);
		query += " limit "+ (lv.getPage()-1) * lv.getRowsInPage() + "," + lv.getRowsInPage();
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}
	public int countTextualPages(ListView lv,TextualPageSearchCreteria searchCreteria) {
		String query = "select count(*) from textualPageDraft";
		query += getWhereClause(searchCreteria);
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public int countTextualPagesByUrlTitle(int id,String urlTitle){
		String query = "select count(*) from textualPageDraft where urlTitle='" + urlTitle +"' and id<>"+ id;
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	public int countTextualPagesByTitle(int id,String title){
		String query = "select count(*) from textualPageDraft where title='" + title +"' and id<>"+ id;
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public String getWhereClause(TextualPageSearchCreteria searchCreteria){
		String whereClause="";
		whereClause += " where true";
		if(searchCreteria.getSearchByCreator()>0)
			whereClause +=" and creatorId="+searchCreteria.getSearchByCreator();
		if(searchCreteria.getSearchDeleted())
			whereClause +=" and isDeleted=1";
		if(searchCreteria.getSearchList())
			whereClause +=" and wrapExternalPage=1";
		if(!searchCreteria.getSearchBySearchWords().isEmpty())
			whereClause +=" and id in ("+searchCreteria.getSearchBySearchWords() + ")";
		whereClause += "  order by id desc";
		logger.info(whereClause);
		return whereClause;
	}

	public List<TextualPage> getOnlineTextualPages(){
		String query = "select * from textualPage where isDeleted=0 order by id";
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}
	public List<TextualPage> getOnlineMessages(String localeId){
		String query = "select * from textualPage where isDeleted=0 and isMessage=1 and localeId= ? order by id";
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper,localeId);
		return textualPages;
	}
	public List<TextualPage> getOnlineMessagesRolling(String localeId){
		String query = "select * from textualPage where isDeleted=0 and isMessage=1 and (neverExpires=1 or keepInRollingMessagesExpiryTime >= now()) and localeId= ? order by id";
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper, localeId);
		return textualPages;
	}

	public List<TextualPage> getOnlineTextualPagesSearch(String ids){
		String query  = "select distinct textualPage.* from textualPage where isMessage=0 and isDeleted=0 ";
		if(!ids.isEmpty())
			query += " and id in ("+ids+")";
		query += " order by id";
		logger.info(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}
	public List<TextualPage> getOnlineMessagesSearch(String ids){
		String query  = "select distinct textualPage.* from textualPage where isMessage=1 and isDeleted=0 ";
		if(!ids.isEmpty())
			query += " and id in ("+ids+")";
		query += " order by id";
		logger.info(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}

	private ParameterizedRowMapper<TextualPage> rowMapper = new ParameterizedRowMapper<TextualPage>(){
		public TextualPage mapRow(ResultSet rs, int rowNum) throws SQLException{
			TextualPage textualPage = new TextualPage();
			textualPage.setId(rs.getInt("id"));
			textualPage.setTitle(rs.getString("title"));
			textualPage.setUrlTitle(rs.getString("urlTitle"));
			textualPage.setCreatorId(rs.getInt("creatorId"));
			long creationTime = 0;
			Timestamp creationTimeTS = rs.getTimestamp("creationTime");
			if (creationTimeTS != null)
				creationTime = creationTimeTS.getTime();
			textualPage.setCreationTime(creationTime);
			textualPage.setDeskId(rs.getInt("deskId"));
     		textualPage.setRequireLogin(rs.getBoolean("requireLogin"));
     		textualPage.setHtml(rs.getString("html"));
     		textualPage.setDescription(rs.getString("description"));
     		textualPage.setShowFile(rs.getBoolean("showFile"));
     		textualPage.setFileUrl(rs.getString("fileUrl"));
     		textualPage.setWrapExternalPage(rs.getBoolean("wrapExternalPage"));
     		textualPage.setExternalPageUrl(rs.getString("externalPageUrl"));
     		textualPage.setCategoryId(rs.getInt("categoryId"));
     		textualPage.setIsMessage(rs.getBoolean("isMessage"));
     		textualPage.setMessageType(rs.getInt("messageType"));
     		long keepInRollingMessagesExpiryTime = 0;
			Timestamp keepInRollingMessagesExpiryTimeTS = rs.getTimestamp("keepInRollingMessagesExpiryTime");
			if (keepInRollingMessagesExpiryTimeTS != null)
				keepInRollingMessagesExpiryTime = keepInRollingMessagesExpiryTimeTS.getTime();
			textualPage.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
			textualPage.setNeverExpires(rs.getBoolean("neverExpires"));
			long updateTime = 0;
			Timestamp updateTimeTS = rs.getTimestamp("updateTime");
			if (updateTimeTS != null)
				updateTime = updateTimeTS.getTime();
			textualPage.setUpdateTime(updateTime);
			textualPage.setLocaleId(rs.getString("localeId"));
           return textualPage;
        }
	};
	
	public void addTemplate(Template template){
		String query = "insert textualPageTemplate set title = ?" +
				", template = ?" + 
				", updateTime = now()" +
				", personId = ?" +
				", modifierId = ?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				template.getTitle(),
				template.getTemplate(),
				template.getPersonId(),
				template.getModifierId());
	}
	
	public void updateTemplate(Template template){
		String query = "update textualPageTemplate set title = ?" +
				", template = ?" + 
				", updateTime = now()" +
				", modifierId = ?" + "" +
				"  where id=?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				template.getTitle(),
				template.getTemplate(),
				template.getModifierId(),
				template.getId());
	}
	
	public Template getTemplate(int id){
		String query = "select * from textualPageTemplate where id = ?";
		logger.info(query);
		Template template = getSimpleJdbcTemplate().queryForObject(query,templateRowMapper,id);
		return template;
	}
	
	private ParameterizedRowMapper<Template> templateRowMapper = new ParameterizedRowMapper<Template>(){
		public Template mapRow(ResultSet rs, int rowNum) throws SQLException{
			Template template = new Template();
			template.setId(rs.getInt("id"));
			template.setTitle(rs.getString("title"));
			template.setTemplate(rs.getString("template"));
            return template;
		}
	};
	
	public List<Template> getTemplates(){
		String query = "select * from textualPageTemplate;";
		logger.info(query);
		List<Template> templates = getSimpleJdbcTemplate().query(query,templateRowMapper);
		return templates;
	}
	
	public List<TextualPageOld> getTextualPagesOldWebsite(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages left join PubPagesLastUpdates on PubPages.ardNum = PubPagesLastUpdates.ardNum WHERE isDeleted=0 and PubPages.ardNum<>253;";
			ResultSet resultSet = statement.executeQuery(query);
			return moveResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	public List<TextualPageOld> moveResultSetToPubPage(ResultSet resultSet){
		try{
			List<TextualPageOld> pubPages = new ArrayList<TextualPageOld>();
			while (resultSet.next()){
				TextualPageOld pubPage = new TextualPageOld();
				pubPage.setId(resultSet.getInt("ardNum"));
				pubPage.setTitle(resultSet.getString("title"));
				pubPage.setHtml(resultSet.getString("html"));
				pubPage.setDocType(resultSet.getString("docType"));
				pubPage.setPubDate(resultSet.getLong("pubDate"));
				pubPage.setDeskId(resultSet.getString("deskId"));
				pubPage.setRestricted(resultSet.getBoolean("restricted"));
				pubPage.setMessage(resultSet.getInt("message")==1);
				pubPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
				pubPage.setFileRepresentation(resultSet.getBoolean("fileRepresentation"));
				pubPage.setLink(resultSet.getString("link"));
				pubPage.setInternalUseDescription(resultSet.getString("internalUseDescription"));
				pubPage.setWraper(resultSet.getBoolean("wraper"));
				pubPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
				pubPage.setOnSite(resultSet.getBoolean("onSite"));
				pubPage.setCategory(resultSet.getString("category"));
				pubPage.setUpdateTime(resultSet.getLong("date"));
				pubPages.add(pubPage);
			}
			return pubPages;
		}
		catch (SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public void insertArdNum(int ardNum,int id){
		String query  = "insert textualPageHistoryId set textualPageId = ?, textualPageHistoryId = ?";
		getSimpleJdbcTemplate().update(query,id, ardNum);
	}
	
	public Timestamp getTextualPagesLastUpdate(){
		String query = "select updateTime from textualPage order by updateTime desc limit 1";
		return getSimpleJdbcTemplate().queryForObject(query, new ParameterizedRowMapper<Timestamp>() {
			@Override
			public Timestamp mapRow(ResultSet r, int arg1)
					throws SQLException {
				Timestamp lastUpdate = r.getTimestamp("updateTime");
				return lastUpdate;
			}			
		});
	}

	
}
