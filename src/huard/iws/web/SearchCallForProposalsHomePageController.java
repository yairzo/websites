package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.CallForProposal;
import huard.iws.service.CallForProposalService;
import huard.iws.util.RequestWrapper;
import huard.iws.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

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

		//calendar
		int month= request.getSessionIntParameter("month", 0);
		int year= request.getSessionIntParameter("year", 0);
		String date = year + "-" + month +"-01";
		String firstDay = callForProposalService.getFirstDayOfCalendarMonth(date);
		if(month==12){
			month=1;
			year+=1;
		}
		else month+=1;
		date =  year + "-" + month +"-01";
		String lastDay = callForProposalService.getLastDayOfCalendarMonth(date);
		List<CallForProposal> callForProposals = callForProposalService.getCalendarMonthCallForProposals(firstDay,lastDay);
		
		List<DayInCalendar> calendarList = new ArrayList<DayInCalendar>();
		List<CallForProposal> callForProposalsOnSameDay= new ArrayList<CallForProposal>();
		String nextDay = firstDay;
		
		for(CallForProposal callForProposal : callForProposals){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String finalSubmission = formatter.format(callForProposal.getFinalSubmissionTime());
			while(DateUtils.parseDate(nextDay,"yyyy-MM-dd")<DateUtils.parseDate(finalSubmission,"yyyy-MM-dd")){
				DayInCalendar dayInCalendar = new DayInCalendar();
				dayInCalendar.setDay(nextDay);
				dayInCalendar.setCallForProposals(callForProposalsOnSameDay);
				calendarList.add(dayInCalendar);
				nextDay = DateUtils.addDay(nextDay);
				callForProposalsOnSameDay = new ArrayList<CallForProposal>();
			}
			callForProposalsOnSameDay.add(callForProposal);
		}

		while (DateUtils.parseDate(nextDay,"yyyy-MM-dd") < DateUtils.parseDate(lastDay,"yyyy-MM-dd")){//loop remaining days
			DayInCalendar dayInCalendar = new DayInCalendar();
			dayInCalendar.setDay(nextDay);
			dayInCalendar.setCallForProposals(callForProposalsOnSameDay);
			calendarList.add(dayInCalendar);
			nextDay = DateUtils.addDay(nextDay);
			callForProposalsOnSameDay = new ArrayList<CallForProposal>();
		}
		model.put("calendarList", calendarList);
		
		model.put("month", request.getSession().getAttribute("month"));
		model.put("year", request.getSession().getAttribute("year"));
	
		return new ModelAndView ("searchCallForProposalsHomePage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SearchCallForProposalsHomePageControllerCommand command = new SearchCallForProposalsHomePageControllerCommand();
		if (!isFormSubmission(request.getRequest())){//on show
			if(request.getSession().getAttribute("month")==null){
				Calendar now = Calendar.getInstance();
				int month = now.get(Calendar.MONTH);
				request.getSession().setAttribute("month", month+1);
				int year = now.get(Calendar.YEAR);
				request.getSession().setAttribute("year", year);
			}
			else{
				String action = request.getParameter("action", "");			
				if(action.equals("nextMonth")){
					
					int month = request.getSessionIntParameter("month", 0);
					if(month==12){
						int year = request.getSessionIntParameter("year", 0);
						year+=1;
						request.getSession().setAttribute("year", year);
						month=1;
					}
					else{
						month+=1;
					}
					request.getSession().setAttribute("month", month);
				}
				else if (action.equals("prevMonth")){
					int month = request.getSessionIntParameter("month", 0);
					if(month==1){
						int year = request.getSessionIntParameter("year", 0);
						year-=1;
						request.getSession().setAttribute("year", year);
						month=12;
					}
					else{
						month-=1;
					}
					request.getSession().setAttribute("month", month);
				}			
				else if (action.equals("nextYear")){
					int year = request.getSessionIntParameter("year", 0);
					year+=1;
					request.getSession().setAttribute("year", year);
				}			
				else if (action.equals("prevYear")){
					int year = request.getSessionIntParameter("year", 0);
					year-=1;
					request.getSession().setAttribute("year", year);
				}			
			}
		}
		return command;
	}

	public class SearchCallForProposalsHomePageControllerCommand{
		
	}
	
	public class DayInCalendar{
		String day;
		List<CallForProposal> callForProposals;
		
		public String getDay(){
			return day;
		}
		public void setDay(String day){
			this.day= day;
		}
		public List<CallForProposal> getCallForProposals() {
			return callForProposals;
		}

		public void setCallForProposals(List<CallForProposal> callForProposals) {
			this.callForProposals = callForProposals;
		}

	}
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
}
