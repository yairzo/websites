package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class EnablePersonUserController extends GeneralController{

	private static final Logger logger = Logger.getLogger(EnablePersonUserController.class);

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){

		int  personId = request.getIntParameter("id", 0);
		String md5 = request.getParameter("md5", "");
		logger.info("A person: " + personId + "is trying to enable his account with md5: " + md5);
		boolean personEnabled = personService.enablePersonPrivilege(md5);
		model.put("userPersonBean", userPersonBean);
		model.put("personEnabled", personEnabled);
		
		

		logger.info("Person: " + personId +"with md5: " + md5 + " enabled ? " + personEnabled);

		if (personEnabled){
			// caller page i.e. redirect page
			String callerPage = request.getParameter("cp", "welcome.html");
			if (callerPage.equals("conferenceProposal.html"))
				model.put("id", 0);
			else if (callerPage.equals("personPost.html"))
				model.put("id", personId);
			model.put("cp", callerPage);
		}
		return new ModelAndView ("enablePersonUser",model);
	}

}
