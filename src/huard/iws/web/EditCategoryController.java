package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.TextualPage;
import huard.iws.model.Category;
import huard.iws.service.TextualPageService;
import huard.iws.service.CategoryService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditCategoryController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		CategoryBean categoryBean = (CategoryBean)command;
		if(!request.getParameter("textualPage", "").isEmpty()){
			String textualPageId = request.getParameter("textualPage", "");
			categoryBean.setUrl("textualPage.html?id="+textualPageId);
		}
		
		//update
		categoryService.updateCategory(categoryBean.toCategory());

	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", categoryBean.getId())	;
		return new ModelAndView(new RedirectView("categories.html"),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		
		int id = request.getIntParameter("id", 0);
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			Category category= new Category();
			//textualPage.setCreatorId(userPersonBean.getId());
			//int textualPageId = textualPageService.insertTextualPage(textualPage);
			Map<String, Object> newModel = new HashMap<String, Object>();
			//newModel.put("id",textualPageId);
			return new ModelAndView ( new RedirectView("textualPage.html"), newModel);
		}
		else{//show edit
			CategoryBean category = (CategoryBean) model.get("command");
			List<TextualPage> textualPages = textualPageService.getOnlineTextualPages();
			List<TextualPageBean> textualPageBeans = new ArrayList<TextualPageBean>();
			for (TextualPage textualPage: textualPages){
				TextualPageBean textualPageBean = new TextualPageBean(textualPage);
				textualPageBeans.add(textualPageBean);
			}
			model.put("textualPages", textualPageBeans);
			
			model.put("topCategory",categoryService.getTopCategory(category.toCategory(),category.getLocaleId()).getId());

			model.put("id",category.getId());
			return new ModelAndView ( this.getFormView(), model);
		}		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CategoryBean category = new CategoryBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return category;
		
		category = new CategoryBean(categoryService.getCategory(id));
		logger.info(" id: " + category.getId());
		
		return category;
	}

	

	
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}

	
}
