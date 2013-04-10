package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.model.Faculty;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.FacultyService;
import huard.iws.service.PersonListService;
import huard.iws.util.ConferenceProposalSearchCreteria;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

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

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		//ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand)command;

		//request.getSession().setAttribute("conferenceProposalsSearchCreteria", searchCommand.getSearchCreteria());
		//request.getSession().setAttribute("conferenceProposalsListView", searchCommand.getListView());
		
		
		Map<String, Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("addToDeadline") && request.getIntParameter("conferenceProposalId", 0)>0){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(request.getIntParameter("conferenceProposalId", 0));
			cp.setIsInsideDeadline(true);
			//assign default grade
			String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			cp.setGrade(conferenceProposalService.getMaxGrade(cp.getApproverId(), prevdeadline)+1);
			conferenceProposalService.updateConferenceProposal(cp);
		}		
		if (action.equals("removeFromDeadline") && request.getIntParameter("conferenceProposalId", 0)>0){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(request.getIntParameter("conferenceProposalId", 0));
			cp.setIsInsideDeadline(false);
			String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			conferenceProposalService.rearangeGrades(cp.getGrade(), cp.getApproverId(), prevdeadline);
			cp.setGrade(0);
			conferenceProposalService.updateConferenceProposal(cp);
		}		
		
		if (action.equals("startGrading")){
			ConferenceProposalGrading conferenceProposalGrading = new ConferenceProposalGrading();
			conferenceProposalGrading.setApproverId(request.getIntParameter("approver", 0));
			conferenceProposalGrading.setAdminId(userPersonBean.getId());
			String deadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalDeadline");
			logger.info("deadline : " + deadline);
			conferenceProposalGrading.setDeadline(DateUtils.parseDate(deadline, "yyyy-MM-dd"));
			conferenceProposalGrading.setAdminSendRemark(request.getParameter("adminSendRemarks", ""));
			conferenceProposalGrading.setFinishedGradingDate(1000);
			conferenceProposalListService.insertGradingInfo(conferenceProposalGrading);
			//send mail to approver to start grading
			PersonBean updatedApprover = new PersonBean(personService.getPerson(request.getIntParameter("approver", 0)));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceGradeMail(conferenceProposalGrading,updatedApprover, userPersonBean,"startGrading");
			//update statuses for all relevant proposals
			String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			conferenceProposalListService.updateStatusPerGrading(previousDeadline,updatedApprover.getId(),ConferenceProposalBean.getStatusMapId("STATUS_SENT_TO_APPROVER"));
		}
		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());
		model.put("titleCode", request.getSession().getAttribute("titleCode"));

		ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand) model.get("command");
		if(request.getSession().getAttribute("self").equals("1"))
			model.put("self",true);
		else
			model.put("self",false);

		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsPage(searchCommand.getListView(), searchCommand.getSearchCreteria(),userPersonBean,false);
		List<ConferenceProposalBean> conferenceProposalBeans = new ArrayList<ConferenceProposalBean>();

		for (ConferenceProposal conferenceProposal: conferenceProposals){
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			//personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			conferenceProposalBeans.add(conferenceProposalBean);
		}
		model.put("conferenceProposals", conferenceProposalBeans);
		
		//get faculty name by user facultyId
		Faculty faculty = facultyService.getFaculty(userPersonBean.getFacultyId());
		model.put("myFaculty", faculty.getNameHebrew());
		model.put("searchByApprover", searchCommand.getSearchCreteria().getSearchByApprover());
		model.put("searchByStatus", searchCommand.getSearchCreteria().getSearchByStatus());
		model.put("searchByDeadline", searchCommand.getSearchCreteria().getSearchByDeadline());
		// a list of possible proposal approvers
		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("conferenceProposal", "proposalApproversListId")));
		
		String deadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalDeadline");
		List<ConferenceProposalGrading> conferenceProposalGradings = conferenceProposalListService.getAllGradingsByCurrentDeadline(deadline);
		model.put("conferenceProposalGradings", conferenceProposalGradings);
		if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER"))
			model.put("approver",true);
		else
			model.put("approver",false);
		if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN"))
			model.put("admin",true);
		else
			model.put("admin",false);
		if(userPersonBean.isOnlyAuthorized("CONFERENCE", "RESEARCHER"))
			model.put("researcher",true);
		else
			model.put("researcher",false);
		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalListCommand searchCommand = new ConferenceProposalListCommand();

		if(request.getParameter("action", "").equals("cleanSearch")	)//clean search button
			request.getSession().setAttribute("cleanSearch","true");
		
		if (isFormSubmission(request.getRequest())){//on submit
			String whereClause = " true";
			ConferenceProposalSearchCreteria searchCreteria = new ConferenceProposalSearchCreteria();
			int searchByApprover = request.getIntParameter("searchByApprover", 0);
			if( searchByApprover > 0){
				whereClause += " and approverId=" + request.getIntParameter("searchByApprover", 0);
			}
			int searchByStatus = request.getIntParameter("searchByStatus", -2);
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN")){
				if(searchByStatus>=0)
					whereClause += " and statusId =" + searchByStatus;
				if(searchByStatus==-2)
					whereClause += " and submitted = 1";
			}
			if ((userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER") || userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_COMMITTEE")) && request.getSession().getAttribute("self").equals("0") ){
				whereClause += " and submitted = 1";
			}
			
			String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");

			int searchByDeadline = request.getIntParameter("searchByDeadline", 0);
			if( searchByDeadline > 0){
					if(searchByDeadline==1)//for this conference meeting
						whereClause += " and isInsideDeadline = 1";
					else if(searchByDeadline==2)//for next conference meeting
						whereClause += " and isInsideDeadline = 0";
					whereClause += " and date(deadline)>'"+previousDeadline +"'";
			}	
			searchCreteria.setWhereClause(whereClause);
			searchCreteria.setSearchByApprover(searchByApprover);
			searchCreteria.setSearchByStatus(searchByStatus);
			searchCreteria.setSearchByDeadline(searchByDeadline);
			int self=0;
			if(request.getSession().getAttribute("self")!=null && request.getSession().getAttribute("self").equals("1"))
				self=1;
			searchCreteria.setSelf(self);
			searchCommand.setSearchCreteria(searchCreteria);
			logger.info("form backing search command on submission: " + searchCommand.getSearchCreteria().getWhereClause());
			logger.info("form backing search command on submission: " + searchCommand.getSearchCreteria().getSearchByStatus());
			request.getSession().setAttribute("conferenceProposalSearchCreteria", searchCreteria);
			ListView listView = new ListView();
			listView.setPage(request.getIntParameter("listView.page", 1));			
			request.getSession().setAttribute("conferenceProposalListView", listView);
			//request.getSession().setAttribute("newSearch", "no");
		}
		else {//on show
			ConferenceProposalSearchCreteria searchCreteria = (ConferenceProposalSearchCreteria) request.getSession().getAttribute("conferenceProposalSearchCreteria");
			request.getSession().setAttribute("conferenceProposalsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("conferenceProposalListView");
			//if (searchCreteria == null || !request.getSession().getAttribute("newSearch").equals("no")){//to clear all search params when coming from menu
			if (searchCreteria == null || (request.getSession().getAttribute("cleanSearch")!=null && request.getSession().getAttribute("cleanSearch").equals("true"))){
				request.getSession().setAttribute("cleanSearch","");
				//default view
				searchCreteria = new ConferenceProposalSearchCreteria();
				String whereClause ="";
				String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
				if(request.getParameter("type", "").equals("self") || userPersonBean.isOnlyAuthorized("CONFERENCE", "RESEARCHER")){
					request.getSession().setAttribute("self", "1");
					searchCreteria.setSelf(1);
				}
				else{
					request.getSession().setAttribute("self", "0");
					searchCreteria.setSelf(0);
					whereClause = " submitted=1 and isInsideDeadline = 1 and date(deadline)>'"+previousDeadline +"'";
					searchCreteria.setSearchByStatus(-2);
				}
				searchCreteria.setWhereClause(whereClause);
				int roleFilterId = request.getIntParameter("rf", 0);
				String roleFilter = Constants.getUsersRoles().get(roleFilterId);
				if (roleFilter == null)
					roleFilter = "";
				searchCreteria.setRoleFilter(roleFilter);
				
				searchCreteria.setSearchByApprover(ConferenceProposalSearchCreteria.NO_APPROVER);
				searchCreteria.setSearchByDeadline(ConferenceProposalSearchCreteria.CURRENT_DEADLINE);
				listView = null;
			}
			if (listView == null){
				listView = new ListView();
				listView.setOrderBy("lastNameHebrew,firstNameHebrew");
			}
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);
			conferenceProposalListService.prepareListView(listView, searchCreteria, userPersonBean,false);

			searchCommand.setSearchCreteria(searchCreteria);
			logger.info("form backing search command on showing form: " + searchCommand.getSearchCreteria().getWhereClause());
			logger.info("form backing search command on showing form: " + searchCommand.getSearchCreteria().getSearchByStatus());
			searchCommand.setListView(listView);
			//request.getSession().setAttribute("newSearch", "yes");
		}
		return searchCommand;
	}

	public class ConferenceProposalListCommand{
		ConferenceProposalSearchCreteria searchCreteria = new ConferenceProposalSearchCreteria();
		ListView listView = new ListView();
		int conferenceProposalId=0;


		public int getConferenceProposalId() {
			return conferenceProposalId;
		}
		public void setConferenceProposalId(int conferenceProposalId) {
			this.conferenceProposalId = conferenceProposalId;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
		public ConferenceProposalSearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(ConferenceProposalSearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
	}

	private ConferenceProposalListService conferenceProposalListService;

	public void setConferenceProposalListService(ConferenceProposalListService conferenceProposalListService) {
		this.conferenceProposalListService = conferenceProposalListService;
	}

	/*private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}*/	

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
	
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}
	
	
	private FacultyService facultyService;

	public void setFacultyService(FacultyService facultyService) {
		this.facultyService = facultyService;
	}
	

}