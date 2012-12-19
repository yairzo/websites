package huard.iws.servlet;

import huard.iws.bean.CallOfProposalBeanOld;
import huard.iws.bean.PersonBean;
import huard.iws.model.CallOfProposalOld;
import huard.iws.model.Fund;
import huard.iws.service.CallOfProposalServiceOld;
import huard.iws.service.PersonService;
import huard.iws.service.FundService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.UserPersonUtils;

import java.io.IOException;

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
	private CallOfProposalServiceOld callOfProposalServiceOld;
	private FundService fundService;

	final static long serialVersionUID = 0;

	public void init(){
		Object obj = context.getBean("personService");
		personService = (PersonService)obj;
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type;
		if ( (type = request.getParameter("type"))==null ) return;

		String aId;
		if ( ( aId = request.getParameter("id")) == null) return;
		if ( !(request.getParameter("id")).matches("^(?!^0)\\d{1,9}$")) return;
		
		int id = 0;
		id  = Integer.parseInt(aId);
		if (id == 0) return;

		init();
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService);


		if (type.equals("callOfProposal")){
			if (! userPersonBean.isAuthorized("POST", "ADMIN") && ! userPersonBean.isAuthorized("POST", "CREATOR"))
				return ;
			Object obj = context.getBean("callOfProposalServiceOld");
			callOfProposalServiceOld = (CallOfProposalServiceOld)obj;
			CallOfProposalOld callOfProposal = callOfProposalServiceOld.getCallOfProposal(id);
			//if no such callOfProposal - user entered wrong number
			if (callOfProposal.getId()==0)
				return;
			
			CallOfProposalBeanOld callOfProposalBean = new CallOfProposalBeanOld(callOfProposal, true);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(callOfProposalBean.toString());
			out.flush();
			out.close();
		}
		if (type.equals("callOfProposalTitle")){
			if (! userPersonBean.isAuthorized("POST", "ADMIN") && ! userPersonBean.isAuthorized("POST", "CREATOR"))
				return ;
			Object obj = context.getBean("callOfProposalServiceOld");
			callOfProposalServiceOld = (CallOfProposalServiceOld)obj;
			CallOfProposalOld callOfProposal = callOfProposalServiceOld.getCallOfProposal(id);
			//if no such callOfProposal - user entered wrong number
			if (callOfProposal.getId()==0)
				return;
			
			CallOfProposalBeanOld callOfProposalBean = new CallOfProposalBeanOld(callOfProposal, true);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(callOfProposalBean.getTitle() + " - " + callOfProposalBean.getId());
			out.flush();
			out.close();
		}
		if (type.equals("fundDesk")){
			Object obj = context.getBean("fundService");
			fundService = (FundService)obj;
			Fund fund = fundService.getFundByFinancialId(id);
System.out.println("111111111111111111:"+fund.getDeskId());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream out = response.getOutputStream();
			out.print(fund.getDeskId());
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
