package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.CallOfProposal;
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
		final String query = "insert textualPageDraft set title='', creatorId = ?;";
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
		final String query = "insert textualPage set textualPageId = ?"+
				", title = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showImage = ?" +
				", imageUrl = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getId(),
				textualPage.getTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowImage(),
	    		textualPage.getImageUrl(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl());
	}
	
	public void updateTextualPage(TextualPage textualPage){
		String query = "update textualPageDraft set " +
				" title = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showImage = ?" +
				", imageUrl = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?" +
			" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowImage(),
	    		textualPage.getImageUrl(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl(),
	    		textualPage.getId());
		
		if (textualPage.getAttachment() != null && textualPage.getAttachment().getFile().length>0){
			query = "delete from textualPageFile where textualPageId = ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId());
			query  = "insert textualPageFile set textualPageId = ?, attachment = ?, attachmentContentType= ?";
			getSimpleJdbcTemplate().update(query,textualPage.getId(), textualPage.getAttachment().getFile(), textualPage.getAttachment().getContentType());
		}

	}
	
	public void updateTextualPageOnline(TextualPage textualPage){
		String query = "update textualPage set " +
				" title = ?" +
				", creatorId = ?" +
				", deskId = ?" +
				", requireLogin = ?" +
				", html = ?" + 
				", description = ?" +
				", showImage = ?" +
				", imageUrl = ?" +
				", showFile = ?" +
				", fileUrl = ?" +
				", wrapExternalPage = ?" +
				", externalPageUrl = ?" +
				" where callOfProposalId = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				textualPage.getTitle(),
				textualPage.getCreatorId(),
				textualPage.getDeskId(),
	    		textualPage.getRequireLogin(),
	    		textualPage.getHtml(),
	    		textualPage.getDescription(),
	    		textualPage.getShowImage(),
	    		textualPage.getImageUrl(),
	    		textualPage.getShowFile(),
	    		textualPage.getFileUrl(),
	    		textualPage.getWrapExternalPage(),
	    		textualPage.getExternalPageUrl(),
	    		textualPage.getId());
	
	}	
	
	public void removeTextualPageOnline(int id){
		String query = "delete from textualPage where textualPageId= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<TextualPage> getTextualPages(){
		String query = "select * from textualPageDraft order by title";
		System.out.println(query);
		List<TextualPage> TextualPages = getSimpleJdbcTemplate().query(query, rowMapper);
		return TextualPages;
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
     		textualPage.setShowImage(rs.getBoolean("showImage"));
     		textualPage.setImageUrl(rs.getString("imageUrl"));
     		textualPage.setShowFile(rs.getBoolean("showFile"));
     		textualPage.setFileUrl(rs.getString("fileUrl"));
     		textualPage.setWrapExternalPage(rs.getBoolean("wrapExternalPage"));
     		textualPage.setExternalPageUrl(rs.getString("externalPageUrl"));
           return textualPage;
        }
	};
	

}
