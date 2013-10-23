package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.service.CallForProposalService;
import huard.iws.service.TextualPageService;
import huard.iws.service.SphinxSearchService;
import huard.iws.util.BaseUtils;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.CallForProposal;
import huard.iws.model.Language;
import huard.iws.model.TextualPage;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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

		//page title
		Language lang = (huard.iws.model.Language)model.get("lang");
		model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".website.search"));

		//callForProposals
		String callForProposalIds =  (String)request.getSession().getAttribute("callForProposalIds");
		request.getSession().setAttribute("callForProposalIds", null);
		if (callForProposalIds == null)// on first time
			callForProposalIds = "";
		List<CallForProposal> callForProposals = callForProposalService.getCallForProposalsOnline(callForProposalIds);
		List<CallForProposalBean> callForProposalBeans = new ArrayList<CallForProposalBean>();
		for (CallForProposal callForProposal: callForProposals){
			CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal,true);
			if(callForProposalBean.getTitle().startsWith("###")) callForProposalBean.setTitle("");
			callForProposalBeans.add(callForProposalBean);
		}
		model.put("callForProposals", callForProposalBeans);

		//textualPages
		String textualPageIds =  (String)request.getSession().getAttribute("textualPageIds");
		request.getSession().setAttribute("textualPageIds", null);
		if (textualPageIds == null)// on first time
			textualPageIds = "";
		List<TextualPageBean> textualPageBeans = new ArrayList<TextualPageBean>();
		if(!textualPageIds.isEmpty()){//if not entered any phrase dont show any textual pages
			List<TextualPage> textualPages = textualPageService.getOnlineTextualPagesSearch(textualPageIds);
			for (TextualPage textualPage: textualPages){
				TextualPageBean textualPageBean = new TextualPageBean(textualPage);
				textualPageBeans.add(textualPageBean);
			}
		}
		model.put("textualPages", textualPageBeans);
		model.put("textualPagesIsDefault", textualPageIds.isEmpty());
		
		//messages
		List<TextualPage> textualMessages = textualPageService.getOnlineMessagesSearch(textualPageIds);
		List<TextualPageBean> textualMessageBeans = new ArrayList<TextualPageBean>();
		for (TextualPage textualMessage: textualMessages){
			TextualPageBean textualMessageBean = new TextualPageBean(textualMessage);
			textualMessageBeans.add(textualMessageBean);
		}
		model.put("textualMessages", textualMessageBeans);
		
		//show searched words
		String searchWords="";
		if(request.getSession().getAttribute("searchWords")!=null)
			searchWords = ((String)request.getSession().getAttribute("searchWords")).replace("\"", "&quot;");
		model.put("searchWords",searchWords);
		request.getSession().setAttribute("searchWords", "");
		
		long lastUpdateTime = Math.max(callForProposalService.getCallForProposalsLastUpdate().getTime(), 
				textualPageService.getTextualPagesLastUpdate().getTime());
		model.put("updateTime", DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy"));

		if(searchWords.isEmpty())
			model.put("isDefault", true);
		
		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("searchPageStatic",model);
		else
			return new ModelAndView ("searchPage",model);

	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		
		SearchWebsiteControllerCommand command = new SearchWebsiteControllerCommand();
		if(request.getSession().getAttribute("callForProposalIds")==null && request.getSession().getAttribute("textualPageIds")==null){
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
		}
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

	private SphinxSearchService sphinxSearchService;

	public void setSphinxSearchService(SphinxSearchService sphinxSearchService) {
		this.sphinxSearchService = sphinxSearchService;
	}

}
