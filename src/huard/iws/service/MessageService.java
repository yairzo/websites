package huard.iws.service;

import huard.iws.model.Attachment;
import huard.iws.util.FileSystemResourceWrapper;

import java.util.List;

import org.springframework.context.MessageSourceAware;

public interface MessageService extends MessageSourceAware{

	public String getMessage(String key);

	public String getMessage(String key, String localeId);

	public String getMessage(String key, String [] params);

	public boolean equals(String key, String text);

	public void sendMail(String to, String from,
			String subject, String body);

	public void sendMail(String to, String from,
			String subject, String body, List<FileSystemResourceWrapper> resources);

	public void sendMail(String [] to, String from,
			String [] cc, String [] bcc, String subject, String body, List<FileSystemResourceWrapper> resources);

	public void sendMail(String to, String from,
			String subject, String body, List<FileSystemResourceWrapper> resources, List<Attachment> attachments);

	public void sendMail(String [] to, String from,
			String [] cc, String [] bcc, String subject, String body, List<FileSystemResourceWrapper> resources, List<Attachment> attachments);
}
