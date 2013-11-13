package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.Language;
import huard.iws.model.PageBodyImage;
import huard.iws.model.TextualPage;
import huard.iws.service.CallForProposalService;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.TextualPageService;
import huard.iws.util.DateUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
public class WebsiteHomePageController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		//page title
		model.put("pageTitle", "");
		

		//messages
		Language language = (Language)model.get("lang");
		List<TextualPage> textualPages = textualPageService.getOnlineMessagesRolling(language.getLocaleId());
		List<TextualPageBean> textualPageBeans=new ArrayList<TextualPageBean>();
		for (TextualPage textualPage: textualPages){
			TextualPageBean textualPageBean = new TextualPageBean(textualPage);
			textualPageBeans.add(textualPageBean);
		}
		model.put("textualPages", textualPageBeans);
		
		//pics
		List<PageBodyImage> pageBodyImages = pageBodyImageService.getApprovedPageBodyImages();
		model.put("images", pageBodyImages);
		
		long lastUpdateTime = Math.max(callForProposalService.getCallForProposalsLastUpdate().getTime(), 
				textualPageService.getTextualPagesLastUpdate().getTime());
		model.put("updateTime", DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy"));
		
		model.put("ilr", "/homePage.html");
		
		model.put("removeHomePageLink", "true");

		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("websiteHomePageStatic",model);
		else
			return new ModelAndView ("websiteHomePage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		WebsiteHomePageControllerCommand command = new WebsiteHomePageControllerCommand();
		return command;
	}

	public class WebsiteHomePageControllerCommand{
		
	}
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}

	private PageBodyImageService pageBodyImageService;

	public void setPageBodyImageService(
			PageBodyImageService pageBodyImageService) {
		this.pageBodyImageService = pageBodyImageService;
	}	
	

}
