package huard.iws.util;

import huard.iws.bean.PersonBean;
import huard.iws.model.Person;
import huard.iws.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContext;

public class UserPersonUtils {
	//private static final Logger logger = Logger.getLogger(UserPersonUtils.class);

	public static PersonBean getUserAsPersonBean(HttpServletRequest request, PersonService personService){
		HttpSession session = request.getSession();
		//We try to get the person from the session
		PersonBean personBean = (PersonBean)session.getAttribute("userPerson");
		//We check if it's an anonymous user
		boolean anonymous = false;
		if (personBean == null)
			anonymous = true;
		else
			anonymous = personBean.getPrivileges().contains("ROLE_LISTS_ANONYMOUS");

		if (anonymous){
			//in case it's a real anonymous
			if (personBean != null){
				personBean.getPrivileges().clear();
			}
			//collect the username and authorities from acegi attr in the session
			String username = (String)session.getAttribute("ACEGI_SECURITY_LAST_USERNAME");
			SecurityContext sc = (SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT");
			Person aPerson = null;
			// It may be an anonymous user with no username
			if (username != null && username.length() == 8){
				aPerson = personService.getPersonByCivilId(username);
				// For the case it's a subscribing user
				if (aPerson == null){
					aPerson = new Person();
					aPerson.setCivilId(username);
				}
			}

			// For the case it's an anonymous user
			if (aPerson == null)
				aPerson = new Person();
			personBean = new PersonBean(aPerson);
			//if it has authorities
			if (sc != null)
				personBean.setPersonPriviliges(sc.getAuthentication().getAuthorities());
			//if no authorities let's give him anonymous authorities
			else
				personBean.setPersonPriviliges( new GrantedAuthority [] {
						new GrantedAuthorityImpl("ROLE_LISTS_ANONYMOUS")
				});
		}
		session.setAttribute("userPerson", personBean);
		return personBean;
	}

	public static boolean isNeedEditDetails(RequestWrapper request){
		HttpSession session = request.getSession();
		SecurityContext sc = (SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT");
		return sc.getAuthentication().getAuthorities()[0].equals("ROLE_EDIT_USER_DETAILS");
	}
}
