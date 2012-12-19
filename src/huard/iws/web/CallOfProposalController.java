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
					String originalName = file.getOriginalFilename();
					String ext = originalName.substring(originalName.lastIndexOf(".")+1);
					String title = originalName;
					if(originalName.lastIndexOf(".")>-1)
						title = originalName.substring(0,originalName.lastIndexOf("."));
					Attachment attachment = new Attachment();
					attachment.setFile(file.getBytes());
					attachment.setContentType(getContentType(ext));
					attachment.setTitle(title);
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
//System.out.println("1111111111111111111111 ajaxSubmit:"+request.getBooleanParameter("ajaxSubmit", false));			
		if (request.getBooleanParameter("ajaxSubmit", false))
			return null;
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callOfProposalBean.getId())	;
		return new ModelAndView(new RedirectView("callForProposal.html"), newModel);
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
			return new ModelAndView ( new RedirectView("callForProposal.html"), newModel);
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
			if (callOfProposal.getPublicationTime()==0)
				model.put("publicationTime", "");
			else
				model.put("publicationTime", formatter.format(callOfProposal.getPublicationTime()));
			if (callOfProposal.getFinalSubmissionTime()==0)
				model.put("finalSubmissionTime", "");
			else
				model.put("finalSubmissionTime", formatter.format(callOfProposal.getFinalSubmissionTime()));
			if (callOfProposal.getKeepInRollingMessagesExpiryTime()==0)
				model.put("keepInRollingMessagesExpiryTime", "");
			else
				model.put("keepInRollingMessagesExpiryTime", formatter.format(callOfProposal.getKeepInRollingMessagesExpiryTime()));

			//extra submission dates
			Date tmpDate = new Date();
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
				Fund fund = fundService.getFundByFinancialId(callOfProposal.getFundId());
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
		
		//dates
		if(request.getParameter("publicationTimeStr", "").equals("")){
			callOfProposalBean.setPublicationTime(0);
		}
		else{
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("publicationTimeStr", "")); 
				callOfProposalBean.setPublicationTime(formattedDate.getTime());
			}
			catch(Exception e){
				callOfProposalBean.setPublicationTime(0);
			}
		}
		if(request.getParameter("finalSubmissionTimeStr", "").equals("")){
			callOfProposalBean.setFinalSubmissionTime(0);
		}
		else{
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("finalSubmissionTimeStr", "")); 
				callOfProposalBean.setFinalSubmissionTime(formattedDate.getTime());
			}
			catch(Exception e){
				callOfProposalBean.setFinalSubmissionTime(0);
			}
		}
		if(request.getParameter("keepInRollingMessagesExpiryTimeStr", "").equals("")){
			callOfProposalBean.setKeepInRollingMessagesExpiryTime(0);
		}
		else{
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("keepInRollingMessagesExpiryTimeStr", "")); 
				callOfProposalBean.setKeepInRollingMessagesExpiryTime(formattedDate.getTime());
			}
			catch(Exception e){
				callOfProposalBean.setKeepInRollingMessagesExpiryTime(0);
			}
		}
		
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
