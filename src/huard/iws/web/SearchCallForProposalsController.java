package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.SubjectBean;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.service.SubjectService;
import huard.iws.service.SphinxSearchService;
import huard.iws.util.BaseUtils;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.CallForProposal;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchCallForProposalsController extends GeneralWebsiteFormController {

	private final int LIMIT_ROWS=20;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		SearchCallForProposalsControllerCommand command = (SearchCallForProposalsControllerCommand) model.get("command");

		model.put("pageTitle", messageService.getMessage("iw_IL.website.search"));

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
		List<MopDesk> mopDesks = mopDeskService.getMopDesks();
		model.put("mopDesks", mopDesks);
		//subjects
		Subject rootSubject = subjectService.getSubject(1, userPersonBean.getPreferedLocaleId());
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, userPersonBean.getPreferedLocaleId());
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
		if(!userPersonBean.isAuthorized("ROLE_LISTS_ANONYMOUS") && !userPersonBean.getSubjectsIds().isEmpty()){
			String linkToPersonPost = "<a href=\"personPost.html?id="+userPersonBean.getId() +"\">"+messageService.getMessage("iw_IL.website.searchBySubjects")+"</a>";
			model.put("linkToPersonPost",linkToPersonPost);
		}
		//if(request.getSession().getAttribute("callForProposalId")!=null && !request.getSession().getAttribute("callForProposalId").equals(""))
		//	model.put("callForProposalId", request.getSession().getAttribute("callForProposalId"));

		
		if(request.getSession().getAttribute("t")!=null && request.getSession().getAttribute("t").equals("1")){
			request.getSession().setAttribute("t","");
			return new ModelAndView ("searchCallForProposals1",model);
		}
		else if(request.getSession().getAttribute("t")!=null && request.getSession().getAttribute("t").equals("0")){
			request.getSession().setAttribute("t","");
			return new ModelAndView ("searchCallForProposalsStatic",model);
		}
		else
			return new ModelAndView ("searchCallForProposals",model);
		//return new ModelAndView ("searchCallForProposals",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		if(request.getSession().getAttribute("t")==null || request.getSession().getAttribute("t").equals(""))
			request.getSession().setAttribute("t",request.getParameter("t", ""));

		SearchCallForProposalsControllerCommand command = new SearchCallForProposalsControllerCommand();
		if (isFormSubmission(request.getRequest())){//on submit
			CallForProposalSearchCreteria searchCreteria = new CallForProposalSearchCreteria();
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
			request.getSession().setAttribute("callForProposalSearchCreteria", searchCreteria);
		}
		else{//on show
			CallForProposalSearchCreteria searchCreteria = (CallForProposalSearchCreteria) request.getSession().getAttribute("callForProposalSearchCreteria");
			request.getSession().setAttribute("callForProposalsSearchCreteria", null);
			if (searchCreteria == null){// on first time
				searchCreteria = new CallForProposalSearchCreteria();
				if(!userPersonBean.isAuthorized("ROLE_LISTS_ANONYMOUS") && !userPersonBean.getSubjectsIds().isEmpty())
					searchCreteria.setSearchBySubjectIds(BaseUtils.getString(userPersonBean.getSubjectsIds()));
				searchCreteria.setLimit(LIMIT_ROWS);
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
