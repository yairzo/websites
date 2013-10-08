package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.service.CategoryService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public abstract class GeneralWebsiteController extends GeneralController{

	@Override
	protected ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean) throws Exception {
		//language
		
		LanguageUtils.applyLanguage(model, request, response, userPersonBean.getPreferedLocaleId());
		
		LanguageUtils.applyLanguages(model);
		
		//top categories
		Category rootCategory = categoryService.getRootCategory(userPersonBean.getPreferedLocaleId());
		List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
		List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: languageRootCategories){
			languageRootCategoryBeans.add( new CategoryBean (category));
		}
		model.put("languageRootCategories", languageRootCategoryBeans);
		//category
		model.put("category",categoryService.getCategory(rootCategory.getId()));
		
		return handleRequestWebsite(request, response, model, userPersonBean);
	}

	protected abstract ModelAndView handleRequestWebsite(
			RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean personBean) throws Exception;



	public CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


}
