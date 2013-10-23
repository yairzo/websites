package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.CallForProposal;
import huard.iws.model.Language;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.service.SphinxSearchService;
import huard.iws.service.SubjectService;
import huard.iws.util.BaseUtils;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchCallForProposalsController extends GeneralWebsiteFormController {
	private static final Logger logger = Logger.getLogger(SearchCallForProposalsController.class);

	private final int LIMIT_ROWS=20;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		if(request.getSession().getAttribute("cleanSearch")!=null && request.getSession().getAttribute("cleanSearch").equals("true")){
			request.getSession().setAttribute("cleanSearch", null);
			return null;
		}
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		SearchCallForProposalsControllerCommand command = (SearchCallForProposalsControllerCommand) model.get("command");
		Language lang = (huard.iws.model.Language)model.get("lang");
		model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".website.search"));

		List<CallForProposal> callForProposals = callForProposalService.getCallForProposalsOnline(command.getSearchCreteria());
		List<CallForProposalBean> callForProposalBeans = new ArrayList<CallForProposalBean>();
		for (CallForProposal callForProposal: callForProposals){
			CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal,true);
			if(callForProposalBean.getTitle().startsWith("###"))
				callForProposalBean.setTitle("");
			callForProposalBeans.add(callForProposalBean);
		}
		model.put("callForProposals", callForProposalBeans);
		//desks
		List<MopDesk> mopDesks = mopDeskService.getPublishingMopDesks();
		model.put("mopDesks", mopDesks);
		//subjects
		Subject rootSubject = subjectService.getSubject(1, userPersonBean.getPreferedLocaleId());
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, lang.getLocaleId());
		List<Integer> subjectsToCheck = new ArrayList<Integer>();
		logger.info("Subjects: " + command.getSearchCreteria().getSearchBySubjectIds());
		if (!command.getSearchCreteria().getSearchBySubjectIds().isEmpty()){
			for (String subject: command.getSearchCreteria().getSearchBySubjectIds().split(","))
				subjectsToCheck.add(Integer.parseInt(subject));
		}
		else if(!userPersonBean.isAuthorized("ROLE_LISTS_ANONYMOUS") && !userPersonBean.getSubjectsIds().isEmpty()){
			subjectsToCheck.addAll(userPersonBean.getSubjectsIds());
		}
		rootSubjectBean.checkSubjects(subjectsToCheck);
		model.put("rootSubject", rootSubjectBean);
		//show searched parameters
		model.put("searchWords",command.getSearchCreteria().getSearchWords().replace("\"", "&quot;"));
		model.put("submissionDateFrom", DateUtils.formatDate(command.getSearchCreteria().getSearchBySubmissionDateFrom(),"yyyy-MM-dd","dd/MM/yyyy"));
		model.put("submissionDateTo", DateUtils.formatDate(command.getSearchCreteria().getSearchBySubmissionDateTo(),"yyyy-MM-dd","dd/MM/yyyy"));
		model.put("deskId",command.getSearchCreteria().getSearchByDesk());
		model.put("fundId",command.getSearchCreteria().getSearchByFund());
		try{
			if(command.getSearchCreteria().getSearchByFund()>0 )
				model.put("searchWords",fundService.getFundByFinancialId(command.getSearchCreteria().getSearchByFund()).getName());
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		model.put("typeId",command.getSearchCreteria().getSearchByType());
		model.put("temporaryFund",command.getSearchCreteria().getSearchByTemporaryFund());
		model.put("searchDeleted",command.getSearchCreteria().getSearchDeleted());
		model.put("searchExpired",command.getSearchCreteria().getSearchExpired());
		model.put("targetAudience",command.getSearchCreteria().getSearchByTargetAudience());
		model.put("searchByAllYear",command.getSearchCreteria().getSearchByAllYear());
		model.put("searchOpen",command.getSearchCreteria().getSearchOpen());
		model.put("searchByAllSubjects",command.getSearchCreteria().getSearchByAllSubjects());
		model.put("isDefault",command.getSearchCreteria().isDefault());
		if(!userPersonBean.isAuthorized("ROLE_LISTS_ANONYMOUS") && !userPersonBean.getSubjectsIds().isEmpty()){
			String linkToPersonPost = "<a href=\"personPost.html?id="+userPersonBean.getId() +"\">"+messageService.getMessage("iw_IL.website.searchBySubjects")+"</a>";
			model.put("linkToPersonPost",linkToPersonPost);
		}
		//if(request.getSession().getAttribute("callForProposalId")!=null && !request.getSession().getAttribute("callForProposalId").equals(""))
		//	model.put("callForProposalId", request.getSession().getAttribute("callForProposalId"));
		
		long lastUpdateTime = callForProposalService.getCallForProposalsLastUpdate().getTime();
		model.put("updateTime", DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy"));

		
		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("searchCallForProposalsStatic",model);
		else
			return new ModelAndView ("searchCallForProposals",model);
		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		SearchCallForProposalsControllerCommand command = new SearchCallForProposalsControllerCommand();
		if (isFormSubmission(request.getRequest())){//on submit
			CallForProposalSearchCreteria searchCreteria = new CallForProposalSearchCreteria();
			if(request.getParameter("action", "").equals("cleanSearch")){
				request.getSession().setAttribute("cleanSearch", "true");
			}
			else{
			if(!request.getParameter("submissionDateFrom", "").equals(""))
				searchCreteria.setSearchBySubmissionDateFrom(DateUtils.formatToSqlDate(request.getParameter("submissionDateFrom", ""),"dd/MM/yyyy"));
			if(!request.getParameter("submissionDateTo", "").equals(""))
				searchCreteria.setSearchBySubmissionDateTo(DateUtils.formatToSqlDate(request.getParameter("submissionDateTo", ""),"dd/MM/yyyy"));
			searchCreteria.setSearchByAllYear(request.getBooleanParameter("allYear", false));
			searchCreteria.setSearchByTemporaryFund(request.getBooleanParameter("temporaryFund", false));
			searchCreteria.setSearchByFund(request.getIntParameter("fundId", 0));
			searchCreteria.setSearchByDesk(request.getIntParameter("deskId", 0));
			searchCreteria.setSearchBySubjectIds(request.getParameter("subjectsIdsString", ""));
			searchCreteria.setSearchByType(request.getIntParameter("typeId", 0));
			if(request.getIntParameter("fundId", 0)==0){// when the text in searchWords is not the fund name
				Set<Long> sphinxIds=new LinkedHashSet<Long>();
				if(!request.getParameter("searchWords", "").isEmpty()){
					sphinxIds.add(new Long(0));//so wont show everything when deos'nt find any ids
					sphinxIds.addAll(sphinxSearchService.getMatchedIds(request.getParameter("searchWords", ""),"call_for_proposal_index"));
				}
				searchCreteria.setSearchBySearchWords(sphinxIds);
				searchCreteria.setSearchWords(request.getParameter("searchWords", ""));
			}
			searchCreteria.setSearchDeleted(request.getBooleanParameter("deleted", false));
			searchCreteria.setSearchOpen(request.getBooleanParameter("open", false));
			searchCreteria.setSearchExpired(request.getBooleanParameter("expired", false));
			searchCreteria.setSearchByTargetAudience(request.getIntParameter("targetAudience", 0));
			searchCreteria.setSearchByAllSubjects(request.getBooleanParameter("allSubjects", false));
			}
			request.getSession().setAttribute("callForProposalSearchCreteria", searchCreteria);
		}
		else{//on show
			CallForProposalSearchCreteria searchCreteria = (CallForProposalSearchCreteria) request.getSession().getAttribute("callForProposalSearchCreteria");
			request.getSession().setAttribute("callForProposalsSearchCreteria", null);
			if (searchCreteria == null)// on first time
				searchCreteria = new CallForProposalSearchCreteria();
			if(searchCreteria.getSearchBySubjectIds().isEmpty() 
					&& !userPersonBean.isAuthorized("ROLE_LISTS_ANONYMOUS") 
					&& !userPersonBean.getSubjectsIds().isEmpty())
				searchCreteria.setSearchBySubjectIds(BaseUtils.getString(userPersonBean.getSubjectsIds()));
			if (searchCreteria.isDefault()){				
				searchCreteria.setLimit(LIMIT_ROWS);
			}
			else{
				searchCreteria.setLimit(0);
			}
			command.setSearchCreteria(searchCreteria);
			//when returning to callForProposal after login - should open the call again
			/*if(request.getParameter("callForProposalId","")!=null && !request.getParameter("callForProposalId","").equals(""))
				request.getSession().setAttribute("callForProposalId", request.getParameter("callForProposalId",""));
			else
				request.getSession().setAttribute("callForProposalId", "");*/
		}
		return command;
	}

	public class SearchCallForProposalsControllerCommand{
		private CallForProposalSearchCreteria searchCreteria = new CallForProposalSearchCreteria();
		private ListView listView = new ListView();
		
		public CallForProposalSearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(CallForProposalSearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
		public List<Integer> getSubjectsIds() {
			return BaseUtils.getIntegerList(searchCreteria.getSearchBySubjectIds(),",");//for keeping the chosen subject after searched
		}

	}
	
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
	
	private SubjectService subjectService;

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}
	
	private SphinxSearchService sphinxSearchService;

	public void setSphinxSearchService(SphinxSearchService sphinxSearchService) {
		this.sphinxSearchService = sphinxSearchService;
	}

}
