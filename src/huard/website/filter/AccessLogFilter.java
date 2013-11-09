package huard.website.filter;

import huard.website.baseDb.ConnectionSupplier;
import huard.website.baseDb.ManagedConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessLogFilter implements Filter {
	protected FilterConfig filterConfig = null;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		long startTime = System.currentTimeMillis();
		TestResponse responseWrapper = new TestResponse(
				(HttpServletResponse) response);
		
		long stopTime = System.currentTimeMillis();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURL().toString();
		if (isContentQuery(url)) {
			ManagedConnection connection = ConnectionSupplier
					.getConnectionSupplier().getConnection("HUARD", "UPDATE");
			try {
				Statement statement = connection.createStatement();

				String remoteAddress = httpServletRequest.getRemoteAddr();
				if (!remoteAddress
						.matches("[\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+"))
					remoteAddress = "127.0.0.1";
				long ipAsLong = Long.parseLong(remoteAddress.replaceAll("\\.",
						""));
				Enumeration e = httpServletRequest.getHeaderNames();
				long processingTime = stopTime - startTime;
				String referer = "";
				String browser = "";
				int status = responseWrapper.getStatus();
				for (; e.hasMoreElements();) {
					String headerName = "" + e.nextElement();
					// System.out.println(headerName+"
					// "+httpServletRequest.getHeader(headerName));
					if (headerName.equals("user-agent"))
						browser = httpServletRequest.getHeader(headerName);
					if (headerName.equals("referer"))
						referer = httpServletRequest.getHeader(headerName);
				}
				String query = "INSERT AccessLog SET ip=\""
						+ remoteAddress + "\", ipAsLong=\"" + ipAsLong + "\","
						+ " time=\"" + startTime + "\", processingTime=\""
						+ processingTime + "\", url=\"" + url + "\","
						+ " status=\"" + status + "\", referer=\"" + referer
						+ "\", browser=\"" + browser + "\";";
				System.out.println(query);
				statement.executeUpdate(query);
				statement.close();
			} catch (SQLException e) {
				System.out.println("filters.AccessLogFilter.doFilter(): " + e);
			}
			ConnectionSupplier.getConnectionSupplier().archiveConnection(
					connection, "HUARD");
			
		}
		try{
			chain.doFilter(request, responseWrapper);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private boolean isContentQuery(String url){
		return (url.endsWith("jsp") || url.matches("^.*htm[l]{0,1}$"));
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
