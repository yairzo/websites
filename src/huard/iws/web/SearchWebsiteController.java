package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.service.CallForProposalService;
import huard.iws.service.TextualPageService;
import huard.iws.service.CategoryService;
import huard.iws.service.SphinxSearchService;
import huard.iws.util.BaseUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.CallForProposal;
import huard.iws.model.Category;
import huard.iws.model.TextualPage;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchWebsiteController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Set<Long> sphinxIds=new LinkedHashSet<Long>();
		Set<Long> sphinxTextualIds=new LinkedHashSet<Long>();
		if(!request.getParameter("searchWords", "").isEmpty()){
			sphinxIds.add(new Long(0));//so wont show everything when deos'nt find any ids
			sphinxIds.addAll(sphinxSearchService.getMatchedIds(request.getParameter("searchWords", ""),"call_for_proposal_index"));
			sphinxTextualIds.add(new Long(0));//so wont show everything when deos'nt find any ids
			sphinxTextualIds.addAll(sphinxSearchService.getMatchedIds(request.getParameter("searchWords", ""),"textual_page_index"));
		}
		request.getSession().setAttribute("callForProposalIds", BaseUtils.getStringFromLongSet(sphinxIds));
		request.getSession().setAttribute("textualPageIds", BaseUtils.getStringFromLongSet(sphinxTextualIds));
		request.getSession().setAttribute("searchWords", request.getParameter("searchWords", ""));
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		SearchWebsiteControllerCommand command = (SearchWebsiteControllerCommand) model.get("command");
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

		//callForProposals
		String callForProposalIds =  (String)request.getSession().getAttribute("callForProposalIds");
		request.getSession().setAttribute("callForProposalIds", null);
		if (callForProposalIds == null)// on first time
			callForProposalIds = "";
		List<CallForProposal> callForProposals = callForProposalService.getCallForProposalsOnline(callForProposalIds);
		List<CallForProposalBean> callForProposalBeans = new ArrayList<CallForProposalBean>();
		for (CallForProposal callForProposal: callForProposals){
			CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal,false);
			if(callForProposalBean.getTitle().startsWith("###")) callForProposalBean.setTitle("");
			callForProposalBeans.add(callForProposalBean);
		}
		model.put("callForProposals", callForProposalBeans);

		//textualPages
		String textualPageIds =  (String)request.getSession().getAttribute("textualPageIds");
		request.getSession().setAttribute("textualPageIds", null);
		if (textualPageIds == null)// on first time
			textualPageIds = "";
		List<TextualPage> textualPages = textualPageService.getOnlineTextualPagesSearch(textualPageIds);
		List<TextualPageBean> textualPageBeans = new ArrayList<TextualPageBean>();
		for (TextualPage textualPage: textualPages){
			TextualPageBean textualPageBean = new TextualPageBean(textualPage);
			textualPageBeans.add(textualPageBean);
		}
		model.put("textualPages", textualPageBeans);
		
		//show searched words
		String searchWords="";
		if(request.getSession().getAttribute("searchWords")!=null)
			searchWords = ((String)request.getSession().getAttribute("searchWords")).replace("\"", "&quot;");
		model.put("searchWords",searchWords);
		request.getSession().setAttribute("searchWords", "");

		return new ModelAndView ("searchPage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SearchWebsiteControllerCommand command = new SearchWebsiteControllerCommand();
		return command;
	}

	public class SearchWebsiteControllerCommand{
		private ListView listView = new ListView();
		
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
		
	
	}
	
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}

	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private SphinxSearchService sphinxSearchService;

	public void setSphinxSearchService(SphinxSearchService sphinxSearchService) {
		this.sphinxSearchService = sphinxSearchService;
	}

}
