package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.Attachment;
import huard.iws.model.Post;
import huard.iws.model.Subject;
import huard.iws.service.PersonListService;
import huard.iws.service.PostService;
import huard.iws.service.SendPostService;
import huard.iws.service.SubjectService;
import huard.iws.util.BaseUtils;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		if (postBean.getDate() != null && postBean.getHour() !=null){
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
		String action = request.getParameter("action", "");
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
		}

		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", postBean.getId());
		return new ModelAndView(new RedirectView("post.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		PostBean postBean = (PostBean) model.get("command");

		boolean allowedToViewPost = postBean.isVerified() && sendPostService.isToBeSentTo(postBean.toPost(), userPersonBean);

		if ( ! userPersonBean.isAnyAuthorized(new String []{"POST_ADMIN","POST_CREATOR","POST_SENDER"})
				&& ! allowedToViewPost)
			return new ModelAndView(new RedirectView("accessDenied.html"));

		LanguageUtils.applyLanguage(model, request, response, postBean.getLocaleId());
		LanguageUtils.applyLanguages(model);

		int listId = configurationService.getConfigurationInt("postCreatorsListId");

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
				postBean.getSendTime().getTime(), postBean.getLocaleId()));

		model.put("postTypes", postService.getPostTypes());

		return new ModelAndView("editPost", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		Post post = new Post();
		PostBean postBean = new PostBean();
		if ( ! isFormSubmission(request.getRequest())){
			int id = request.getIntParameter("id", 0);
			if (id ==0){
				id = postService.insertPost(userPersonBean.getId());
				post.setId(id);
				post.setCreatorId(userPersonBean.getId());
			}
			else{
				post = postService.getPost(id);
			}
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

}
