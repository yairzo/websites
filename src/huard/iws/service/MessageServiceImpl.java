package huard.iws.service;

import huard.iws.model.Attachment;
import huard.iws.util.Base64Utils;
import huard.iws.util.FileSystemResourceWrapper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MessageServiceImpl implements MessageService{
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);

	public String getMessage (String key){
		return getMessage(key, new String[]{});
	}

	public String getMessage (String key, String localeId){
		return getMessage(localeId+"." + key, new String[]{});
	}

	public String getMessage (String key, String [] params){
		return messageSource.getMessage(key, params,
				Locale.ENGLISH);
	}

	public boolean equals (String key, String text){
		return text.equals(getMessage(key));
	}

	public void sendMail(String to, String from,
			String subject, String body){
		sendMail(to, from, subject, body, null, null);
	}


	public void sendMail(String to, String from,
			String subject, String body, List<FileSystemResourceWrapper> resources){
		sendMail(to, from, subject, body, resources, null);
	}



	public void sendMail(String to, String from,
			String subject, String body, List<FileSystemResourceWrapper> resources,
			List<Attachment> attachments ){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		try{
			send(mailMessage, attachments, resources);
		}
		catch (Exception e){
			logger.info(e);
		}
	}


	public void sendMail(String [] to, String from,
			String [] cc, String [] bcc, String subject, String body, List<FileSystemResourceWrapper> resources){
		sendMail(to, from, cc, bcc, subject, body, resources, null);
	}

	public void sendMail(String [] to, String from,
			String [] cc, String [] bcc, String subject, String body, List<FileSystemResourceWrapper> resources,
			List<Attachment> attachments){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setCc(cc);
		mailMessage.setBcc(bcc);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		try{
			send(mailMessage, attachments, resources);
		}
		catch (Exception e){
			logger.info(e);
		}
	}


	private void send(SimpleMailMessage mailMessage, List<Attachment> attachments,
			List<FileSystemResourceWrapper> resources)
		throws MessagingException, UnsupportedEncodingException{
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configurationService.getConfigurationString("smtpServer"));
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(mailMessage.getTo());
		helper.setCc(mailMessage.getCc());
		helper.setFrom(mailMessage.getFrom());
		helper.setSubject(mailMessage.getSubject());

        helper.setText(mailMessage.getText(),true);
        if (resources != null){
        for (FileSystemResourceWrapper resource: resources)
        	helper.addInline(resource.getName(), resource.getFileSystemResource());
        }

        if (attachments != null)
			for (Attachment attachment: attachments){
				helper.addAttachment(encode(attachment.getTitle()), new ByteArrayResource(attachment.getFile()));
			}
        sender.send(message);
	}

	private String encode(String string)
	{
		Charset charset = Charset.forName("utf8");
		byte[] bytes = charset.encode(string).array();
		String encodedString = Base64Utils.byteArrayToBase64(bytes);
		String result = "=?UTF-8?B?" + encodedString + "?=";
		return result;
	}

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSourcePar) {
	        messageSource = messageSourcePar;
	 }

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
