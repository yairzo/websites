package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.service.TextualPageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.TextualPage;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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
		model.put("pageTitle", messageService.getMessage("iw_IL.website.messages"));

	
		//messages
		List<TextualPage> textualMessages = textualPageService.getOnlineMessagesSearch("");
		List<TextualPageBean> textualMessageBeans = new ArrayList<TextualPageBean>();
		for (TextualPage textualMessage: textualMessages){
			TextualPageBean textualMessageBean = new TextualPageBean(textualMessage);
			textualMessageBeans.add(textualMessageBean);
		}
		model.put("textualMessages", textualMessageBeans);
		

		if(request.getSession().getAttribute("t")!=null && request.getSession().getAttribute("t").equals("1")){
			request.getSession().setAttribute("t","");
			return new ModelAndView ("messages",model);
		}
		else if(request.getSession().getAttribute("t")!=null && request.getSession().getAttribute("t").equals("0")){
			request.getSession().setAttribute("t","");
			return new ModelAndView ("messagesStatic",model);
		}
		else
			return new ModelAndView ("messages",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		
		if(request.getSession().getAttribute("t")==null || request.getSession().getAttribute("t").equals(""))
			request.getSession().setAttribute("t",request.getParameter("t", ""));

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
