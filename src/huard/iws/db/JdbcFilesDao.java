package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import huard.iws.model.Abstract;
import huard.iws.model.Attachment;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcFilesDao extends SimpleJdbcDaoSupport implements FilesDao{
	
	public Attachment getTextualPageFile(String filename){
		String query = "select * from textualPageFile where filename = ?";
		logger.debug(query);
		Attachment attachment = getSimpleJdbcTemplate().queryForObject(query, attachmentRowMapper, filename);
		return attachment;
	}
	
	public Attachment getCallForProposalFile(String filename){
		String query = "select * from callForProposalFile where filename = ?";
		logger.debug(query);
		Attachment attachment = getSimpleJdbcTemplate().queryForObject(query, attachmentRowMapper, filename);
		return attachment;
	}
	
	public static ParameterizedRowMapper<Attachment> attachmentRowMapper = new ParameterizedRowMapper<Attachment>(){
		public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
			Attachment file = new Attachment();
			file.setId(rs.getInt("id"));
			file.setFile(rs.getBytes("attachment"));			
			file.setContentType(rs.getString("attachmentContentType"));
			file.setTitle(rs.getString("title"));
			file.setFilename(rs.getString("filename"));
            return file;
		}
	};

	public Abstract getAbstractFile(String filename){
		String query = "select * from registrationFormFile where filename = ?";
		logger.debug(query);
		Abstract attachment = getSimpleJdbcTemplate().queryForObject(query, abstractRowMapper, filename);
		return attachment;
	}
	
	public static ParameterizedRowMapper<Abstract> abstractRowMapper = new ParameterizedRowMapper<Abstract>(){
		public Abstract mapRow(ResultSet rs, int rowNum) throws SQLException{
			Abstract file = new Abstract();
			file.setId(rs.getInt("id"));
			file.setFile(rs.getBytes("attachment"));			
			file.setContentType(rs.getString("attachmentContentType"));
			file.setFilename(rs.getString("filename"));
			file.setSubject(rs.getString("subject"));
			file.setMethodPresentation(rs.getBoolean("methodPresentation"));
            return file;
		}
	};

}
