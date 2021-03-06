package huard.iws.servlet;

import huard.iws.bean.PersonBean;
import huard.iws.model.CallForProposal;
import huard.iws.model.Country;
import huard.iws.model.Fund;
import huard.iws.model.Institute;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.Person;
import huard.iws.model.Post;
import huard.iws.service.CallForProposalService;
import huard.iws.service.FundService;
import huard.iws.service.HujiAuthorizationService;
import huard.iws.service.InstituteListService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.service.PersonListService;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonService;
import huard.iws.service.UniverseService;
import huard.iws.service.PostService;
import huard.iws.service.CountryService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.ListView;
import huard.iws.util.UserPersonUtils;
import huard.iws.util.CallForProposalSearchCreteria;

import java.io.IOException;
import java.io.PrintWriter;
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
	private CallForProposalService callForProposalService;
	private PostService postService;
	private CountryService countryService;
	private HujiAuthorizationService hujiAuthorizationService;

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
		obj  = context.getBean("callForProposalService");
		callForProposalService = (CallForProposalService)obj;
		obj  = context.getBean("postService");
		postService = (PostService)obj;
		obj  = context.getBean("countryService");
		countryService = (CountryService)obj;
		obj = context.getBean("hujiAuthorizationService");
		hujiAuthorizationService = (HujiAuthorizationService)obj;
	}

	private boolean isAuthorized(HttpServletRequest request){
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);
		String [] authorities = new String [] {
				"ROLE_EQF_ADMIN", 
				"ROLE_EQF_MOP", 
				"ROLE_LISTS_ADMIN", 
				"ROLE_LISTS_EDITOR",	
				"ROLE_POST_ADMIN", 
				"ROLE_POST_CREATOR", 
				"ROLE_POST_READER",	
				"ROLE_CONFERENCE_ADMIN", 
				"ROLE_CONFERENCE_APPROVER", 
				"ROLE_CONFERENCE_COMMITTEE", 
				"ROLE_WEBSITE_EDIT", 
				"ROLE_WEBSITE_ADMIN",
				"ROLE_WEBSITE_HUJI"
		};
		if (userPersonBean.isAnyAuthorized(authorities))
			return true;
		else if (userPersonBean.isAuthorized("LISTS", "ANONYMOUS") 
				&& request.getParameter("type")!=null 
				&& request.getParameter("type").equals("fundsWithId")) 
			return true;
		
		String proposalId;
		int aProposalId=0;
		if ((proposalId = request.getParameter("proposalId"))!=null){
			aProposalId = Integer.parseInt(proposalId);
		}
		if (aProposalId == 0) 
			return false;

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
		
		if (type.equals("fundsWithId")){
			String term = request.getParameter("term");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			sb.append("[");		
			List<Fund> funds = fundService.getFilteredFunds(term);
			for (Fund fund: funds){
				if (!first)
					sb.append(",");
				first = false;
				String listItem = "{\"label\":\"" + fund.getName() + "\",\"id\":"+fund.getId()+"}";
				sb.append(listItem);
			}
			sb.append("]");
			ServletOutputStream out = response.getOutputStream();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		if (type.equals("countries")){
			String term = request.getParameter("term");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			String localeId = request.getParameter("localeId");
			boolean first = true;
			sb.append("[");		
			List<Country> countries = countryService.getFilteredCountries(term);
			for (Country country: countries){
				if (!first)
					sb.append(",");
				first = false;
				String listItem ="";
				if(localeId.equals("en_US"))
					listItem = "{\"label\":\"" + country.getName() + "\",\"id\":"+country.getId()+"}";
				else
					listItem = "{\"label\":\"" + country.getNameHebrew() + "\",\"id\":"+country.getId()+"}";
				sb.append(listItem);
			}
			sb.append("]");

			PrintWriter out = response.getWriter();
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
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("conference researchers")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			sb.append("[");		
			String term = request.getParameter("term");
			List<Person> persons = personListService.getFilteredPersons(new String [] { "lastNameHebrew", "firstNameHebrew"},
					term, "ROLE_CONFERENCE_RESEARCHER");
			boolean first = true;
			for (Person person: persons){
				if (!first)
					sb.append(",");
				first = false;
				PersonBean personBean = new PersonBean(person);
				String listItem = "{\"label\":\"" + personBean.getFullNameHebrew() + "\",\"id\":"+personBean.getId()+"}";
				sb.append(listItem);
			}
			sb.append("]");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		
		if (type.equals("organizationUnit")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			List<OrganizationUnit> organizationUnits = organizationUnitService.getOrganizationUnits();
			for (OrganizationUnit organizationUnit: organizationUnits){
				sb.append(organizationUnit.getNameHebrew() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
		}

		if (type.equals("callForProposal")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			String localeId = request.getParameter("localeId");
			if (localeId == null)
				return;
			StringBuilder sb = new StringBuilder();
			CallForProposalSearchCreteria searchCreteria= new CallForProposalSearchCreteria();
			List<CallForProposal> callForProposals = callForProposalService.getCallForProposalsOnline(searchCreteria);
			for (CallForProposal callForProposal: callForProposals){
				sb.append(callForProposal.getTitle() + " - " + callForProposal.getId() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		
		if (type.equals("post")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			StringBuilder sb = new StringBuilder();
			PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);
			ListView listView = new ListView();
			listView.setOrderBy("sendTime desc");
			List<Post> posts = postService.getPosts(listView,null,userPersonBean);
			for (Post post: posts){
				sb.append(post.getMessageSubject() + ",,");
			}
			sb.delete(sb.length()-2, sb.length());
			PrintWriter out = response.getWriter();
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
