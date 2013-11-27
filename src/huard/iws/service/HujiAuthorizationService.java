package huard.iws.service;

import javax.servlet.http.HttpServletRequest;

public interface HujiAuthorizationService {

	public boolean isHujiAuthorized (String username, String password);
	
	public boolean isHujiIp (HttpServletRequest request);
	
	

}
