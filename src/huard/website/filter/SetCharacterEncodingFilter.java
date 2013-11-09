package huard.website.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class SetCharacterEncodingFilter implements Filter {



    protected FilterConfig filterConfig = null;

    // --------------------------------------------------------- Public Methods


    /**
     * Take this filter out of service.
     */
    public void destroy() {

        this.filterConfig = null;

    }



    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException {

    // Set the character encoding to be used
       request.setCharacterEncoding("UTF-8");
       response.setContentType("text/html; charset=UTF-8");
	// Pass control on to the next filter
        
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
     * @param filterConfig The filter configuration object
     */
    public void init(FilterConfig filterConfig) throws ServletException {

	this.filterConfig = filterConfig;

    }


}
