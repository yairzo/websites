package huard.iws.servlet;

import huard.iws.constant.Constants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionVariablesHandler extends HttpServlet{

	final static long serialVersionUID = 0;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String value = request.getParameter("value");
		String action = request.getParameter("action");

		HttpSession session = request.getSession();

		if (action !=null && action.equals("moveOn"))
			moveTabsOneStepForward(session, name);



		if (value!=null && ! value.isEmpty()){
			System.out.print("Name: "+name + " Value: " + value);
			session.setAttribute(name, value);
		}
		else{
			value = (String) session.getAttribute(name);
			if (value==null){
				value=Constants.getTabs().get(1);
				session.setAttribute(name, value);
				System.out.print("Name: "+name + " Value: " + value);
			}
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.setHeader("Pragma",  "no-cache");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(""+value);
			out.flush();
			out.close();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req,res);
	}

	private void moveTabsOneStepForward(HttpSession session, String name){
		String currentTab = (String)session.getAttribute(name);
		int tabId = Constants.getTabsInv().get(currentTab);
		tabId++;
		session.setAttribute(name, Constants.getTabs().get(tabId));
	}

}
