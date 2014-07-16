package huard.iws.web.mop;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import huard.iws.bean.TextualPageBean;
import huard.iws.model.Language;
import huard.iws.model.TextualPage;
import huard.iws.service.TextualPageService;

import huard.iws.web.*;

public class ControllerModelApplierImpl implements ControllerModelApplier{
	
	public Map<String, Object> applyWebsiteHomePageModel(Map<String, Object> model){
		//page title
		model.put("pageTitle", "");
		
		//messages
		Language language = (Language)model.get("lang");
		List<TextualPage> textualPages = textualPageService.getOnlineMessagesRolling(language.getLocaleId());
		List<TextualPageBean> textualPageBeans=new ArrayList<TextualPageBean>();
		List<TextualPageBean> textualPageBeansForIE=new ArrayList<TextualPageBean>();
		boolean first=true;
		for (TextualPage textualPage: textualPages){
			TextualPageBean textualPageBean = new TextualPageBean(textualPage);
			textualPageBeans.add(textualPageBean);
			if(first)
				textualPageBeansForIE.add(textualPageBean);
			first=false;
		}
		model.put("textualPages", textualPageBeans);
		model.put("textualPagesForIE", textualPageBeansForIE);
		
		return model;
	}

	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}	
}
