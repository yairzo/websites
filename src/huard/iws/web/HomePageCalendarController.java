package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.CallForProposal;
import huard.iws.service.CallForProposalService;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class HomePageCalendarController extends GeneralWebsiteController {


	protected ModelAndView handleRequestWebsite(
			RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean personBean) throws Exception
	{
		//page title
		model.put("pageTitle", "");
		
		String month= request.getParameter("month", "");
		String year= request.getParameter("year", "");
		String type = request.getParameter("type", "");		
		StringBuilder sb = new StringBuilder();
		if(type.equals("changeMonth")){
			List<Integer> daysWithFunds = callForProposalService.getDaysWithFunds(month,year);
			for (Integer day: daysWithFunds){
				sb.append(day+",");
			}
			if(sb.length()>1)
				sb.deleteCharAt(sb.length()-1);
			model.put("content", sb.toString());
		}
		else if (type.equals("callForProposalsPerDay")){
			List<CallForProposal> callForProposalsPerDay = callForProposalService.getCallForProposalsPerDay(request.getParameter("date",""));			
			for(CallForProposal callForProposal:callForProposalsPerDay){
				sb.append("<dfn class=\"viewProposal\" id=\""+callForProposal.getId()+"\">"+callForProposal.getTitle()+"</dfn><br><br>");
			}
			model.put("content", sb.toString());
		}

	
		return new ModelAndView ("homePageCalendar",model);
	}

	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
}
