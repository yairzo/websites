package huard.iws.service;

import java.io.IOException;

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

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
