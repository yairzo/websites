package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.service.TextualPageService;
import huard.iws.service.CategoryService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TextualPageController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageBean textualPageBean = (TextualPageBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", textualPageBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageBean textualPageBean = (TextualPageBean) model.get("command");

		//top categories
		Category rootCategory = categoryService.getRootCategory();
		List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
		List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: languageRootCategories){
			languageRootCategoryBeans.add( new CategoryBean (category));
		}
		model.put("languageRootCategories", languageRootCategoryBeans);
		//category
		model.put("category",categoryService.getCategory(textualPageBean.getCategoryId()));
		//language
		LanguageUtils.applyLanguage(model, request, response,userPersonBean.getPreferedLocaleId());
		LanguageUtils.applyLanguages(model);
		//page title
		model.put("pageTitle", textualPageBean.getTitle());
		
		model.put("id",textualPageBean.getId());
		return new ModelAndView ( this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageBean textualPageBean = new TextualPageBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
			
		if ( isFormSubmission(request.getRequest()) 
				|| id == 0)
			return textualPageBean;
		
		textualPageBean = new TextualPageBean(textualPageService.getTextualPage(id));
		logger.info("textualPageBean id: " + textualPageBean.getId());
		
		return textualPageBean;
	}

	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	

	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
}
