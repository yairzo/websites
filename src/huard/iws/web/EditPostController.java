package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.Language;
import huard.iws.model.Post;
import huard.iws.model.Subject;
import huard.iws.service.CallForProposalService;
import huard.iws.service.PersonListService;
import huard.iws.service.PostService;
import huard.iws.service.SendPostService;
import huard.iws.service.SubjectService;
import huard.iws.util.BaseUtils;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;
import huard.iws.web.CallForProposalController.CallForProposalContact;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditPostController extends GeneralFormController {

	private static final Logger logger = Logger.getLogger(EditPostController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		PostBean postBean = (PostBean)command;
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", postBean.getId());
		
		String action = request.getParameter("action", "");
		
		PostBean dbpost= new PostBean(postService.getPost(postBean.getId()));
		if(action.equals("cancelVerified")){
			dbpost.setVerified(false);
			postService.updatePost(dbpost.toPost());
			return new ModelAndView(new RedirectView("post.html"), newModel);
		}
		
		if (dbpost.isVerified())//if verified do not update
			return new ModelAndView(new RedirectView("post.html"), newModel);

		String deletePost = request.getParameter("deletePost", "");
		if (deletePost.equals("true")){
			postService.deletePost(postBean.getId());
			return new ModelAndView(new RedirectView("posts.html"));
		}
		
		String subjectsIdsString = request.getParameter("subjectsIdsString","");
		
		List<Integer> subjectsIds = BaseUtils.getIntegerList(subjectsIdsString, ",");
		if (postBean.getSubjectsIds() != null)
			postBean.getSubjectsIds().clear();
		for (int subjectId: subjectsIds){
			postBean.getSubjectsIds().add(subjectId);
		}

		Post preUpdatePost = postService.getPost(postBean.getId());

		List<Attachment> attachmentsToRemove = new ArrayList<Attachment>();
		for (int i = 0 ; i < preUpdatePost.getAttachments().size() ; i++){
			if (! request.getBooleanParameter("filech"+i, false))
				attachmentsToRemove.add(preUpdatePost.getAttachments().get(i));
		}
		preUpdatePost.getAttachments().removeAll(attachmentsToRemove);
		postBean.setAttachments(preUpdatePost.getAttachments());

		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
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
				attachment.setContentType(file.getContentType());
				postBean.getAttachments().add(attachment);
			}
		}
		if (postBean.getDate() != null && !postBean.getDate().equals("") && postBean.getHour() !=null && !postBean.getHour().equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh");
			try {
				long datetime = sdf.parse(postBean.getDate()+" "+postBean.getHour()).getTime();
				postBean.setSendTime(new Timestamp(datetime));
			}
			catch(Exception e){
				logger.info(e);
				postBean.setSendTime(new Timestamp(System.currentTimeMillis()));
			}
		}
		if (postBean.getSubjectsIds() == null){
				postBean.setSubjectsIds(new ArrayList<Integer>());
		}
		if (action.equals("sendme")){
			postBean.setSelfSend(true);
		}
		if (action.equals("cancel")){
			return new ModelAndView(new RedirectView("posts.html"));
		}

		postService.updatePost(postBean.toPost());
		if (action.equals("sendme")){
			sendPostService.prepareSelfSendPosts();
			sendPostService.sendSelfPosts();
			String userMessage = messageService.getMessage("iw_IL.post.sentToMe");
			request.getSession().setAttribute("userMessage", userMessage);
		}

		//Map<String, Object> newModel = new HashMap<String, Object>();
		//newModel.put("id", postBean.getId());
		return new ModelAndView(new RedirectView("post.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		
		if (request.getParameter("action", "").equals("new")){
			int id = postService.insertPost(userPersonBean.getId());
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",id);
			//when opened from callForProposal
			if(!request.getParameter("callForProposal", "").isEmpty()){
				Post tmpPost = postService.getPost(id);
				CallForProposal callForProposal = callForProposalService.getCallForProposal(request.getIntParameter("callForProposal", 0));
				CallForProposalBean callForProposalBean = new CallForProposalBean(callForProposal, true);
				tmpPost.setMessageSubject(callForProposalBean.getId() + " - " + callForProposalBean.getTitle());
				tmpPost.setMessage(callForProposalBean.toPostMessage());
				//if(userPersonBean.isPostNewDesign())
				tmpPost.setMessageNew(callForProposalBean.toPostMessageNew());
				tmpPost.setLocaleId(callForProposalBean.getLocaleId());
				tmpPost.setTypeId(postService.getPostTypeFromCP(callForProposalBean.getTypeId()));
				tmpPost.setCallForProposalUrlTitle(callForProposalBean.getUrlTitle());
				String budgetDetails = callForProposalBean.getBudgetDetails();
				String additionalAddresses="";
				Pattern p = Pattern.compile("mailto:([^\"]*)");
				Matcher m = p.matcher(budgetDetails);
				while(m.find()){
					additionalAddresses=additionalAddresses+","+m.group(1);
				}
				if(additionalAddresses.length()>1)
					additionalAddresses=additionalAddresses.substring(1);
				tmpPost.setAdditionalAddresses(additionalAddresses);
				List<Integer> subjectsIds = callForProposalBean.getSubjectsIds();
				for (int subjectId: subjectsIds){
					tmpPost.getSubjectsIds().add(subjectId);
				}
				List<Attachment> attachments = callForProposalBean.getAttachments();
				for (Attachment attachment: attachments){
					attachment.setId(0);
					tmpPost.getAttachments().add(attachment);
				}
				postService.updatePost(tmpPost);
			}
			return new ModelAndView ( new RedirectView("post.html"), newModel);
		}

		PostBean postBean = (PostBean) model.get("command");
		
		LanguageUtils.applyLanguage(model, postBean.getLocaleId());

		boolean allowedToViewPost = postBean.isVerified() && sendPostService.isToBeSentTo(postBean.toPost(), userPersonBean);

		if ( ! userPersonBean.isAnyAuthorized(new String []{"POST_ADMIN","POST_CREATOR","POST_SENDER","POST_READER"})
				&& ! allowedToViewPost)
			return new ModelAndView(new RedirectView("accessDenied.html"));

		int listId = configurationService.getConfigurationInt("post","postCreatorsListId");

		List<PersonBean> senders = personListService.getPersonsList(listId, postBean.getLocaleId());
		model.put("senders", senders);

		Subject rootSubject;
		if (postBean.isVerified())
			rootSubject = subjectService.getSubjectFilterSubSubjects(1, postBean.getLocaleId(), postBean.getSubjectsIds());
		else
			rootSubject = subjectService.getSubject(1, postBean.getLocaleId());

		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, postBean.getLocaleId());

		model.put("rootSubject", rootSubjectBean);

		model.put("sendDateTime", DateUtils.getLocaleDependentLongDateTimeFormat(
				postBean.getSendTime().getTime(), postBean.getLocaleId(),false));

		model.put("postTypes", postService.getPostTypes());
		
		if(userPersonBean.isPostNewDesign())
			model.put("showNewDesign",true);

		
		logger.info("Lang: " + ((Language)model.get("lang")).getLocaleId());
		
		return new ModelAndView("editPost", model);
		
		
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		Post post = new Post();
		PostBean postBean = new PostBean();
		
		if ( isFormSubmission(request.getRequest()) || request.getParameter("action","").equals("new"))
			return postBean;
		
		if ( ! isFormSubmission(request.getRequest())){
			int id = request.getIntParameter("id", 0);
			/*if (id ==0){
				id = postService.insertPost(userPersonBean.getId());
				post.setId(id);
				post.setCreatorId(userPersonBean.getId());
			}
			else{*/
				post = postService.getPost(id);
			//}
			postBean = new PostBean(post);
		}
		return postBean;
	}

	private PostService postService;


	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private SubjectService subjectService;


	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	private SendPostService sendPostService;

	public void setSendPostService(SendPostService sendPostService) {
		this.sendPostService = sendPostService;
	}
	
	private CallForProposalService callForProposalService;
	
	public void setCallForProposalService(
			CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
}
