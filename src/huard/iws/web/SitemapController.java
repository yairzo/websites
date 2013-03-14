package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CategoryBean;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.Category;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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

		model.put("pageTitle", "מפת האתר");

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

		return new ModelAndView (this.getFormView(),model);
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
	
}
