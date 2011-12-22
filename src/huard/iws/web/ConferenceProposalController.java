package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.Faculty;
import huard.iws.model.FinancialSupport;
import huard.iws.model.InitiatingBody;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.FacultyService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonListService;
import huard.iws.util.RequestWrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
		
		//if added financialsupport or committee
		if(request.getParameter("action","").equals("fromAssosiateSave")){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setType(1);
			financialSupport.setConferenceProposalId(conferenceProposalBean.getId());
			financialSupport.setName(request.getParameter("fromAssosiate_name", ""));
			financialSupport.setSum(request.getParameter("fromAssosiate_sum", ""));
			financialSupport.setCurrency(request.getParameter("fromAssosiate_currency", ""));
			conferenceProposalService.insertFinancialSupport(financialSupport);
		}
		else if(request.getParameter("action","").equals("fromExternalSave")){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setType(2);
			financialSupport.setConferenceProposalId(conferenceProposalBean.getId());
			financialSupport.setName(request.getParameter("fromExternal_name", ""));
			financialSupport.setSum(request.getParameter("fromExternal_sum", ""));
			financialSupport.setCurrency(request.getParameter("fromExternal_currency", ""));
			conferenceProposalService.insertFinancialSupport(financialSupport);
		}
		else if(request.getParameter("action","").equals("fromAdmitanceFeeSave")){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setType(3);
			financialSupport.setConferenceProposalId(conferenceProposalBean.getId());
			financialSupport.setName(request.getParameter("fromAdmitanceFee_name", ""));
			financialSupport.setSum(request.getParameter("fromAdmitanceFee_sum", ""));
			financialSupport.setCurrency(request.getParameter("fromAdmitanceFee_currency", ""));
			conferenceProposalService.insertFinancialSupport(financialSupport);
		}
		else if(request.getParameter("action","").equals("scientificCommitteeSave")){
			Committee committee = new Committee();
			committee.setType(1);
			committee.setConferenceProposalId(conferenceProposalBean.getId());
			committee.setName(request.getParameter("scientificCommittee_name", ""));
			committee.setInstitute(request.getParameter("scientificCommittee_institute", ""));
			committee.setInstituteRole(request.getParameter("scientificCommittee_instituteRole", ""));
			committee.setCommitteeRole(request.getParameter("scientificCommittee_committeeRole",""));
			conferenceProposalService.insertCommittee(committee);
		}
		else if(request.getParameter("action","").equals("operationalCommitteeSave")){
			Committee committee = new Committee();
			committee.setType(2);
			committee.setConferenceProposalId(conferenceProposalBean.getId());
			committee.setName(request.getParameter("operationalCommittee_name", ""));
			committee.setInstitute(request.getParameter("operationalCommittee_institute", ""));
			committee.setInstituteRole(request.getParameter("operationalCommittee_instituteRole", ""));
			committee.setCommitteeRole(request.getParameter("operationalCommittee_committeeRole",""));
			conferenceProposalService.insertCommittee(committee);
		}
		else if(request.getParameter("action","").equals("deleteFinancialSupport")){
			conferenceProposalService.deleteFinancialSupport(request.getIntParameter("financialSupportId", 0));
		}
		else if(request.getParameter("action","").equals("deleteCommittee")){
			conferenceProposalService.deleteCommittee(request.getIntParameter("committeeId", 0));
		}

		//if not added attachment don't override prev attachment
		ConferenceProposalBean origConferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
		if(conferenceProposalBean.getGuestsAttach().length==0)
			conferenceProposalBean.setGuestsAttach(origConferenceProposalBean.getGuestsAttach());
		if(conferenceProposalBean.getProgramAttach().length==0)
			conferenceProposalBean.setProgramAttach(origConferenceProposalBean.getProgramAttach());
		if(conferenceProposalBean.getFinancialAttach().length==0)
			conferenceProposalBean.setFinancialAttach(origConferenceProposalBean.getFinancialAttach());

		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				MultipartFile file = multipartRequest.getFile(filename);
				if (filename.equals("guestsAttach") && conferenceProposalBean.getFinancialAttach()!=null && conferenceProposalBean.getGuestsAttach().length>0){
					conferenceProposalBean.setGuestsAttachContentType(file.getContentType());
				}
				else if (filename.equals("programAttach") && conferenceProposalBean.getFinancialAttach()!=null && conferenceProposalBean.getProgramAttach().length>0){
					conferenceProposalBean.setProgramAttachContentType(file.getContentType());
				}
				else if (filename.equals("financialAttach") && conferenceProposalBean.getFinancialAttach()!=null && conferenceProposalBean.getFinancialAttach().length>0){
					conferenceProposalBean.setFinancialAttachContentType(file.getContentType());
				}
			}
		}		
		conferenceProposalBean.setGrade(origConferenceProposalBean.getGrade());
		conferenceProposalBean.setDeadline(origConferenceProposalBean.getDeadline());
		//assigned new approver
		/*if(attachmentsConferenceProposalBean.getApproverId()!=conferenceProposalBean.getApproverId()){
			//assign grade
			conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId())+1);
			//send mail to approver
			PersonBean updatedApprover = new PersonBean(personService.getPerson(conferenceProposalBean.getApproverId()));
			if (updatedApprover.isValidEmail()) 
				mailMessageService.createSimpleConferenceMail(updatedApprover, userPersonBean, conferenceProposalBean, "updatedApprover");
		}*/
		if(request.getParameter("action","").equals("submitForGrading")){// && conferenceProposalBean.getApproverId()>0){
			conferenceProposalBean.setSubmitted(true);
			//assign default grade
			String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
			conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), deadline)+1);
		}
		/*if(conferenceProposalBean.getSubmitted() && conferenceProposalBean.getApproverId()>0){
			//assign default grade
			String deadline = configurationService.getConfigurationString("conferenceProposalDeadline");
			conferenceProposalBean.setGrade(conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(), deadline)+1);
		}*/
		if(request.getParameter("action","").equals("unsubmitForGrading")){
			conferenceProposalBean.setSubmitted(false);
			conferenceProposalBean.setGrade(0);
		}
		/*if(!conferenceProposalBean.getSubmitted()){
			conferenceProposalBean.setSubmitted(false);
			conferenceProposalBean.setGrade(0);
		}*/		
		//update
		if(!request.getParameter("startConfDate", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
			Date fromDate = (Date)formatter.parse(request.getParameter("startConfDate", "")); 
			conferenceProposalBean.setFromDate(fromDate.getTime());
		}
		if(!request.getParameter("endConfDate", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
			Date toDate = (Date)formatter.parse(request.getParameter("endConfDate", "")); 
			conferenceProposalBean.setToDate(toDate.getTime());
		}
		if(request.getParameter("action","").equals("submitFaculty")){
			//update only relevant fields
			origConferenceProposalBean.setAdminRemarks(conferenceProposalBean.getAdminRemarks());
			origConferenceProposalBean.setApproverEvaluation(conferenceProposalBean.getApproverEvaluation());
			if(!request.getParameter("cancelSubmission", "").equals("")){
				origConferenceProposalBean.setSubmitted(false);
				origConferenceProposalBean.setGrade(0);
			}
			conferenceProposalService.updateConferenceProposal(origConferenceProposalBean.toConferenceProposal());
		}
		else
			conferenceProposalService.updateConferenceProposal(conferenceProposalBean.toConferenceProposal());
			
		//return to same page
		model.put("id", conferenceProposalBean.getId())	;			
		return new ModelAndView(new RedirectView("editConferenceProposal.html"),model);
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
			DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			Date deadlineD = (Date)formatter.parse(deadline); 
			conferenceProposal.setDeadline(deadlineD.getTime());
			int conferenceProposalId = conferenceProposalService.insertConferenceProposal(conferenceProposal);
			logger.info("conferenceProposalId " + conferenceProposalId);
			model.put("id",conferenceProposalId);
			return new ModelAndView ( new RedirectView("editConferenceProposal.html"), model);
		}
		else{//show edit
			ConferenceProposalBean conferenceProposal = (ConferenceProposalBean) model.get("command");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = new Date(conferenceProposal.getFromDate());
			model.put("startConfDate", formatter.format(fromDate));
			Date toDate = new Date(conferenceProposal.getToDate());
			model.put("endConfDate", formatter.format(toDate));
			
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
				//String userMessage = messageService.getMessage("iw_IL.conferenceProposal.firstVersion");
				//request.getSession().setAttribute("userMessage", userMessage);
				request.getSession().setAttribute("firstVersion", true);
			}
			if (version == conferenceProposalService.getLastVersion(id)){
				//String userMessage = messageService.getMessage("iw_IL.conferenceProposal.lastVersion");
				//request.getSession().setAttribute("userMessage", userMessage);
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
