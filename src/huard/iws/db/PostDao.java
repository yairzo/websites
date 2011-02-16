package huard.iws.db;

import huard.iws.bean.PersonBean;
import huard.iws.model.Post;
import huard.iws.model.PostType;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostDao {

	public Post getPost(int id);

	public void updatePost(Post post);

	public int insertPost(int personId);

	public void deletePost(int id);

	public List<Post> getPosts();

	public List<Post> getPosts(String additionalCondition);

	public List<Post> getPosts(ListView lv, PersonBean userPersonBean, boolean receivedOnly);

	public List<Post> getPosts(ListView lv, SearchCreteria search, PersonBean userPersonBean, boolean receivedOnly);

	public List<Post> getYetSentPosts();

	public List<Post> getSelfSendPosts();

	public Set<Integer> getPreparedPostPersons(int postId);

	public List<Post> getPreparedPersonPosts(int personId);

	public Map<Integer,Set<Integer>> getPreparedPostPersonsMap();

	public void insertPersonPost(int personId, int postId);

	public void insertPersonPost(int personId, int postId, boolean selfSend);

	public void deletePersonPost(int personId, int postId);

	public void markPersonPostAsSent(int personId, int postId);

	public List<PostType> getPostTypes();

}
