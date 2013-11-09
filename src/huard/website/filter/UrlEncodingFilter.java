package huard.website.filter;

import huard.website.util.LanguageUtils;
import huard.website.util.UrlUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlEncodingFilter implements Filter {
	protected FilterConfig filterConfig = null;

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {

		this.filterConfig = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String params = httpServletRequest.getQueryString();
		String url = httpServletRequest.getRequestURL().toString();
		
		
		
		//if (url.endsWith("pubPageViewer.jsp")) {
			if (params == null)
				params = "";						
			
			String newParams = "";
			for (char c : params.toCharArray()) {
				char newChar = c;
				if ((int) c >= 224 && (int) c <= 248) {
					newChar = (char) ((int) c - 224 + 1488);
				}
				newParams += "" + newChar;
			}
			url = url + newParams;
			System.out.println(url);
			if (LanguageUtils.textContainsHebrew(url)) {
				url = UrlUtils.encodeUrlParams(url);
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect(url);
			}
		//}
		try{
			chain.doFilter(request, response);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Place this filter into service.
	 *
	 * @param filterConfig
	 *            The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
