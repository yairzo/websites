package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.MailMessage;
import huard.iws.util.DateUtils;

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

public class JdbcMailMessageDao extends SimpleJdbcDaoSupport implements MailMessageDao {

	public MailMessage getMailMessage(int id){
		String query = "select * from mailMessage where id=?";
		MailMessage mailMessage =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		mailMessage.setRecipientsPersonsIds(getRecipientsPersonIds (mailMessage.getId()));

		List<Integer> recipientsPersonsIds = getRecipientsPersonIds (mailMessage.getId());
		mailMessage.setRecipientsPersonsIds(recipientsPersonsIds);

		List <Attachment> attachments = getAttachments (mailMessage.getId());
		mailMessage.setAttachments(attachments);

		return mailMessage;
	}

	private ParameterizedRowMapper<MailMessage> rowMapper = new ParameterizedRowMapper<MailMessage>(){
		public MailMessage mapRow(ResultSet rs, int rowNum) throws SQLException{
            MailMessage mailMessage = new MailMessage();
            mailMessage.setId(rs.getInt("id"));
            mailMessage.setListId(rs.getInt("listId"));
            mailMessage.setMessageSubject(rs.getString("messageSubject"));
            mailMessage.setMessage(rs.getString("message"));
            mailMessage.setSenderPersonId(rs.getInt("senderPersonId"));
            mailMessage.setAdditionalAddresses(rs.getString("additionalAddresses"));
            mailMessage.setCc(rs.getString("cc"));
            mailMessage.setBcc(rs.getString("bcc"));
            mailMessage.setSendTime(rs.getTimestamp("sendTime"));
            return mailMessage;
        }
	};

	public List<MailMessage> getMailMessages(){
		String query = "select * from mailMessage";
		List<MailMessage> mailMessages =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return mailMessages;
	}

	public void updateMailMessage(MailMessage mailMessage){
		final String query = "update mailMessage set listId = ?, messageSubject = ?" +
		", message = ?, senderPersonId = ?, additionalAddresses = ?, cc = ?, bcc = ?, sendTime = ? where id = ?;";
		getSimpleJdbcTemplate().update(query,
				mailMessage.getListId(),
				mailMessage.getMessageSubject(),
				mailMessage.getMessage(),
				mailMessage.getSenderPersonId(),
				mailMessage.getAdditionalAddresses(),
				mailMessage.getCc(),
				mailMessage.getBcc(),
				mailMessage.getSendTime(),
				mailMessage.getId()
		);
		this.deleteMailMessageRecipients(mailMessage.getId());
		this.insetMailMessageRecipients(mailMessage);
		this.updateAttachments(mailMessage);
	}

	public int insertMailMessage(MailMessage mailMessage){
		final String query = "insert mailMessage set listId = ?, messageSubject = ?" +
				", message = ?, senderPersonId = ?, additionalAddresses = ?, cc = ?, bcc = ?, sendTime = ?;";
		final int listId = mailMessage.getListId();
		final String messageSubject = mailMessage.getMessageSubject();
		final String message = mailMessage.getMessage();
		final int senderPersonId = mailMessage.getSenderPersonId();
		final String additionalAddresses = mailMessage.getAdditionalAddresses();
		final String cc = mailMessage.getCc();
		final String bcc = mailMessage.getBcc();
		final Timestamp sendTime = DateUtils.getCurrentTime();

		this.insetMailMessageRecipients(mailMessage);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1, listId);
		            ps.setString(2, messageSubject);
		            ps.setString(3, message);
		            ps.setInt(4, senderPersonId);
		            ps.setString(5, additionalAddresses);
		            ps.setString(6, cc);
		            ps.setString(7, bcc);
		            ps.setTimestamp(8, sendTime);
		            return ps;
		        }
		    },
		    keyHolder);
		mailMessage.setId(keyHolder.getKey().intValue());
		this.updateAttachments(mailMessage);
		return keyHolder.getKey().intValue();
	}

	public void deleteMailMessageRecipients(int mailMessageId){
		String query="delete from mailMessageRecipient where mailMessageId = ?;";
		getSimpleJdbcTemplate().update(query, mailMessageId);

	}

	public void insetMailMessageRecipients(MailMessage mailMessage){
		String query="insert mailMessageRecipient set mailMessageId = ?, personId = ?;";
		for (Integer recipientPersonId: mailMessage.getRecipientsPersonsIds() ){
			getSimpleJdbcTemplate().update(query,
				mailMessage.getId(),
				recipientPersonId );
		}
	}

	private List<Integer> getRecipientsPersonIds (int mailMessageId){
		String query = "select * from mailMessageRecipient where mailMessageId=?";
		List<Integer> recipientsPersonIds  =
			getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
		            return rs.getInt("personId");
		        }

			}, mailMessageId);
		return recipientsPersonIds;

	}

	private List<Attachment> getAttachments (int mailMessageId){
		String query = "select * from mailMessageAttach where mailMessageId = ?";
		final int aMailMessageId = mailMessageId;
		List <Attachment> attachments = getSimpleJdbcTemplate().query (query,
				new ParameterizedRowMapper<Attachment>(){
					public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
						Attachment attachment = new Attachment();
						attachment.setParentId(aMailMessageId);
						attachment.setTitle(rs.getString("title"));
						attachment.setFile(rs.getBytes("file"));
						attachment.setContentType(rs.getString("contentType"));
						attachment.setPlace(rs.getInt("place"));
						return attachment;
					}

		}, mailMessageId);
		return attachments;
	}

	public void updateAttachments(MailMessage mailMessage){
		String query = "delete from mailMessageAttach where mailMessageId = ?";
		getSimpleJdbcTemplate().update(query, mailMessage.getId());
		query = "insert mailMessageAttach set mailMessageId = ?, title = ?, file = ?, contentType = ?, place = ?";
		final int mailMessageId = mailMessage.getId();
		for ( Attachment attachment : mailMessage.getAttachments()){
			getSimpleJdbcTemplate().update(query,
					mailMessageId,
					attachment.getTitle(),
					attachment.getFile(),
					attachment.getContentType(),
					attachment.getPlace()
			);
		}
	}

}
