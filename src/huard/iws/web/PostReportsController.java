package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.Post;
import huard.iws.model.Subject;
import huard.iws.service.PostService;
import huard.iws.service.SubjectService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;



public class PostReportsController extends GeneralController{

	private final int RECENT_SUBSCRIBERS_NUM = 10;

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){

		LanguageUtils.applyLanguage(model, request, response, "iw_IL");

		List<PersonBean> subscribers = postService.getRecentSubscribers(RECENT_SUBSCRIBERS_NUM);

		model.put("subscribersCount", postService.getSubscribersCount());		

		List<PersonBean> subscribersNoSubjects = postService.getSubscribersNoSubjects();

		model.put("subscribersNoSubjects", subscribersNoSubjects);

		model.put("subscribersNoSubjectsCount", subscribersNoSubjects.size());

		List<PersonBean> subscribersDisabled = postService.getSubscribersDisabled();

		model.put("subscribersDisabled", subscribersDisabled);

		model.put("subscribersDisabledCount", subscribersDisabled.size());

		Subject rootSubject = subjectService.getSubject(1, "iw_IL");

		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, "iw_IL");

		model.put("rootSubject", rootSubjectBean);


		model.put("subscribers", subscribers);

		List<Post> openPosts = postService.getOpenPosts();
		Map<Integer, Integer> getCountPostPersonsToSend = postService.getCountPostPersonsToSend();
		Map<Integer, Integer> getCountPostPersonsSent = postService.getCountPostPersonsSent();
		for(Post openPost:openPosts){
			if(getCountPostPersonsToSend.containsKey(openPost.getId()) ){
				int toSend = getCountPostPersonsToSend.get(openPost.getId());
				int sent=0;
				if(getCountPostPersonsSent.containsKey(openPost.getId()))
					sent = getCountPostPersonsSent.get(openPost.getId());
				openPost.setCountNotSent(toSend-sent);
			}
		}
		model.put("openPosts", openPosts);

		model.put("openPostsCount", openPosts.size());
		

		
		int notSentPosts = postService.countNotSentPostPersons();
		model.put("notSentPostsCount", notSentPosts);
		int sentPosts = postService.countSentPostPersons();
		model.put("sentPostsCount", sentPosts);

		int subjectId = request.getIntParameter("sid", 0);

		if (subjectId > 0){
			List<PersonBean> subscribersSubject = postService.getSubscribersSubject(subjectId);
			model.put("subscribersSubject", subscribersSubject);
		}

		return new ModelAndView ("postReports", model);
	}


	private PostService postService;


	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	private SubjectService subjectService;

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

}
