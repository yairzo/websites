package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.model.Language;
import huard.iws.service.CategoryService;
import huard.iws.service.ConfigurationService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public abstract class GeneralWebsiteFormController extends GeneralFormController
{

	@Override
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		LanguageUtils.applyLanguage(model, request, response, userPersonBean.getPreferedLocaleId());

		LanguageUtils.applyLanguages(model);

		//top categories
		Language language = (Language)model.get("lang");
		Category rootCategory = categoryService.getRootCategory(language.getLocaleId());
		List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
		List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: languageRootCategories){
			languageRootCategoryBeans.add( new CategoryBean (category));
		}
		model.put("languageRootCategories", languageRootCategoryBeans);
		//category
		model.put("category",categoryService.getCategory(rootCategory.getId()));

		model.put("contactsPageUrlTitle", configurationService.getConfigurationString("website", "contactsPage" + language.getNameShortCapitalized() + "UrlTitle"));
		
		model.put("request", request);
		model.put("ilr", "");

		ModelAndView modelAndView = onShowFormWebsite(request, response, userPersonBean, model);
		return modelAndView;

	}

	protected abstract ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception;


	public CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
