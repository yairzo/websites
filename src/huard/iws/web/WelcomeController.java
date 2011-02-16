package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.util.UserPersonUtils;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class WelcomeController extends GeneralController{

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){

		LanguageUtils.applyLanguage(model, request, response, "iw_IL");

		if (UserPersonUtils.isNeedEditDetails(request)){
			model.put("id", userPersonBean.getId());
			return new ModelAndView (new RedirectView("person.html"), model);
		}
		else if (userPersonBean.isAuthorized("EQF", "RESEARCHER")){
			return new ModelAndView (new RedirectView("proposals.html"));
		}
		else{
			return new ModelAndView("welcome",model);
		}
	}
}
