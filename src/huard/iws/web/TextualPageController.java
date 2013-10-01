package huard.iws.web;

import huard.iws.bean.AListBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.AList;
import huard.iws.model.Category;
import huard.iws.service.ListService;
import huard.iws.service.TextualPageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RedirectViewExtended;
import huard.iws.util.RequestWrapper;
import huard.iws.util.TextUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TextualPageController extends GeneralWebsiteFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageBean textualPageBean = (TextualPageBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", textualPageBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageBean textualPageBean = (TextualPageBean) model.get("command");
		if(textualPageBean.getId()==0)//someone entered non-existing id
			return new ModelAndView ( "websitePageNotFound", model);

		//when coming from old site:
		int ardNum = (Integer) request.getSession().getAttribute("ardNum");
		request.getSession().setAttribute("ardNum",0);
		if (ardNum > 0){
			String urlTitle=textualPageService.getTextualPageUrlTitleByArdNum(ardNum);
		//	return new ModelAndView ( new RedirectViewExtended("page/"+urlTitle), new HashMap<String, Object>());
			return new ModelAndView ( new RedirectViewExtended("textualPage.html?urlTitle="+urlTitle), new HashMap<String, Object>());
		}
		//if(request.getIntParameter("id", 0)>0)//if link was written with id and not with url title
		//	return new ModelAndView ( new RedirectViewExtended("page/"+textualPageBean.getUrlTitle()), new HashMap<String, Object>());

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
			AList list = listService.getList(new Integer(textualPageBean.getExternalPageUrl()).intValue());
			AListBean listBean = new AListBean(list, request);
			listBean.initPersonAttributionBeans(-1,0);
			listBean.initColumnsInstructionBeans(0);
			LanguageUtils.applyLanguage(model, request, response, listBean.getDisplayName());
			if (listBean.isCompound()){
				model.put("list", listBean);
				model.put("aCompoundView", true);
			}
			else{
				model.put("listBean", listBean);
			}
		}
		
		
		model.put("id",textualPageBean.getId());
		if(request.getParameter("t", "").equals("1"))
			return new ModelAndView ("textualPage1",model);
		else if(request.getParameter("t", "").equals("0")){
			if(request.getParameter("list","").equals("1"))
				return new ModelAndView ("textualPageWithListStatic",model);
			else
				return new ModelAndView ("textualPageStatic",model);
		}
		else
			return new ModelAndView ("textualPage",model);		
		//return new ModelAndView ( this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageBean textualPageBean = new TextualPageBean();

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

	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}
	
	
}
