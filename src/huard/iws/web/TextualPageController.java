package huard.iws.web;

import huard.iws.bean.AListBean;
import huard.iws.bean.IListViewableBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.AList;
import huard.iws.model.Category;
import huard.iws.model.Language;
import huard.iws.service.ListService;
import huard.iws.service.TextualPageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RedirectViewExtended;
import huard.iws.util.RequestWrapper;
import huard.iws.util.TextUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mysql.jdbc.Field;

public class TextualPageController extends GeneralWebsiteFormController {

	private static final Logger logger = Logger.getLogger(TextualPageController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		//TextualPageBean textualPageBean = (TextualPageBean)command;
		request.getSession().setAttribute("filterOrganizationUnit",request.getParameter("filterOrganizationUnit", ""));

		//Map<String,Object> newModel = new HashMap<String, Object>();
		//newModel.put("id", textualPageBean.getId())	;
		//return new ModelAndView(new RedirectView(getSuccessView()),newModel);
		return new ModelAndView ( new RedirectViewExtended("/page/" + request.getParameter("urlTitle", "")), new HashMap<String, Object>());
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageBean textualPageBean = (TextualPageBean) model.get("command");
		if(textualPageBean.getId()==0){//someone entered non-existing id
			String page =configurationService.getConfigurationString("iws", "websiteName").equals("websiteNano")?"websiteNanoPageNotFound":"websitePageNotFound";
			return new ModelAndView (page, model);
		}
		//when coming from old site:
		int ardNum = (Integer) request.getSession().getAttribute("ardNum");
		request.getSession().setAttribute("ardNum",0);
		if (ardNum > 0){
			String urlTitle=textualPageService.getTextualPageUrlTitleByArdNum(ardNum);
			return new ModelAndView ( new RedirectViewExtended("/page/"+urlTitle), new HashMap<String, Object>());
		}

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

		//if picture file
		if(textualPageBean.getShowFile()){
			if(TextUtils.isImage(textualPageBean.getAttachment().getContentType()))
				model.put("isImage",true);	
		}

		//if list
		if(textualPageBean.getWrapExternalPage()){
			if(request.getSession().getAttribute("filterOrganizationUnit")==null || request.getSession().getAttribute("filterOrganizationUnit")=="null")
				request.getSession().setAttribute("filterOrganizationUnit","");

			AList list = listService.getList(new Integer(textualPageBean.getExternalPageUrl()).intValue());
			AListBean listBean = new AListBean(list, request);			
			//listBean.init(0);			
			request.getSession().setAttribute("filterOrganizationUnit","");

			if (listBean.isCompound()){
				model.put("list", listBean);
				model.put("aCompoundView", true);
			}
			else{
				model.put("listBean", listBean);
			}
			listBean = new AListBean(list, request);
			model.put("completeListForFilter", listBean);

		}		
		
		Language pageLanguage = LanguageUtils.getLanguage(textualPageBean.getTitle());
		model.put("pageLang", pageLanguage);
		
		
		model.put("id",textualPageBean.getId());
		model.put("ilr", "/page/" + textualPageBean.getUrlTitle());
		
		String page =configurationService.getConfigurationString("iws", "websiteName").equals("websiteNano")?"textualPageNano":"textualPage";
		return new ModelAndView (page,model);
		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageBean textualPageBean = new TextualPageBean();

		logger.info("urlTitle: " + request.getParameter("urlTitle", ""));
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		String urlTitle = request.getParameter("urlTitle", "");
		//when coming from old site
		request.getSession().setAttribute("ardNum", request.getIntParameter("ardNum", 0));
			
		if ( isFormSubmission(request.getRequest()) 
				|| (id == 0 && urlTitle.isEmpty()))
			return textualPageBean;
		
		if(request.getParameter("draft","").equals("true"))
			textualPageBean = new TextualPageBean(textualPageService.getTextualPage(id));
		else{
			if(id>0)
				textualPageBean = new TextualPageBean(textualPageService.getTextualPageOnline(id));
			else if(!urlTitle.isEmpty())
				textualPageBean = new TextualPageBean(textualPageService.getTextualPageOnline(urlTitle));
		}
		logger.info("textualPageBean id: " + textualPageBean.getId());
		
		return textualPageBean;
	}
	
	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}
	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	
	
	
	
	
	
}
