package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CategoryBean;
import huard.iws.service.CategoryService;
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

public class CategoryListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		
		if (request.getIntParameter("add", 0) > 0) 
			categoryService.insertCategory(request.getIntParameter("add", 0));

		if (request.getIntParameter("delete", 0) > 0) {
			categoryService.deleteCategory(categoryService.getCategory(request.getIntParameter("delete", 0)));
		}
			
		if (request.getIntParameter("moveUp", 0) > 0) 
			categoryService.moveCategoryUp(request.getIntParameter("moveUp", 0));
		
		if (request.getIntParameter("moveDown", 0) > 0) 
			categoryService.moveCategoryDown(request.getIntParameter("moveDown", 0));

		return new ModelAndView(new RedirectView("categories.html"));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

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
		CategoryListBean categoryListBean = new CategoryListBean();
		return categoryListBean;
	}

	public class CategoryListBean{
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
	
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
