package huard.iws.db;

import huard.iws.model.Abstract;
import huard.iws.model.Attachment;
import huard.iws.model.RegistrationForm;
import huard.iws.util.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcRegistrationFormDao extends SimpleJdbcDaoSupport implements RegistrationFormDao {
	
	Logger logger = Logger.getLogger("JdbcCallForProposalDao");

	public RegistrationForm getRegistrationForm(int id){
		try{
			String query = "select * from registrationForm where id =?";
			RegistrationForm registrationForm =
				getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			logger.debug(query+ id);
			System.out.println("111111111111111111111:"+query+ id);
			applyFiles(registrationForm);
			return 	registrationForm;	
		}
		catch(Exception e){
			return new RegistrationForm();
		}
	}
	


	private void applyFiles(RegistrationForm registrationForm){
		String query = "select * from registrationFormFile where registrationFormId = ?";
		List<Abstract> files =  getSimpleJdbcTemplate().query(query, filesRowMapper, registrationForm.getId());
		registrationForm.setAttachments(files);
	}
	

	private ParameterizedRowMapper<Abstract> filesRowMapper = new ParameterizedRowMapper<Abstract>(){
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
	
	
	public int insertRegistrationForm(RegistrationForm registrationForm){
		final String query = "insert ignore registrationForm set updateTime=now();";
		logger.debug(query);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey()==null?0:keyHolder.getKey().intValue();
	}
	

	public void updateRegistrationForm(RegistrationForm registrationForm){
		String query = "update registrationForm set " +
				" title = ?" +
				", firstName = ?" +
				", lastName = ?" +
				", department = ?" +
				", superviser = ?" +
				", phone = ?" +
				", mobile = ?" +
				", fax = ?" +
				", contactEmail = ?" +
				", oneDay = ?" +
				", needAccomodation = ?" +
				", bus = ?" +
				", returnBus = ?" +
				", firstRoommate = ?" +
				", secondRoommate = ?" +
				", roomType = ?" +
				", updateTime = ?" +
			" where id = ?;";
		logger.debug(query);

		
		getSimpleJdbcTemplate().update(query,
				registrationForm.getTitle(),
				registrationForm.getFirstName(),
				registrationForm.getLastName(),
				registrationForm.getDepartment(),
				registrationForm.getSuperviser(),
				registrationForm.getPhone(),
				registrationForm.getMobile(),
	    		registrationForm.getFax(),
	    		registrationForm.getContactEmail(),
	    		registrationForm.isOneDay(),
	    		registrationForm.isNeedAccomodation(),
	    		registrationForm.isBus(),
	    		registrationForm.isReturnBus(),
	    		registrationForm.getFirstRoommate(),
	    		registrationForm.getSecondRoommate(),
	    		registrationForm.isRoomTypeDouble(),
	    		SQLUtils.getTimestampString(System.currentTimeMillis()),
				registrationForm.getId());
	
	}
	
	
	public List<RegistrationForm> getRegistrationForms(){
		String query  = "select * from registrationForm";
		logger.debug(query);
		List<RegistrationForm> registrationForms = getSimpleJdbcTemplate().query(query, rowMapper);
		return registrationForms;
	}
	
	private ParameterizedRowMapper<RegistrationForm> rowMapper = new ParameterizedRowMapper<RegistrationForm>(){
		public RegistrationForm mapRow(ResultSet rs, int rowNum) throws SQLException{
			RegistrationForm registrationForm = new RegistrationForm();
			registrationForm.setId(rs.getInt("id"));
			registrationForm.setTitle(rs.getString("title"));
			registrationForm.setFirstName(rs.getString("firstName"));
			registrationForm.setLastName(rs.getString("lastName"));
			registrationForm.setDepartment(rs.getString("department"));
			registrationForm.setSuperviser(rs.getString("superviser"));
			registrationForm.setPhone(rs.getString("phone"));
			registrationForm.setMobile(rs.getString("mobile"));
			registrationForm.setFax(rs.getString("fax"));
			registrationForm.setContactEmail(rs.getString("contactEmail"));
			registrationForm.setOneDay(rs.getBoolean("oneDay"));
			registrationForm.setNeedAccomodation(rs.getBoolean("needAccomodation"));
			registrationForm.setBus(rs.getBoolean("bus"));
			registrationForm.setReturnBus(rs.getBoolean("returnBus"));
			registrationForm.setFirstRoommate(rs.getString("firstRoommate"));
			registrationForm.setSecondRoommate(rs.getString("secondRoommate"));
			registrationForm.setRoomTypeDouble(rs.getBoolean("roomType"));
			long updateTime = 0;
			Timestamp updateTimeTS = rs.getTimestamp("updateTime");
			if (updateTimeTS != null)
				updateTime = updateTimeTS.getTime();
			registrationForm.setUpdateTime(updateTime);
           return registrationForm;
        }
	};
	
	public int insertAttachmentToRegistrationForm(int registrationFormId, Abstract attachment){
		final String query  = "insert registrationFormFile set registrationFormId = ?, attachment = ?, attachmentContentType = ?,"
				+ "filename = ?, methodPresentation=?, subject=?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final int finalRegistrationFormId= registrationFormId;
		final byte[] file= attachment.getFile();
		final String contentType = attachment.getContentType();
		final String filename = attachment.getFilename();
		final boolean methodPresentation = attachment.getMethodPresentation();
		final String subject = attachment.getSubject();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, finalRegistrationFormId);
						ps.setBytes(2, file);
						ps.setString(3, contentType);
						ps.setString(4, filename);
						ps.setBoolean(5, methodPresentation);
						ps.setString(6, subject);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void deleteFile(int id){
		String query = "delete from registrationFormFile where id = ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
}
