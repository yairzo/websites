package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.Country;
import huard.iws.model.Language;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;
import huard.iws.service.CallForProposalService;
import huard.iws.service.CountryService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PersonListService;
import huard.iws.service.SubjectService;
import huard.iws.util.BaseUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

public class EditCallForProposalController extends GeneralFormController{
	
	private static final Logger logger = Logger.getLogger(GeneralFormController.class);
	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		CallForProposalBean callForProposalBean = (CallForProposalBean) command;

		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callForProposalBean.getId())	;

		if(request.getIntParameter("deleteAttachment", 0) > 0){
			callForProposalService.deleteFile(request.getIntParameter("deleteAttachment", 0));
			return new ModelAndView(new RedirectView("editCallForProposal.html"), newModel);
		}
		
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
					attachment.setContentType(TextUtils.getContentType(ext));
					attachment.setTitle(TextUtils.getIcon(ext) + title);
					attachments.add(attachment);
				}
				callForProposalBean.setAttachments(attachments);
			}
		}	
		
		//title
		if(!request.getParameter("tempTitle", "").isEmpty())
			callForProposalBean.setTitle(request.getParameter("tempTitle", ""));
		if(!request.getParameter("tempUrlTitle", "").isEmpty())
			callForProposalBean.setUrlTitle(request.getParameter("tempUrlTitle", ""));
		
		//subjectIds
		String subjectsIdsString = request.getParameter("subjectsIdsString","");
		List<Integer> subjectsIds = BaseUtils.getIntegerList(subjectsIdsString, ",");
		if (callForProposalBean.getSubjectsIds() != null)
			callForProposalBean.getSubjectsIds().clear();
		
		for (int subjectId: subjectsIds){
			callForProposalBean.getSubjectsIds().add(subjectId);
		}
		
		//countries
		String countryArr = request.getParameter("countryArr","");
		callForProposalBean.setCountryIds(BaseUtils.getIntegerList(countryArr, ","));
		
		
		if(request.getParameter("action", "").equals("delete"))
			callForProposalBean.setIsDeleted(1);
		
		//update
		callForProposalService.updateCallForProposal(callForProposalBean.toCallForProposal());


		//upload
		if (request.getBooleanParameter("online", false)){
			if(callForProposalService.existsCallForProposalOnline(callForProposalBean.getId()))
				callForProposalService.updateCallForProposalOnline(callForProposalBean.toCallForProposal());
			else
				callForProposalService.insertCallForProposalOnline(callForProposalBean.toCallForProposal());
		}
		
		if (request.getBooleanParameter("offline", false) || request.getParameter("action", "").equals("delete")){
			if(callForProposalService.existsCallForProposalOnline(callForProposalBean.getId()))
				callForProposalService.removeCallForProposalOnline(callForProposalBean.getId());
		}

		if(request.getParameter("action", "").equals("copy")){
			int newId = callForProposalService.copyCallForProposal(callForProposalBean.toCallForProposal());
			return new ModelAndView(new RedirectView("editCallForProposal.html?id=" + newId));
		}
		
		if(request.getParameter("action", "").equals("delete")){
			return new ModelAndView(new RedirectView("callForProposals.html"));
		}

		if (request.getBooleanParameter("ajaxSubmit", false))
			return null;
		
		return new ModelAndView(new RedirectView("editCallForProposal.html"), newModel);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{
		
		CallForProposalBean callForProposalBean = (CallForProposalBean) model.get("command");
		if(callForProposalBean.getId()==0 && !request.getParameter("action", "").equals("new"))//someone entered non-existing id
			return new ModelAndView ( "pageNotFound", model);
		
		int id = request.getIntParameter("id", 0);
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			CallForProposal callForProposal= new CallForProposal();
			callForProposal.setCreatorId(userPersonBean.getId());
			callForProposal.setLocaleId(userPersonBean.getPreferedLocaleId());
			int callForProposalId = callForProposalService.insertCallForProposal(callForProposal);
			logger.info("callForProposalId " + callForProposalId);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",callForProposalId);
			return new ModelAndView ( new RedirectView("editCallForProposal.html"), newModel);
		}
		else{//show edit
			
			//language
			LanguageUtils.applyLanguage(model, callForProposalBean.getLocaleId());
			

			//subjects
			Subject rootSubject = subjectService.getSubject(1, callForProposalBean.getLocaleId());
			SubjectBean rootSubjectBean = new SubjectBean(rootSubject, callForProposalBean.getLocaleId());
			model.put("rootSubject", rootSubjectBean);

			//title
			String title="";
			if(!callForProposalBean.getTitle().startsWith("###"))
				title = callForProposalBean.getTitle();
			title= title.replace("\"","&quot;");
			model.put("title", title);
			String urlTitle="";
			if(!callForProposalBean.getUrlTitle().startsWith("###"))
				urlTitle = callForProposalBean.getUrlTitle();
			urlTitle= urlTitle.replace("\"","&quot;");
			model.put("urlTitle", urlTitle);
			
			//desk contact persons
			String budgetTitle="Budget";
			String assistantTitle="Assistant";
			List<PersonBean> deskPersons =new ArrayList<PersonBean>();
			Language language= new Language();
			language = (Language)model.get("lang");
			if(language.getLocaleId().equals("iw_IL")){
				budgetTitle="תקציב";
				assistantTitle="עוזר";
				deskPersons=mopDeskService.getPersonsList(callForProposalBean.getDeskId(),0);
			}
			else
				deskPersons=mopDeskService.getPersonsListEnglish(callForProposalBean.getDeskId(),0);
			model.put("deskPersons", deskPersons);
			//desk budget officers
			List<PersonBean> deskBudgetPersons = new ArrayList<PersonBean>();
			for(PersonBean personBean:deskPersons){
				if(personBean.getTitle().indexOf(budgetTitle)>=0)
					deskBudgetPersons.add(personBean);
			}
			model.put("deskBudgetPersons", deskBudgetPersons);
			//desk assistants
			List<PersonBean> deskAssistants = new ArrayList<PersonBean>();
			for(PersonBean personBean:deskPersons){
				if(personBean.getTitle().indexOf(assistantTitle)>=0)
					deskAssistants.add(personBean);
			}
			model.put("deskAssistants", deskAssistants);
			
			//desks
			List<MopDesk> mopDesks = mopDeskService.getPublishingMopDesks();
			model.put("mopDesks", mopDesks);
			
			//countries
			List<Country> countries = new ArrayList<Country>();
			for(Integer countryId:callForProposalBean.getCountryIds())
				countries.add(countryService.getCountry(countryId));
			model.put("countries", countries);
					
			//creators
			int listId = configurationService.getConfigurationInt("post","postCreatorsListId");
			List<PersonBean> creators = personListService.getPersonsList(listId, language.getLocaleId());
			model.put("creators", creators);

					

			//online
			if(callForProposalService.existsCallForProposalOnline(callForProposalBean.getId()))
				model.put("online", true);
			else
				model.put("online", false);
			
			model.put("id",callForProposalBean.getId());
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		CallForProposalBean callForProposalBean = new CallForProposalBean();
		//logger.info("action : " + request.getParameter("action",""));
		
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return callForProposalBean;
		
		callForProposalBean = new CallForProposalBean(callForProposalService.getCallForProposal(id),true);
		
		return callForProposalBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	

	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
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
	
	private CountryService countryService;

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}
	
	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

}
