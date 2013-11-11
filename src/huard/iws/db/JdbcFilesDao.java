package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import huard.iws.model.Attachment;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcFilesDao extends SimpleJdbcDaoSupport implements FilesDao{
	
	public Attachment getTextualPageFile(String filename){
		String query = "select * from textualPageFile where filename = ?";
		Attachment attachment = getSimpleJdbcTemplate().queryForObject(query, attachmentRowMapper, filename);
		return attachment;
	}
	
	public static ParameterizedRowMapper<Attachment> attachmentRowMapper = new ParameterizedRowMapper<Attachment>(){
		public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
			Attachment file = new Attachment();
			file.setFile(rs.getBytes("attachment"));
			file.setContentType(rs.getString("attachmentContentType"));
			file.setFilename(rs.getString("filename"));
			file.setId(rs.getInt("id"));
            return file;
		}
	};


}