package huard.iws.web;

import huard.iws.bean.AListBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.AList;
import huard.iws.service.ListListService;
import huard.iws.service.ListService;
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

public class ListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		AListBean aCommand = (AListBean) command;
		String action = request.getParameter("action", "");
		Map<String, Object> newModel = new HashMap<String, Object>();
		if (action.equals("add")){
			return new ModelAndView(new RedirectView("list.html"),newModel);
		}

		if (action.equals("edit") && aCommand.getId()>0){
			newModel.put("id", aCommand.getId());
			return new ModelAndView(new RedirectView("list.html"),newModel);
		}
		if (action.equals("delete") && aCommand.getId()>0){
			newModel.put("id", aCommand.getId());
			return new ModelAndView(new RedirectView("deleteList.html"),newModel);
		}
		if (action.equals("view") && aCommand.getId()>0){
			return new ModelAndView(new RedirectView("viewList.html?id="+aCommand.getId()));
		}
		if (action.equals("copy") && aCommand.getId()>0){
			int newListId = listService.copyList(aCommand.getId(), request);
			return new ModelAndView(new RedirectView("list.html?id=" + newListId));
		}

		request.getSession().setAttribute("listsSearchCreteria", aCommand.getSearchCreteria());

		if (action.equals("search"))
			aCommand.getListView().setPage(1);

		request.getSession().setAttribute("listsListView", aCommand.getListView());

		return new ModelAndView(new RedirectView(getSuccessView()));
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		recordProtectService.freeRecordsByUsername(userPersonBean.getUsername());

		AListBean command = (AListBean) model.get("command");

		int itemsInPage = request.getSessionIntParameter("iip", 15);

		model.put("iip", itemsInPage);

		// The number of items per page has change, we must show the first page.
		if (request.isRequestHasParameter("iip"))
			command.getListView().setPage(1);

		listListService.prepareListView(command.getListView(), command.getSearchCreteria(),
				userPersonBean, itemsInPage);

		List<AList> lists = listListService.getListsPage(command.getListView(), command.getSearchCreteria(),
				userPersonBean, itemsInPage);

		List<AListBean> listBeans = new ArrayList<AListBean>();
		for (AList aList: lists){
			AListBean aListBean = new AListBean(aList, request);
			listBeans.add(aListBean);
		}

		model.put("lists", listBeans);

		List<AList> allLists = listListService.getLists(userPersonBean);

		model.put("allLists", allLists);

		return new ModelAndView ("listList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		AListBean command = new AListBean(request);

		SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("listsSearchCreteria");
		request.getSession().setAttribute("listsSearchCreteria", null);

		if (searchCreteria == null)
			searchCreteria = new SearchCreteria();
			searchCreteria.setSearchField("name");

		ListView listView = (ListView) request.getSession().getAttribute("listsListView");


		if (listView == null){
			listView = new ListView();
			listView.setOrderBy("lastNameHebrew,firstNameHebrew");

		}
		command.setSearchCreteria(searchCreteria);
		command.setListView(listView);



		return command;
	}

	public class ListControllerCommand{
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

	private RecordProtectService recordProtectService;

	private ListListService listListService;

	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}


	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}
}
