package huard.iws.web;

import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Post;
import huard.iws.service.MailMessageService;
import huard.iws.service.PostService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ApprovePostController extends GeneralController{

	//private static final Logger logger = Logger.getLogger(ProposalStateHistoryController.class);

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){


		int  postId = request.getIntParameter("id", 0);
		Post post = new Post();
		if (postId > 0)
			post = postService.getPost(postId);
		if (post.getCreatorId() != userPersonBean.getId()){
			model.put("acceptApproval", false);
		}
		else{
			post.setVerified(true);
			postService.updatePost(post);

			//Let's immediatly send the post to the additional addresses, they don't depend on the recieving habits of
			//out 'regular' subscribers


			for (String emailAddress: post.getAdditionalAddresses().split("[,;]")){
				MailMessageBean mailMessageBean = new MailMessageBean();
				mailMessageBean.setSenderPersonId(post.getSenderId());
				mailMessageBean.setAdditionalAddresses(emailAddress);
				mailMessageBean.setMessageSubject(post.getMessageSubject());
				mailMessageBean.setMessage(post.getMessage());

				mailMessageService.sendMailMessage(mailMessageBean);
			}

			model.put("acceptApproval", true);
		}
		return new ModelAndView ("approvePost", model);
	}

	private PostService postService;

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}

}
