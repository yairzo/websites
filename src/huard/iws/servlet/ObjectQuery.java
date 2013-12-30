package huard.iws.servlet;

import huard.iws.bean.CallForProposalBeanOld;
import huard.iws.bean.PersonBean;
import huard.iws.model.CallForProposalOld;
import huard.iws.model.Fund;
import huard.iws.service.CallForProposalService;
import huard.iws.service.CallForProposalServiceOld;
import huard.iws.service.FundService;
import huard.iws.service.HujiAuthorizationService;
import huard.iws.service.PersonService;
import huard.iws.service.TextualPageService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.UserPersonUtils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

public class ObjectQuery extends HttpServlet{
	//private static final Logger logger = Logger.getLogger(SelectBoxFiller.class);
	private ApplicationContext context = ApplicationContextProvider.getContext();
	private PersonService personService;
	private HujiAuthorizationService hujiAuthorizationService;
	private CallForProposalServiceOld callForProposalServiceOld;
	private FundService fundService;
	private TextualPageService textualPageService;
	private CallForProposalService callForProposalService;

	final static long serialVersionUID = 0;

	public void init(){
		Object obj = context.getBean("personService");
		personService = (PersonService)obj;
		Object obj2 = context.getBean("hujiAuthorizationService");
		hujiAuthorizationService = (HujiAuthorizationService)obj2;
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type;
		if ( (type = request.getParameter("type"))==null ) return;

		String aId;
		int id = 0;
		if ( ( aId = request.getParameter("id")) == null) return;
		if ( !(request.getParameter("id")).matches("^(?!^0)\\d{1,9}$")) return;
		id  = Integer.parseInt(aId);
		if (id == 0) return;
		
		init();
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService, hujiAuthorizationService);


		if (type.equals("callForProposal")){
			if (! userPersonBean.isAuthorized("POST", "ADMIN") && ! userPersonBean.isAuthorized("POST", "CREATOR"))
				return ;
			Object obj = context.getBean("callForProposalServiceOld");
			callForProposalServiceOld = (CallForProposalServiceOld)obj;
			CallForProposalOld callForProposal = callForProposalServiceOld.getCallForProposal(id);
			//if no such callForProposal - user entered wrong number
			if (callForProposal.getId()==0)
				return;
			
			CallForProposalBeanOld callForProposalBean = new CallForProposalBeanOld(callForProposal, true);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.print(callForProposalBean.toPostMessage());
			out.flush();
			out.close();
		}
		if (type.equals("callForProposalTitle")){
			if (! userPersonBean.isAuthorized("POST", "ADMIN") && ! userPersonBean.isAuthorized("POST", "CREATOR"))
				return ;
			Object obj = context.getBean("callForProposalServiceOld");
			callForProposalServiceOld = (CallForProposalServiceOld)obj;
			CallForProposalOld callForProposal = callForProposalServiceOld.getCallForProposal(id);
			//if no such callForProposal - user entered wrong number
			if (callForProposal.getId()==0)
				return;
			
			CallForProposalBeanOld callForProposalBean = new CallForProposalBeanOld(callForProposal, true);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.print(callForProposalBean.getTitle() + " - " + callForProposalBean.getId());
			out.flush();
			out.close();
		}
		if (type.equals("fundDesk")){
			Object obj = context.getBean("fundService");
			fundService = (FundService)obj;
			Fund fund = fundService.getFundByFinancialId(id);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(fund.getDeskId());
			out.flush();
			out.close();
		}		
		if (type.equals("textualPageCheckUrlTitle")){
			Object obj = context.getBean("textualPageService");
			textualPageService = (TextualPageService)obj;
			int count = textualPageService.countTextualPagesByUrlTitle(id,request.getParameter("title"));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(count);
			out.flush();
			out.close();
		}		
		if (type.equals("textualPageCheckTitle")){
			Object obj = context.getBean("textualPageService");
			textualPageService = (TextualPageService)obj;
			int count = textualPageService.countTextualPagesByTitle(id,request.getParameter("title"));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(count);
			out.flush();
			out.close();
		}		
		if (type.equals("callForProposalCheckTitle")){
			Object obj = context.getBean("callForProposalService");
			callForProposalService = (CallForProposalService)obj;
			int count = callForProposalService.countCallForProposalsByTitle(id,request.getParameter("title"));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(count);
			out.flush();
			out.close();
		}		
		if (type.equals("callForProposalCheckUrlTitle")){
			Object obj = context.getBean("callForProposalService");
			callForProposalService = (CallForProposalService)obj;
			int count = callForProposalService.countCallForProposalsByUrlTitle(id,request.getParameter("title"));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(count);
			out.flush();
			out.close();
		}		
	
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req,res);
	}

}
