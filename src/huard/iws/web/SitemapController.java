package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.model.Language;
import huard.iws.service.GeneralService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SitemapController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		return new ModelAndView(new RedirectView("sitemap.html"));
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		//page title
		Language lang = (huard.iws.model.Language)model.get("lang");
		model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".general.siteMap"));

		Category rootCategoryHeb = categoryService.getRootCategory("iw_IL");
		model.put("rootCategoryHeb", rootCategoryHeb);
		List <Category> hebCategories = categoryService.getCategories(rootCategoryHeb.getId());
		List <CategoryBean> hebCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: hebCategories){
			hebCategoryBeans.add( new CategoryBean (category));
		}
		model.put("hebCategories", hebCategoryBeans);
		Category rootCategoryEng = categoryService.getRootCategory("en_US");
		model.put("rootCategoryEng", rootCategoryEng);
		List <Category> engCategories = categoryService.getCategories(rootCategoryEng.getId());
		List <CategoryBean> engCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: engCategories){
			engCategoryBeans.add( new CategoryBean (category));
		}
		model.put("engCategories", engCategoryBeans);

		model.put("updateTime", generalService.getLastUpdate());
		
		
		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("sitemapStatic",model);
		else
			return new ModelAndView ("sitemap",model);		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SitemapBean sitemapBean = new SitemapBean();
		return sitemapBean;
	}

	public class SitemapBean{
		private ListView listView = new ListView();

		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}

	}
	
	public GeneralService generalService;

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
}
