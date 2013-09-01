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

public class CallForProposalCalendarController extends GeneralWebsiteController {


	protected ModelAndView handleRequestWebsite(
			RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean personBean) throws Exception
	{
		//page title
		model.put("pageTitle", "");
		
		int month= request.getSessionIntParameter("month", 0);
		int year= request.getSessionIntParameter("year", 0);
		String action = request.getParameter("action", "");		
		if(action.equals("nextMonth")){
			if(month==12){
				request.getSession().setAttribute("year", year+=1);
				month=1;
			}
			else 
				month+=1;
			request.getSession().setAttribute("month", month);
		}
		else if (action.equals("prevMonth")){
			if(month==1){
				request.getSession().setAttribute("year", year-=1);
				month=12;
			}
			else
				month-=1;
			request.getSession().setAttribute("month", month);
		}			
		else if (action.equals("nextYear")){
			request.getSession().setAttribute("year", year+=1);
		}			
		else if (action.equals("prevYear")){
			request.getSession().setAttribute("year", year-=1);
		}	
		else{
			Calendar now = Calendar.getInstance();
			month = now.get(Calendar.MONTH)+1;
			request.getSession().setAttribute("month", month);
			year = now.get(Calendar.YEAR);
			request.getSession().setAttribute("year", year);
		}

		//get call for proposals for month
		String startdate = year + "-" + month +"-01";
		String firstDay = callForProposalService.getFirstDayOfCalendarMonth(startdate);
		if(month==12){
			month=1;
			year+=1;
		}
		else month+=1;
		String enddate =  year + "-" + month +"-01";
		String lastDay = callForProposalService.getLastDayOfCalendarMonth(enddate);
		List<CallForProposal> callForProposals = callForProposalService.getCalendarMonthCallForProposals(firstDay,lastDay);
		
		
		//fund map id to fund
		Map<Integer, Fund> fundsMap = new HashMap<Integer, Fund>();
		List<Fund> allfunds= fundService.getFunds();
		for (Fund fund: allfunds){
			fundsMap.put(fund.getFinancialId(), fund);
		}

		//days map without callForProposals
		LinkedHashMap <String,DayInCalendar> dayMap = callForProposalService.getMonthDaysMap(firstDay); 
	
		//populate days with calls
		List<DayInCalendar> calendarList = new ArrayList<DayInCalendar>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for(CallForProposal callForProposal : callForProposals){
			String finalSubmission = formatter.format(callForProposal.getFinalSubmissionTime());
			DayInCalendar dayInCalendar = dayMap.get(finalSubmission);
			List<FundInDay> dayFunds = dayInCalendar.getFundsInDay();
			Fund fund= fundsMap.get(callForProposal.getFundId());
			if (fund==null) continue;
			boolean existsFundOnDay=false;
			for(FundInDay dayFund: dayFunds){//find same fund
				if (dayFund.getFundShortName().equals(fund.getShortName())){
					List<CallForProposal> dayFundInDays = dayFund.getCallForProposals();
					dayFundInDays.add(callForProposal);
					dayFund.setCallForProposals(dayFundInDays);
					existsFundOnDay=true;
				}
			}
			if(!existsFundOnDay){//add new
				FundInDay fundInDay = new FundInDay();
				fundInDay.setFundShortName(fund.getShortName());
				List<CallForProposal> dayFundInDays = new ArrayList<CallForProposal>();
				dayFundInDays.add(callForProposal);
				fundInDay.setCallForProposals(dayFundInDays);
				dayFunds.add(fundInDay);
			}
			dayInCalendar.setFundsInDay(dayFunds);
			dayMap.put(finalSubmission,dayInCalendar);
		}
		
		calendarList.addAll(dayMap.values());
		
		//put day,month for display in calendar + limit weeks if too long
		int calendarLength=0;
		List<DayInCalendar> shortCalendarList= new ArrayList<DayInCalendar>();
		for(DayInCalendar dayInCalendar:calendarList){
			dayInCalendar.setDayOnly(dayInCalendar.getDay().substring(8));
			dayInCalendar.setMonthOnly(dayInCalendar.getDay().substring(5, 7));
			shortCalendarList.add(dayInCalendar);
			calendarLength++;
			if(calendarLength==35){
				int monthNow = new Integer(dayInCalendar.getDay().substring(5, 7)).intValue();
				if (monthNow>request.getSessionIntParameter("month", 0))
					break;
			}
		}
		model.put("calendarList", shortCalendarList);
		model.put("month", request.getSession().getAttribute("month"));
		model.put("year", request.getSession().getAttribute("year"));
	
		if(request.getParameter("t", "").equals("1"))
			return new ModelAndView ("callForProposalCalendar1",model);
		else if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("callForProposalCalendarStatic",model);
		else
			return new ModelAndView ("callForProposalCalendar",model);		
		//return new ModelAndView ("callForProposalCalendar",model);
	}


	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}
	
}
