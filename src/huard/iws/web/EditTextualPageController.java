package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.AList;
import huard.iws.model.Attachment;
import huard.iws.model.Category;
import huard.iws.model.MopDesk;
import huard.iws.model.PageBodyImage;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.service.CategoryService;
import huard.iws.service.ListListService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.TextualPageService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditTextualPageController extends GeneralFormController {

	private static final Logger logger = Logger.getLogger(EditTextualPageController.class);
	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageBean textualPageBean = (TextualPageBean)command;
		
		logger.info("Textual page url title: " + textualPageBean.getUrlTitle());
		
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				MultipartFile file = multipartRequest.getFile(filename);
				String originalName = file.getOriginalFilename();
				String ext = originalName.substring(originalName.lastIndexOf(".")+1);
				if (filename.equals("textualPageFile") && file.getSize()>0){
					Attachment attachment = new Attachment();
					attachment.setFile(file.getBytes());
					attachment.setContentType(getContentType(ext));
					attachment.setTitle(textualPageBean.getUrlTitle());
					attachment.setFilename(textualPageBean.getUrlTitle()+ "." + ext);
					textualPageBean.setAttachment(attachment);
				}
			}
		}	
		
		if(request.getParameter("action", "").equals("delete"))
			textualPageBean.setIsDeleted(1);
		
		//title
		if(!request.getParameter("tempTitle", "").isEmpty())
			textualPageBean.setTitle(request.getParameter("tempTitle", ""));
		if(!request.getParameter("tempUrlTitle", "").isEmpty())
			textualPageBean.setUrlTitle(request.getParameter("tempUrlTitle", ""));

		//update
		textualPageService.updateTextualPage(textualPageBean.toTextualPage());

		//upload
		if (request.getBooleanParameter("online", false)){
			if(textualPageService.existsTextualPageOnline(textualPageBean.getId()))
				textualPageService.updateTextualPageOnline(textualPageBean.toTextualPage());
			else
				textualPageService.insertTextualPageOnline(textualPageBean.toTextualPage());
		}
		if (request.getBooleanParameter("offline", false) || request.getParameter("action", "").equals("delete")){
			if(textualPageService.existsTextualPageOnline(textualPageBean.getId()))
				textualPageService.removeTextualPageOnline(textualPageBean.getId());
		}
		//templates
		String action=request.getParameter("action","");
		if(!action.isEmpty() && action.equals("addTemplate")){
			Template template = new Template();
			template.setTitle(request.getParameter("templateTitle",""));
			template.setTemplate(textualPageBean.getHtml());
			template.setPersonId(userPersonBean.getId());
			template.setModifierId(userPersonBean.getId());
			textualPageService.addTemplate(template);
		}
		if(!action.isEmpty() && action.equals("updateTemplate")){
			Template template = textualPageService.getTemplate(request.getIntParameter("templateId",0));
			template.setTemplate(textualPageBean.getHtml());
			template.setModifierId(userPersonBean.getId());
			textualPageService.updateTemplate(template);
		}
		if(!action.isEmpty() && action.equals("showTemplate")){
			request.getSession().setAttribute("showTemplate",true);
			request.getSession().setAttribute("templateId", request.getParameter("templateId",""));
		}
		
		if(request.getParameter("action", "").equals("delete")){
			return new ModelAndView(new RedirectView("textualPages.html"));
		}
		
		if (request.getBooleanParameter("ajaxSubmit", false))
			return null;
		
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", textualPageBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		TextualPageBean textualPageBean = (TextualPageBean) model.get("command");
		if(textualPageBean.getId()==0 && !request.getParameter("action", "").equals("new"))//someone entered non-existing id
			return new ModelAndView ( "pageNotFound", model);
		
		int id = request.getIntParameter("id", 0);
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){//if new or no id passed in url
			TextualPage textualPage= new TextualPage();
			textualPage.setCreatorId(userPersonBean.getId());
			textualPage.setLocaleId(userPersonBean.getPreferedLocaleId());
			int textualPageId = textualPageService.insertTextualPage(textualPage);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",textualPageId);
			return new ModelAndView ( new RedirectView("editTextualPage.html"), newModel);
		}
		else{//show edit
			//language
			LanguageUtils.applyLanguage(model, textualPageBean.getLocaleId());
			
			//desks
			List<MopDesk> mopDesks = mopDeskService.getMopDesks();
			model.put("mopDesks", mopDesks);
			//online
			if(textualPageService.existsTextualPageOnline(textualPageBean.getId()))
				model.put("online", true);
			else
				model.put("online", false);
			//title
			String title="";
			if(!textualPageBean.getTitle().startsWith("###"))
				title = textualPageBean.getTitle();
			title= title.replace("\"","&quot;");
			model.put("title", title);
			String urlTitle="";
			if(!textualPageBean.getUrlTitle().startsWith("###"))
				urlTitle = textualPageBean.getUrlTitle();
			urlTitle= urlTitle.replace("\"","&quot;");
			model.put("urlTitle", urlTitle);
			//templates
			List<Template> templates = textualPageService.getTemplates();
			model.put("templates", templates);
			if(request.getSession().getAttribute("showTemplate")!=null && request.getSession().getAttribute("showTemplate").toString().equals("true")){
				int templateId=Integer.parseInt(request.getSession().getAttribute("templateId").toString());
				Template template = textualPageService.getTemplate(templateId);
				String templateStripped= template.getTemplate().replace("\n", "");
				templateStripped =templateStripped.replace("\r", "");
				model.put("templateHtml", templateStripped);
				model.put("showTemplate", request.getSession().getAttribute("showTemplate"));
				request.getSession().setAttribute("showTemplate", false);
			}
			//categories
			Category rootCategoryHeb = categoryService.getRootCategory("iw_IL");
			List<Category> categories = categoryService.getlanguageRootCategories(rootCategoryHeb.getId());
			Category rootCategoryEng = categoryService.getRootCategory("en_US");
			categories.addAll(categoryService.getlanguageRootCategories(rootCategoryEng.getId()));
			model.put("categories", categories);
			//lists
			List<AList> alists = listListService.getLists();
			model.put("alists", alists);
			//date
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			if (textualPageBean.getKeepInRollingMessagesExpiryTime()==0)
				model.put("keepInRollingMessagesExpiryTime", "");
			else
				model.put("keepInRollingMessagesExpiryTime", formatter.format(textualPageBean.getKeepInRollingMessagesExpiryTime()));
			//if this textual page is a url of any category - get the top category 
			Category category = categoryService.getCategoryByUrl("textualPage.html?id="+textualPageBean.getId());
			if(category.getId()>0)
				model.put("pageTopCategory",categoryService.getTopCategory(category,category.getLocaleId()).getId());
			else
				model.put("pageTopCategory","0");
				
			 //gallery
			  int page = 0;
			  String aPage;
			  if ( request.getParameter("page","") != null && !request.getParameter("page","").equals("")){
				  aPage = request.getParameter("page","");
				  page = Integer.parseInt(aPage);
			  }
			  model.put("page", page);
			  model.put("previousPage", Math.max(0, page -1));
			  model.put("nextPage", Math.min(pageBodyImageService.countImagePages(8),  page + 1));

			  List<PageBodyImage> pageBodyImages =	pageBodyImageService.getPageBodyImages(8,page,userPersonBean);
			  List<PageBodyImageBean> pageBodyImagesBeans = new  ArrayList<PageBodyImageBean>();
			  for (PageBodyImage pageBodyImage: pageBodyImages){
				  pageBodyImagesBeans.add( new	PageBodyImageBean(pageBodyImage));
			  }
			  model.put("images", pageBodyImagesBeans);
			  
			model.put("websiteName", configurationService.getConfigurationString("iws", "websiteName"));
			
			model.put("id",textualPageBean.getId());
			return new ModelAndView ( this.getFormView(), model);
		}		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageBean textualPageBean = new TextualPageBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		if(request.getParameter("keepInRollingMessagesExpiryTimeStr", "").equals("")){
			textualPageBean.setKeepInRollingMessagesExpiryTime(0);
		}
		else{
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("keepInRollingMessagesExpiryTimeStr", "")); 
				textualPageBean.setKeepInRollingMessagesExpiryTime(formattedDate.getTime());
			}
			catch(Exception e){
				textualPageBean.setKeepInRollingMessagesExpiryTime(0);
			}
		}
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return textualPageBean;
		
		textualPageBean = new TextualPageBean(textualPageService.getTextualPage(id));
		logger.info("textualPageBean id: " + textualPageBean.getId());
		
		return textualPageBean;
	}

	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	
	private static String getContentType(String extension){
		if(extension.equals("pdf"))
			return "application/pdf";
		else if (extension.equals("doc"))
			return "application/msword";
		else if (extension.equals("docx"))
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		else if (extension.equals("xls"))
			return "application/vnd.ms-excel";
		else if (extension.equals("xlsx"))
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if (extension.equals("txt"))
			return "text/plain";
		else if (extension.equals("jpg")|| extension.equals("jpeg"))
			return "image/jpeg";
		else
			return "text/html";
	}	
	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
	
	private ListListService listListService;

	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}
	
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
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
