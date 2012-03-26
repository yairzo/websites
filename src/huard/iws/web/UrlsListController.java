package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PageUrlBean;
import huard.iws.model.PageUrl;
import huard.iws.service.PersonService;
import huard.iws.service.UrlsCheckerService;
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

public class UrlsListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		UrlListControllerCommand aCommand = (UrlListControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("search"))
			aCommand.getListView().setPage(1);

		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		UrlListControllerCommand command = (UrlListControllerCommand) model.get("command");

		String server = configurationService.getConfigurationString("websiteDb");

		System.out.println("Show form search command: " + command.getSearchCreteria().getWhereClause());
		List<PageUrl> pageUrls = urlsCheckerService.getSearchPubPagesUrls(command.getListView(), command.getSearchCreteria(),server);
		List<PageUrlBean> pubPagesBeans = new ArrayList<PageUrlBean>();
		for (PageUrl pageUrl: pageUrls){
			PageUrlBean pageUrlbean = new PageUrlBean(pageUrl);
			pubPagesBeans.add(pageUrlbean);
		}
		pageUrls = urlsCheckerService.getSearchInfoPagesUrls(command.getListView(), command.getSearchCreteria(),server);
		List<PageUrlBean> infoPagesBeans = new ArrayList<PageUrlBean>();
		for (PageUrl pageUrl: pageUrls){
			PageUrlBean urlbean = new PageUrlBean(pageUrl);
			infoPagesBeans.add(urlbean);
		}
		
		model.put("pageStatus", request.getSession().getAttribute("pageStatus"));
		model.put("infoPagesURLs", infoPagesBeans);
		model.put("pubPagesURLs", pubPagesBeans);
		return new ModelAndView ("urlsList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		UrlListControllerCommand command = new UrlListControllerCommand();
		if (!isFormSubmission(request.getRequest())){

			SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("urlsSearchCreteria");
			if (searchCreteria == null){
				searchCreteria = new SearchCreteria();
				searchCreteria.setWhereClause(" status='dead'");
				request.getSession().setAttribute("pageStatus", "0");
			}

			ListView listView = (ListView) request.getSession().getAttribute("urlsListView");
			request.getSession().setAttribute("urlsListView", null);
			if (listView == null){
				listView = new ListView();
				String orderBy = request.getParameter("orderBy","");
				if( !orderBy.equals(""))
					listView.setOrderBy(orderBy);
				else
					listView.setOrderBy("ardNum");
			}
			
			command.setSearchCreteria(searchCreteria);
			command.setListView(listView);
		}
		if (isFormSubmission(request.getRequest())){
			SearchCreteria searchCreteria = new SearchCreteria();
			String whereClause = "";
			String pageStatus = request.getParameter("pageStatus","");
			if( pageStatus.equals("0")){
				whereClause += " status='dead'";
			}
			else if( pageStatus.equals("1")){
				whereClause += " (status='dead' or status='changed')";
			}
			searchCreteria.setWhereClause(whereClause);
			request.getSession().setAttribute("pageStatus", pageStatus);
			
			command.setSearchCreteria(searchCreteria);
			request.getSession().setAttribute("urlsSearchCreteria", searchCreteria);

			ListView listView = new ListView();
			String orderBy = request.getParameter("orderBy","");
			if( !orderBy.equals(""))
				listView.setOrderBy(orderBy);
			else
				listView.setOrderBy("ardNum");
			listView.setPage(request.getIntParameter("listView.page", 1));			

			command.setListView(listView);
			request.getSession().setAttribute("urlsListView", listView);
		}
		return command;
	}

	public class UrlListControllerCommand{
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();
		/*int urlId=0;
		public int getPostId() {
			return postId;
		}
		public void setPostId(int postId) {
			this.postId = postId;
		}*/
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

	private PersonService personService;

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private UrlsCheckerService urlsCheckerService;

	public UrlsCheckerService getUrlsCheckerService() {
		return urlsCheckerService;
	}

	public void setUrlsCheckerService(UrlsCheckerService urlsCheckerService) {
		this.urlsCheckerService = urlsCheckerService;
	}
}
