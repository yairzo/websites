package huard.iws.web;

import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PersonBean;
import huard.iws.service.ConfigurationService;
import huard.iws.service.GeneralService;
import huard.iws.service.HujiAuthorizationService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonPrivilegeService;
import huard.iws.service.PersonService;
import huard.iws.service.UserMessageService;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.util.UserPersonUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public abstract class GeneralFormController extends SimpleFormController{

	private static final Logger logger = Logger.getLogger(GeneralFormController.class);
	private final String ERRORS_MAIL_ADDRESS = "hadar123@gmail.com,zohar.yair@gmail.com";
	//private final String ERRORS_MAIL_ADDRESS = "hadar@localhost.localdomain";

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception
			{
		request.setCharacterEncoding("UTF-8");
		RequestWrapper requestWrapper = new RequestWrapper( request);
		logger.info("personService is null " + (personService == null));
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);
		try
		{
			return getFormBackingObject(requestWrapper, userPersonBean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
			}

	/**
	 * Overriding classes must implement this, even if no support for backing
	 * object is required.
	 *
	 * @param requestWrapper
	 * @return
	 */
	protected abstract Object getFormBackingObject(
			RequestWrapper requestWrapper, PersonBean userPersonBean) throws Exception;




	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
					throws Exception
					{
		RequestWrapper requestWrapper = new RequestWrapper( request);
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);
		personPrivilegeService.updateLastAction(userPersonBean);
		Object command = errors.getTarget();
		Map<String, Object> model = errors.getModel();
		List<FieldError> errorsList = errors.getBindingResult().getFieldErrors();
		String errorsMessage="";
		for (FieldError error : errorsList ) {
			logger.info (error.getObjectName() + " - " + error.getDefaultMessage());
			errorsMessage += "<br>" + error.getObjectName() + " - " + error.getDefaultMessage();
		}
		if(!errorsMessage.isEmpty()){
			errorsMessage="User:" + userPersonBean.getDegreeFullName() + errorsMessage;
			MailMessageBean mailMessageBean = new MailMessageBean();
			mailMessageBean.setAdditionalAddresses(ERRORS_MAIL_ADDRESS);
			mailMessageBean.setMessageSubject("Form Errors");
			mailMessageBean.setMessage(errorsMessage);
			mailMessageService.sendMailMessage(mailMessageBean);
		}

		model.put("command", command);
		model.put("userPersonBean", userPersonBean);
		model.put("validationErrors", errors.hasErrors());

		String callerPage = requestWrapper.getParameter("cp", "welcome.html");
		int callerPageObjectId = requestWrapper.getIntParameter("cpoi", 0);
		if (callerPageObjectId == 0)
			callerPage = "welcome.html";
		model.put("cp", callerPage);
		model.put("cpoi", callerPageObjectId);
		String userMessage;
		if ((userMessage = userMessageService.getSessionUserMessage(request)) != null){
			model.put("userMessage", userMessage);
		}

		model.put("server", configurationService.getConfigurationString("iws", "server"));

		String lastUpdate = calculateLastUpdate(requestWrapper);
		model.put("lastUpdate", lastUpdate);

		LanguageUtils.applyLanguage(model, requestWrapper, response, userPersonBean.getPreferedLocaleId());

		LanguageUtils.applyLanguages(model);

		String showPopup =  configurationService.getConfigurationString("iws", "showPopup");
		if(showPopup.equals("yes")){
			String popupMessage = messageService.getMessage("iw_IL.general.popup");
			model.put("popupMessage", popupMessage);
		}
		else{
			model.put("popupMessage", "");
		}
		
		model.put("updateTime", generalService.getLastUpdate());
		
		//logger.info("Lang general: " + ((Language)model.get("lang")).getLocaleId());
		ModelAndView modelAndView = onShowForm(requestWrapper, response, userPersonBean, model);
		if (modelAndView != null)
			return modelAndView;


		return super.showForm(request, response, errors);
	}

	/**
	 * This method must be implemented, and the implementations may choose to
	 * return another model and view, or null.
	 *
	 * @param requestWrapper
	 * @return
	 * @throws Exception
	 */
	protected abstract ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception;


	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception
					{
		RequestWrapper requestWrapper = new RequestWrapper( request );
		Map<String, Object> model = null;
		if (errors != null)
			model = errors.getModel();
		else
			model = new HashMap<String, Object>();

		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);
		return onSubmit(command, model, requestWrapper, userPersonBean);
					}

	/**
	 * Implemening classes must implement this method, which is called on form
	 * submit
	 *
	 * @param command
	 * @param model
	 * @param requestWrapper
	 * @return
	 * @throws Exception
	 */
	protected abstract ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
					throws Exception;



	private String calculateLastUpdate(RequestWrapper request){
		HttpSession session = request.getSession();
		String lastUpdate = (String) session.getAttribute("lastUpdate");
		if (lastUpdate == null){
			Calendar c = new GregorianCalendar();
			c.setTime ( new Date() );
			int day = c.get(Calendar.DAY_OF_WEEK);
			if (day == 6)
				c.add(Calendar.DAY_OF_WEEK, -1);
			else if (day == 7)
				c.add(Calendar.DAY_OF_WEEK, -2);
			else{
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int daysOffset = hour < 10 ? -1 : 0;
				c.add(Calendar.DAY_OF_WEEK, daysOffset);
			}
			lastUpdate = DateUtils.formatDate(c.getTimeInMillis(), "dd/MM/yyyy");
			session.setAttribute("lastUpdate", lastUpdate);
		}
		return lastUpdate;
	}


	protected PersonPrivilegeService personPrivilegeService;

	public void setPersonPrivilegeService(PersonPrivilegeService personPrivilegeService) {
		this.personPrivilegeService = personPrivilegeService;
	}

	protected PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	protected UserMessageService userMessageService;

	public void setUserMessageService(UserMessageService userMessageService) {
		this.userMessageService = userMessageService;
	}

	protected MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	protected ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	protected MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}
	
	protected HujiAuthorizationService hujiAuthorizationService;

	public void setHujiAuthorizationService(
			HujiAuthorizationService hujiAuthorizationService) {
		this.hujiAuthorizationService = hujiAuthorizationService;
	}
	
	public GeneralService generalService;

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
}
