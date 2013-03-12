package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CategoryBean;
import huard.iws.service.CategoryService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;
import huard.iws.model.Category;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SitemapController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		return new ModelAndView(new RedirectView("sitemap.html"));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		//top categories
		Category rootCategory = categoryService.getRootCategory("iw_IL");
		List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
		List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: languageRootCategories){
			languageRootCategoryBeans.add( new CategoryBean (category));
		}
		model.put("languageRootCategories", languageRootCategoryBeans);
		//category
		model.put("category",categoryService.getCategory(rootCategory.getId()));
		//language
		LanguageUtils.applyLanguage(model, request, response,userPersonBean.getPreferedLocaleId());
		LanguageUtils.applyLanguages(model);
		//page title
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
	
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
