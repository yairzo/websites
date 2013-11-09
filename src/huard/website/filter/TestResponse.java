package huard.website.filter;
import java.io.IOException;
import javax.servlet.http.*;

public class TestResponse extends HttpServletResponseWrapper {
	private int statusCode;
	public TestResponse(HttpServletResponse response) {
		super(response);
		statusCode=200;
	}
	public int getStatus() {
		return statusCode;
	}
	public void sendError(int errorCode) throws IOException {
		this.statusCode = errorCode;
		super.sendError(errorCode);
	}
	public void sendError(int errorCode, String errorMessage) throws IOException {
		this.statusCode = errorCode;
		super.sendError(errorCode, errorMessage);
	}
	public void setStatus(int statusCode) {
		this.statusCode = statusCode;
		super.setStatus(statusCode);
	}
}

