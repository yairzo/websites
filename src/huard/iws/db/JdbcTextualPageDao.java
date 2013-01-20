package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.CallOfProposal;
import huard.iws.model.Category;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
		String query = "select * from textualPage where textualPageId =?";
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
		final String query = "insert textualPageDraft set title='', creatorId = ?, html='', description='', updateTime=now();";
		logger.info(query);
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
		return keyHolder.getKey().intValue();
	}
	
	public void insertTextualPageOnline(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(textualPage.getKeepInRollingMessagesExpiryTime()).toString();
		final String query = "insert textualPage set textualPageId = ?"+
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
				", isMessage = ?" +
				", keepInRollingMessagesExpiryTime = ?" +
				", updateTime = now()";
		logger.info(query);
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
	    		textualPage.getIsMessage(),
	    		keepInRollingMessagesExpiryTime);
	}
	
	public void updateTextualPage(TextualPage textualPage){
		String keepInRollingMessagesExpiryTime="";
		if(textualPage.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(textualPage.getKeepInRollingMessagesExpiryTime()).toString();
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
				", keepInRollingMessagesExpiryTime = ?" +
				", updateTime = now()" +
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
	    		keepInRollingMessagesExpiryTime,	    		
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
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(textualPage.getKeepInRollingMessagesExpiryTime()).toString();
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
				", keepInRollingMessagesExpiryTime = ?" +
				", updateTime = now()" +
				" where textualPageId = ?;";
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
	    		keepInRollingMessagesExpiryTime,	    		
	    		textualPage.getId());
	
	}	
	
	public void removeTextualPageOnline(int id){
		String query = "delete from textualPage where textualPageId= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<TextualPage> getTextualPages(){
		String query = "select * from textualPageDraft order by id";
		System.out.println(query);
		List<TextualPage> textualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return textualPages;
	}

	public List<TextualPage> getOnlineTextualPages(){
		String query = "select * from textualPage order by id";
		System.out.println(query);
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
	

}
