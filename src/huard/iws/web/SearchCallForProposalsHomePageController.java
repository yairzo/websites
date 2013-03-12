package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.SubjectBean;
import huard.iws.service.CallForProposalService;
import huard.iws.service.CategoryService;
import huard.iws.service.FundService;
import huard.iws.service.MopDeskService;
import huard.iws.service.SubjectService;
import huard.iws.service.SphinxSearchService;
import huard.iws.util.BaseUtils;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.web.SearchCallForProposalsController.SearchCallForProposalsControllerCommand;
import huard.iws.model.CallForProposal;
import huard.iws.model.Category;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchCallForProposalsHomePageController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
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
		model.put("pageTitle", messageService.getMessage("iw_IL.website.search"));

		return new ModelAndView ("searchCallForProposalsHomePage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SearchCallForProposalsHomePageControllerCommand command = new SearchCallForProposalsHomePageControllerCommand();
		return command;
	}

	public class SearchCallForProposalsHomePageControllerCommand{

	}
	
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
