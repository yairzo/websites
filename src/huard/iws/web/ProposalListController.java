package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.model.Fund;
import huard.iws.service.FundService;
import huard.iws.service.PersonListService;
import huard.iws.service.ProposalListService;
import huard.iws.service.ProposalService;
import huard.iws.service.ProposalStateService;
import huard.iws.service.RecordProtectService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;
import huard.iws.util.UserPersonUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ProposalListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(ProposalListController.class);

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		ProposalListControllerCommand aCommand = (ProposalListControllerCommand)command;
		Map newModel = new HashMap();
		String action = request.getParameter("action", "");

		if (action.equals("open")){
			newModel.put("action","open");
			return new ModelAndView( new RedirectView("proposal.html"),newModel);
		}
		if (action.equals("edit") && aCommand.getProposalId()>0){
			newModel.put("id",aCommand.getProposalId());
			return new ModelAndView( new RedirectView("proposal.html"),newModel);
		}
		else if (action.equals("opencopy") && aCommand.getProposalId()>0){
			newModel.put("action", action);
			newModel.put("originalProposalId",aCommand.getProposalId());
			return new ModelAndView( new RedirectView("proposal.html"),newModel);
		}
		else if (action.equals("delete") && aCommand.getProposalId()>0){
			proposalService.deleteProposal(aCommand.getProposalId());
			return new ModelAndView( new RedirectView("proposals.html"),newModel);
		}

		request.getSession().setAttribute("proposalsSearchCreteria", aCommand.getSearchCreteria());

		if (action.equals("search"))
			aCommand.getListView().setPage(1);

		request.getSession().setAttribute("proposalsListView", aCommand.getListView());

		 return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		if (UserPersonUtils.isNeedEditDetails(request)){
			model.put("id", userPersonBean.getId());
			return new ModelAndView (new RedirectView("person.html"), model);
		}

		recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());
		ProposalListControllerCommand aCommand = (ProposalListControllerCommand) model.get("command");

		// search by fund short name, needs translation from short name to id
		// so we can search in proposal table.

		SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("proposalsSearchCreteria");
		request.getSession().setAttribute("proposalsSearchCreteria", null);

		if (searchCreteria == null)
			searchCreteria = new SearchCreteria();

		String originalSearchField = searchCreteria.getSearchField();
		String originalSearchPhrase;
		if ((originalSearchPhrase = searchCreteria.getSearchPhrase()) != null){
			if (searchCreteria.getSearchField().equals("fundId")){
				Integer fundId;
				if (( fundId = fundService.getFundIdByShortName(searchCreteria.getSearchPhrase())) != null){
					searchCreteria.setSearchPhrase(""+fundId);
				}
			}
			if (searchCreteria.getSearchField().equals("yearId")){
				String yearIdSearchPhrase = searchCreteria.getSearchPhrase();
				int yearId = Integer.parseInt(yearIdSearchPhrase.split("/")[1])*100000 +Integer.parseInt(yearIdSearchPhrase.split("/")[0]) ;
				searchCreteria.setSearchPhrase(""+(yearId > 0 ? yearId : ""));
			}

			if (searchCreteria.getSearchField().equals("mainResearcherId") && ! "".equals(searchCreteria.getSearchPhrase())){
				if (personListService.getResearchersMap().containsKey(searchCreteria.getSearchPhrase())){
					searchCreteria.setSearchPhrase(""+
							personListService.getResearchersMap()
							.get(searchCreteria.getSearchPhrase()).getId());
				}
				else{
					searchCreteria.setSearchPhrase(""+0);
				}
			}
		}


		ListView listView = (ListView) request.getSession().getAttribute("proposalsListView");


		if (listView == null){
			listView = new ListView();

		}

		proposalListService.prepareListView(listView, searchCreteria, userPersonBean);

		List<ProposalBean> proposalBeans = proposalListService.getProposalsPage(listView, searchCreteria, userPersonBean);

		searchCreteria.setSearchField(originalSearchField);
		searchCreteria.setSearchPhrase(originalSearchPhrase);


		aCommand.setSearchCreteria(searchCreteria);
		aCommand.setListView(listView);

		model.put("command", aCommand);


//		 create a funds map that fits the user (not needed for a researcher)

		Map <Integer,Fund> funds = null;
		if (userPersonBean.isAuthorized("EQF", "ADMIN") ){
				funds = fundService.getFundsMap();
		}
		else if (userPersonBean.isAuthorized("EQF", "MOP") ){
			funds = fundService.getFundsMapByDesk(userPersonBean.getId());
		}
		else{
			funds = fundService.getFundsMapByProposals(userPersonBean);
		}

		// put it on the model for the search by fund name

		if (funds!=null) {
			model.put("funds", funds.values());
		}

		model.put("proposals", proposalBeans);

		List<ProposalBean> userViewableProposalBeans = proposalListService.getProposals(userPersonBean);


		model.put("viewableProposals", userViewableProposalBeans);

		LinkedHashMap<Integer, PersonBean> mainResearchersBeans = new LinkedHashMap<Integer, PersonBean>();
		for (ProposalBean proposalBean: userViewableProposalBeans){
			PersonBean personBean = proposalBean.getMainResearcher();
			mainResearchersBeans.put(personBean.getId(), personBean);
		}
		model.put("mainResearchers", mainResearchersBeans.values());

		String [] proposalStatesDisplay = proposalStateService.getProposalStatesDisplay();

		model.put("proposalStates", proposalStatesDisplay);

		Map<String, Integer> statesMap = proposalStateService.getProposalStates();
		for (String state: statesMap.keySet()){
			model.put(state, statesMap.get(state));
		}


		request.getSession().setAttribute("searchCreteria", null);

		 request.getSession().setAttribute("listView", null);

		return new ModelAndView(this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		ProposalListControllerCommand aCommand = new ProposalListControllerCommand();
		return aCommand;
	}


	public class ProposalListControllerCommand{
		SearchCreteria searchCreteria = new SearchCreteria();
		ListView listView = new ListView();
		int proposalId=0;

		public int getProposalId() {
			return proposalId;
		}
		public void setProposalId(int proposalId) {
			this.proposalId = proposalId;
		}
		public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
	}

	private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}

	private ProposalListService proposalListService;

	public void setProposalListService(ProposalListService proposalListService) {
		this.proposalListService = proposalListService;
	}

	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	private ProposalStateService proposalStateService;

	public void setProposalStateService(ProposalStateService proposalStateService) {
		this.proposalStateService = proposalStateService;
	}

	private ProposalService proposalService;

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
}
