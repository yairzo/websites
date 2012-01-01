package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ConferenceProposalBean;
import huard.iws.constant.Constants;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.MailMessageService;
import huard.iws.service.PersonListService;
import huard.iws.service.RecordProtectService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class ConferenceProposalListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(PersonListController.class);
    private final int ROWS_IN_PAGE=5;

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand)command;

		request.getSession().setAttribute("conferenceProposalsSearchCreteria", searchCommand.getSearchCreteria());
		request.getSession().setAttribute("conferenceProposalsListView", searchCommand.getListView());
		
		
		Map newModel = new HashMap();
		String action = request.getParameter("action", "");

		if (action.equals("edit") && searchCommand.getConferenceProposalId()>0){
			newModel.put("id",searchCommand.getConferenceProposalId());
			return new ModelAndView( new RedirectView("editConferenceProposal.html"),newModel);
		}
		if (action.equals("delete") && searchCommand.getConferenceProposalId()>0){
			System.out.println("11111111 delete " + searchCommand.getConferenceProposalId());
		}
		if (action.equals("startGrading")){
			//send mail to approver to start grading
			PersonBean updatedApprover = new PersonBean(personService.getPerson(request.getIntParameter("approver", 0)));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceGradeMail(updatedApprover, userPersonBean,"startGrading");
		}

		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand) model.get("command");

		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsPage(searchCommand.getListView(), searchCommand.getSearchCreteria(),userPersonBean);
		List<ConferenceProposalBean> conferenceProposalBeans = new ArrayList<ConferenceProposalBean>();

		for (ConferenceProposal conferenceProposal: conferenceProposals){
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			//personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			conferenceProposalBeans.add(conferenceProposalBean);
		}
		model.put("conferenceProposals", conferenceProposalBeans);
		//save the search params for paging
		model.put("searchByApprover", request.getSession().getAttribute("searchByApprover"));
		model.put("searchBySubmitted", request.getSession().getAttribute("searchBySubmitted"));
		model.put("searchByDeadline", request.getSession().getAttribute("searchByDeadline"));
		// a list of possible proposal approvers
		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("proposalApproversListId")));

		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalListCommand searchCommand = new ConferenceProposalListCommand();
		if (!isFormSubmission(request.getRequest())){
			SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("conferenceProposalsSearchCreteria");
			request.getSession().setAttribute("conferenceProposalsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("conferenceProposalsListView");
			if (searchCreteria == null){
				//deafult view
				searchCreteria = new SearchCreteria();
				String whereClause ="";
				String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				if(userPersonBean.getPrivileges().contains("ROLE_EQF_RESEARCHER")){
					whereClause = " date(deadline)>'"+previousDeadline +"'";
					request.getSession().setAttribute("searchBySubmitted", 2);
				}
				else
					whereClause = " submitted=1 and date(deadline)>'"+previousDeadline +"'";
				searchCreteria.setWhereClause(whereClause);
				int roleFilterId = request.getIntParameter("rf", 0);
				String roleFilter = Constants.getUsersRoles().get(roleFilterId);
				if (roleFilter == null)
					roleFilter = "";
				searchCreteria.setRoleFilter(roleFilter);
				listView = null;
			}
			if (listView == null){
				listView = new ListView();
				listView.setOrderBy("lastNameHebrew,firstNameHebrew");
			}
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);
			conferenceProposalListService.prepareListView(listView, searchCreteria, userPersonBean);

			searchCommand.setSearchCreteria(searchCreteria);
			searchCommand.setListView(listView);

			request.getSession().setAttribute("searchCreteria", null);
			request.getSession().setAttribute("listView", null);
		}
		if (isFormSubmission(request.getRequest())){
			String whereClause = "";
			SearchCreteria searchCreteria = new SearchCreteria();
			if(request.getIntParameter("searchByApprover", 0)>0){
				whereClause += " approverId=" + request.getIntParameter("searchByApprover", 0);
			}
			if(request.getIntParameter("searchBySubmitted", 0)==0){
					if(!whereClause.equals(""))
						whereClause+=" and";
					whereClause += " submitted=1";
			}
			else if(request.getIntParameter("searchBySubmitted", 0)==1){
					if(!whereClause.equals(""))
						whereClause+=" and";
					whereClause += " submitted=0";
			}
			String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
			if(request.getIntParameter("searchByDeadline", 0)==0){
					if(!whereClause.equals(""))
						whereClause+=" and";
					whereClause += " date(deadline)>'"+previousDeadline +"'";
			}	
			searchCreteria.setWhereClause(whereClause);
			searchCommand.setSearchCreteria(searchCreteria);
			request.getSession().setAttribute("searchByApprover", request.getIntParameter("searchByApprover", 0));
			request.getSession().setAttribute("searchBySubmitted", request.getIntParameter("searchBySubmitted", 0));
			request.getSession().setAttribute("searchByDeadline", request.getIntParameter("searchByDeadline", 0));
		}
		return searchCommand;
	}

	public class ConferenceProposalListCommand{
		SearchCreteria searchCreteria = new SearchCreteria();
		ListView listView = new ListView();
		int conferenceProposalId=0;


		public int getConferenceProposalId() {
			return conferenceProposalId;
		}
		public void setConferenceProposalId(int conferenceProposalId) {
			this.conferenceProposalId = conferenceProposalId;
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

	private ConferenceProposalListService conferenceProposalListService;

	public void setConferenceProposalListService(ConferenceProposalListService conferenceProposalListService) {
		this.conferenceProposalListService = conferenceProposalListService;
	}

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}	

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
	

	/*private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}*/
}