package huard.iws.web;

import huard.iws.bean.CallOfProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.bean.SubjectBean;
import huard.iws.model.CallOfProposal;
import huard.iws.model.Subject;
import huard.iws.model.Attachment;
import huard.iws.service.CallOfProposalService;
import huard.iws.service.PersonListService;
import huard.iws.service.SubjectService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.util.BaseUtils;
import huard.iws.util.RequestWrapper;
import java.util.List;
import java.util.ArrayList;

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

public class CallOfProposalController extends GeneralFormController{
	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		CallOfProposalBean callOfProposalBean = (CallOfProposalBean) command;
		

		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			List<Attachment> attachments = new ArrayList<Attachment>();
			String addFile = request.getParameter("addFile", "no");
			if(addFile.equals("yes")){
				while (fileNames.hasNext()) {
					String filename = (String) fileNames.next();
					MultipartFile file = multipartRequest.getFile(filename);
					Attachment attachment = new Attachment();
					attachment.setFile(file.getBytes());
					attachment.setContentType(file.getContentType().equals("application/octet-stream")?"application/vnd.openxmlformats-officedocument.wordprocessingml.document":file.getContentType());
					attachments.add(attachment);
				}
				callOfProposalBean.setAttachments(attachments);
			}
		}	
		
		//subjectIds
		String subjectsIdsString = request.getParameter("subjectsIdsString","");
		List<Integer> subjectsIds = BaseUtils.getIntegerList(subjectsIdsString, ",");
		if (callOfProposalBean.getSubjectsIds() != null)
			callOfProposalBean.getSubjectsIds().clear();
		for (int subjectId: subjectsIds){
			callOfProposalBean.getSubjectsIds().add(subjectId);
		}
		
		//submissionDates
		List<Long> submissionDates= new ArrayList<Long>();
		if(!request.getParameter("submissionDate1", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(request.getParameter("submissionDate1", ""));
			submissionDates.add(formattedDate.getTime());
		}
		if(!request.getParameter("submissionDate2", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(request.getParameter("submissionDate2", "")); 
			submissionDates.add(formattedDate.getTime());
		}
		if(!request.getParameter("submissionDate3", "").equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(request.getParameter("submissionDate3", "")); 
			submissionDates.add(formattedDate.getTime());
		}
		callOfProposalBean.setSubmissionDates(submissionDates);
		
		//update
		callOfProposalService.updateCallOfProposal(callOfProposalBean.toCallOfProposal());

		//upload
		if (request.getBooleanParameter("online", false)){
			if(callOfProposalService.existsCallOfProposalOnline(callOfProposalBean.getId()))
				callOfProposalService.updateCallOfProposalOnline(callOfProposalBean.toCallOfProposal());
			else
				callOfProposalService.insertCallOfProposalOnline(callOfProposalBean.toCallOfProposal());
		}
		if (request.getBooleanParameter("offline", false)){
			if(callOfProposalService.existsCallOfProposalOnline(callOfProposalBean.getId()))
				callOfProposalService.removeCallOfProposalOnline(callOfProposalBean.getId());
		}
			
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callOfProposalBean.getId())	;
		return new ModelAndView(new RedirectView("callOfProposal.html"), newModel);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{
		
		int id = request.getIntParameter("id", 0);
		Subject rootSubject = subjectService.getSubject(1, "iw_IL");
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, "iw_IL");
		model.put("rootSubject", rootSubjectBean);
		
		
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			CallOfProposal callOfProposal= new CallOfProposal();
			callOfProposal.setCreatorId(userPersonBean.getId());
			int callOfProposalId = callOfProposalService.insertCallOfProposal(callOfProposal);
			logger.info("callOfProposalId " + callOfProposalId);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",callOfProposalId);
			return new ModelAndView ( new RedirectView("callOfProposal.html"), newModel);
		}
		else{//show edit
			CallOfProposalBean callOfProposal = (CallOfProposalBean) model.get("command");
			
			//title
			String title="";
			if(!callOfProposal.getTitle().startsWith("###"))
				title = callOfProposal.getTitle();
			model.put("title", title);
			
			//desk contact persons
			List<PersonBean> deskPersons = mopDeskService.getPersonsList(callOfProposal.getDeskId(),0);
			model.put("deskPersons", deskPersons);
			//desk budget officers
			List<PersonBean> deskBudgetPersons = new ArrayList<PersonBean>();
			for(PersonBean personBean:deskPersons){
				if(personBean.getTitle().indexOf("תקציב")>=0)
					deskBudgetPersons.add(personBean);
			}
			model.put("deskBudgetPersons", deskBudgetPersons);
			//desk assistants
			List<PersonBean> deskAssistants = new ArrayList<PersonBean>();
			for(PersonBean personBean:deskPersons){
				if(personBean.getTitle().indexOf("עוזר")>=0)
					deskAssistants.add(personBean);
			}
			model.put("deskAssistants", deskAssistants);
			
			//dates
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date tmpDate = new Date();
			if (callOfProposal.getPublicationTime().equals("01/01/1970"))
				model.put("publicationTime", "");
			else
				model.put("publicationTime", callOfProposal.getPublicationTime());
			if (callOfProposal.getFinalSubmissionTime().equals("01/01/1970"))
				model.put("finalSubmissionTime", "");
			else
				model.put("finalSubmissionTime", callOfProposal.getFinalSubmissionTime());
			if (callOfProposal.getKeepInRollingMessagesExpiryTime().equals("01/01/1970"))
				model.put("keepInRollingMessagesExpiryTime", "");
			else
				model.put("keepInRollingMessagesExpiryTime", callOfProposal.getKeepInRollingMessagesExpiryTime());

			//extra submission dates
			int i=1;
			if(callOfProposal.getSubmissionDates()!=null){
				for(Long submissionDate: callOfProposal.getSubmissionDates()){
					tmpDate = new Date(submissionDate);
					String submissionName="submissionDate" + i;
					model.put(submissionName, formatter.format(tmpDate));
					i++;
				}
			}
			//funds
			String selectedFund="";
			if(callOfProposal.getFundId()>0){
				Fund fund = fundService.getFund(callOfProposal.getFundId());
				if (fund!=null)
					selectedFund=fund.getName();
			}
			model.put("selectedFund", selectedFund);
			//desks
			List<MopDesk> mopDesks = mopDeskService.getMopDesks();
			model.put("mopDesks", mopDesks);
			//online
			if(callOfProposalService.existsCallOfProposalOnline(callOfProposal.getId()))
				model.put("online", true);
			else
				model.put("online", false);
			
			model.put("id",callOfProposal.getId());
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		CallOfProposalBean callOfProposalBean = new CallOfProposalBean();
		//logger.info("action : " + request.getParameter("action",""));
		
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return callOfProposalBean;
		
		callOfProposalBean = new CallOfProposalBean(callOfProposalService.getCallOfProposal(id),false);
		
		return callOfProposalBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	
	private CallOfProposalService callOfProposalService;

	public void setCallOfProposalService(CallOfProposalService callOfProposalService) {
		this.callOfProposalService = callOfProposalService;
	}
	
	private SubjectService subjectService;

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
	
	

}
