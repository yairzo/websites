package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.db.PostDao;
import huard.iws.model.Post;
import huard.iws.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;


import java.io.BufferedWriter;
import java.io.FileWriter;
 
import org.apache.log4j.Logger;


public class SendPostServiceImpl implements SendPostService{
	private static final Logger logger = Logger.getLogger(SendPostServiceImpl.class);
	Map<Integer, Set<Integer>> preparedPostPersonsMap = null;

	private void markSentPosts(List<Post> yetSentPosts, List<PersonBean> persons){
		// get posts yet to be marked as sent, part of them may have been sent already
	try{
		FileWriter fstream = new FileWriter("/home/tomcat/tmp/postlog.txt", true); 
		BufferedWriter out = new BufferedWriter(fstream);
		for (Post post: yetSentPosts){
			if (!post.isVerified())
				continue;
			boolean preparedSendToAll = true;
			for (PersonBean person: persons){
				//out.write("\n post:"+post.getId() +",person:"+person.getId());
				if (isToBeSentTo(post, person)){
					if (isPreparedToSend(post,person)){
						continue;
					}
					else{
						preparedSendToAll = false;
						out.write("else. post:"+post.getId() +",person:"+person.getId());
						out.newLine();
					}
				}
			}
			out.newLine();
			out.write("preparedSendToAll:"+preparedSendToAll);
			if (preparedSendToAll){
				post.setSent(true);
				postService.updatePost(post);
			}			
		}
		out.close();
	}
	catch (Exception e){
		System.err.println("Error: " + e.getMessage());
	}
	    
	}

	private boolean isPreparedToSend(Post post, PersonBean person){
		if (! preparedPostPersonsMap.containsKey(post.getId()))
			return false;
		return preparedPostPersonsMap.get(post.getId()).contains(person.getId());
	}

	public boolean isToBeSentTo(Post post, PersonBean person){
		for (int postSubjectId: post.getSubjectsIds()){
			for (int personSubjectId: person.getSubjectsIds()){
				if (postSubjectId == personSubjectId){
					return true;
				}
			}
		}
		return false;
	}


	public void prepareSendPosts(int dayOfWeek, int hourOfDay){
		logger.info("prepareSendPosts: starting ....  " + DateUtils.getCurrentTime());

		//logger.info("a side job: deleting old post subscribers...");
		//int r = postService.deleteOldPostSubscribers();
		//logger.info(r + " old post subscribers were deleted");

		List<Post> yetSentPosts = postService.getYetSentPosts();

		List<PersonBean> persons = postService.getSubscribers();

		preparedPostPersonsMap = postDao.getPreparedPostPersonsMap();

		logger.info("prepareSendPosts: prepared post map size: " + preparedPostPersonsMap.size());

		if (dayOfWeek == -1 && hourOfDay == -1){
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(System.currentTimeMillis());
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		}

		if (dayOfWeek == -1 || hourOfDay == -1){
			logger.info("prepareSendPosts: probably wrong number of arguments. exiting !");
			return;
		}

		logger.info("prepareSendPosts: day: " + dayOfWeek + " hour: "+ hourOfDay);

		markSentPosts(yetSentPosts, persons);
		int prepareSendPostCounter = 0;
		for (Post post: yetSentPosts){
			if (! post.isSent() && post.isVerified()){
				for (PersonBean person: persons){
					if (!isPreparedToSend(post, person) && isToBeSentTo(post, person)){
						if (post.isSendImmediately() || person.isPostReceiveImmediately() || (person.getPostReceiveDays().contains(dayOfWeek)
								&& person.getPostReceiveHour() == hourOfDay)){
							postDao.insertPersonPost(person.getId(), post.getId());
							prepareSendPostCounter++;
						}
					}
				}
			}
		}
		logger.info("prepareSendPosts: Done. prepared " + prepareSendPostCounter +
				"posts. " + DateUtils.getCurrentTime());
	}

	public void prepareSelfSendPosts(){
		preparedPostPersonsMap = postDao.getPreparedPostPersonsMap();
		List<Post> yetSentPosts = postService.getSelfSendPosts();
		List<PersonBean> personsBeans = personListService.getPersonsList(configurationService.getConfigurationInt("post", "postCreatorsListId"));
		//markSentPosts(yetSentPosts, personsBeans);
		for (Post post: yetSentPosts){
			if (post.isSelfSend() && ! post.isVerified() && ! post.isSelfSent()){
				postDao.deletePersonPost(post.getCreatorId(), post.getId());
				postDao.insertPersonPost(post.getCreatorId(), post.getId(), true);
				post.setSelfSent(true);
				postDao.updatePost(post);
			}
		}
	}


	public void sendPosts(){
		logger.info("sendPosts: Starting ....  " + DateUtils.getCurrentTime());
		List<PersonBean> persons = postService.getSubscribers();
		int sentMailsCounter = 0;
		for (PersonBean person: persons){
			String personMD5 = person.getPersonMD5();
			List<Post> posts = postDao.getPreparedPersonPosts(person.getId());
			List<PostBean> postsBeans = new ArrayList<PostBean>();
			for (Post post: posts){
				PostBean postBean = new PostBean(post);
				postBean.setRecipientId(person.getId());
				postsBeans.add(postBean);
			}
			if (posts.size() > 0){
				callForProposalServiceOld.insertAuthorizedMD5(personMD5);
				mailMessageService.createPostsMessage(person, postsBeans, personMD5);
				sentMailsCounter++;
			}
			markPersonPostsAsSent(person.getId(), posts);
		}
		logger.info("sendPosts: "+ sentMailsCounter + " mails sent. Done. ");
	}

	public void sendSelfPosts(){
		List<PersonBean> personsBeans = personListService.getPersonsList(configurationService.getConfigurationInt("post", "postCreatorsListId"));
		for (PersonBean personBean: personsBeans){
			List<Post> posts = postDao.getPreparedPersonPosts(personBean.getId());
			List<PostBean> postsBeans = new ArrayList<PostBean>();
			for (Post post: posts){
				PostBean postBean = new PostBean(post);
				postBean.setRecipientId(personBean.getId());
				postsBeans.add(postBean);
			}
			if (posts.size() > 0)
				mailMessageService.createPostsMessage(personBean, postsBeans);
			markPersonPostsAsSent(personBean.getId(), posts);
		}
	}

	public void markPersonPostsAsSent(int personId, List<Post> posts){
		for (Post post: posts){
			postDao.markPersonPostAsSent(personId, post.getId());
		}
	}

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private PostService postService;

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	private PostDao postDao;

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}


	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	private CallForProposalServiceOld callForProposalServiceOld;

	public void setCallForProposalServiceOld(CallForProposalServiceOld callForProposalServiceOld) {
		this.callForProposalServiceOld = callForProposalServiceOld;
	}
}
