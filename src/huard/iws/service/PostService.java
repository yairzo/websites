package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.Post;
import huard.iws.model.PostType;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;
import java.util.Map;

public interface PostService {

	public Post getPost(int id);

	public void updatePost( Post post);

	public int insertPost (int personId);

	public void deletePost(int id);

	public List<Post> getPosts();

	public List<Post> getYetSentPosts();

	public List<Post> getSelfSendPosts();

	public List<Post> getPostsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean, boolean receivedOnly);

	public void prepareListView(ListView lv, SearchCreteria search, PersonBean userPersonBean, boolean receivedOnly);

	public List<PostType> getPostTypes();

	public Map<Integer, PostType> getPostTypesMap();

	public List<PersonBean> getSubscribers();

	public List<PersonBean> getSubscribersNoSubjects();

	public List<PersonBean> getSubscribersDisabled();

	public List<PersonBean> getSubscribersSubject(int subjectId);

	public List<Post> getOpenPosts();

}
