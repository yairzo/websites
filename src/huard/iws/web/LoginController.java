package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.MessageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class LoginController extends GeneralController{

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
		Map<String, Object> model, PersonBean userPersonBean){

		
		/*Enumeration attrs =  request.getRequest().getAttributeNames();
		while(attrs.hasMoreElements()) {
		    System.out.println(attrs.nextElement());
		}*/
		
		//logger.info("Request uri: " + request.getRequest().getAttribute("javax.servlet.forward.request_uri"));			
		
		LanguageUtils.applyLanguage(model, request, response, "iw_IL");

		if (! userPersonBean.isAuthorized("LISTS", "ANONYMOUS")){
			return new ModelAndView( new RedirectView("welcome.html"));
		}

		int loginError = request.getIntParameter("login_error", 0);
		logger.info("login error: " + loginError);
		model.put("loginError", messageService.getMessage("iw_IL.general.login.loginError."+loginError));
		
		int titleCode = request.getIntParameter("tc", 0);

		String requestURI = (String)request.getRequest().getAttribute("javax.servlet.forward.request_uri"); 
		if (requestURI != null && requestURI.contains("conferenceProposal")){
			model.put("moduleToSubscribe", "conference");
			titleCode = 2;
		}
		else if (titleCode == 1){
			model.put("moduleToSubscribe", "post");
		}
		
		model.put("title", messageService.getMessage("iw_IL.general.login.title."+titleCode));
		
		model.put("titleCode", titleCode);
		
		model.put("username", messageService.getMessage("iw_IL.general.login.username."+titleCode));

		model.put("usernameInstructions", messageService.getMessage("iw_IL.general.login.usernameInstructions."+titleCode));

		model.put("passwordInstructions", messageService.getMessage("iw_IL.general.login.passwordInstructions."+titleCode));

		model.put("generalLoginInstructions", messageService.getMessage("iw_IL.general.login.generalLoginInstructions."+titleCode));
		
		return new ModelAndView("login",model);
	}



}
