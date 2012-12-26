package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.ConfigurationService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonPrivilegeService;
import huard.iws.service.PersonService;
import huard.iws.service.UserMessageService;
import huard.iws.util.DateUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.util.UserPersonUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class GeneralController extends AbstractController{

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestWrapper requestWrapper = new RequestWrapper( request );
		Map<String, Object> model = new HashMap<String, Object>();
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService);
		personPrivilegeService.updateLastAction(userPersonBean);
		model.put("userPersonBean", userPersonBean);
		String userMessage;
		if ((userMessage = userMessageService.getSessionUserMessage(request))!=null){
			model.put("userMessage", userMessage);
		}
		String callerPage = requestWrapper.getParameter("cp", "welcome.html");
		model.put("cp", callerPage);

		String lastUpdate = calculateLastUpdate(requestWrapper);
		model.put("lastUpdate", lastUpdate);

		String showPopup =  configurationService.getConfigurationString("iws", "showPopup");
		if(showPopup.equals("yes")){
			String popupMessage = messageService.getMessage("iw_IL.general.popup");
			model.put("popupMessage", popupMessage);
		}
		else{
			model.put("popupMessage", "");
		}

		return handleRequest(requestWrapper, response, model, userPersonBean);
	}

	protected abstract ModelAndView handleRequest(
			RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean personBean) throws Exception;


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

	protected PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	protected PersonPrivilegeService personPrivilegeService;

	public void setPersonPrivilegeService(PersonPrivilegeService personPrivilegeService) {
		this.personPrivilegeService = personPrivilegeService;
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

}
