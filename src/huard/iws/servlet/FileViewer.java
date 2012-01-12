package huard.iws.servlet;

import huard.iws.bean.PostBean;
import huard.iws.model.Attachment;
import huard.iws.model.Person;
import huard.iws.model.Post;
import huard.iws.model.Proposal;
import huard.iws.model.ProposalAttachment;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonService;
import huard.iws.service.PostService;
import huard.iws.service.ProposalService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class FileViewer extends HttpServlet {

	private static final long serialVersionUID = -1;
	//private static final Logger logger = Logger.getLogger(FileViewer.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String username = (String)session.getAttribute("ACEGI_SECURITY_LAST_USERNAME");

		RequestWrapper requestWrapper = new RequestWrapper(request);

		byte [] file = null;
		String contentType = requestWrapper.getParameter("contentType", "");
		int attachmentId = requestWrapper.getIntParameter("attachmentId", 0);
		if (attachmentId <= 0 )
			return;
		
		int proposalId = requestWrapper.getIntParameter("proposalId", 0);
		int postId = requestWrapper.getIntParameter("postId", 0);
		int conferenceProposalId = requestWrapper.getIntParameter("conferenceProposalId", 0);

		if (proposalId > 0){

			String attachType  = requestWrapper.getParameter("attachType","");

			if (attachType.isEmpty())
				return;

			Object obj = ApplicationContextProvider.getContext().getBean("personService");
			PersonService personService = (PersonService)obj;

			obj  = ApplicationContextProvider.getContext().getBean("personProposalService");
			PersonProposalService personProposalService = (PersonProposalService)obj;

			Person person = personService.getPersonByCivilId(username);
			boolean aProposalResearcher = personProposalService.isExists(person.getId(), proposalId);
			if (! aProposalResearcher) return;

			Object bean = ApplicationContextProvider.getContext().getBean("proposalService");
			ProposalService proposalService = (ProposalService)bean;

			Proposal proposal = proposalService.getProposal("proposal", proposalId, username);


			if (attachType.equals("proposal"))	 {
				for (ProposalAttachment proposalAttachment: proposal.getResearchAttachments()){
					if (attachmentId == proposalAttachment.getId()){
						file = proposalAttachment.getFile();
					}
				}
			}
			else if (attachType.equals("ethics")) file = proposal.getEthicsAttach();
			else if (attachType.equals("safety")) file = proposal.getSafetyAttach();
			else if (attachType.equals("human")) file = proposal.getHumanAttach();
			else if (attachType.equals("animals")) file = proposal.getAnimalsAttach();
			else return;

		}

		else if (postId > 0){
			Object obj = ApplicationContextProvider.getContext().getBean("postService");
			PostService postService = (PostService) obj;

			Post post = postService.getPost(postId);
			PostBean postBean = new PostBean(post);
			String md5 = requestWrapper.getParameter("md5", "");
			if (! md5.equals(postBean.getMd5()))
				return ;

			Attachment attachment = postBean.getAttachmentsMap().get(attachmentId);
			file = attachment.getFile();
		}
		
		else if (conferenceProposalId > 0){
			String attachFile  = requestWrapper.getParameter("attachFile","");

			if (attachFile.isEmpty())
				return;


			Object bean = ApplicationContextProvider.getContext().getBean("conferenceProposalService");
			ConferenceProposalService conferenceProposalService = (ConferenceProposalService)bean;

			ConferenceProposal conferenceProposal = conferenceProposalService.getConferenceProposal(conferenceProposalId);

			if (attachFile.equals("guestsAttach")){
				file = conferenceProposal.getGuestsAttach();
				contentType=conferenceProposal.getGuestsAttachContentType();
			}
			else if (attachFile.equals("programAttach")){
				file = conferenceProposal.getProgramAttach();
				contentType=conferenceProposal.getProgramAttachContentType();
			}
			else if (attachFile.equals("financialAttach")){
				file = conferenceProposal.getFinancialAttach();
				contentType=conferenceProposal.getFinancialAttachContentType();
			}
			else return;

		}


		try{

		if (file !=null && file.length > 0){
			response.setContentType(contentType);
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.write(file);
			out.flush();
			out.close();
		}
	}catch(Exception e){
		System.out.println("Exception------>"+e);
	}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req,res);
	}


}