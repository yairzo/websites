package huard.iws.web;

import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.ConferenceProposalBean;
import huard.iws.constant.Constants;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MessageService;
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


public class ConferenceProposalGradeController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(PersonListController.class);
    private final int ROWS_IN_PAGE=100;

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		ConferenceProposalGradeCommand gradeCommand = (ConferenceProposalGradeCommand)command;

		request.getSession().setAttribute("conferenceProposalsSearchCreteria", gradeCommand.getSearchCreteria());
		request.getSession().setAttribute("conferenceProposalsListView", gradeCommand.getListView());
		
		
		
		String action = request.getParameter("action", "");
		String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
		if (action.equals("movedown") && gradeCommand.getConferenceProposalId()>0 ){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(gradeCommand.getConferenceProposalId());
			if(cp.getGrade()<conferenceProposalService.getMaxGrade(cp.getApproverId(),prevdeadline))
				conferenceProposalListService.gradeHigher(cp,prevdeadline);
		}
		if (action.equals("moveup") && gradeCommand.getConferenceProposalId()>0 ){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(gradeCommand.getConferenceProposalId());
			if( cp.getGrade()>1)
				conferenceProposalListService.gradeLower(cp,prevdeadline);
		}
		if (action.equals("save") && gradeCommand.getConferenceProposalId()>0){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(gradeCommand.getConferenceProposalId());
			String evaluationField = "approverEvaluation" + gradeCommand.getConferenceProposalId();
			String evaulationFieldValue = request.getParameter(evaluationField, "");
			cp.setApproverEvaluation(evaulationFieldValue);
			conferenceProposalService.updateConferenceProposal(cp);
		}		
		/*if (action.equals("edit") && gradeCommand.getConferenceProposalId()>0){
			ConferenceProposal cp = conferenceProposalService.getConferenceProposal(gradeCommand.getConferenceProposalId());
			newModel.put("id",gradeCommand.getConferenceProposalId());
			return new ModelAndView( new RedirectView("editConferenceProposal.html"),newModel);
		}*/
		if (action.equals("stopGrading")){
			String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
			conferenceProposalListService.updateLastGradingByApproverDeadline(userPersonBean.getId(),deadline);
			//send mail to admins list
			mailMessageService.createDeanGradeFinishedGradingMail(userPersonBean,"finishedGrading");
			//return success message
			String userMessage = messageService.getMessage("iw_IL.conferenceProposal.finishedGradingSuccess");
			request.getSession().setAttribute("userMessage", userMessage);
		}
		if (action.equals("saveDeadlineRemarks")){
			String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
			conferenceProposalService.updateDeadlineRemarks(userPersonBean.getId(),previousDeadline,request.getParameter("deadlineRemarks", ""));
		}		

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		ConferenceProposalGradeCommand gradeCommand = (ConferenceProposalGradeCommand) model.get("command");
		
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsPage(gradeCommand.getListView(),gradeCommand.getSearchCreteria(),userPersonBean);
		List<ConferenceProposalBean> conferenceProposalBeans = new ArrayList<ConferenceProposalBean>();
		String deadlineRemarks="";
		for (ConferenceProposal conferenceProposal: conferenceProposals){
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			//personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			conferenceProposalBeans.add(conferenceProposalBean);
			deadlineRemarks = conferenceProposalBean.getDeadlineRemarks();
		}
		model.put("conferenceProposals", conferenceProposalBeans);
		model.put("deadlineRemarks", deadlineRemarks);

		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalGradeCommand gradeCommand = new ConferenceProposalGradeCommand();
		String previousDeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
		String whereClause = " submitted=1 and date(deadline)>'"+previousDeadline +"' and isInsideDeadline=1";
		if (!isFormSubmission(request.getRequest())){
			SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("conferenceProposalsSearchCreteria");
			request.getSession().setAttribute("conferenceProposalsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("conferenceProposalsListView");

			if (searchCreteria == null){
				searchCreteria = new SearchCreteria();
				int roleFilterId = request.getIntParameter("rf", 0);
				String roleFilter = Constants.getUsersRoles().get(roleFilterId);
				if (roleFilter == null)
					roleFilter = "";
				searchCreteria.setRoleFilter(roleFilter);				
				searchCreteria.setWhereClause(whereClause);
				listView = null;
			}

			if (listView == null){
				listView = new ListView();
				listView.setOrderBy("lastNameHebrew,firstNameHebrew");
			}
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);
			conferenceProposalListService.prepareListView(listView, searchCreteria, userPersonBean);

			gradeCommand.setSearchCreteria(searchCreteria);
			gradeCommand.setListView(listView);

			request.getSession().setAttribute("searchCreteria", null);
			request.getSession().setAttribute("listView", null);
		}
		else{
			gradeCommand.getSearchCreteria().setWhereClause(whereClause);
		}
		return gradeCommand;
	}

	public class ConferenceProposalGradeCommand{
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
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}	
	
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/*private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}*/
}