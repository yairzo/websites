package huard3.actions;
import huard3.utils.PageHandler;
import huard3.utils.Utils;


public class DocumentUploader {
	private String ardNum;
	private String username;
	
	public void setArdNum(String ardNum){
		this.ardNum = ardNum;
	}
	public void setUsername(String username){
		this.username = username;
	}
	
	public boolean sendHtmlToSite(){
		PageHandler pageHandler = new PageHandler();
		String src = "/home/" + username + "/internet/resend.html";
		String dest = Utils.getPath()+"html/" + ardNum + ".html";
		return pageHandler.sendHtmlToSite(src,dest);
	}

}
