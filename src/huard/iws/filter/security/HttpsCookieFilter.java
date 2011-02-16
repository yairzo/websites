package huard.iws.filter.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpsCookieFilter implements Filter {


	public void destroy () {}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {


		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final HttpSession session = httpRequest.getSession(false);

		if (session != null) {
			final Cookie sessionCookie = new Cookie("JSESSIONID", session
					.getId());
			sessionCookie.setMaxAge(-1);
			sessionCookie.setSecure(false);
			sessionCookie.setPath(httpRequest.getContextPath());
			httpResponse.addCookie(sessionCookie);
		}

		chain.doFilter(request, response);
	}


	public void init(FilterConfig arg0) throws ServletException {}

}
