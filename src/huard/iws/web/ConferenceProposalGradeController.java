package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.util.ConferenceProposalSearchCreteria;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
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
		String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
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
			return new ModelAndView( new RedirectView("conferenceProposal.html"),newModel);
		}*/
		if (action.equals("stopGrading")){
			String deadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalDeadline");
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN") && !request.getSession().getAttribute("approverId").equals(""))//if admin enters on behalf of approver
				conferenceProposalListService.updateLastGradingByApproverDeadline(new Integer(request.getSession().getAttribute("approverId").toString()).intValue(),deadline,request.getParameter("deadlineRemarks", ""));
			else
				conferenceProposalListService.updateLastGradingByApproverDeadline(userPersonBean.getId(),deadline,request.getParameter("deadlineRemarks", ""));
			//send mail to admins list
			mailMessageService.createDeanGradeFinishedGradingMail(userPersonBean,"finishedGrading");
			//return success message
			String userMessage = messageService.getMessage("iw_IL.conferenceProposal.finishedGradingSuccess");
			request.getSession().setAttribute("userMessage", userMessage);
			//update statuses for all relevant proposals
			String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN") && !request.getSession().getAttribute("approverId").equals(""))//if admin enters on behalf of approver
				conferenceProposalListService.updateStatusPerGrading(previousDeadline,new Integer(request.getSession().getAttribute("approverId").toString()).intValue(),ConferenceProposalBean.getStatusMapId("STATUS_READY_FOR_CONFERENCE"));
			else
				conferenceProposalListService.updateStatusPerGrading(previousDeadline,userPersonBean.getId(),ConferenceProposalBean.getStatusMapId("STATUS_READY_FOR_CONFERENCE"));
		}
		if (action.equals("saveDeadlineRemarks")){
			String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN") && !request.getSession().getAttribute("approverId").equals(""))//if admin enters on behalf of approver
				conferenceProposalService.updateDeadlineRemarks(new Integer(request.getSession().getAttribute("approverId").toString()).intValue(),previousDeadline,request.getParameter("deadlineRemarks", ""));
			else
				conferenceProposalService.updateDeadlineRemarks(userPersonBean.getId(),previousDeadline,request.getParameter("deadlineRemarks", ""));
		}		

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		model.put("titleCode", request.getSession().getAttribute("titleCode"));
		ConferenceProposalGradeCommand gradeCommand = (ConferenceProposalGradeCommand) model.get("command");
		
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsPage(gradeCommand.getListView(),gradeCommand.getSearchCreteria(),userPersonBean,true);
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
		
		int grader=0;
		if(request.getIntParameter("approverId",0)>0)
			grader = request.getIntParameter("approverId",0);
		else
			grader = userPersonBean.getId();
		String deadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalDeadline");
		ConferenceProposalGrading conferenceProposalGrading= conferenceProposalListService.getApproverlastGrading(grader,deadline);
		model.put("adminDeadlineRemarks",conferenceProposalGrading.getAdminSendRemark());
		if(conferenceProposalGrading.getFinishedGradingDate()>1000)
			model.put("GradingFinished",true);
		else
			model.put("GradingFinished",false);
		if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN"))
			model.put("admin", true);
		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalGradeCommand gradeCommand = new ConferenceProposalGradeCommand();
		String previousDeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
		String whereClause = " submitted=1 and date(deadline)>'"+previousDeadline +"' and isInsideDeadline=1";
		if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN") && !request.getParameter("approverId","").equals(""))//if admin enters on behalf of approver
			request.getSession().setAttribute("approverId",request.getParameter("approverId",""));
		if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN") && request.getSession().getAttribute("approverId")!=null && !request.getSession().getAttribute("approverId").equals(""))//if admin enters on behalf of approver
			whereClause +=" and approverId=" + request.getSession().getAttribute("approverId");

		if (!isFormSubmission(request.getRequest())){
			ConferenceProposalSearchCreteria searchCreteria = (ConferenceProposalSearchCreteria) request.getSession().getAttribute("conferenceProposalsSearchCreteria");
			request.getSession().setAttribute("conferenceProposalsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("conferenceProposalsListView");

			if (searchCreteria == null){
				searchCreteria = new ConferenceProposalSearchCreteria();
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
			conferenceProposalListService.prepareListView(listView, searchCreteria, userPersonBean,true);

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
		//SearchCreteria searchCreteria = new SearchCreteria();
		ConferenceProposalSearchCreteria searchCreteria = new ConferenceProposalSearchCreteria();

		ListView listView = new ListView();
		int conferenceProposalId=0;

		public int getConferenceProposalId() {
			return conferenceProposalId;
		}
		public void setConferenceProposalId(int conferenceProposalId) {
			this.conferenceProposalId = conferenceProposalId;
		}

		/*public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}*/
		
		public ConferenceProposalSearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(ConferenceProposalSearchCreteria searchCreteria) {
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

	/*private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}*/	
	
	/*private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}*/
}