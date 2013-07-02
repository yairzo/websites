package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Fund;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.util.RedirectViewExtended;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class CallForProposalController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		CallForProposalBean callForProposalBean = (CallForProposalBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callForProposalBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		CallForProposalBean callForProposalBean = (CallForProposalBean) model.get("command");
		if(callForProposalBean.getId()==0)//someone entered non-existing id
			return new ModelAndView ( "websitePageNotFound", model);

		//when coming from old site:
		int ardNum = (Integer) request.getSession().getAttribute("ardNum");
		request.getSession().setAttribute("ardNum",0);
		if (ardNum > 0){
			String urlTitle=callForProposalService.getCallForProposalUrlTitleByArdNum(ardNum);
			return new ModelAndView ( new RedirectViewExtended("call_for_proposal/"+urlTitle), new HashMap<String, Object>());
		}
		if(request.getIntParameter("id", 0)>0)//if link was written with id and not with url title
			return new ModelAndView ( new RedirectViewExtended("call_for_proposal/"+callForProposalBean.getUrlTitle()), new HashMap<String, Object>());
		
		//language
		LanguageUtils.applyLanguage(model, request, response,callForProposalBean.getLocaleId());
		LanguageUtils.applyLanguages(model);
		
		//page title
		model.put("pageTitle", callForProposalBean.getTitle());

		//dates
		if (callForProposalBean.getFinalSubmissionTime()==0)
			model.put("finalSubmissionTime", "");
		else
			model.put("finalSubmissionTime", DateUtils.getLocaleLongDateTimeFormatWithDay(callForProposalBean.getFinalSubmissionTime(),callForProposalBean.getLocaleId(),false));
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (callForProposalBean.getPublicationTime()==0)
			model.put("publicationTime", "");
		else
			model.put("publicationTime", formatter.format(callForProposalBean.getPublicationTime()));
		if (callForProposalBean.getUpdateTime()==0)
			model.put("updateTime", "");
		else
			model.put("updateTime", formatter.format(callForProposalBean.getUpdateTime()));
		//extra submission dates
		Date tmpDate = new Date();
		int i=1;
		if(callForProposalBean.getSubmissionDates()!=null){
			for(Long submissionDate: callForProposalBean.getSubmissionDates()){
				if(submissionDate==1000) continue;
				tmpDate = new Date(submissionDate);
				String submissionName="submissionDate" + i;
				model.put(submissionName, formatter.format(tmpDate));
				i++;
			}
		}
		//funds
		String selectedFund="";
		if(callForProposalBean.getFundId()>0){
			Fund fund = fundService.getFundByFinancialId(callForProposalBean.getFundId());
			if (fund.getId()>0)
				selectedFund=fund.getName();
		}
		model.put("selectedFund", selectedFund);
		//stripped fields
		String stripped =callForProposalBean.getFundingPeriod().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedFundingPeriod", stripped);
		stripped =callForProposalBean.getAmountOfGrant().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedAmountOfGrant", stripped);
		stripped =callForProposalBean.getEligibilityRequirements().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedEligibilityRequirements", stripped);
		stripped =callForProposalBean.getActivityLocation().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedActivityLocation", stripped);
		stripped =callForProposalBean.getPossibleCollaboration().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedPossibleCollaboration", stripped);

		boolean authorized= true;	
		if(!userPersonBean.isAuthorized("ROLE_WEBSITE_READ") && !userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT") && !userPersonBean.isAuthorized("ROLE_WEBSITE_ADMIN")  && !userPersonBean.isAuthorized("ROLE_WEBSITE_HUJI"))
			authorized= false;	
		model.put("authorized", authorized);	

		model.put("id",callForProposalBean.getId());
		return new ModelAndView ( this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallForProposalBean callForProposalBean = new CallForProposalBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		String urlTitle = request.getParameter("urlTitle", "");
		logger.info("urlTitle: " + urlTitle);
		//when coming from old site
		request.getSession().setAttribute("ardNum", request.getIntParameter("ardNum", 0));
	
			
		if ( isFormSubmission(request.getRequest()) || (id == 0 && urlTitle.isEmpty()))
			return callForProposalBean;
		
		if(request.getParameter("draft","").equals("true"))
			callForProposalBean = new CallForProposalBean(callForProposalService.getCallForProposal(id),true);
		else{
			if(id>0)
				callForProposalBean = new CallForProposalBean(callForProposalService.getCallForProposalOnline(id),true);
			else if(!urlTitle.isEmpty())
				callForProposalBean = new CallForProposalBean(callForProposalService.getCallForProposalOnline(urlTitle),true);
		}
		logger.info("callForProposalBean id: " + callForProposalBean.getId());
		return callForProposalBean;
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
