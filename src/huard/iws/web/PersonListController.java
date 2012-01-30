package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.Person;
import huard.iws.service.PersonListService;
import huard.iws.service.RecordProtectService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class PersonListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(PersonListController.class);
    private final int ROWS_IN_PAGE=10;
    
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		PersonListControllerCommand aCommand = (PersonListControllerCommand)command;

		Map<String, Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		request.getSession().setAttribute("personsSearchCreteria", aCommand.getSearchCreteria());
	
		if (action.equals("search"))
			aCommand.getListView().setPage(1);

		request.getSession().setAttribute("personsListView", aCommand.getListView());

		if (action.equals("edit") && aCommand.getPersonId()>0){
			newModel.put("id",aCommand.getPersonId());
			return new ModelAndView( new RedirectView("person.html"),newModel);
		}
		if (action.equals("delete") && aCommand.getPersonId()>0){
			newModel.put("id",aCommand.getPersonId());
			return new ModelAndView( new RedirectView("deletePerson.html"), newModel);
		}

		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		PersonListControllerCommand command = (PersonListControllerCommand) model.get("command");
		List<Person> persons = personListService.getPersonsPage(command.getListView(), command.getSearchCreteria());
		List<PersonBean> personBeans = new ArrayList<PersonBean>();

		for (Person person: persons){
			PersonBean personBean = new PersonBean(person);
			personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			if (! personBean.getDegreeFullName().trim().isEmpty())
				personBeans.add(personBean);
		}
		
		if (personBeans.size() == 1){
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id", personBeans.get(0).getId());
			return new ModelAndView(new RedirectView("person.html"), newModel);
		}
		
		model.put("persons", personBeans);
		
		

		return new ModelAndView ("personList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		PersonListControllerCommand aCommand = new PersonListControllerCommand();
		if (!isFormSubmission(request.getRequest())){
			SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("personsSearchCreteria");
			request.getSession().setAttribute("personsSearchCreteria", null);
			ListView listView = (ListView) request.getSession().getAttribute("personsListView");
			
			if (searchCreteria == null){
				searchCreteria = new SearchCreteria();
				searchCreteria.setSearchField("lastNameHebrew");
				int roleFilterId = request.getIntParameter("rf", 0);
				String roleFilter = Constants.getUsersRoles().get(roleFilterId);
				if (roleFilter == null)
					roleFilter = "";
				searchCreteria.setRoleFilter(roleFilter);
				listView = null;
			}

			if (listView == null){
				listView = new ListView();
				listView.setOrderBy("lastNameHebrew,firstNameHebrew");
			}
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);
			personListService.prepareListView(listView, searchCreteria);

			aCommand.setSearchCreteria(searchCreteria);
			aCommand.setListView(listView);

			request.getSession().setAttribute("searchCreteria", null);
			request.getSession().setAttribute("listView", null);
		}
		return aCommand;
	}

	public class PersonListControllerCommand{
		SearchCreteria searchCreteria = new SearchCreteria();
		ListView listView = new ListView();
		int personId=0;


		public int getPersonId() {
			return personId;
		}
		public void setPersonId(int personId) {
			this.personId = personId;
		}
		public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}


	}

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}
}