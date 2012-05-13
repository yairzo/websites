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
import huard.iws.service.MailMessageService;
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
		ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand)command;

		//request.getSession().setAttribute("conferenceProposalsSearchCreteria", searchCommand.getSearchCreteria());
		//request.getSession().setAttribute("conferenceProposalsListView", searchCommand.getListView());
		
		
		Map<String, Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		/*if (action.equals("delete") && searchCommand.getConferenceProposalId()>0){
			ConferenceProposalBean origConferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(searchCommand.getConferenceProposalId()));
			origConferenceProposalBean.setDeleted(true);
			if(origConferenceProposalBean.getSubmitted()){
				//if was already submitted need to rearrange grades
				origConferenceProposalBean.setSubmitted(false);
				origConferenceProposalBean.setSubmissionDate(1000);//1970-01-01 02:00:01
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalService.rearangeGrades(origConferenceProposalBean.getGrade(), origConferenceProposalBean.getApproverId(), prevdeadline);
				origConferenceProposalBean.setGrade(0);
			}
			conferenceProposalService.updateConferenceProposal(origConferenceProposalBean.toConferenceProposal());
		}*/
		
		if (action.equals("startGrading")){
			ConferenceProposalGrading conferenceProposalGrading = new ConferenceProposalGrading();
			conferenceProposalGrading.setApproverId(request.getIntParameter("approver", 0));
			conferenceProposalGrading.setAdminId(userPersonBean.getId());
			String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
			logger.info("deadline : " + deadline);
			conferenceProposalGrading.setDeadline(DateUtils.parseDate(deadline, "yyyy-MM-dd"));
			conferenceProposalGrading.setAdminSendRemark(request.getParameter("adminSendRemarks", ""));
			conferenceProposalGrading.setFinishedGradingDate(1000);
			conferenceProposalListService.insertGradingInfo(conferenceProposalGrading);
			//send mail to approver to start grading
			PersonBean updatedApprover = new PersonBean(personService.getPerson(request.getIntParameter("approver", 0)));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceGradeMail(conferenceProposalGrading,updatedApprover, userPersonBean,"startGrading");
		}

		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		ConferenceProposalListCommand searchCommand = (ConferenceProposalListCommand) model.get("command");

		logger.info("Show form search command: " + searchCommand.getSearchCreteria().getWhereClause());
		logger.info("Show form search command: " + searchCommand.getSearchCreteria().getSearchBySubmitted());
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsPage(searchCommand.getListView(), searchCommand.getSearchCreteria(),userPersonBean,false);
		List<ConferenceProposalBean> conferenceProposalBeans = new ArrayList<ConferenceProposalBean>();

		for (ConferenceProposal conferenceProposal: conferenceProposals){
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			//personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			conferenceProposalBeans.add(conferenceProposalBean);
		}
		model.put("conferenceProposals", conferenceProposalBeans);
		
		// We handle the case of a new form, no search options were selected
		/*Integer searchByApprover = (Integer) request.getSession().getAttribute("searchByApprover");
		if (searchByApprover == null)
			searchByApprover = 0;
		Integer searchBySubmitted = (Integer) request.getSession().getAttribute("searchBySubmitted");
		if (searchBySubmitted == null)
			searchBySubmitted = 0;
		Integer searchByDeadline = (Integer) request.getSession().getAttribute("searchByDeadline");
		if (searchByDeadline == null)
			searchByDeadline = 0;*/		

		//get faculty name by user facultyId
		Faculty faculty = facultyService.getFaculty(userPersonBean.getFacultyId());
		model.put("myFaculty", faculty.getNameHebrew());
		model.put("searchByApprover", searchCommand.getSearchCreteria().getSearchByApprover());
		model.put("searchBySubmitted", searchCommand.getSearchCreteria().getSearchBySubmitted());
		model.put("searchByDeadline", searchCommand.getSearchCreteria().getSearchByDeadline());
		// a list of possible proposal approvers
		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("proposalApproversListId")));
		
		String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
		List<ConferenceProposalGrading> conferenceProposalGradings = conferenceProposalListService.getAllGradingsByCurrentDeadline(deadline);
		model.put("conferenceProposalGradings", conferenceProposalGradings);
		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalListCommand searchCommand = new ConferenceProposalListCommand();
		if (!isFormSubmission(request.getRequest())){
			ConferenceProposalSearchCreteria searchCreteria = (ConferenceProposalSearchCreteria) request.getSession().getAttribute("conferenceProposalSearchCreteria");
			request.getSession().setAttribute("conferenceProposalsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("conferenceProposalListView");
			if (searchCreteria == null){
				//default view
				searchCreteria = new ConferenceProposalSearchCreteria();
				String whereClause ="";
				String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_RESEARCHER")){
					whereClause = " date(deadline)>'"+previousDeadline +"'";
					//searchCreteria.setSearchBySubmitted(ConferenceProposalSearchCreteria.DRAFT);
					
				}
				else{
					whereClause = " submitted=1 and date(deadline)>'"+previousDeadline +"'";
					searchCreteria.setSearchBySubmitted(ConferenceProposalSearchCreteria.SUBMITTED);
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
			logger.info("form backing search command on showing form: " + searchCommand.getSearchCreteria().getSearchBySubmitted());
			searchCommand.setListView(listView);			
		}
		if (isFormSubmission(request.getRequest())){
			String whereClause = "";
			ConferenceProposalSearchCreteria searchCreteria = new ConferenceProposalSearchCreteria();
			int searchByApprover = request.getIntParameter("searchByApprover", 0);
			if( searchByApprover > 0){
				whereClause += " approverId=" + request.getIntParameter("searchByApprover", 0);
			}
			int searchBySubmitted = request.getIntParameter("searchBySubmitted", 0);
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN")){
				if(!whereClause.isEmpty())
					whereClause += " and";
				if(searchBySubmitted==3)
					whereClause += " deleted = 1";
				else if (searchBySubmitted < 2)// 2 is all proposals
					whereClause += " submitted =" + searchBySubmitted;
			}
			else if (userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER")
				|| userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_COMMITTEE")){
				if(!whereClause.isEmpty())
					whereClause += " and";
				whereClause += " submitted = 1";
			}
			
			String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
			int searchByDeadline = request.getIntParameter("searchByDeadline", 0);
			if( searchByDeadline > 0){
					if(!whereClause.isEmpty())
						whereClause+=" and"; 
					if(searchByDeadline==1)//for this conference meeting
						whereClause += " isInsideDeadline = 1";
					else if(searchByDeadline==2)//for next conference meeting
						whereClause += " isInsideDeadline = 0";
					if(!whereClause.isEmpty())
						whereClause+=" and"; 
					whereClause += " date(deadline)>'"+previousDeadline +"'";
			}	
			searchCreteria.setWhereClause(whereClause);
			searchCreteria.setSearchByApprover(searchByApprover);
			searchCreteria.setSearchBySubmitted(searchBySubmitted);
			searchCreteria.setSearchByDeadline(searchByDeadline);
			searchCommand.setSearchCreteria(searchCreteria);
			logger.info("form backing search command on submission: " + searchCommand.getSearchCreteria().getWhereClause());
			logger.info("form backing search command on submission: " + searchCommand.getSearchCreteria().getSearchBySubmitted());
			request.getSession().setAttribute("conferenceProposalSearchCreteria", searchCreteria);
			ListView listView = new ListView();
			listView.setPage(request.getIntParameter("listView.page", 1));			
			request.getSession().setAttribute("conferenceProposalListView", listView);
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

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}	

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