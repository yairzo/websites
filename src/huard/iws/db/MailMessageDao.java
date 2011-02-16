package huard.iws.db;

import huard.iws.model.MailMessage;

import java.util.List;

public interface MailMessageDao {

	public MailMessage getMailMessage(int id);

	public List<MailMessage> getMailMessages();

	public int insertMailMessage(MailMessage mailMessage);

	public void updateMailMessage(MailMessage mailMessage);

	public void insetMailMessageRecipients(MailMessage mailMessage);

}
