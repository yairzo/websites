package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.CallForProposal;
import huard.iws.model.Country;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;
import huard.iws.service.CallForProposalService;
import huard.iws.service.CountryService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PersonDeskService;
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

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class CallForProposalListController extends GeneralFormController {

    private final int ROWS_IN_PAGE=20;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		return new ModelAndView(new RedirectView(getSuccessView()),new HashMap<String, Object>());
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		CallForProposalListControllerCommand command = (CallForProposalListControllerCommand) model.get("command");

		List<CallForProposal> callForProposals = callForProposalService.getCallForProposals(command.getListView(),command.getSearchCreteria());
		List<CallForProposalBean> callForProposalBeans = new ArrayList<CallForProposalBean>();
		for (CallForProposal callForProposal: callForProposals){
			CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal,false);
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
				model.put("searchWords",fundService.getFund(command.getSearchCreteria().getSearchByFund()).getName());
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
		//model.put("searchByAllCountries",command.getSearchCreteria().getSearchByAllCountries());
		//countries
		List<Country> countries = new ArrayList<Country>();
		for(Integer countryId:BaseUtils.getIntegerList(command.getSearchCreteria().getSearchByCountryIds(),",")){
			countries.add(countryService.getCountry(countryId));
		}
		model.put("countries", countries);
		return new ModelAndView ("callForProposals",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallForProposalListControllerCommand command = new CallForProposalListControllerCommand();
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
			searchCreteria.setSearchByCountryIds(request.getParameter("countryArr", ""));
			searchCreteria.setSearchByType(request.getIntParameter("typeId", 0));
			if(request.getIntParameter("fundId", 0)==0){// when the text in searchWords is not the fund name
				Set<Long> sphinxIds=new LinkedHashSet<Long>();
				if(!request.getParameter("searchWords", "").isEmpty()){
					sphinxIds.add(new Long(0));//so wont show everything when deos'nt find any ids
					sphinxIds.addAll(sphinxSearchService.getMatchedIds(request.getParameter("searchWords", ""),"call_for_proposal_draft_index"));
				}
				searchCreteria.setSearchBySearchWords(sphinxIds);
				searchCreteria.setSearchWords(request.getParameter("searchWords", ""));
			}
			searchCreteria.setSearchDeleted(request.getBooleanParameter("deleted", false));
			searchCreteria.setSearchOpen(request.getBooleanParameter("open", false));
			searchCreteria.setSearchExpired(request.getBooleanParameter("expired", false));
			searchCreteria.setSearchByTargetAudience(request.getIntParameter("targetAudience", 0));
			if(userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT")){
				//personDeskService.init();
				if(personDeskService.getPersonDeskMap().containsKey(userPersonBean.getId()))
					searchCreteria.setSearchByDesk(personDeskService.getPersonDeskMap().get(userPersonBean.getId()));
				else searchCreteria.setSearchByDesk(99);
			}
			searchCreteria.setSearchByAllSubjects(request.getBooleanParameter("allSubjects", false));
			//searchCreteria.setSearchByAllCountries(request.getBooleanParameter("allCountries", false));
			request.getSession().setAttribute("callForProposalSearchCreteria", searchCreteria);
			
			ListView listView = new ListView();
			if(request.getParameter("action", "").equals("search"))
				listView.setPage(1);
			else//pagination
				listView.setPage(request.getIntParameter("listView.page", 1));			
			request.getSession().setAttribute("callForProposalListView", listView);
		}
		else{//on show
			ListView listView = (ListView) request.getSession().getAttribute("callForProposalListView");
			request.getSession().setAttribute("callForProposalListView", null);
			if (listView == null)
				listView = new ListView();
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);
			
			CallForProposalSearchCreteria searchCreteria = (CallForProposalSearchCreteria) request.getSession().getAttribute("callForProposalSearchCreteria");
			request.getSession().setAttribute("callForProposalsSearchCreteria", null);
			if (searchCreteria == null){// on first time
				searchCreteria = new CallForProposalSearchCreteria();
				if(userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT") ){
					//show all subjects
					searchCreteria.setSearchBySubjectIds(BaseUtils.getString(subjectService.getSubjectsIds()));
				}
				else if (userPersonBean.isAuthorized("ROLE_WEBSITE_READ") && !userPersonBean.getSubjectsIds().isEmpty()){
					//show researcher subjects
					searchCreteria.setSearchBySubjectIds(BaseUtils.getString(userPersonBean.getSubjectsIds()));
				}
			}
			if(userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT")){
				//personDeskService.init();
				if(personDeskService.getPersonDeskMap().containsKey(userPersonBean.getId()))
					searchCreteria.setSearchByDesk(personDeskService.getPersonDeskMap().get(userPersonBean.getId()));
				else searchCreteria.setSearchByDesk(99);
			}
			//	searchCreteria.setSearchByCreator(userPersonBean.getId());	
			command.setSearchCreteria(searchCreteria);
			callForProposalService.prepareListView(listView,searchCreteria);
			command.setListView(listView);
		}
		return command;
	}

	public class CallForProposalListControllerCommand{
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
	
	private CountryService countryService;

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}
	
	private CallForProposalService callForProposalService;
	
	public void setCallForProposalService(
			CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
	private PersonDeskService personDeskService;

	public void setPersonDeskService(PersonDeskService personDeskService) {
		this.personDeskService = personDeskService;
	}	

}
