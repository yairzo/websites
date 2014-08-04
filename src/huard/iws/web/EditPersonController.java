package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonListAttributionBean;
import huard.iws.model.Faculty;
import huard.iws.model.PageBodyImage;
import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.FacultyService;
import huard.iws.service.ListService;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.PersonAttributionListService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.MD5Encoder;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SysUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class EditPersonController extends GeneralFormController {
	//private static final Logger logger = Logger.getLogger(EditPersonController.class);
	private final static boolean UPDATE_LAST_LOGIN = true;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		String action = request.getParameter("action", "");

		PersonBean personBean = (PersonBean) command;
		Map<String, Object> newModel = new HashMap<String, Object>();

		if (action.equals("edit") && personBean.getPersonAttributionId()>0){
			newModel.put("id",personBean.getPersonAttributionId());
			return new ModelAndView( new RedirectView("personAttribution.html"),newModel);
		}
		else if (action.equals("delete") && personBean.getPersonAttributionId()>0){
			newModel.put("id",personBean.getPersonAttributionId());
			return new ModelAndView( new RedirectView("deletePersonAttribution.html"),newModel);
		}
		else if (action.equals("save")){
			if (personBean.getId()>0){
				String imageUrl=uploadImage(request,personBean.getId());
				if(!imageUrl.isEmpty())
					personBean.setImageUrl(imageUrl);
				personService.updatePerson(personBean.toPerson());
				if (personBean.isSelfSubscriber()){
					String md5 = MD5Encoder.digest(personBean.getId() + SysUtils.toTimestamp(System.currentTimeMillis()));
					String privilege = request.getParameter("singlePrivilege", "ROLE_POST_READER");
					personService.updatePersonPrivilegeMD5(personBean.toPerson(), privilege, UPDATE_LAST_LOGIN, md5);
					if (privilege.equals("ROLE_POST_READER"))
						mailMessageService.createPostSubscriptionMail(personBean, md5);
					else if (privilege.equals("ROLE_CONFERENCE_RESEARCHER"))
						mailMessageService.createConferenceSubscriptionMail(personBean, md5);
					String userMessage = messageService.getMessage("iw_IL.general.subscriptionMailSend");
					request.getSession().setAttribute("userMessage", userMessage);
					newModel.put("cp", "j_acegi_logout");
					return new ModelAndView (new RedirectView("viewMessage.html"), newModel);
				}
				else if (personBean.isYearFirstVisit()){
					String userMessage = messageService.getMessage("iw_IL.general.updatePersonDetailsThanks");
					request.getSession().setAttribute("userMessage", userMessage);
					newModel.put("cp", "j_acegi_logout");
					return new ModelAndView (new RedirectView("viewMessage.html"), newModel);
				}
				else{
					personAttributionListService.updateConnectedDetailsPersonAttributions(personBean.getId(),personBean.toPerson().toPersonAttribution());
				}
			}
			else {
				int id = personService.insertPerson(personBean.toPerson());
				personBean.setId(id);
				String imageUrl=uploadImage(request,personBean.getId());
				if(!imageUrl.isEmpty())
					personBean.setImageUrl(imageUrl);
				personService.updatePerson(personBean.toPerson());
		}

		}
		newModel.put("id", personBean.getId());
		return new ModelAndView(new RedirectView("person.html"),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{		
		int id = request.getIntParameter("id", 0);
		if ( userPersonBean.getId() != id && ! userPersonBean.isAuthorized("ADMIN"))
			return new ModelAndView(new RedirectView("accessDenied.html"));

		List<PersonListAttribution> personAttributions = new ArrayList<PersonListAttribution>();
		LanguageUtils.applyLanguages(model);
		List<PersonListAttributionBean> personAttributionBeans = new ArrayList<PersonListAttributionBean>();

		if (id > 0){
			personAttributions = personAttributionListService.getPersonAttributionsByPersonId(id);
			for (PersonListAttribution attrib: personAttributions){
				PersonListAttributionBean attribBean = new PersonListAttributionBean(attrib);
				attribBean.setList(listService.getList("list", attrib.getListId(), userPersonBean.getUsername()));
				personAttributionBeans.add(attribBean);
			}
		}
		model.put("personAttributions", personAttributionBeans);
		List<Faculty> faculties = facultyService.getFaculties();
		model.put("faculties", faculties);

		String callerPage = request.getParameter("cp", "persons.html");
		model.put("cp", callerPage);
		
		LanguageUtils.applyLanguage(model, request, response, "iw_IL");
		return new ModelAndView ( "editPerson", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		Person person = new Person();
		int id = request.getIntParameter("id", 0);
		if (id > 0)
			person = personService.getPerson("person", id, userPersonBean.getUsername());
		PersonBean personBean = new PersonBean(person);
		if (userPersonBean.isAuthorized("EDIT", "USER_DETAILS")){
			if (! personService.isSubscribed(personBean.getId())){
				personBean.setSelfSubscriber(true);
				String privilege = personService.getSinglePrivilege(personBean.getId(), false);
				GrantedAuthority [] grantedAuthorities = new GrantedAuthority[]{
						new GrantedAuthorityImpl(privilege)	};
				personBean.setPersonPriviliges(grantedAuthorities);
			}
			else{
				personBean.setYearFirstVisit(true);
			}
		}
		
		if (userPersonBean.isAuthorized("LISTS", "ADMIN") || userPersonBean.isAuthorized("LISTS", "EDITOR"))
			personBean.setPassValidation(true);

		return personBean;
	}

	protected String uploadImage(RequestWrapper request,int itemId){
		try{
			if (request.getRequest().getContentType().indexOf("multipart")!=-1){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
				Iterator fileNames = multipartRequest.getFileNames();
				if (fileNames.hasNext()) {
					String filename = (String) fileNames.next();
					MultipartFile file = multipartRequest.getFile(filename);
					String originalName = file.getOriginalFilename();
					if (filename.equals("image") && file.getSize()>0){
						PageBodyImageBean pageBodyImageBean = new PageBodyImageBean();
						PageBodyImage existingPageBodyImage=pageBodyImageService.getPageBodyImage("person"+itemId);
						if(existingPageBodyImage.getId()!=0)
							pageBodyImageBean=new PageBodyImageBean(existingPageBodyImage);
						pageBodyImageBean.setImage(file.getBytes());
						pageBodyImageBean.setName( originalName.substring(0,originalName.lastIndexOf(".")));
						pageBodyImageBean.setTitle("person"+itemId);
						if(existingPageBodyImage.getId()!=0)
							pageBodyImageService.updatePageBodyImage(pageBodyImageBean.toPageBodyImage());
						else
							pageBodyImageService.insertPageBodyImage(pageBodyImageBean.toPageBodyImage());
						return pageBodyImageBean.getTitle();
					}
				}
			}
			return "";
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}

	}

	private PersonAttributionListService personAttributionListService;


	public void setPersonAttributionListService(
			PersonAttributionListService personAttributionService) {
		this.personAttributionListService = personAttributionService;
	}

	private ListService listService;


	public void setListService(ListService listService) {
		this.listService = listService;
	}

	private FacultyService facultyService;


	public void setFacultyService(FacultyService facultyService) {
		this.facultyService = facultyService;
	}

	private PageBodyImageService pageBodyImageService;
	public void setPageBodyImageService(PageBodyImageService pageBodyImageService) {
		this.pageBodyImageService = pageBodyImageService;
	}	
	/*private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}*/


}
