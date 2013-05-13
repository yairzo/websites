package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.CallForProposalService;
import huard.iws.util.RequestWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class WebsiteHomePageController extends GeneralWebsiteFormController {


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
		
		//call for proposal calendar
	    int year = Calendar.getInstance().get(Calendar.YEAR);  
	    int month = Calendar.getInstance().get(Calendar.MONTH)+1; //zero-based 		
		StringBuilder sb = new StringBuilder();
	    List<Integer> daysWithFunds = callForProposalService.getDaysWithFunds(String.valueOf(month),String.valueOf(year));
		for (Integer day: daysWithFunds){
			sb.append(day+",");
		}
		if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);
		model.put("daysWithFunds", sb.toString());
		
		return new ModelAndView ("websiteHomePage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		WebsiteHomePageControllerCommand command = new WebsiteHomePageControllerCommand();
		return command;
	}

	public class WebsiteHomePageControllerCommand{
		
	}
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}

	
}
