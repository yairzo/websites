package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.PersonPrivilege;
import huard.iws.service.PersonPrivilegeService;
import huard.iws.util.RequestWrapper;
import huard.iws.util.UserPersonUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class WelcomeController extends GeneralController{
	private static final Logger logger = Logger.getLogger(WelcomeController.class);
	
	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){

		//LanguageUtils.applyLanguage(model, request, response, "iw_IL");
		
		List<PersonPrivilege> privileges = personPrivilegeService.getPersonPrivileges(userPersonBean.getId());
		String redirect = "";
		if (!privileges.isEmpty())
			redirect = privileges.get(0).getSubscriptionInitPage();
		if (redirect.startsWith("/"))
			redirect = redirect.replaceFirst("/", "");
		
		logger.info("Redirect: " + redirect);
		if (!redirect.isEmpty()){
			personPrivilegeService.clearSubscriptionInitPage(userPersonBean.getId());
			return new ModelAndView(new RedirectView(redirect));
		}
		
		model.put("titleCode", request.getSession().getAttribute("titleCode"));

		if (UserPersonUtils.isNeedEditDetails(request)){
			model.put("id", userPersonBean.getId());
			return new ModelAndView (new RedirectView("person.html"), model);
		}
		/*else if (userPersonBean.isAuthorized("EQF", "RESEARCHER")){
			return new ModelAndView (new RedirectView("proposals.html"));
		}*/
		else{
			model.put("websiteName", configurationService.getConfigurationString("iws", "websiteName"));

			return new ModelAndView("welcome",model);
		}
	}
}
