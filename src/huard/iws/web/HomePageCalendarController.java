package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.CallForProposal;
import huard.iws.model.Fund;
import huard.iws.model.FundInDay;
import huard.iws.model.DayInCalendar;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.util.RequestWrapper;
import huard.iws.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

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
				sb.append("<ul>");
				String dir;
				if(callForProposal.getLocaleId().equals("iw_IL")) dir="rtl";
				else dir="ltr";
				sb.append("<li dir=\""+dir+"\">" +callForProposal.getTitle()+"</li>");
				//sb.append("<a href=\"\" class=\"viewProposal\" id=\""+callForProposal.getId()+"\">" +callForProposal.getTitle()+"</a><br>");
				sb.append("</ul>");
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
