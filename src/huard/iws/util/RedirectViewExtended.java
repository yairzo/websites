package huard.iws.util;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;

public class RedirectViewExtended extends RedirectView{
		
	public RedirectViewExtended() {
		super();
	}
	
	public RedirectViewExtended(String url) {
		super(url);
	}
		
        @Override
        protected void sendRedirect(HttpServletRequest request,
                        HttpServletResponse response, String targetUrl,
                        boolean http10Compatible) throws IOException {
				response.setStatus(301);
				response.setHeader("Location", response.encodeRedirectURL(targetUrl));        
		}

}