package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.model.Faculty;
import huard.iws.service.ConferenceProposalListService;
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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ConferenceProposalController extends GeneralFormController{

	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		//System.out.println("11111111111 here");
		ConferenceProposalBean conferenceProposalBean = (ConferenceProposalBean) command;
		ConferenceProposalBean origConferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
		
	
		// this part saves the content type of the attachments
		/*if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				
				String filename = (String) fileNames.next();
				System.out.println("******************************");
				System.out.println(" filename : " + filename );
				System.out.println("******************************");
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
				else if (filename.equals("companyAttach") && conferenceProposalBean.getCompanyAttach()!=null && conferenceProposalBean.getCompanyAttach().length>0){
					conferenceProposalBean.setCompanyAttachContentType(file.getContentType());
				}
				else if (filename.startsWith("fromAssosiate")){
					//We have to handle the binding, no auto binding here
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAssosiate().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromAssosiate().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
				else if (filename.startsWith("fromExternal")){
					//We have to handle the binding, no auto binding here
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromExternal().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromExternal().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
				else if (filename.startsWith("fromAdmitanceFee")){
					//We have to handle the binding, no auto binding here
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAdmitanceFee().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromAdmitanceFee().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
			}
		}	*/
		
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
		if(conferenceProposalBean.getCompanyAttach().length==0){
			conferenceProposalBean.setCompanyAttach(origConferenceProposalBean.getCompanyAttach());
			conferenceProposalBean.setCompanyAttachContentType(origConferenceProposalBean.getCompanyAttachContentType());
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromAssosiate().size(); i ++){
			if (i < origConferenceProposalBean.getFromAssosiate().size()){
				byte [] file = origConferenceProposalBean.getFromAssosiate().get(i).getReferenceFile();
				conferenceProposalBean.getFromAssosiate().get(i).setReferenceFile(file);
				String contentType = origConferenceProposalBean.getFromAssosiate().get(i).getFileContentType();
				conferenceProposalBean.getFromAssosiate().get(i).setFileContentType(contentType);
			}
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromExternal().size(); i ++){
			if (i < origConferenceProposalBean.getFromExternal().size()){
				byte [] file = origConferenceProposalBean.getFromExternal().get(i).getReferenceFile();
				conferenceProposalBean.getFromExternal().get(i).setReferenceFile(file);
				String contentType = origConferenceProposalBean.getFromExternal().get(i).getFileContentType();
				conferenceProposalBean.getFromExternal().get(i).setFileContentType(contentType);
			}
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromAdmitanceFee().size(); i ++){
			if ( i < origConferenceProposalBean.getFromAdmitanceFee().size()){
				byte [] file = origConferenceProposalBean.getFromAdmitanceFee().get(i).getReferenceFile();
				conferenceProposalBean.getFromAdmitanceFee().get(i).setReferenceFile(file);
				String contentType = origConferenceProposalBean.getFromAdmitanceFee().get(i).getFileContentType();
				conferenceProposalBean.getFromAdmitanceFee().get(i).setFileContentType(contentType);
			}
		}
		
		
		//set fields that don't appear in the page
		conferenceProposalBean.setGrade(origConferenceProposalBean.getGrade());
		conferenceProposalBean.setDeadline(origConferenceProposalBean.getDeadline());
		conferenceProposalBean.setSubmitted(origConferenceProposalBean.getSubmitted());
		conferenceProposalBean.setSubmissionDate(origConferenceProposalBean.getSubmissionDate());
		conferenceProposalBean.setOpenDate(origConferenceProposalBean.getOpenDate());
		if(conferenceProposalBean.getSubmitted()){
			conferenceProposalBean.setOperationalCommittees(origConferenceProposalBean.getOperationalCommittees());
			conferenceProposalBean.setScientificCommittees(origConferenceProposalBean.getScientificCommittees());
			conferenceProposalBean.setFromAssosiate(origConferenceProposalBean.getFromAssosiate());
			conferenceProposalBean.setFromExternal(origConferenceProposalBean.getFromExternal());
			conferenceProposalBean.setFromAdmitanceFee(origConferenceProposalBean.getFromAdmitanceFee());
		}
		
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
		conferenceProposalBean.setOrganizingCompany(request.getBooleanParameter("organizingCompany", false));
		//if changed IsInsideDeadline to enter current grading
		if(!request.getParameter("isInsideDeadline","").equals("")){ 
				conferenceProposalBean.setIsInsideDeadline(true);
				//assign default grade
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
		}
		else{
			conferenceProposalBean.setIsInsideDeadline(origConferenceProposalBean.getIsInsideDeadline());
		}
		
		//submit for grade
		if(request.getParameter("action","").equals("submitForGrading")){
			conferenceProposalBean.setSubmitted(true);
			conferenceProposalBean.setSubmissionDate(System.currentTimeMillis());
			if (System.currentTimeMillis()> conferenceProposalBean.getDeadline()){// submitted after deadline
				conferenceProposalBean.setIsInsideDeadline(false);
			}
			else{
				//assign default grade
				String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
			}
			//send mail to approver
			PersonBean updatedApprover = new PersonBean(personService.getPerson(conferenceProposalBean.getApproverId()));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceMail(updatedApprover, conferenceProposalBean.getResearcher(), conferenceProposalBean, "updatedApprover");
		}
		
		//committeRemarks
		String committeeRemarks=request.getParameter("newCommitteeRemarks","");
		if(!committeeRemarks.equals("")){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				committeeRemarks =userPersonBean.getDegreeFullName() + "," + formatter.format(new Date()) + "-" + committeeRemarks +"\n";
				conferenceProposalBean.setCommitteeRemarks(origConferenceProposalBean.getCommitteeRemarks() + committeeRemarks);
		}
		
		//cancelSubmission
		if(!request.getParameter("cancelSubmission", "").equals("")){
				conferenceProposalBean.setSubmitted(false);
				conferenceProposalBean.setSubmissionDate(1000);//1970-01-01 02:00:01
				if(origConferenceProposalBean.getGrade()>0){
					String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
					conferenceProposalService.rearangeGrades(origConferenceProposalBean.getGrade(), origConferenceProposalBean.getApproverId(), prevdeadline);
					conferenceProposalBean.setGrade(0);
				}
		}
		
		String action = request.getParameter("action", "");
		if (action.equals("delete")){
			conferenceProposalBean.setDeleted(true);
			if(conferenceProposalBean.getSubmitted()){//if was already submitted need to rearrange grades
				conferenceProposalBean.setSubmitted(false);
				conferenceProposalBean.setSubmissionDate(1000);//1970-01-01 02:00:01
				if(origConferenceProposalBean.getGrade()>0){
					String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
					conferenceProposalService.rearangeGrades(conferenceProposalBean.getGrade(), conferenceProposalBean.getApproverId(), prevdeadline);
					conferenceProposalBean.setGrade(0);
				}

			}
		}
		
		conferenceProposalService.updateConferenceProposal(conferenceProposalBean.toConferenceProposal());
		if(request.getParameter("showMessage", "").equals("saved")){
			String userMessage = messageService.getMessage("iw_IL.conferenceProposal.saved");
			request.getSession().setAttribute("userMessage", userMessage);
		}		
		if(request.getParameter("showMessage", "").equals("submitted")){
			String userMessage = messageService.getMessage("iw_IL.conferenceProposal.submitted");
			request.getSession().setAttribute("userMessage", userMessage);
		}		
		if(request.getParameter("showMessage", "").equals("deleted")){
			String userMessage = messageService.getMessage("iw_IL.conferenceProposal.deleted");
			request.getSession().setAttribute("userMessage", userMessage);
		}	
		
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
		//System.out.println("22222222222 here");
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
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_RESEARCHER") && conferenceProposal.getResearcher().getId()!=userPersonBean.getId()){
				return new ModelAndView ( new RedirectView("accessDenied.html"), null);
			}
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER") && conferenceProposal.getApprover()!=null && conferenceProposal.getApprover().getId()!=userPersonBean.getId()){
				return new ModelAndView ( new RedirectView("accessDenied.html"), null);
			}
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
			model.put("committeeRemarksWithLineBreaks", conferenceProposal.getCommitteeRemarks().replace("\n", "<br/>"));
			String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
			model.put("maxGrade",conferenceProposalService.getMaxGrade(conferenceProposal.getApproverId(),prevdeadline));
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER")){
				if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
					model.put("creator",true);
				else model.put("approver", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN")){
				if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
					model.put("creator",true);
				else model.put("admin", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_COMMITTEE")){
				if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
					model.put("creator",true);
				else model.put("committee", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_RESEARCHER")){
				model.put("creator",true);
			}
			//flag if grading process was finished - to warn in case someone clicks on un-submit
			if(conferenceProposal.getApprover()!=null){
				ConferenceProposalGrading conferenceProposalGrading= conferenceProposalListService.getApproverlastGrading(conferenceProposal.getApprover().getId());
				if(conferenceProposalGrading.getFinishedGradingDate()>1000)
					model.put("GradingFinished",true);
				else
					model.put("GradingFinished",false);
			}else{
				model.put("GradingFinished",true);
			}
			
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
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}
	
	private ConferenceProposalListService conferenceProposalListService;

	public void setConferenceProposalListService(ConferenceProposalListService conferenceProposalListService) {
		this.conferenceProposalListService = conferenceProposalListService;
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
