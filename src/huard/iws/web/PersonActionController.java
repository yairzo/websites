package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.PersonService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Leon
 *
 */
public class PersonActionController extends GeneralController {

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private static final Logger logger = Logger.getLogger(PersonActionController.class);

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		

		if (request.isRequestHasParameter("actionCommand") && request.isRequestHasParameter("personId")) {
			if (request.getParameter("actionCommand", "").equals("changeCollectPublications")) {
				int personId = request.getIntParameter("personId", 0);
				logger.info("Changing Collection of publications for person ID: "+personId);
				personService.changeCollectPublications(personId);
			}
		}
		
		return null;
	}


}