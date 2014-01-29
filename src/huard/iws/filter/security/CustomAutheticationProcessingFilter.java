package huard.iws.filter.security;

import huard.iws.model.Person;
import huard.iws.service.HujiAuthorizationService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.MD5Encoder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.context.ApplicationContext;

public class CustomAutheticationProcessingFilter extends AuthenticationProcessingFilter{
	private static final String SAVED_REQUEST_MANDATORY_USER_DETAILS_CHANGE =
			"SAVED_REQUEST_MANDATORY_USER_DETAILS_CHANGE";
	private boolean alwaysUseDefaultTargetUrl = false;
	private String imposedTargetUrl;
	private String subscriptionInitPage;

	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
		
		request.getSession().setAttribute("userPerson", null);		
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null || ! password.matches("^[a-zA-Z0-9]{4,9}$")) {
			password = "";
		}
		
		//successful subscription process end in page welcome.html
		//welcome.html will redirect to this session stored attribute value
		subscriptionInitPage = request.getParameter("ilr");
		if (subscriptionInitPage == null){
			subscriptionInitPage = "";
		}		
		
		String moduleToSubscribe = request.getParameter("mts");
		System.out.println("module to subscribe: " + moduleToSubscribe);
		if (moduleToSubscribe == null)
			moduleToSubscribe = "";
		List<String> modulePrivileges = new ArrayList<String>();
		if (moduleToSubscribe.equals("post")){
			modulePrivileges.add("ROLE_POST_READER");
			modulePrivileges.add("ROLE_WEBSITE_READ");
		}
		else if (moduleToSubscribe.equals("conference")){
			modulePrivileges.add("ROLE_POST_READER");
			modulePrivileges.add("ROLE_WEBSITE_READ");
			modulePrivileges.add("ROLE_CONFERENCE_RESEARCHER");
		}
		
		
		if(!subscriptionInitPage.isEmpty() || request.getSession().getAttribute("titleCode") == null){
			setAuthenticationFailureUrl("/sign_in_failed");
		}
		else
			setAuthenticationFailureUrl("/login.html?login_error=1&tc="+request.getSession().getAttribute("titleCode"));
		
		String encodedPassword = MD5Encoder.digest(password);

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, encodedPassword);

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession();
		session.setAttribute(ACEGI_SECURITY_LAST_USERNAME_KEY, username);

		ApplicationContext context = ApplicationContextProvider.getContext();

		PersonService personService = (PersonService) context.getBean("personService");

		Person person = personService.getPersonByCivilId(username);

		HujiAuthorizationService hujiAuthorizationService = (HujiAuthorizationService)  context.getBean("hujiAuthorizationService");

		// We handle here 2 cases: 1. the person is not in local db than we check if it is huji authorized. 2.
		// the person is in the local db (imported persons) but do not have a user
		// notice that we call the hujiAuthorizationService only when it's a completly new person
		boolean hujiAuthorized = hujiAuthorizationService.isHujiAuthorized(username, password);
		boolean newUser = (person == null
				|| (person != null && ! personService.isSubscribed(person.getId())));
		boolean disabledUser = (person != null && personService.isDisabled(person.getId()));
		boolean yearsFirstLogin = (person != null && personService.isYearFirstLogin(person.getId()));
		boolean authenticated = (person != null && personService.authenticate(person.getId(), encodedPassword));
		boolean autoSubscribed = (person != null && personService.isAutoSubscribed(person.getId()));

		if (!disabledUser && ((hujiAuthorized && newUser) || (yearsFirstLogin && authenticated))){
			if (yearsFirstLogin)
				personService.updateLastLogin(person.getId());

			if (newUser){
				if (person == null){
					person = new Person();
					person.setCivilId(username);
					int personId = personService.insertPerson(person);
					person.setId(personId);
				}
				for (String privilege: modulePrivileges){
					personService.insertPersonPrivilege(person, privilege, false, encodedPassword, subscriptionInitPage);
				}
			}

			// keep the current authorities

			session.setAttribute("SAVED_ACEGI_SECURITY_CONTEXT", session.getAttribute("ACEGI_SECURITY_CONTEXT"));


			// replace the granted authorities
			GrantedAuthority [] grantedAuthorities = new GrantedAuthority[]{
					new GrantedAuthorityImpl("ROLE_EDIT_USER_DETAILS")
			};

			authRequest = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);

			setDetails(request, authRequest);

			//set the url to redirect to, to edit the user's details
			imposedTargetUrl = "/person.html";
			if (person != null)
				imposedTargetUrl += "?id="+person.getId();
							
			// save the curent request in the session so we'll know
			// to what url to redirect the user after she edit's her personal details

			SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute(
					AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY);

			session.setAttribute(CustomAutheticationProcessingFilter.SAVED_REQUEST_MANDATORY_USER_DETAILS_CHANGE, savedRequest);

			savedRequest = (SavedRequest) session.getAttribute("SAVED_REQUEST_MANDATORY_USER_DETAILS_CHANGE");

		}
		else if (hujiAuthorized && autoSubscribed && !authenticated ){
			// it's an existing user authorized by huji but not authenticated in this system
			// that means her huji password was changed. in this case we save the new password
			// we demand she was foremerly auto subscribed to avoid password change for manually subscribed 
			// users that has a huji password and decided to put it instead of the manually set password.
			personService.updatePersonPrivilegePassword(person, encodedPassword);
			setDetails(request, authRequest);
			authRequest = (UsernamePasswordAuthenticationToken)this.getAuthenticationManager().authenticate(authRequest);			
		}		
		else {
			// in case it's not the first visit this year continue as usual
			setDetails(request, authRequest);
			authRequest = (UsernamePasswordAuthenticationToken)this.getAuthenticationManager().authenticate(authRequest);
		}
		return authRequest;
	}



	/*
	 *  If imposedTargetUrl is not null, that's were we would like to redirect
	 *  otherwise take out the url from the request
	 */
	protected String determineTargetUrl(HttpServletRequest request) {
		// Don't attempt to obtain the url from the saved request if
		// alwaysUsedefaultTargetUrl is set
		logger.info("determineTargetUrl");
		String fullRequestUrl = obtainFullRequestUrl(request);
		logger.info("request uri "  + request.getRequestURI() + " " + request.getRequestURL());
		logger.info("full " + fullRequestUrl);
		String initiatedLoginRedirect;
		if (fullRequestUrl != null && fullRequestUrl.contains("ilr=")){
			//in case initiated login redirect was supplied
			initiatedLoginRedirect = fullRequestUrl.replaceAll("^http[s]{0,1}://.*?\\.html\\?.*?ilr=(.*?)$", "$1");
		}
		else {
			//in case ilr param was supplied by the login form
			initiatedLoginRedirect = request.getParameter("ilr");
			logger.info("ilr: " + initiatedLoginRedirect);
			if (initiatedLoginRedirect == null)
				initiatedLoginRedirect = "";
		}
		logger.info("initi " + initiatedLoginRedirect);
		String targetUrl;
		
		if (imposedTargetUrl !=null){
			targetUrl = imposedTargetUrl;
			imposedTargetUrl=null;
			logger.info("target: 1" + targetUrl) ;
		}
		else if ( !initiatedLoginRedirect.isEmpty() && !initiatedLoginRedirect.equals(fullRequestUrl)){
			if (initiatedLoginRedirect.startsWith("/"))
				initiatedLoginRedirect = initiatedLoginRedirect.replaceFirst("/", "");
			targetUrl = "/"+initiatedLoginRedirect;
			logger.info("target: 2" + targetUrl) ;
		}
		else{
			targetUrl = alwaysUseDefaultTargetUrl ? null : fullRequestUrl;
			logger.info("target: 3" + targetUrl) ;
		}

		if (targetUrl == null || targetUrl.matches("^http[s]{0,1}://.*?login.html.*?$")) {
			targetUrl = getDefaultTargetUrl();
		}
		logger.info("target url: " + targetUrl);
		return targetUrl;
	}



	public boolean isAlwaysUseDefaultTargetUrl() {
		return alwaysUseDefaultTargetUrl;
	}



	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

}