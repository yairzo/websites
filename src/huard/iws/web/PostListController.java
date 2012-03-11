package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.model.Post;
import huard.iws.service.PersonService;
import huard.iws.service.PostService;
import huard.iws.util.ConferenceProposalSearchCreteria;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class PostListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(PostListController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		PostListControllerCommand aCommand = (PostListControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("edit") && aCommand.getPostId()>0){
			newModel.put("id",aCommand.getPostId());
			return new ModelAndView( new RedirectView("post.html"),newModel);
		}
		if (action.equals("delete") && aCommand.getPostId()>0){
			newModel.put("id",aCommand.getPostId());
			return new ModelAndView( new RedirectView("deletePost.html"),newModel);
		}
		if (action.equals("copy") && aCommand.getPostId()>0){
			int newPostId = postService.copyPost(aCommand.getPostId(), userPersonBean);
			return new ModelAndView(new RedirectView("post.html?id=" + newPostId));
		}

		if (action.equals("search"))
			aCommand.getListView().setPage(1);
		//request.getSession().setAttribute("postsSearchCreteria", aCommand.getSearchCreteria());
		//request.getSession().setAttribute("postsListView", aCommand.getListView());

		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		PostListControllerCommand command = (PostListControllerCommand) model.get("command");

		boolean isReceived = userPersonBean.isAuthorized("POST", "READER");
		if (! isReceived){
			int aIsReceived = request.getIntParameter("received", 0);
			isReceived = (aIsReceived == 1);
		}
		
		postService.prepareListView(command.getListView(), command.getSearchCreteria(), userPersonBean, isReceived );

		//request.getSession().setAttribute("searchCreteria", null);
		//request.getSession().setAttribute("listView", null);

		System.out.println("Show form search command: " + command.getSearchCreteria().getWhereClause());
		List<Post> posts = postService.getPostsPage(command.getListView(), command.getSearchCreteria(), userPersonBean, isReceived);
		List<PostBean> postBeans = new ArrayList<PostBean>();

		for (Post post: posts){
			PostBean postBean = new PostBean(post);
			//postBean.setBusyRecord(recordProtectService.isRecordBusy("post",postBean.getId(), userPostBean.getUsername()));
			postBeans.add(postBean);
		}
		model.put("searchSent", request.getSession().getAttribute("searchSent"));
		model.put("posts", postBeans);
		return new ModelAndView ("postList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		PostListControllerCommand command = new PostListControllerCommand();
		if (!isFormSubmission(request.getRequest())){

			SearchCreteria searchCreteria = (SearchCreteria) request.getSession().getAttribute("postsSearchCreteria");
			//request.getSession().setAttribute("postsSearchCreteria", null);
			if (searchCreteria == null)
				searchCreteria = new SearchCreteria();

			ListView listView = (ListView) request.getSession().getAttribute("postsListView");
			if (listView == null){
				listView = new ListView();
				listView.setOrderBy("isVerified, sendTime");
			}
			
			command.setSearchCreteria(searchCreteria);
			command.setListView(listView);
		}
		if (isFormSubmission(request.getRequest())){
			SearchCreteria searchCreteria = new SearchCreteria();
			String whereClause = "";
			String postDate = request.getParameter("postDate","");
			if( !postDate.equals("")){
				whereClause += " Date(sendTime)='" + request.getParameter("postDate", "") + "'";
			}
			String searchSent = request.getParameter("searchSent","");
			if( searchSent.equals("0")){
				if (!whereClause.equals(""))
					whereClause += " and ";
				whereClause += " isSent=1";
			}
			searchCreteria.setWhereClause(whereClause);
			request.getSession().setAttribute("searchSent", searchSent);
			
			String searchPhrase = request.getParameter("searchPhrase","");
			if( !searchPhrase.equals("")){
				searchCreteria.setSearchField("messageSubject");
				searchCreteria.setSearchPhrase(searchPhrase);
			}
			
			command.setSearchCreteria(searchCreteria);
			request.getSession().setAttribute("postsSearchCreteria", searchCreteria);
			ListView listView = new ListView();
			listView.setOrderBy("isVerified, sendTime");
			listView.setPage(request.getIntParameter("listView.page", 1));			
			request.getSession().setAttribute("postsListView", listView);
			command.setListView(listView);
		}
		return command;
	}

	public class PostListControllerCommand{
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();
		int postId=0;
		public int getPostId() {
			return postId;
		}
		public void setPostId(int postId) {
			this.postId = postId;
		}
		public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}


	}

	private PersonService personService;

	private PostService postService;

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}
}
