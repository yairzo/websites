package huard.iws.web;

import java.util.Map;

import huard.iws.bean.PersonBean;
import huard.iws.service.MessageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class LoginController extends GeneralController{

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
		Map<String, Object> model, PersonBean userPersonBean){

		LanguageUtils.applyLanguage(model, request, response, "iw_IL");

		if (! userPersonBean.isAuthorized("LISTS", "ANONYMOUS")){
			return new ModelAndView( new RedirectView("welcome.html"));
		}

		int titleCode = request.getIntParameter("tc", 0);

		model.put("titleCode", titleCode);

		model.put("title", messageService.getMessage("iw_IL.general.login.title."+titleCode));

		model.put("usernameInstructions", messageService.getMessage("iw_IL.general.login.usernameInstructions."+titleCode));

		model.put("passwordInstructions", messageService.getMessage("iw_IL.general.login.passwordInstructions."+titleCode));

		model.put("generalLoginInstructions", messageService.getMessage("iw_IL.general.login.generalLoginInstructions."+titleCode));

		return new ModelAndView("login",model);
	}


	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
