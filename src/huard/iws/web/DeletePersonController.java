package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.Person;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeletePersonController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeletePersonController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Person person = (Person) command;
		personService.deletePerson(person.getId());
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deletePerson", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		Person person = new Person();
		int aId = request.getIntParameter("id", 0);
		if (aId > 0)
			person = personService.getPerson("person", aId, userPersonBean.getUsername());
		return person;
	}

}
