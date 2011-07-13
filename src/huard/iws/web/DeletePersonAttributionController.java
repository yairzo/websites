package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonListAttributionBean;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListService;
import huard.iws.service.PersonAttributionService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeletePersonAttributionController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeletePersonAttributionController.class);

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		PersonListAttributionBean personAttributionBean = (PersonListAttributionBean) command;
		personAttributionService.deletePersonAttribution(personAttributionBean.getId(), personAttributionBean.getListId());
		Map newModel = new HashMap();
		newModel.put("id", personAttributionBean.getPersonId());
		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deletePersonAttribution", model);
	}


	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		PersonListAttribution personAttribution = new PersonListAttribution();

		int aId = request.getIntParameter("id", 0);
		if (aId > 0)
			personAttribution = personAttributionService.getPersonAttribution("personAttribution", aId,
					userPersonBean.getUsername());

		PersonListAttributionBean personAttributionBean = new PersonListAttributionBean(personAttribution);
		personAttributionBean.setPerson(personService.getPerson("person",personAttributionBean.getPersonId(),
				userPersonBean.getUsername()));
		personAttributionBean.setList(listService.getList("list",personAttributionBean.getListId(),
				userPersonBean.getUsername()));
		return personAttributionBean;
	}

	private PersonAttributionService personAttributionService;

	public void setPersonAttributionService(PersonAttributionService personAttributionService) {
		this.personAttributionService = personAttributionService;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}

}
