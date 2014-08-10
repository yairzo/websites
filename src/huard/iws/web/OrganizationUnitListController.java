package huard.iws.web;

import huard.iws.bean.OrganizationUnitBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.OrganizationUnit;
import huard.iws.service.OrganizationUnitService;
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

public class OrganizationUnitListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		OrganizationUnitListControllerCommand aCommand = (OrganizationUnitListControllerCommand) command;
		String action = request.getParameter("action", "");
		Map<String, Object> newModel = new HashMap<String, Object>();
		if (action.equals("edit") && aCommand.getId()>0){
			newModel.put("id", aCommand.getId());
			return new ModelAndView(new RedirectView("organizationUnit.html"),newModel);
		}
		if (action.equals("delete") && aCommand.getId()>0){
			newModel.put("id", aCommand.getId());
			return new ModelAndView(new RedirectView("deleteOrganizationUnit.html"),newModel);
		}

		request.getSession().setAttribute("organizationunitsSearchCreteria", aCommand.getSearchCreteria());

		// If it's a search the result will start from page 1

		if (action.equals("search"))
			aCommand.getListView().setPage(1);


		request.getSession().setAttribute("organizationunitsListView", aCommand.getListView());

		return new ModelAndView(new RedirectView(getSuccessView()));
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		OrganizationUnitListControllerCommand command = (OrganizationUnitListControllerCommand) model.get("command");

		int itemsInPage = request.getSessionIntParameter("iip", 15);

		model.put("iip", itemsInPage);

		// The number of items per page has change, we must show the first page.
		if (request.isRequestHasParameter("iip"))
			command.getListView().setPage(1);

		organizationUnitService.prepareListView(command.getListView(), command.getSearchCreteria(),
			  itemsInPage);

		List<OrganizationUnit> organizationUnits = organizationUnitService.getOrganizationUnitsPage(command.getListView(),
				command.getSearchCreteria(), itemsInPage);

		List<OrganizationUnitBean> organizationUnitBeans = new ArrayList<OrganizationUnitBean>();
		for (OrganizationUnit organizationUnit: organizationUnits){
			OrganizationUnitBean organizationUnitBean = new OrganizationUnitBean(organizationUnit);
			organizationUnitBeans.add(organizationUnitBean);
		}

		model.put("organizationUnits", organizationUnitBeans);

		//List<OrganizationUnit> allOrganizationUnits = organizationUnitService.getOrganizationUnits();

		//model.put("allOrganizationUnits", allOrganizationUnits);
		model.put("pageName", messageService.getMessage("iw_IL.websiteInterface.organizationUnitsList"));

		return new ModelAndView ("organizationUnitList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		OrganizationUnitListControllerCommand command = new OrganizationUnitListControllerCommand();

		SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("organizationunitsSearchCreteria");
		request.getSession().setAttribute("organizationunitsSearchCreteria", null);

		if (searchCreteria == null)
			searchCreteria = new SearchCreteria();
			searchCreteria.setSearchField("nameHebrew");

		ListView listView = (ListView) request.getSession().getAttribute("organizationunitsListView");


		if (listView == null){
			listView = new ListView();
			listView.setOrderBy("nameHebrew");

		}
		command.setSearchCreteria(searchCreteria);
		command.setListView(listView);



		return command;
	}

	public class OrganizationUnitListControllerCommand{
		private int id;
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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

	private OrganizationUnitService organizationUnitService;

	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}

	private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}
}