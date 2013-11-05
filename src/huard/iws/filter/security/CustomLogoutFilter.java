package huard.iws.filter.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class CustomLogoutFilter implements Filter{


    private static final Logger logger = Logger.getLogger(CustomLogoutFilter.class);

    private String filterProcessesUrl = "/j_acegi_logout";
    private String logoutSuccessUrl;
    private LogoutHandler[] handlers;

    //~ Constructors ===================================================================================================

    public CustomLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
        Assert.hasText(logoutSuccessUrl, "LogoutSuccessUrl required");
        Assert.notEmpty(handlers, "LogoutHandlers are required");
        this.logoutSuccessUrl = logoutSuccessUrl;
        this.handlers = handlers;
    }

    //~ Methods ========================================================================================================

    /**
     * Not used. Use IoC container lifecycle methods instead.
     */
    public void destroy() {
    }



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (requiresLogout(httpRequest, httpResponse)) {
			String titleCode="0";
			if(httpRequest.getSession().getAttribute("titleCode")!=null)
					titleCode=httpRequest.getSession().getAttribute("titleCode").toString();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (logger.isDebugEnabled()) {
				logger.debug("Logging out user '" + auth + "' and redirecting to logout page");
			}
			for (int i = 0; i < handlers.length; i++) {
				handlers[i].logout(httpRequest, httpResponse, auth);
			}
			httpRequest.getSession().removeAttribute("userPerson");
			httpRequest.getSession().removeAttribute("SAVED_REQUEST_MANDATORY_USER_DETAILS_CHANGE");
			
			if(request.getParameter("s")!=null && request.getParameter("s").equals("1")){
				logoutSuccessUrl="/homePage.html";
			}else
				logoutSuccessUrl="/welcome.html?tc="+titleCode;//override security.xml
			
			sendRedirect(httpRequest, httpResponse, logoutSuccessUrl);
			return;
		}

		chain.doFilter(request, response);
	}

	/**
     * Not used. Use IoC container lifecycle methods instead.
     *
     * @param arg0 ignored
     *
     * @throws ServletException ignored
     */
    public void init(FilterConfig arg0) throws ServletException {
	}

    /**
     * Allow subclasses to modify when a logout should take place.
     *
     * @param request the request
     * @param response the response
     *
     * @return <code>true</code> if logout should occur, <code>false</code> otherwise
     */
    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything from the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        int queryParamIndex = uri.indexOf('?');

        if (queryParamIndex > 0) {
            // strip everything from the first question mark
            uri = uri.substring(0, queryParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

    /**
     * Allow subclasses to modify the redirection message.
     *
     * @param request  the request
     * @param response the response
     * @param url      the URL to redirect to
     *
     * @throws IOException in the event of any failure
     */
    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
            throws IOException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = request.getContextPath() + url;
        }

        response.sendRedirect(response.encodeRedirectURL(url));
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        Assert.hasText(filterProcessesUrl, "FilterProcessesUrl required");
        this.filterProcessesUrl = filterProcessesUrl;
    }

    protected String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

}
