package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.model.TextualPageOld;
import huard.iws.util.DateUtils;
import huard.iws.util.TextualPageSearchCreteria;
import huard.iws.util.ListView;

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
		String query = "select * from textualPageDraft where id =?";
		TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		getTextualPageFile(textualPage);
		return 	textualPage;	
	}
	
	public TextualPage getTextualPageOnline(int id){
		String query = "select * from textualPage where id =?";
		TextualPage textualPage =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		getTextualPageFile(textualPage);
		return 	textualPage;	
	}
	
	private void getTextualPageFile(TextualPage textualPage){
		String query = "select * from textualPageFile where textualPageId = ?";
		try{
		Attachment attachment =  getSimpleJdbcTemplate().queryForObject(query, attachmentRowMapper, textualPage.getId());
		if(attachment!=null)
			textualPage.setAttachment(attachment);
		}
		catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	private ParameterizedRowMapper<Attachment> attachmentRowMapper = new ParameterizedRowMapper<Attachment>(){
		public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
			Attachment file = new Attachment();
			file.setFile(rs.getBytes("attachment"));
			file.setContentType(rs.getString("attachmentContentType"));
			file.setId(rs.getInt("id"));
            return file;
		}
	};
	
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

		final String query = "insert ignore textualPageDraft set title='" + textualPage.getTitle() + "', creatorId = ?, html='', description='', updateTime=now();";
		//logger.info(query);
		final int creatorId= textualPage.getCreatorId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, creatorId);
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
				", updateTime = now()"+
				", isDeleted = ?";
		//logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getId(),
				textualPage.getTitle(),
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
	    		textualPage.getIsDeleted());
	}
	
	public void updateTextualPage(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(textualPage.getKeepInRollingMessagesExpiryTime());
		String query = "update textualPageDraft set " +
				" title = ?" +
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
				", updateTime = now()" +
				", isDeleted = ?" +
			" where id = ?;";
		//logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
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
	    		textualPage.getIsDeleted(),
	    		textualPage.getId());
		
		if (textualPage.getAttachment() != null && textualPage.getAttachment().getFile()!=null){
			query = "delete from textualPageFile where textualPageId = ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId());
			query  = "insert textualPageFile set textualPageId = ?, attachment = ?, attachmentContentType= ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId(), textualPage.getAttachment().getFile(), textualPage.getAttachment().getContentType());
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
				", updateTime = now()" +
				", isDeleted = ?" +
				" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
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
	    		textualPage.getIsDeleted(),
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
	public String getWhereClause(TextualPageSearchCreteria searchCreteria){
		String whereClause="";
		whereClause += " where true";
		if(searchCreteria.getSearchByCreator()>0)
			whereClause +=" and creatorId="+searchCreteria.getSearchByCreator();
		if(!searchCreteria.getSearchDeleted())//not include deleted
			whereClause +=" and isDeleted=0";
		if(!searchCreteria.getSearchBySearchWords().isEmpty())
			whereClause +=" and id in ("+searchCreteria.getSearchBySearchWords() + ")";
		whereClause += "  order by id";
		logger.info(whereClause);
		return whereClause;
	}

	public List<TextualPage> getOnlineTextualPages(){
		String query = "select * from textualPage where isDeleted=0 order by id";
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}
	
	public List<TextualPage> getOnlineTextualPagesSearch(String ids){
		String query  = "select distinct textualPage.* from textualPage";
		if(!ids.isEmpty())
			query += " where id in ("+ids + ") and isDeleted=0 order by id";
		logger.info(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}

	private ParameterizedRowMapper<TextualPage> rowMapper = new ParameterizedRowMapper<TextualPage>(){
		public TextualPage mapRow(ResultSet rs, int rowNum) throws SQLException{
			TextualPage textualPage = new TextualPage();
			textualPage.setId(rs.getInt("id"));
			textualPage.setTitle(rs.getString("title"));
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
			long updateTime = 0;
			Timestamp updateTimeTS = rs.getTimestamp("updateTime");
			if (updateTimeTS != null)
				updateTime = updateTimeTS.getTime();
			textualPage.setUpdateTime(updateTime);
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

	
}
