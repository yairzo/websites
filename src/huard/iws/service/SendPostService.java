package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.Post;

public interface SendPostService {

	public void prepareSendPosts(int dayOfWeek, int hourOfDay);

	public void prepareSelfSendPosts();

	public void sendPosts();

	public void sendSelfPosts();

	public boolean isToBeSentTo(Post post, PersonBean person);

}
