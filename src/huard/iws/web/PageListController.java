package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class PageListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		//PageListControllerCommand aCommand = (PageListControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		//PageListControllerCommand command = (PageListControllerCommand) model.get("command");

		/*List<PageUrl> pageUrls = urlsCheckerService.getSearchPubPagesUrls(command.getListView(), command.getSearchCreteria(),server);
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
		}*/
		
		//model.put("pageStatus", request.getSession().getAttribute("pageStatus"));
		//model.put("infoPagesURLs", infoPagesBeans);
		//model.put("pubPagesURLs", pubPagesBeans);
		return new ModelAndView ("pageList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		PageListControllerCommand command = new PageListControllerCommand();
		return command;
	}

	public class PageListControllerCommand{
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();

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

}
