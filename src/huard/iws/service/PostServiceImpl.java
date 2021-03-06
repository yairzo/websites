package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PostDao;
import huard.iws.db.QueryElementsMap;
import huard.iws.db.QueryElementsMap.QueryValidKey;
import huard.iws.model.Post;
import huard.iws.model.PostType;
import huard.iws.util.ListPaginator;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostServiceImpl implements PostService{

	private final int POSTS_IN_PAGE = 10;
	private final boolean ENABLED = true;
	private final boolean DISABLED = false;

	public Post getPost(int id){
		return postDao.getPost(id);
	}

	public void updatePost( Post post){
		postDao.updatePost(post);
	}

	public int insertPost (int personId){
		return postDao.insertPost(personId);
	}

	public void deletePost(int id){
		postDao.deletePost(id);
	}

	public List<Post> getPosts(){
		return postDao.getPosts();
	}

	public List<Post> getYetSentPosts(){
		return postDao.getYetSentPosts();
	}

	public List<Post> getSelfSendPosts(){
		return postDao.getSelfSendPosts();
	}

	public List<Post> getPosts(ListView lv, SearchCreteria search,
			PersonBean userPersonBean) {
		if (search != null && ((search.getSearchField()!=null
				&& search.getSearchPhrase()!=null && !search.getSearchPhrase().equals("")) || (search.getWhereClause()!=null && !search.getWhereClause().equals(""))) ){
			return postDao.getPosts(lv, search, userPersonBean);
		}
		else {
			return postDao.getPosts(lv, userPersonBean);
		}
	}

	public int getLastPageNum(ListView lv, SearchCreteria search,
			PersonBean userPersonBean){
		ListPaginator lp = new ListPaginator(getPosts(lv, search, userPersonBean), POSTS_IN_PAGE);
		return lp.getNumOfPages();
	}

	public List<Post> getPostsPage(ListView lv, SearchCreteria search,
			PersonBean userPersonBean) {
		
		ListPaginator lp = new ListPaginator(getPosts(lv, search, userPersonBean), POSTS_IN_PAGE);
		List l = lp.getPage(lv.getPage());
		List<Post> postPage = new ArrayList<Post>();
		for (Object o : l){
			Post person = (Post) o;
			postPage.add(person);
		}
		return postPage;
	}

	public void prepareListView(ListView lv, SearchCreteria search,
			PersonBean userPersonBean){
		ListPaginator lp = new ListPaginator(getPosts(lv, search, userPersonBean), POSTS_IN_PAGE);
		lv.setLastPage(lp.getNumOfPages());
		lv.setNearPages(lp.getNearPages(lv.getPage()));
	}

	public List<PostType> getPostTypes(){
		return postDao.getPostTypes();
	}

	public Map<Integer, PostType> getPostTypesMap(){
		Map<Integer, PostType> postTypesMap = new HashMap<Integer, PostType>();
		for (PostType postType: getPostTypes()){
			postTypesMap.put(postType.getId(), postType);
		}
		return postTypesMap;
	}

	public List<PersonBean> getSubscribers(){
		QueryElementsMap queryElementsMap = new QueryElementsMap();
		queryElementsMap.put(QueryValidKey.additionalConditions, "receivePosts = 1");		
		List<PersonBean> subscribers = personService.getUsers("ROLE_POST_READER", ENABLED, queryElementsMap);
		return subscribers;
	}
	
	public int getSubscribersCount(){
		QueryElementsMap queryElementsMap = new QueryElementsMap();
		queryElementsMap.put(QueryValidKey.additionalConditions, "receivePosts = 1");		
		return personService.getUsersCount("ROLE_POST_READER", ENABLED, queryElementsMap);		
	}
	
	public List<PersonBean> getRecentSubscribers(int numRecentSeubscribers){
		QueryElementsMap queryElementsMap = new QueryElementsMap();
		queryElementsMap.put(QueryValidKey.additionalConditions, "receivePosts = 1");
		queryElementsMap.put(QueryValidKey.limitPhrase, "limit " + numRecentSeubscribers);
		List<PersonBean> subscribers = personService.getUsers("ROLE_POST_READER", ENABLED, 
				queryElementsMap);
		return subscribers;		
	}

	public List<PersonBean> getSubscribersNoSubjects(){
		QueryElementsMap queryElementsMap = new QueryElementsMap();
		queryElementsMap.put(QueryValidKey.additionalConditions, "subjectId is null");
		queryElementsMap.put(QueryValidKey.joinPhrase, " left join subjectToPerson on person.id = subjectToPerson.personId");
		List<PersonBean> subscribers = personService.getUsers("ROLE_POST_READER", ENABLED, queryElementsMap);
		return subscribers;
	}

	public List<PersonBean> getSubscribersDisabled(){
		List<PersonBean> subscribers = personService.getUsers("ROLE_POST_READER", DISABLED);
		return subscribers;
	}

	public List<PersonBean> getSubscribersSubject(int subjectId){
		
		QueryElementsMap queryElementsMap = new QueryElementsMap();
		queryElementsMap.put(QueryValidKey.joinPhrase, " inner join subjectToPerson"
				+ " on (subjectToPerson.subjectId = " + subjectId + " and person.id = subjectToPerson.personId)");
		List<PersonBean> subscribers = personService.getUsers("ROLE_POST_READER", ENABLED, queryElementsMap);
		return subscribers;
	}



	public List<Post> getOpenPosts(){
		return postDao.getPosts("isVerified = 1 and isSent = 0");
	}

	
	public int copyPost (int sourcePostId, PersonBean userPersonBean){
		Post newPost = this.getPost(sourcePostId);
		int newPostId = this.insertPost(userPersonBean.getId());
		newPost.setId(newPostId);
		newPost.setMessageSubject(newPost.getMessageSubject() + " - copy");
		newPost.setSelfSend(false);
		newPost.setSelfSent(false);
		newPost.setVerified(false);
		newPost.setSent(false);
		newPost.setCreatorId(userPersonBean.getId());
		newPost.setSenderId(userPersonBean.getId());
		postDao.updatePost(newPost);
		return newPostId;
	}
	public int countNotSentPostPersons(){
		return postDao.countNotSentPostPersons();
	}

	public int countSentPostPersons(){
		return postDao.countSentPostPersons();
	}

	public Map<Integer,Integer> getCountPostPersonsToSend(){
		return postDao.getCountPostPersonsToSend();
	}

	public Map<Integer,Integer> getCountPostPersonsSent(){
		return postDao.getCountPostPersonsSent();
	}
	public Post getPostByMessageSubject(String messageSubject){
		return postDao.getPostByMessageSubject(messageSubject);
	}
	
	public int getPostTypeFromCP(int callForProposalTypeId){
		return postDao.getPostTypeFromCP(callForProposalTypeId);
	}
	
	private PostDao postDao;

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
