package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.PersonService;
import huard.iws.service.PersonPrivilegeService;
import huard.iws.model.Person;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ActivePersonsController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		ActivePersonsControllerCommand aCommand = (ActivePersonsControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("search"))
			aCommand.getListView().setPage(1);

		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		List<Integer> personids = personPrivilegeService.getActivePersons();
		List<PersonBean> persons =  new ArrayList<PersonBean>();
		for (int i=0;i<personids.size();i++){
			Person person = personService.getPerson(personids.get(i));
			persons.add(new PersonBean(person));
		}
			
		model.put("activePersons", persons);
		return new ModelAndView ("activePersonsList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		ActivePersonsControllerCommand command = new ActivePersonsControllerCommand();
		return command;
	}

	public class ActivePersonsControllerCommand{
		private ListView listView = new ListView();

		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}


	}

	private PersonService personService;

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
