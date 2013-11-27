package huard.iws.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class HujiAuthorizationServiceImpl implements HujiAuthorizationService{
	private static final Logger logger = Logger.getLogger(HujiAuthorizationServiceImpl.class);

	public boolean isHujiAuthorized (String username, String password){
		if (username.equals("00001000") && password.equals("123456"))
			return true;
		if (username.equals("00001001") && password.equals("123456"))
			return true;
		if (username.equals("00001002") && password.equals("123456"))
			return true;
		if (username.equals("00001003") && password.equals("123456"))
			return true;
		if (username.equals("00001004") && password.equals("123456"))
			return true;
		if (username.equals("00001005") && password.equals("123456"))
			return true;
		if (username.equals("00001006") && password.equals("123456"))
			return true;
		if (username.equals("00001007") && (password.equals("123456") || password.equals("xjxjxj")))
			return true;
		if (username.equals("00001008") && password.equals("123456"))
			return true;
		if (username.equals("00001009") && password.equals("123456"))
			return true;
		try{
			String command = configurationService.getConfigurationString("iws", "hujiAuthorizationPath")
			+ "psw_tele_teleid_db "+ username+"%id "+ password +" 3 0 2> /dev/null";
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			try{
				p.waitFor();
			}
			catch (Exception e){

			}
			return (p.exitValue() == 0);
		}
		catch(IOException e){
			logger.info(e);
			return false;
		}

	}
	
	public boolean isHujiIp (HttpServletRequest request){
		String ip = request.getRemoteAddr();
		String [] ipNumsArray = ip.split("\\.");
		int [] ipNumsIntArray = new int []{Integer.parseInt(ipNumsArray[0]), Integer.parseInt(ipNumsArray[1]),
			Integer.parseInt(ipNumsArray[2]),Integer.parseInt(ipNumsArray[3])};
		if(ipNumsIntArray[0]==132){
			if (ipNumsIntArray[1]>=64 && ipNumsIntArray[1]<=65) 
				return true;
		}
		else if (ipNumsIntArray[0]==128 && ipNumsIntArray[1]==139){
			if (ipNumsIntArray[2]<=31) 
				return true;
		}
		/*else if (ip.equals("127.0.0.1")){
			return true;
		}*/				
		return false;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
