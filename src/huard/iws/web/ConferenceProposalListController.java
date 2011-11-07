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


public class ConferenceProposalListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(PersonListController.class);

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		ConferenceProposalSearchController searchCommand = (ConferenceProposalSearchController)command;

		Map newModel = new HashMap();
		String action = request.getParameter("action", "");

		request.getSession().setAttribute("searchCreteria", searchCommand.getSearchCreteria());

		if (action.equals("search"))
			searchCommand.getListView().setPage(1);

		request.getSession().setAttribute("conferenceProposalsListView", searchCommand.getListView());

		if (action.equals("edit") && searchCommand.getConferenceProposalId()>0){
			newModel.put("id",searchCommand.getConferenceProposalId());
			return new ModelAndView( new RedirectView("conferenceProposal.html"),newModel);
		}
		if (action.equals("delete") && searchCommand.getConferenceProposalId()>0){
			newModel.put("id",searchCommand.getConferenceProposalId());
			return new ModelAndView( new RedirectView("deleteConferenceProposal.html"), newModel);
		}

		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		ConferenceProposalSearchController searchCommand = (ConferenceProposalSearchController) model.get("command");

		List<Person> persons = personListService.getPersonsPage(searchCommand.getListView(), searchCommand.getSearchCreteria());
		List<PersonBean> personBeans = new ArrayList<PersonBean>();

		for (Person person: persons){
			PersonBean personBean = new PersonBean(person);
			personBean.setBusyRecord(recordProtectService.isRecordBusy("person",personBean.getId(), userPersonBean.getUsername()));
			personBeans.add(personBean);
		}
		model.put("persons", personBeans);

		return new ModelAndView ("personList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalSearchController searchCommand = new ConferenceProposalSearchController();
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

			personListService.prepareListView(listView, searchCreteria);

			searchCommand.setSearchCreteria(searchCreteria);
			searchCommand.setListView(listView);

			request.getSession().setAttribute("searchCreteria", null);
			request.getSession().setAttribute("listView", null);
		}
		return searchCommand;
	}

	public class ConferenceProposalSearchController{
		SearchCreteria searchCreteria = new SearchCreteria();
		ListView listView = new ListView();
		int conferenceProposalId=0;


		public int getConferenceProposalId() {
			return conferenceProposalId;
		}
		public void setConferenceProposalId(int conferenceProposalId) {
			this.conferenceProposalId = conferenceProposalId;
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