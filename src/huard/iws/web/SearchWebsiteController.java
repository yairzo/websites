package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.CallForProposal;
import huard.iws.model.Language;
import huard.iws.model.TextualPage;
import huard.iws.service.CallForProposalService;
import huard.iws.service.SphinxSearchService;
import huard.iws.service.TextualPageService;
import huard.iws.util.BaseUtils;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SearchWebsiteController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		//view
		String viewType = request.getParameter("v", "");
		boolean searchBoxBottom = viewType.equals("new_cfps");
		model.put("searchBoxBottom", searchBoxBottom);
		model.put("viewType", viewType);

		//page title
		Language lang = (huard.iws.model.Language)model.get("lang");
		if(viewType.equals("new_cfps"))
			model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".website.recentCallForProposals"));
		else
			model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".website.generalSearch"));
		
		//show searched words
		String searchWords="";
		if(request.getSession().getAttribute("searchWords")!=null)
			searchWords = ((String)request.getSession().getAttribute("searchWords")).replace("\"", "&quot;");
		model.put("searchWords",searchWords);
		//request.getSession().setAttribute("searchWords", "");
		if(searchWords.isEmpty())
			model.put("isDefault", true);

		long lastUpdateTime = Math.max(callForProposalService.getCallForProposalsLastUpdate().getTime(), 
				textualPageService.getTextualPagesLastUpdate().getTime());
		model.put("updateTime", DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy"));

		String callForProposalIds =  (String)request.getSession().getAttribute("callForProposalIds");
		//request.getSession().setAttribute("callForProposalIds", null);
		String textualPageIds =  (String)request.getSession().getAttribute("textualPageIds");
		//request.getSession().setAttribute("textualPageIds", null);

		if(!searchWords.isEmpty() || viewType.equals("new_cfps")){
			//callForProposals
			if (callForProposalIds == null)	callForProposalIds = "";
			List<CallForProposal> callForProposals = callForProposalService.getCallForProposalsOnlineSimple(callForProposalIds,viewType);
			List<CallForProposalBean> callForProposalBeans = new ArrayList<CallForProposalBean>();
			for (CallForProposal callForProposal: callForProposals){
				CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal,true);
				if(callForProposalBean.getTitle().startsWith("###")) callForProposalBean.setTitle("");
				callForProposalBeans.add(callForProposalBean);
			}
			model.put("callForProposals", callForProposalBeans);
		}
		if(!searchWords.isEmpty() && !viewType.equals("new_cfps")){
			//textualPages
			if (textualPageIds == null)	textualPageIds = "";
			List<TextualPageBean> textualPageBeans = new ArrayList<TextualPageBean>();
			if(!textualPageIds.isEmpty()){//if not entered any phrase dont show any textual pages
				List<TextualPage> textualPages = textualPageService.getOnlineTextualPagesSearch(textualPageIds);
				for (TextualPage textualPage: textualPages){
					TextualPageBean textualPageBean = new TextualPageBean(textualPage);
					textualPageBeans.add(textualPageBean);
				}
			}
			model.put("textualPages", textualPageBeans);

			//messages
			List<TextualPage> textualMessages = textualPageService.getOnlineMessagesSearch(textualPageIds);
			List<TextualPageBean> textualMessageBeans = new ArrayList<TextualPageBean>();
			for (TextualPage textualMessage: textualMessages){
				TextualPageBean textualMessageBean = new TextualPageBean(textualMessage);
				textualMessageBeans.add(textualMessageBean);
			}
			model.put("textualMessages", textualMessageBeans);
		}
		
		
		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("searchPageStatic",model);
		else
			return new ModelAndView ("searchPage",model);
		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SearchWebsiteControllerCommand command = new SearchWebsiteControllerCommand();
		String viewType = request.getParameter("v", "");
		//if(request.getSession().getAttribute("callForProposalIds")==null && request.getSession().getAttribute("textualPageIds")==null){
		Set<Long> sphinxIds=new LinkedHashSet<Long>();
		Set<Long> sphinxTextualIds=new LinkedHashSet<Long>();
		if(viewType.equals("new_cfps"))
				request.getSession().setAttribute("searchWords","");
		else if(!request.getParameter("searchWords", "").isEmpty()){
				sphinxIds.add(new Long(0));//so wont show everything when deos'nt find any ids
				String escapedSearchWord= request.getParameter("searchWords", "").replace("'", "\\\'");
				escapedSearchWord= escapedSearchWord.replace("\"", "\\\"");
				sphinxIds.addAll(sphinxSearchService.getMatchedIds(escapedSearchWord,"call_for_proposal_index"));
				sphinxTextualIds.add(new Long(0));//so wont show everything when deos'nt find any ids
				sphinxTextualIds.addAll(sphinxSearchService.getMatchedIds(escapedSearchWord,"textual_page_index"));
				request.getSession().setAttribute("callForProposalIds", BaseUtils.getStringFromLongSet(sphinxIds));
				request.getSession().setAttribute("textualPageIds", BaseUtils.getStringFromLongSet(sphinxTextualIds));
				request.getSession().setAttribute("searchWords", escapedSearchWord);
		}
			
		//}
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
	
	private SphinxSearchService sphinxSearchService;

	public void setSphinxSearchService(SphinxSearchService sphinxSearchService) {
		this.sphinxSearchService = sphinxSearchService;
	}
	
	private CallForProposalService callForProposalService;
	
	public void setCallForProposalService(
			CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}

}
