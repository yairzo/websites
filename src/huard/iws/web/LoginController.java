package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.model.Language;
import huard.iws.service.CategoryService;
import huard.iws.service.GeneralService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class LoginController extends GeneralController{

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
		Map<String, Object> model, PersonBean userPersonBean){
		
		LanguageUtils.applyLanguage(model, request, response, "iw_IL");
		Language language = (Language)model.get("lang");

		boolean isWebsiteLogin = request.getBooleanParameter("wl", false);
		
		int loginErrorCode = request.getIntParameter("login_error", -1);
		if (loginErrorCode != -1){
			model.put("loginError", messageService.getMessage("iw_IL.general.login.loginError."+loginErrorCode));
			model.put("loginFailure", loginErrorCode != -1);
		}

		int titleCode = request.getIntParameter("tc", 0);

		String requestURI = (String)request.getRequest().getAttribute("javax.servlet.forward.request_uri"); 
		if ((requestURI != null && requestURI.contains("conferenceProposal")) || titleCode==2){
			model.put("moduleToSubscribe", "conference");
			titleCode = 2;
		}
		else if (titleCode == 1 || titleCode == 0){
			model.put("moduleToSubscribe", "post");
		}

		//We put the titleCode in the session for decision of what to write when logged out
		//it's not desired for website logout. In that case we would like to redirect to the main page
		if (!isWebsiteLogin)
			request.getSession().setAttribute("titleCode", titleCode);

		model.put("title", messageService.getMessage(language.getLocaleId() +".general.login.title."+titleCode));
		
		if(request.getParameter("justLogin", "").equals("1")){
			model.put("title", messageService.getMessage(language.getLocaleId() +".general.justLogin.title"));
			if(loginErrorCode != -1)
				model.put("title", messageService.getMessage(language.getLocaleId() +".general.justLoginError.title"));
		}
		
		model.put("titleCode", titleCode);
		
		model.put("username", messageService.getMessage("iw_IL.general.login.username."+titleCode));

		model.put("usernameInstructions", messageService.getMessage("iw_IL.general.login.usernameInstructions."+titleCode));

		model.put("passwordInstructions", messageService.getMessage("iw_IL.general.login.passwordInstructions."+titleCode));
		
		model.put("generalLoginInstructions", messageService.getMessage(language.getLocaleId() +".general.login.generalLoginInstructions."+titleCode));
		
		//for website login
		//top categories
		
		if (isWebsiteLogin){
			Category rootCategory = categoryService.getRootCategory(language.getLocaleId());
			List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
			List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
			for (Category category: languageRootCategories){
				languageRootCategoryBeans.add( new CategoryBean (category));
			}
			model.put("languageRootCategories", languageRootCategoryBeans);
			//category
			model.put("category",categoryService.getCategory(rootCategory.getId()));
			model.put("ilr", "/homePage.html");
		}
		
		model.put("updateTime", generalService.getLastUpdate());
		
		ModelAndView modelAndView = null;
		if (!isWebsiteLogin)
			modelAndView = new ModelAndView("login",model);
		else
			modelAndView = new ModelAndView("loginWebsite", model);
		return modelAndView;
	}

	public CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public GeneralService generalService;

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
}
