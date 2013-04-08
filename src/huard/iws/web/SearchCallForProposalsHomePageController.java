package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchCallForProposalsHomePageController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//page title
		model.put("pageTitle", "");
		
		return new ModelAndView ("searchCallForProposalsHomePage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SearchCallForProposalsHomePageControllerCommand command = new SearchCallForProposalsHomePageControllerCommand();
		return command;
	}

	public class SearchCallForProposalsHomePageControllerCommand{
		
	}
	

	
}
