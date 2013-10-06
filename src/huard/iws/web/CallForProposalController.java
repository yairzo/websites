package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Fund;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RedirectViewExtended;
import huard.iws.util.RequestWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class CallForProposalController extends GeneralWebsiteFormController {
	private static final Logger logger = Logger.getLogger(CallForProposalController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		CallForProposalBean callForProposalBean = (CallForProposalBean)command;
		
		logger.info("Contacts: " + callForProposalBean.getContactPersonDetails());
	
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
			//return new ModelAndView ( new RedirectViewExtended("call_for_proposal/"+urlTitle), new HashMap<String, Object>());
			return new ModelAndView ( new RedirectViewExtended("callForProposal.html?urlTitle="+urlTitle), new HashMap<String, Object>());
		}
		//if(request.getIntParameter("id", 0)>0)//if link was written with id and not with url title
		//	return new ModelAndView ( new RedirectViewExtended("call_for_proposal/"+callForProposalBean.getUrlTitle()), new HashMap<String, Object>());
		
		//language
		LanguageUtils.applyLanguage(model, request, response,callForProposalBean.getLocaleId());
		LanguageUtils.applyLanguages(model);
		
		//page title
		model.put("pageTitle", callForProposalBean.getTitle());

		//dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		if (callForProposalBean.getFinalSubmissionTime()==0)
			model.put("finalSubmissionTime", "");
		else
			model.put("finalSubmissionTime", formatter.format(callForProposalBean.getFinalSubmissionTime()));
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
		
		List <CallForProposalContact> callForProposalContacts= new ArrayList<CallForProposalContact>();
		Pattern p = Pattern.compile("(?s)~(.*?)//(.*?)//(.*?)~");
		Matcher m = p.matcher(callForProposalBean.getContactPersons());
		while(m.find()){
			CallForProposalContact callForProposalContact= new CallForProposalContact();
			callForProposalContact.setName(m.group(1));
			callForProposalContact.setPosition(m.group(2));
			callForProposalContact.setPhone(m.group(3));
			callForProposalContacts.add(callForProposalContact);
		}
		model.put("callForProposalContacts", callForProposalContacts);
		
		boolean authorized= true;	
		if(!userPersonBean.isAuthorized("ROLE_WEBSITE_READ") && !userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT") && !userPersonBean.isAuthorized("ROLE_WEBSITE_ADMIN")  && !userPersonBean.isAuthorized("ROLE_WEBSITE_HUJI"))
			authorized= false;	
		model.put("authorized", authorized);	

		model.put("id",callForProposalBean.getId());
		if (request.getParameter("p", "").equals("1")){
			if(request.getParameter("t", "").equals("1"))
				return new ModelAndView ("callForProposalPopup1",model);
			else if(request.getParameter("t", "").equals("0"))
				return new ModelAndView ("callForProposalPopupStatic",model);
			else
				return new ModelAndView ("callForProposal",model);		
		}
			
		if(request.getParameter("t", "").equals("1"))
			return new ModelAndView ("callForProposal1",model);
		else if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("callForProposalStatic",model);
		else
			return new ModelAndView ("callForProposal",model);		
		//return new ModelAndView ( this.getFormView(), model);
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

	public class CallForProposalContact{
		public String name;
		public String position;
		public String phone;
		
		public CallForProposalContact() {
			this.name = "";
			this.position = "";
			this.phone = "";
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}		
		
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
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
