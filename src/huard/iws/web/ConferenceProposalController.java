package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.Faculty;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.FacultyService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonListService;
import huard.iws.util.DateUtils;
import huard.iws.util.RequestWrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ConferenceProposalController extends GeneralFormController{

	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{

		ConferenceProposalBean conferenceProposalBean = (ConferenceProposalBean) command;
		ConferenceProposalBean origConferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
		
	
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				MultipartFile file = multipartRequest.getFile(filename);
				if (filename.equals("guestsAttach") && conferenceProposalBean.getGuestsAttach()!=null && conferenceProposalBean.getGuestsAttach().length>0){
					conferenceProposalBean.setGuestsAttachContentType(file.getContentType());
				}
				else if (filename.equals("programAttach") && conferenceProposalBean.getProgramAttach()!=null && conferenceProposalBean.getProgramAttach().length>0){
					conferenceProposalBean.setProgramAttachContentType(file.getContentType());
				}
				else if (filename.equals("financialAttach") && conferenceProposalBean.getFinancialAttach()!=null && conferenceProposalBean.getFinancialAttach().length>0){
					conferenceProposalBean.setFinancialAttachContentType(file.getContentType());
				}
			}
		}		
		//if not added attachment don't override prev attachment
		if(conferenceProposalBean.getGuestsAttach().length==0){
			conferenceProposalBean.setGuestsAttach(origConferenceProposalBean.getGuestsAttach());
			conferenceProposalBean.setGuestsAttachContentType(origConferenceProposalBean.getGuestsAttachContentType());
		}
		if(conferenceProposalBean.getProgramAttach().length==0){
			conferenceProposalBean.setProgramAttach(origConferenceProposalBean.getProgramAttach());
			conferenceProposalBean.setProgramAttachContentType(origConferenceProposalBean.getProgramAttachContentType());
		}
		if(conferenceProposalBean.getFinancialAttach().length==0){
			conferenceProposalBean.setFinancialAttach(origConferenceProposalBean.getFinancialAttach());
			conferenceProposalBean.setFinancialAttachContentType(origConferenceProposalBean.getFinancialAttachContentType());
		}		
		
		//set fields that don't appear in the page
		conferenceProposalBean.setGrade(origConferenceProposalBean.getGrade());
		conferenceProposalBean.setDeadline(origConferenceProposalBean.getDeadline());
		conferenceProposalBean.setSubmitted(origConferenceProposalBean.getSubmitted());
		conferenceProposalBean.setSubmissionDate(origConferenceProposalBean.getSubmissionDate());
		conferenceProposalBean.setOpenDate(origConferenceProposalBean.getOpenDate());
		
		//update dates according to calendar input
		if(!request.getParameter("startConfDate", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = (Date)formatter.parse(request.getParameter("startConfDate", "")); 
			conferenceProposalBean.setFromDate(fromDate.getTime());
		}
		else{
			conferenceProposalBean.setFromDate(origConferenceProposalBean.getFromDate());
		}
		if(!request.getParameter("endConfDate", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date toDate = (Date)formatter.parse(request.getParameter("endConfDate", "")); 
			conferenceProposalBean.setToDate(toDate.getTime());
		}
		else{
			conferenceProposalBean.setToDate(origConferenceProposalBean.getToDate());
		}
		
		//submit for grade
		if(request.getParameter("action","").equals("submitForGrading")){
			conferenceProposalBean.setSubmitted(true);
			conferenceProposalBean.setSubmissionDate(System.currentTimeMillis());
			if (System.currentTimeMillis()> conferenceProposalBean.getDeadline())// submitted after deadline
				conferenceProposalBean.setIsInsideDeadline(false);
			else{
				//assign default grade
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
			}
			//send mail to approver
			PersonBean updatedApprover = new PersonBean(personService.getPerson(conferenceProposalBean.getApproverId()));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceMail(updatedApprover, userPersonBean, conferenceProposalBean, "updatedApprover");
		}
		
		//committeRemarks
		String committeeRemarks=request.getParameter("newCommitteeRemarks","");
		if(!committeeRemarks.equals("")){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				committeeRemarks = "<br>" + committeeRemarks + "-" + userPersonBean.getDegreeFullName() + "," + formatter.format(new Date());
		}
		conferenceProposalBean.setCommitteeRemarks(origConferenceProposalBean.getCommitteeRemarks() + committeeRemarks);
		
		//cancelSubmission
		if(!request.getParameter("cancelSubmission", "").equals("")){
				conferenceProposalBean.setSubmitted(false);
				conferenceProposalBean.setSubmissionDate(1000);//1970-01-01 02:00:01
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalService.rearangeGrades(origConferenceProposalBean.getGrade(), origConferenceProposalBean.getApproverId(), prevdeadline);
				conferenceProposalBean.setGrade(0);
		}
		
		//if changed IsInsideDeadline to enter current grading
		if(conferenceProposalBean.getIsInsideDeadline() && !origConferenceProposalBean.getIsInsideDeadline()){
				conferenceProposalBean.setIsInsideDeadline(true);
				//assign default grade
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
		}
		else{
			conferenceProposalBean.setIsInsideDeadline(origConferenceProposalBean.getIsInsideDeadline());
		}
		
		conferenceProposalService.updateConferenceProposal(conferenceProposalBean.toConferenceProposal());

		//return to same page
		if (request.getBooleanParameter("ajaxSubmit", false)){
			return null;
		}
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", conferenceProposalBean.getId())	;
		return new ModelAndView(new RedirectView("editConferenceProposal.html"), newModel);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{

		model.put("previousVersion", conferenceProposalService.getPreviousVersion(request.getIntParameter("id", 0),request.getIntParameter("version", 0)));
		model.put("nextVersion", conferenceProposalService.getNextVersion(request.getIntParameter("id", 0),request.getIntParameter("version", 0)));
		model.put("firstVersion", request.getSession().getAttribute("firstVersion"));
		model.put("lastVersion", request.getSession().getAttribute("lastVersion"));
		// a list of possible proposal approvers
		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("proposalApproversListId")));
		//get faculty name by user facultyId
		Faculty faculty = facultyService.getFaculty(userPersonBean.getFacultyId());
		model.put("faculty", faculty.getNameHebrew());
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new")){
			ConferenceProposal conferenceProposal= new ConferenceProposal();
			conferenceProposal.setPersonId(userPersonBean.getId());
			String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
			logger.info("Deadline on new proposal: " + deadline);
			conferenceProposal.setDeadline(DateUtils.parseDate(deadline, "yyyy-MM-dd"));
			int conferenceProposalId = conferenceProposalService.insertConferenceProposal(conferenceProposal);
			logger.info("conferenceProposalId " + conferenceProposalId);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",conferenceProposalId);
			return new ModelAndView ( new RedirectView("editConferenceProposal.html"), newModel);
		}
		else{//show edit
			ConferenceProposalBean conferenceProposal = (ConferenceProposalBean) model.get("command");
			
			logger.info("Conference proposal scientific committee: " + conferenceProposal.getScientificCommittees().size());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = new Date(conferenceProposal.getFromDate());
			model.put("startConfDate", formatter.format(fromDate));
			Date toDate = new Date(conferenceProposal.getToDate());
			model.put("endConfDate", formatter.format(toDate));
			model.put("deadlineDate", formatter.format(conferenceProposal.getDeadline()));
			//model.put("prevDeadlineDate", formatter.format(toDate));
			String internalIdString="";
			if(conferenceProposal.getInternalId()>0 && Integer.toString(conferenceProposal.getInternalId()).length()>4){
				internalIdString = Integer.toString(conferenceProposal.getInternalId()).substring(0,4) + "/" + Integer.toString(conferenceProposal.getInternalId()).substring(4);
			}
			model.put("internalIdString", internalIdString);
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean();
		logger.info("action : " + request.getParameter("action",""));
		
		
		if ( isFormSubmission(request.getRequest()) || request.getParameter("action","").equals("new"))
			return conferenceProposalBean;
		
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		int version = request.getIntParameter("version",0);
		request.getSession().setAttribute("firstVersion", false);
		request.getSession().setAttribute("lastVersion", false);
		if(version==0){
			conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(id));
			request.getSession().setAttribute("lastVersion", true);
		}
		else{
			if (version == conferenceProposalService.getFirstVersion(id)){
				request.getSession().setAttribute("firstVersion", true);
			}
			if (version == conferenceProposalService.getLastVersion(id)){
				request.getSession().setAttribute("lastVersion", true);
			}
			conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getVersionConferenceProposal(id,version));
			
		}
		
		return conferenceProposalBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}
	
	
	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
	
	
	private FacultyService facultyService;

	public void setFacultyService(FacultyService facultyService) {
		this.facultyService = facultyService;
	}
	

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}	
	
}
