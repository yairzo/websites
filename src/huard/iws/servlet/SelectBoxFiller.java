package huard.iws.servlet;

import huard.iws.bean.PersonBean;
import huard.iws.model.CallOfProposal;
import huard.iws.model.Country;
import huard.iws.model.Fund;
import huard.iws.model.Institute;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.Person;
import huard.iws.model.Post;
import huard.iws.service.CallOfProposalService;
import huard.iws.service.FundService;
import huard.iws.service.InstituteListService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.service.PersonListService;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonService;
import huard.iws.service.UniverseService;
import huard.iws.service.PostService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.ListView;
import huard.iws.util.UserPersonUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

public class SelectBoxFiller extends HttpServlet {
	//private static final Logger logger = Logger.getLogger(SelectBoxFiller.class);
	ApplicationContext context = ApplicationContextProvider.getContext();
	private PersonService personService;
	private PersonProposalService personProposalService;
	private UniverseService universeService;
	private InstituteListService instituteListService;
	private FundService fundService;
	private PersonListService personListService;
	private OrganizationUnitService organizationUnitService;
	private CallOfProposalService callOfProposalService;
	private PostService postService;

	final static long serialVersionUID = 0;

	public void init(){

		Object obj = context.getBean("personService");
		personService = (PersonService)obj;

		obj = context.getBean("personListService");
		personListService = (PersonListService)obj;

		obj  = context.getBean("personProposalService");
		personProposalService = (PersonProposalService)obj;

		obj  = context.getBean("universeService");
		universeService = (UniverseService)obj;

		obj  = context.getBean("instituteListService");
		instituteListService = (InstituteListService)obj;

		obj  = context.getBean("fundService");
		fundService = (FundService)obj;

		obj  = context.getBean("organizationUnitService");
		organizationUnitService = (OrganizationUnitService)obj;

		obj  = context.getBean("callOfProposalService");
		callOfProposalService = (CallOfProposalService)obj;
		
		obj  = context.getBean("postService");
		postService = (PostService)obj;

	}

	private boolean isAuthorized(HttpServletRequest request){
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService);
		if (userPersonBean.isAuthorized("EQF", "ADMIN")) return true;
		if (userPersonBean.isAuthorized("EQF", "MOP")) return true;
		if (userPersonBean.isAuthorized("LISTS", "ADMIN")) return true;
		if (userPersonBean.isAuthorized("LISTS", "EDITOR")) return true;
		if (userPersonBean.isAuthorized("POST", "ADMIN")) return true;
		if (userPersonBean.isAuthorized("POST", "CREATOR")) return true;
		if (userPersonBean.isAuthorized("POST", "READER")) return true;
		if (userPersonBean.isAuthorized("CONFERENCE", "ADMIN")) return true;
		if (userPersonBean.isAuthorized("CONFERENCE", "APPROVER")) return true;
		if (userPersonBean.isAuthorized("CONFERENCE", "COMMITTEE")) return true;

		String proposalId;
		int aProposalId=0;
		if ((proposalId = request.getParameter("proposalId"))!=null){
			aProposalId = Integer.parseInt(proposalId);
		}
		if (aProposalId == 0) return false;

		boolean aProposalResearcher = personProposalService.isExists(userPersonBean.getId(), aProposalId);
		return aProposalResearcher;
	}



	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (! isAuthorized(request)) return;

		String type;
		if ( (type = request.getParameter("type"))==null ) return;

		if (type.equals("country")){
			String continentId;
			if ((continentId = request.getParameter("continentId")) == null) return;
			List<Country> countries = universeService.getCountries(Integer.parseInt(continentId));
			if (countries.size() > 0){
				StringBuilder sb = new StringBuilder();
				sb.append("<option>בחר/י</option>\n" );
				for (Country country : countries){
					sb.append("<option value=\""+country.getId()+"\">"+country.getName()+"</option>\n" );
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
				ServletOutputStream out = response.getOutputStream();
				out.print(sb.toString());
				out.flush();
				out.close();

			}

		}

		if (type.equals("institute")){
			String countryId;
			if ((countryId = request.getParameter("countryId")) == null) return;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<Institute> institutes = instituteListService.getInstitutes(Integer.parseInt(countryId));
			for (Institute institute: institutes){
				sb.append(institute.getName()+",");
			}
			sb.deleteCharAt(sb.length()-1);
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("fund")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			LinkedHashMap<String,Fund> funds = fundService.getFundsNamesMap();
			for (String name: funds.keySet()){
				sb.append(name+",,");
			}
			sb.deleteCharAt(sb.length()-1);
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("person")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<Person> persons = personListService.getPersons(new String [] { "lastNameHebrew", "firstNameHebrew", "email" });
			for (Person person: persons){
				PersonBean personBean = new PersonBean(person);
				String listItem = personBean.getLastNameHebrew() + " " + personBean.getFirstNameHebrew() + " " + personBean.getEmail();
				sb.append(listItem + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		
		if (type.equals("conference researchers")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<Person> persons = personListService.getConferenceResearchers(new String [] { "lastNameHebrew", "firstNameHebrew"});
			for (Person person: persons){
				PersonBean personBean = new PersonBean(person);
				String listItem = personBean.getLastNameHebrew() + " " + personBean.getFirstNameHebrew();
				sb.append(listItem + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		if (type.equals("all conference researchers")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<Person> persons = personListService.getConferenceResearchers(new String [] { "lastNameHebrew", "firstNameHebrew"});
			sb.append("[");
			for (Person person: persons){
				PersonBean personBean = new PersonBean(person);
				String listItem = "{\"label\":\""+personBean.getFirstNameHebrew()+" " + personBean.getLastNameHebrew()+"\",\"id\":"+personBean.getId()+"}";
				sb.append(listItem + ",");
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]");
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("organization unit")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<OrganizationUnit> organizationUnits = organizationUnitService.getOrganizationUnits();
			for (OrganizationUnit organizationUnit: organizationUnits){
				sb.append(organizationUnit.getNameHebrew() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("callOfProposal")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			String localeId = request.getParameter("localeId");
			if (localeId == null)
				return;
			StringBuilder sb = new StringBuilder();
			List<CallOfProposal> callsOfProposals = callOfProposalService.getCallsOfProposals(localeId);
			for (CallOfProposal callOfProposal: callsOfProposals){
				sb.append(callOfProposal.getTitle() + " - " + callOfProposal.getId() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		
		if (type.equals("post")){
			System.out.println("I'm here !!!!!!!");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService);
			ListView listView = new ListView();
			listView.setOrderBy("sendTime desc");
			List<Post> posts = postService.getPosts(listView,null,userPersonBean);
			for (Post post: posts){
				sb.append(post.getMessageSubject() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
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
