package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.FinancialSupportBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.Faculty;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.FacultyService;
import huard.iws.service.LocksService;
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

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ConferenceProposalController extends GeneralFormController{
	private static final Logger logger = Logger.getLogger(ConferenceProposalController.class);
	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		ConferenceProposalBean conferenceProposalBean = (ConferenceProposalBean) command;
		ConferenceProposalBean origConferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
		
		locksService.updateLock("ConferenceProposal", "edit", String.valueOf(conferenceProposalBean.getId()), 5);
		
		
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				
				String filename = (String) fileNames.next();				
				MultipartFile file = multipartRequest.getFile(filename);
				String originalName = file.getOriginalFilename();
				String ext = originalName.substring(originalName.lastIndexOf(".")+1);
				
				if (filename.equals("guestsAttach") && conferenceProposalBean.getGuestsAttach()!=null && conferenceProposalBean.getGuestsAttach().length>0){
					conferenceProposalBean.setGuestsAttachContentType(getContentType(ext));
				}
				else if (filename.equals("programAttach") && conferenceProposalBean.getProgramAttach()!=null && conferenceProposalBean.getProgramAttach().length>0){
					conferenceProposalBean.setProgramAttachContentType(getContentType(ext));
				}
				else if (filename.equals("financialAttach") && conferenceProposalBean.getFinancialAttach()!=null && conferenceProposalBean.getFinancialAttach().length>0){
					conferenceProposalBean.setFinancialAttachContentType(getContentType(ext));
				}
				else if (filename.equals("companyAttach") && conferenceProposalBean.getCompanyAttach()!=null && conferenceProposalBean.getCompanyAttach().length>0){
					conferenceProposalBean.setCompanyAttachContentType(getContentType(ext));
				}
				else if (filename.startsWith("fromAssosiate")){
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAssosiate().size()){
						FinancialSupportBean financialSupport = conferenceProposalBean.getFromAssosiate().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(getContentType(ext));
						}
					}				
				}
				else if (filename.startsWith("fromExternal")){
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromExternal().size()){
						FinancialSupportBean financialSupport = conferenceProposalBean.getFromExternal().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(getContentType(ext));
						}
					}				
				}
				else if (filename.startsWith("fromAdmitanceFee")){
					String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAdmitanceFee().size()){
						FinancialSupportBean financialSupport = conferenceProposalBean.getFromAdmitanceFee().get(index);
						if (financialSupport != null){
							financialSupport.setFileContentType(getContentType(ext));
						}
					}				
				}
			}
		}	
		
		//if not added attachment don't override prev attachment
		if(conferenceProposalBean.getGuestsAttach().length==0 && !request.getBooleanParameter("deleteGuestsAttach", false)){
			conferenceProposalBean.setGuestsAttach(origConferenceProposalBean.getGuestsAttach());
			conferenceProposalBean.setGuestsAttachContentType(origConferenceProposalBean.getGuestsAttachContentType());
		}
		if(conferenceProposalBean.getProgramAttach().length==0 && !request.getBooleanParameter("deleteProgramAttach", false)){
			conferenceProposalBean.setProgramAttach(origConferenceProposalBean.getProgramAttach());
			conferenceProposalBean.setProgramAttachContentType(origConferenceProposalBean.getProgramAttachContentType());
		}
		if(conferenceProposalBean.getFinancialAttach().length==0 && !request.getBooleanParameter("deleteFinancialAttach", false)){
			conferenceProposalBean.setFinancialAttach(origConferenceProposalBean.getFinancialAttach());
			conferenceProposalBean.setFinancialAttachContentType(origConferenceProposalBean.getFinancialAttachContentType());
		}		
		if(conferenceProposalBean.getCompanyAttach().length==0 && !request.getBooleanParameter("deleteCompanyAttach", false)){
			conferenceProposalBean.setCompanyAttach(origConferenceProposalBean.getCompanyAttach());
			conferenceProposalBean.setCompanyAttachContentType(origConferenceProposalBean.getCompanyAttachContentType());
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromAssosiate().size(); i ++){
			if (i < origConferenceProposalBean.getFromAssosiate().size() && conferenceProposalBean.getFromAssosiate().get(i).getReferenceFile().length==0 &&  i!= request.getIntParameter("deleteAssosiateFileRowId",-1)){
				byte [] file = origConferenceProposalBean.getFromAssosiate().get(i).getReferenceFile();
				conferenceProposalBean.getFromAssosiate().get(i).setReferenceFile(file);
				String contentType = origConferenceProposalBean.getFromAssosiate().get(i).getFileContentType();
				conferenceProposalBean.getFromAssosiate().get(i).setFileContentType(contentType);
			}
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromExternal().size(); i ++){
			if (i < origConferenceProposalBean.getFromExternal().size() && conferenceProposalBean.getFromExternal().get(i).getReferenceFile().length==0 &&  i!= request.getIntParameter("deleteExternalFileRowId",-1)){
				byte [] file = origConferenceProposalBean.getFromExternal().get(i).getReferenceFile();
				conferenceProposalBean.getFromExternal().get(i).setReferenceFile(file);
				String contentType = origConferenceProposalBean.getFromExternal().get(i).getFileContentType();
				conferenceProposalBean.getFromExternal().get(i).setFileContentType(contentType);
			}
		}
		for (int i = 0 ; i < conferenceProposalBean.getFromAdmitanceFee().size(); i ++){
			if ( i < origConferenceProposalBean.getFromAdmitanceFee().size() && conferenceProposalBean.getFromAdmitanceFee().get(i).getReferenceFile().length==0 &&  i!= request.getIntParameter("deleteAdmitanceFeeFileRowId",-1)){
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
				String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
		}
		else{
			conferenceProposalBean.setIsInsideDeadline(origConferenceProposalBean.getIsInsideDeadline());
		}
		
		//submit for grade
		if(request.getParameter("action","").equals("submitForGrading")){
			conferenceProposalBean.setSubmitted(true);
			conferenceProposalBean.setSubmissionDate(System.currentTimeMillis());
			conferenceProposalBean.setStatusId(ConferenceProposalBean.getStatusMapId("STATUS_SUBMITTED"));
			conferenceProposalBean.setStatusDate(System.currentTimeMillis());
			if (System.currentTimeMillis()> conferenceProposalBean.getDeadline()){// submitted after deadline
				conferenceProposalBean.setIsInsideDeadline(false);
			}
			else{
				//assign default grade
				String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
				conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), prevdeadline)+1);
			}
			//send mail to approver
			PersonBean updatedApprover = new PersonBean(personService.getPerson(conferenceProposalBean.getApproverId()));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceMail(updatedApprover, conferenceProposalBean.getResearcher(), conferenceProposalBean, "updatedApprover");
			//release lock
			//locksService.releaseLock("ConferenceProposal", "edit", String.valueOf(conferenceProposalBean.getId()));
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
				conferenceProposalBean.setStatusId(ConferenceProposalBean.getStatusMapId("STATUS_DRAFT"));
				conferenceProposalBean.setStatusDate(System.currentTimeMillis());
				if(origConferenceProposalBean.getGrade()>0){
					String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
					conferenceProposalService.rearangeGrades(origConferenceProposalBean.getGrade(), origConferenceProposalBean.getApproverId(), prevdeadline);
					conferenceProposalBean.setGrade(0);
					conferenceProposalService.checkGrades(origConferenceProposalBean.getApproverId());
				}
		}
		//status changed by admin
		if(!request.getParameter("statusFinished", "").equals("")){
			conferenceProposalBean.setStatusId(ConferenceProposalBean.getStatusMapId("STATUS_FINISHED_TREATMENT"));
			conferenceProposalBean.setStatusDate(System.currentTimeMillis());
		}
		if(!request.getParameter("statusReadyForConference", "").equals("")){
			conferenceProposalBean.setStatusId(ConferenceProposalBean.getStatusMapId("STATUS_READY_FOR_CONFERENCE"));
			conferenceProposalBean.setStatusDate(System.currentTimeMillis());
		}
		//allowEdit
		if(!request.getParameter("allowEdit", "").equals(""))
			request.getSession().setAttribute("adminEdit", true);
		else
			request.getSession().setAttribute("adminEdit", false);

		
		String action = request.getParameter("action", "");
		if (action.equals("delete")){
			conferenceProposalBean.setDeleted(true);
			if(conferenceProposalBean.getSubmitted()){//if was already submitted need to rearrange grades
				conferenceProposalBean.setSubmitted(false);
				conferenceProposalBean.setSubmissionDate(1000);//1970-01-01 02:00:01
				conferenceProposalBean.setStatusId(ConferenceProposalBean.getStatusMapId("STATUS_DELETED"));
				conferenceProposalBean.setStatusDate(System.currentTimeMillis());
				if(origConferenceProposalBean.getGrade()>0){
					String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
					conferenceProposalService.rearangeGrades(conferenceProposalBean.getGrade(), conferenceProposalBean.getApproverId(), prevdeadline);
					conferenceProposalBean.setGrade(0);
					conferenceProposalService.checkGrades(origConferenceProposalBean.getApproverId());
				}

			}
		}
		if (action.equals("deleteForever")){// new requests when researcher does not sign declaration
			conferenceProposalService.deleteConferenceProposal(conferenceProposalBean.getId());
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
			
		//never used
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", conferenceProposalBean.getId());
		return new ModelAndView(new RedirectView("conferenceProposal.html"), newModel);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{
		model.put("titleCode", request.getSession().getAttribute("titleCode"));
	
		int id = request.getIntParameter("id", 0);
		
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			ConferenceProposal conferenceProposal= new ConferenceProposal();
			int researcherId = request.getIntParameter("researcherId", userPersonBean.getId());
			conferenceProposal.setPersonId(researcherId);
			conferenceProposal.setCreatorId(userPersonBean.getId());
			String deadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalDeadline");
			
			conferenceProposal.setDeadline(DateUtils.parseDate(deadline, "yyyy-MM-dd"));
			int conferenceProposalId = conferenceProposalService.insertConferenceProposal(conferenceProposal);
			
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",conferenceProposalId);
			request.getSession().setAttribute("researcherDeclaration", true);
			return new ModelAndView ( new RedirectView("conferenceProposal.html"), newModel);
		}
		else{//show edit
			model.put("previousVersion", conferenceProposalService.getPreviousVersion(id, request.getIntParameter("version", 0)));
			model.put("nextVersion", conferenceProposalService.getNextVersion(id, request.getIntParameter("version", 0)));
			
			if(conferenceProposalService.getLastVersion(id) == conferenceProposalService.getNextVersion(id, request.getIntParameter("version", 0)))
				model.put("nextVersionIsLast",true);
			else
				model.put("nextVersionIsLast",false);
			model.put("firstVersion", request.getSession().getAttribute("firstVersion"));
			model.put("lastVersion", request.getSession().getAttribute("lastVersion"));
			// a list of possible proposal approvers
			model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("conferenceProposal", "proposalApproversListId")));
			ConferenceProposalBean conferenceProposal = (ConferenceProposalBean) model.get("command");
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_RESEARCHER") && !userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_COMMITTEE") && !userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER") && conferenceProposal.getResearcher().getId()!=userPersonBean.getId()){
				return new ModelAndView ( new RedirectView("accessDenied.html"), null);
			}
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER") && conferenceProposal.getApprover()!=null && conferenceProposal.getApprover().getId()!=userPersonBean.getOnBehalfOf("conferenceProposal") && conferenceProposal.getResearcher().getId()!=userPersonBean.getId()){
				return new ModelAndView ( new RedirectView("accessDenied.html"), null);
			}
			//get faculty name by user facultyId
			Faculty faculty = facultyService.getFaculty(conferenceProposal.getResearcher().getFacultyId());
			model.put("faculty", faculty.getNameHebrew());
			
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
			String prevdeadline = configurationService.getConfigurationString("conferenceProposal", "conferenceProposalPrevDeadline");
			model.put("maxGrade",conferenceProposalService.getMaxGrade(conferenceProposal.getApproverId(),prevdeadline));
			if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_APPROVER")){
				if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
					model.put("researcher",true);
				else model.put("approver", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_ADMIN")){
				//if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
				//	model.put("researcher",true);
				//else 
				model.put("admin", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_COMMITTEE")){
				if(conferenceProposal.getResearcher().getId()==userPersonBean.getId())
					model.put("researcher",true);
				else model.put("committee", true);
			}
			else if(userPersonBean.getPrivileges().contains("ROLE_CONFERENCE_RESEARCHER")){
				model.put("researcher",true);
			}
			
			int printEdition = request.getIntParameter("p", 0);
			model.put("printcp", printEdition == 1);
			
			model.put("researcherDeclaration",request.getSession().getAttribute("researcherDeclaration"));
			request.getSession().setAttribute("researcherDeclaration", false);//clear
			
			model.put("adminEdit",request.getSession().getAttribute("adminEdit"));
			request.getSession().setAttribute("adminEdit", false);//clear

			boolean aquiredLock = locksService.acquireLock("ConferenceProposal", "edit", String.valueOf(id), 5,userPersonBean.getId(),"ConferenceProposalController");
			if(!aquiredLock){
				model.put("locked", true);
				model.put("lockedByName",locksService.lockedByName("ConferenceProposal", "edit", String.valueOf(id)));
			}
	
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean();
		logger.info("action : " + request.getParameter("action",""));
		
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return conferenceProposalBean;
		
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
	
	private LocksService locksService;

	public void setLocksService(LocksService locksService) {
		this.locksService = locksService;
	}

	/*private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}	*/
	private static String getContentType(String extension){
		if(extension.equals("pdf"))
			return "application/pdf";
		else if (extension.equals("doc"))
			return "application/msword";
		else if (extension.equals("docx"))
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		else if (extension.equals("xls"))
			return "application/vnd.ms-excel";
		else if (extension.equals("xlsx"))
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if (extension.equals("txt"))
			return "text/plain";
		else if (extension.equals("jpg")|| extension.equals("jpeg"))
			return "image/jpeg ";
		else
			return "text/html";
	}	
}
