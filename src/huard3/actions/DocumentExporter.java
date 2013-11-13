package huard3.actions;
import huard3.utils.PageHandler;
import huard3.utils.Utils;
import java.io.*;


public class DocumentExporter {
	private String ardNum;
	private String username;
	
	

	
	public void setArdNum(String string) {
		ardNum = string;
	}

	
	public void setUsername(String string) {
		username = string;
	}
	
	public boolean exportHtml(){
		String src = Utils.getPath() + "html/" + ardNum + ".html";
		String dest = "/home/" + username + "/internet/resend.html";
		if (new PageHandler().copyFile(src,dest)){
			try{
				Utils.executeCommand("chmod 666 "+dest);
				return true;
			}
			catch (IOException e){
				return false;
			}
		}
		return false;			
			
	}
		
	

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

}
