package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.TextualPage;
import huard.iws.service.SphinxSearchService;
import huard.iws.service.TextualPageService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.TextualPageSearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TextualPageListController extends GeneralFormController {

    private final int ROWS_IN_PAGE=20;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		return new ModelAndView(new RedirectView(getSuccessView()),new HashMap<String, Object>());
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageListControllerCommand command = (TextualPageListControllerCommand) model.get("command");
		List<TextualPage> textualPages = textualPageService.getTextualPages(command.getListView(),command.getSearchCreteria());
		List<TextualPageBean> textualPageBeans = new ArrayList<TextualPageBean>();
		for (TextualPage textualPage: textualPages){
			TextualPageBean textualPageBean = new TextualPageBean(textualPage);
			if(textualPageBean.getTitle().startsWith("###"))
				textualPageBean.setTitle("");
			textualPageBeans.add(textualPageBean);
		}
		model.put("textualPages", textualPageBeans);

		//show searched params
		model.put("searchWords",command.getSearchCreteria().getSearchWords().replace("\"", "&quot;"));
		model.put("searchDeleted",command.getSearchCreteria().getSearchDeleted());
		model.put("searchList",command.getSearchCreteria().getSearchList());
		
		return new ModelAndView ("textualPages",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageListControllerCommand command = new TextualPageListControllerCommand();

		if (isFormSubmission(request.getRequest())){
			TextualPageSearchCreteria searchCreteria = new TextualPageSearchCreteria();
			Set<Long> sphinxTextualIds=new LinkedHashSet<Long>();
			String escapedSearchWord= request.getParameter("searchWords", "").replace("'", "\\\'");
			escapedSearchWord= escapedSearchWord.replace("\"", "\\\"");
			if(!request.getParameter("searchWords", "").isEmpty()){
				sphinxTextualIds.add(new Long(0));//so wont show everything when deos'nt find any ids
				sphinxTextualIds.addAll(sphinxSearchService.getMatchedIds(escapedSearchWord,"textual_page_draft_index"));
			}
			searchCreteria.setSearchBySearchWords(sphinxTextualIds);
			searchCreteria.setSearchWords(escapedSearchWord);
			searchCreteria.setSearchDeleted(request.getBooleanParameter("deleted", false));
			searchCreteria.setSearchList(request.getBooleanParameter("isList", false));
			if(userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT"))
				searchCreteria.setSearchByCreator(userPersonBean.getId());	
			request.getSession().setAttribute("textualPageSearchCreteria", searchCreteria);

			ListView listView = new ListView();
			if(request.getParameter("action", "").equals("search"))
				listView.setPage(1);
			else//pagination
				listView.setPage(request.getIntParameter("listView.page", 1));			
			request.getSession().setAttribute("textualPageListView", listView);
		}
		else{
			ListView listView = (ListView) request.getSession().getAttribute("textualPageListView");
			request.getSession().setAttribute("textualPageListView", null);
			if (listView == null)
				listView = new ListView();
			//add how many rows
			listView.setRowsInPage(ROWS_IN_PAGE);

			TextualPageSearchCreteria searchCreteria = (TextualPageSearchCreteria) request.getSession().getAttribute("textualPageSearchCreteria");
			request.getSession().setAttribute("textualPageSearchCreteria", null);
			if (searchCreteria == null)// on first time
				searchCreteria = new TextualPageSearchCreteria();
			if(userPersonBean.isAuthorized("ROLE_WEBSITE_EDIT"))
				searchCreteria.setSearchByCreator(userPersonBean.getId());	
			command.setSearchCreteria(searchCreteria);

			textualPageService.prepareListView(listView,searchCreteria);
			command.setListView(listView);
		}
		return command;
	}

	public class TextualPageListControllerCommand{
		private TextualPageSearchCreteria searchCreteria = new TextualPageSearchCreteria();
		private ListView listView = new ListView();

		public TextualPageSearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(TextualPageSearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}

	}
	
	private SphinxSearchService sphinxSearchService;

	public void setSphinxSearchService(SphinxSearchService sphinxSearchService) {
		this.sphinxSearchService = sphinxSearchService;
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}

}
