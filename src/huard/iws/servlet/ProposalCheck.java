package huard.iws.servlet;

import huard.iws.model.Person;
import huard.iws.model.Proposal;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonService;
import huard.iws.service.ProposalService;
import huard.iws.util.ApplicationContextProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

public class ProposalCheck extends HttpServlet {
	public static final long serialVersionUID = 1323243435;
	//private static final Logger logger = Logger.getLogger(ProposalCheck.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("ACEGI_SECURITY_LAST_USERNAME");

		ApplicationContext context = ApplicationContextProvider.getContext();
		//ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());//getWebApplicationContext(getServletContext());

		Object obj = context.getBean("personService");
		PersonService personService = (PersonService)obj;

		obj = context.getBean("personProposalService");
		PersonProposalService personProposalService = (PersonProposalService)obj;

		obj = context.getBean("proposalService");
		ProposalService proposalService = (ProposalService)obj;


		String aProposalId;
		int proposalId=0;
		if ((aProposalId = request.getParameter("proposalId"))!=null){
			proposalId = Integer.parseInt(aProposalId);
		}

		if (proposalId == 0) return;

		Person person = personService.getPersonByCivilId(username);
		boolean aProposalPerson = personProposalService.isExists(person.getId(), proposalId);

		if (! aProposalPerson) return;

		String type;
		if ( (type = request.getParameter("type"))==null ) return;

		if (type.equals("stateId")){
			Proposal proposal = proposalService.getProposal(proposalId);

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(""+proposal.getStateId());
			out.flush();
			out.close();

		}

	}




	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req,res);
	}

}
