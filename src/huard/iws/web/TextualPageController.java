package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.service.TextualPageService;
import huard.iws.service.CategoryService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TextualPageController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageBean textualPageBean = (TextualPageBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", textualPageBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageBean textualPageBean = (TextualPageBean) model.get("command");

		//category
		Category category =  new Category();
		if(textualPageBean.getCategoryId()>0)
			category = categoryService.getCategory(textualPageBean.getCategoryId());
		model.put("category",category);
		//page title
		model.put("pageTitle", textualPageBean.getTitle());
		
		//dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (textualPageBean.getUpdateTime()==0)
			model.put("updateTime", "");
		else
			model.put("updateTime", formatter.format(textualPageBean.getUpdateTime()));
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
		
		if(request.getParameter("draft","").equals("true"))
			textualPageBean = new TextualPageBean(textualPageService.getTextualPage(id));
		else
			textualPageBean = new TextualPageBean(textualPageService.getTextualPageOnline(id));
		logger.info("textualPageBean id: " + textualPageBean.getId());
		
		return textualPageBean;
	}

	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	
	
}
