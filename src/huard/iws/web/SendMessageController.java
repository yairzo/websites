package huard.iws.web;

import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.AList;
import huard.iws.model.Attachment;
import huard.iws.model.MailMessage;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonAttributionListService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class SendMessageController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		MailMessageBean mailMessageBean = (MailMessageBean) command;
		if (mailMessageBean.getMessage().equals("")){
			mailMessageBean.setMessageSubject(mailMessageBean.getMessageSubject()
						.concat(" " + messageService.getMessage("iw_IL.iws.general.sendMessage.noBodySubject")));
			mailMessageBean.setMessage(messageService.getMessage("iw_IL.iws.general.sendMessage.noBody"));
		}

		MailMessage preUpdateMailMessage = mailMessageService.getMailMessage(mailMessageBean.getId());

		List<Attachment> attachmentsToRemove = new ArrayList<Attachment>();
		for (int i = 0 ; i < preUpdateMailMessage.getAttachments().size() ; i++){
			if (! request.getBooleanParameter("filech"+i, false))
				attachmentsToRemove.add(preUpdateMailMessage.getAttachments().get(i));
		}
		preUpdateMailMessage.getAttachments().removeAll(attachmentsToRemove);
		mailMessageBean.setAttachments(preUpdateMailMessage.getAttachments());

		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			//sivan
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				MultipartFile file = multipartRequest.getFile(filename);
				if (file.getSize() == 0)
					continue;
				Attachment attachment = new Attachment();
				attachment.setTitle(file.getOriginalFilename());
				attachment.setFile(file.getBytes());
				//sivan
				attachment.setContentType(file.getContentType());
				mailMessageBean.getAttachments().add(attachment);
			}
		}

		String action = request.getParameter("action", "");

		if (! action.equals("cancel")){
			mailMessageService.updateMailMessage(mailMessageBean.toMailMessage());
		}



		Map<String, Object> newModel = new HashMap<String, Object>();

		String callerPage = request.getParameter("cp", "welcome.html");
		newModel.put("cp", callerPage);
		int callerPageObjectId = request.getIntParameter("cpoi", 0);
		newModel.put("cpoi", callerPageObjectId);

		if (action.equals("send")){
			mailMessageService.sendMailMessage(mailMessageBean);
			if (mailMessageBean.isCcMe()){
				mailMessageBean.setSendMeOnly(true);
				String messageSubject = messageService.getMessage("iw_IL.iws.general.sendMessage.ccMe", new String[]{mailMessageBean.getListBean(request).getName()}) + mailMessageBean.getMessageSubject();
				mailMessageBean.setMessageSubject(messageSubject);
				mailMessageBean.getRecipientsPersonsIds().clear();
				mailMessageBean.getRecipientsPersonsIds().add(userPersonBean.getId());
				mailMessageService.sendMailMessage(mailMessageBean);
			}
			userMessageService.putUserMessageInSession(request,
					userMessageService.getMessageSentMessage());
		}
		else if (action.equals("sendMeTest")){
			mailMessageBean.setSendMeOnly(true);
			List<Integer> recipientsIds = new ArrayList<Integer>();
			recipientsIds.add(userPersonBean.getId());
			mailMessageBean.setRecipientsPersonsIds(recipientsIds);
			String messageSubject = messageService.getMessage("iw_IL.iws.general.sendMessage.sendMe") + mailMessageBean.getMessageSubject();
			mailMessageBean.setMessageSubject(messageSubject);
			mailMessageService.sendMailMessage(mailMessageBean);
			newModel.put("listId", mailMessageBean.getListId());
			newModel.put("id", mailMessageBean.getId());

			return new ModelAndView(new RedirectView("sendMessage.html"), newModel);
		}
		else if (action.equals("")){
			newModel.put("id", mailMessageBean.getId());
			return new ModelAndView(new RedirectView("sendMessage.html"), newModel);
		}

		if (callerPageObjectId == 0)
			callerPage = "welcome.html";
		else{
			newModel.remove("cpoi");
			newModel.put("id", callerPageObjectId);
		}
		return new ModelAndView(new RedirectView(callerPage), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		model.put("aNewMessage", request.getIntParameter("id", 0) == 0);
		return new ModelAndView(getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		MailMessageBean mailMessageBean = new MailMessageBean();
		if (!isFormSubmission(request.getRequest())){

			int id = request.getIntParameter("id", 0);
			if (id>0)
				mailMessageBean = new MailMessageBean(mailMessageService.getMailMessage(id));
			else{
				id = mailMessageService.insertMailMessage(mailMessageBean.toMailMessage());
				mailMessageBean.setId(id);
			}
			mailMessageBean.setSenderPersonId(userPersonBean.getId());
			int [] recipientPersonsIds  = request.getIntParameterValues("recipientsPersonsIds");
			for (int recipientPersonId : recipientPersonsIds){
				mailMessageBean.getRecipientsPersonsIds().add(recipientPersonId);
			}
			int listId = request.getIntParameter("listId", 0);
			if (listId > 0){
				mailMessageBean.setListId(listId);
				AList aList = listService.getList(listId);
				if (aList.getListTypeId() == Constants.getListTypesInv().get("person")){
					List<PersonListAttribution> personAttribs = personAttributionListService.getPersonAttributions(listId);
					for (PersonListAttribution personAttrib: personAttribs){
						mailMessageBean.getRecipientsPersonsIds().add(personAttrib.getPersonId());
					}
				}
			}

		}
		return mailMessageBean;
	}


	private PersonAttributionListService personAttributionListService;

	public void setPersonAttributionListService(
			PersonAttributionListService personAttributionListService) {
		this.personAttributionListService = personAttributionListService;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}

	/*private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}*/



}
