package huard3.utils;
import java.io.*;

public class MailSender {
	private String to;
	private String from;
	private String cc;
	private String bcc;
	private String replyTo;
	private String subject;
	private String body;

	public MailSender (String to){
		this.to = to;
	}

	public MailSender (String to, String from, String cc, String bcc, String replyTo, String subject, String body){
		this.to = to;
		this.from = from;
		this.cc = cc;
		this.bcc = bcc;
		this.replyTo = replyTo;
		this.subject = subject;
		this.body = body;

	}

	public void sendMail(){
		createMail();
		try {
			Utils.executeCommand(Utils.getHomeDirPath()+"/mailSender/sendMail");
		}
		catch (IOException ioe){
			System.out.println(ioe);
		}
		deleteMail();
	}

	public void createMail(){
		File file = new File(Utils.getHomeDirPath()+"mailSender/mailToSend.txt");
		while (file.exists()){
			try{
			    Thread.sleep( 2000 );
			}
			catch ( InterruptedException e ){
			   System.out.println( "awakened prematurely" );
			}

		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Utils.getHomeDirPath()+"mailSender/mailToSend.txt"));
			bw.write("Subject: "+subject);
			bw.newLine();
			bw.write("Return-Path: "+from);
			bw.newLine();
			bw.write("To: "+to);
			bw.newLine();
			bw.write("From: "+from);
			bw.newLine();
			bw.write("Cc: "+cc);
			bw.newLine();
			bw.write("Bcc: "+bcc);
			bw.newLine();
			bw.write("Reply-To: "+replyTo);
			bw.newLine();
			bw.write(body);
			bw.close();
		}
		catch(IOException ioe){
			System.out.println("Couldn't Create The File"+ioe);
		}
	}

	public void deleteMail(){
		File file = new File(Utils.getHomeDirPath()+"mailSender/mailToSend.txt");
		file.delete();
	}
}
