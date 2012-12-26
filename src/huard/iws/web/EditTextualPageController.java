package huard.iws.web;

import huard.iws.bean.TextualPageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Attachment;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.model.MopDesk;
import huard.iws.model.Category;
import huard.iws.model.AList;
import huard.iws.service.TextualPageService;
import huard.iws.service.MopDeskService;
import huard.iws.service.ListListService;
import huard.iws.util.ListView;
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

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditTextualPageController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageBean textualPageBean = (TextualPageBean)command;
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				System.out.println("******************************");
				System.out.println(" filename : " + filename );
				System.out.println("******************************");
				MultipartFile file = multipartRequest.getFile(filename);
				String originalName = file.getOriginalFilename();
				String ext = originalName.substring(originalName.lastIndexOf(".")+1);
				if (filename.equals("textualPageFile") && file.getSize()>0){
					Attachment attachment = new Attachment();
					attachment.setFile(file.getBytes());
					attachment.setContentType(getContentType(ext));
					textualPageBean.setAttachment(attachment);
				}
			}
		}	
		
		//update
		textualPageService.updateTextualPage(textualPageBean.toTextualPage());

		//upload
		if (request.getBooleanParameter("online", false)){
			if(textualPageService.existsTextualPageOnline(textualPageBean.getId()))
				textualPageService.updateTextualPageOnline(textualPageBean.toTextualPage());
			else
				textualPageService.insertTextualPageOnline(textualPageBean.toTextualPage());
		}
		if (request.getBooleanParameter("offline", false)){
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
		
		if (request.getBooleanParameter("ajaxSubmit", false))
			return null;
		
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", textualPageBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		
		int id = request.getIntParameter("id", 0);
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			TextualPage textualPage= new TextualPage();
			textualPage.setCreatorId(userPersonBean.getId());
			int textualPageId = textualPageService.insertTextualPage(textualPage);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",textualPageId);
			return new ModelAndView ( new RedirectView("textualPage.html"), newModel);
		}
		else{//show edit
			TextualPageBean textualPageBean = (TextualPageBean) model.get("command");
			//desks
			List<MopDesk> mopDesks = mopDeskService.getMopDesks();
			model.put("mopDesks", mopDesks);
			//online
			if(textualPageService.existsTextualPageOnline(textualPageBean.getId()))
				model.put("online", true);
			else
				model.put("online", false);
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
			List<Category> categories = textualPageService.getCategories();
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
			return "image/jpeg ";
		else
			return "text/html";
	}	
	private TextualPageService textualPageService;

	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	
	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
	
	private ListListService listListService;

	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}
	

}
