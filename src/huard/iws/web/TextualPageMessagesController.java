package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.Language;
import huard.iws.model.TextualPage;
import huard.iws.service.TextualPageService;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TextualPageMessagesController extends GeneralWebsiteFormController {


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
		model.put("pageTitle", messageService.getMessage(lang.getLocaleId() +".website.messages"));

	
		//messages
		List<TextualPage> textualMessages = textualPageService.getOnlineMessages(lang.getLocaleId());
		List<TextualPageBean> textualMessageBeans = new ArrayList<TextualPageBean>();
		for (TextualPage textualMessage: textualMessages){
			TextualPageBean textualMessageBean = new TextualPageBean(textualMessage);
			textualMessageBeans.add(textualMessageBean);
		}
		model.put("textualMessages", textualMessageBeans);
		
		long lastUpdateTime = textualPageService.getTextualPagesLastUpdate().getTime();
		model.put("updateTime", DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy"));

		
		if(request.getParameter("t", "").equals("0"))
			return new ModelAndView ("messagesStatic",model);
		else
			return new ModelAndView ("messages",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		
		TextualPageMessagesControllerCommand command = new TextualPageMessagesControllerCommand();
		return command;
	}

	public class TextualPageMessagesControllerCommand{
		private ListView listView = new ListView();
		
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
}
