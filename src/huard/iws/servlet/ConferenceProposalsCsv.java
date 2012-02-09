package huard.iws.servlet;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.model.Committee;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
public class ConferenceProposalsCsv extends HttpServlet {
	private static final long serialVersionUID = -1;

	// private static final Logger logger = Logger.getLogger(ImageViewer.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream out = null;
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType ("application/octet-stream");
			response.setHeader("Content-disposition","attachment; filename=proposals.csv");
			
			RequestWrapper requestWrapper = new RequestWrapper(request);
			String prevdeadline = requestWrapper.getParameter("prevdeadline", "");
			StringBuffer cpb = generateCsvFileBuffer(prevdeadline);

			String cps = cpb.toString();
			byte[] file = cps.getBytes();
			int length = file.length;
			response.setContentLength(length);
			out = response.getOutputStream();
			out.write(file);
			out.flush();
			out.close();

		} catch (Exception e) {
			System.err.println(e);

		} finally {
			if (out != null)
				out.close();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req, res);
	}

	private static StringBuffer generateCsvFileBuffer(String prevdeadline) {
		StringBuffer b = new StringBuffer();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		b.append("id");
		b.append(',');
		b.append("internal no.");
		b.append(',');
		b.append("open date");
		b.append(',');
		b.append("subject");
		b.append(',');
		b.append("description");
		b.append(',');
		b.append("researcher name");
		b.append(',');
		b.append("approver name");
		b.append(',');
		b.append("from Date");
		b.append(',');
		b.append("to Date");
		b.append(',');
		b.append("location");
		b.append(',');
		b.append("location details");
		b.append(',');
		b.append("no. of foreign lecturers");
		b.append(',');
		b.append("no. of local lecturers");
		b.append(',');
		b.append("no. of audience lecturers");
		b.append(',');
		b.append("no. of foreign guests");
		b.append(',');
		b.append("no. of local guests");
		b.append(',');
		b.append("no. of audience guests");
		b.append(',');
		b.append("initiating Body");
		b.append(',');
		b.append("initiating Body Role");
		b.append(',');
		b.append("total cost");
		b.append(',');
		b.append("total cost currency");
		b.append(',');
		b.append("support sum");
		b.append(',');
		b.append("support sum currency");
		b.append(',');
		b.append("auditorium");
		b.append(',');
		b.append("seminar room");
		b.append(',');
		b.append("no. participants");
		b.append(',');
		b.append("preffered campus");
		b.append(',');
		b.append("organizing company name");
		b.append(',');
		b.append("organizing company phone");
		b.append(',');
		b.append("organizing company fax");
		b.append(',');
		b.append("organizing company email");
		b.append(',');
		b.append("contact person name");
		b.append(',');
		b.append("contact person role");
		b.append(',');
		b.append("contact person phone");
		b.append(',');
		b.append("contact person fax");
		b.append(',');
		b.append("contact person email");
		b.append(',');
		b.append("remarks");
		b.append(',');
		b.append("submitted");
		b.append(',');
		b.append("submission date");
		b.append(',');
		b.append("Approver evaluation");
		b.append(',');
		b.append("Grade");
		b.append(',');
		b.append("admin remarks");
		b.append(',');
		b.append("deadline");
		b.append(',');
		b.append("deadline remarks");
		b.append(',');
		b.append("is inside current deadline");
		b.append(',');
		b.append("committee remarks");
		b.append('\n');

		// get data
		Object bean = ApplicationContextProvider.getContext().getBean("conferenceProposalListService");
		ConferenceProposalListService conferenceProposalListService = (ConferenceProposalListService) bean;
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsByDate(prevdeadline);
		for (ConferenceProposal conferenceProposal : conferenceProposals) {
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			b.append(conferenceProposalBean.getId());
			b.append(',');
			b.append(conferenceProposalBean.getInternalId());
			b.append(',');
			b.append(formatter.format(conferenceProposalBean.getOpenDate()));
			b.append(',');
			String subject = " ";
			if (!conferenceProposalBean.getSubject().equals(""));
			subject = conferenceProposalBean.getSubject().replace(",", " ").trim();
			b.append(subject);
			b.append(',');
			String desc = " ";
			if (!conferenceProposalBean.getDescription().equals(""));
				desc = conferenceProposalBean.getDescription().replace(",", " ").trim();
			b.append(desc);
			b.append(',');
			b.append(conferenceProposalBean.getResearcher().getDegreeFullNameHebrew());
			b.append(',');
			if(	conferenceProposalBean.getApproverId()==0)
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApprover().getDegreeFullNameHebrew());
			b.append(',');
			if(conferenceProposalBean.getFromDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getFromDate()));
			else
				b.append(" ");
			b.append(',');
			if(conferenceProposalBean.getToDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getToDate()));
			else
				b.append(" ");
			b.append(',');
			String location="";
			if(conferenceProposalBean.getLocation().equals("1"))
				location = "אוניברסיטה";
			else if(conferenceProposalBean.getLocation().equals("2"))
				location = "ירושלים";
			else
				location = "אחר";
			b.append(location);
			b.append(',');
			if(conferenceProposalBean.getLocationDetail().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getLocationDetail());
			b.append(',');
			b.append(conferenceProposalBean.getForeignLecturers());
			b.append(',');
			b.append(conferenceProposalBean.getLocalLecturers());
			b.append(',');
			b.append(conferenceProposalBean.getAudienceLecturers());
			b.append(',');
			b.append(conferenceProposalBean.getForeignGuests());
			b.append(',');
			b.append(conferenceProposalBean.getLocalGuests());
			b.append(',');
			b.append(conferenceProposalBean.getAudienceGuests());
			b.append(',');
			if(conferenceProposalBean.getInitiatingBody().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getInitiatingBody());
			b.append(',');
			String initiatingBodyRole="";
			if(conferenceProposalBean.getInitiatingBodyRole()==1)
				initiatingBodyRole = "מנהל גוף";
			else if(conferenceProposalBean.getInitiatingBodyRole()==2)
				initiatingBodyRole = "עובד בגוף";
			else if(conferenceProposalBean.getInitiatingBodyRole()==3)
				initiatingBodyRole = "ראש הגוף";
			else if(conferenceProposalBean.getInitiatingBodyRole()==4)
				initiatingBodyRole = "חבר בגוף";
			else if(conferenceProposalBean.getInitiatingBodyRole()==5)
				initiatingBodyRole = "חבר ניהולי";
			b.append(initiatingBodyRole);
			b.append(',');
			b.append(conferenceProposalBean.getTotalCost());
			b.append(',');
			b.append(conferenceProposalBean.getTotalCostCurrency()==1?"שקל":"דולר");
			b.append(',');
			b.append(conferenceProposalBean.getSupportSum());
			b.append(',');
			b.append(conferenceProposalBean.getSupportCurrency()==1?"שקל":"דולר");
			b.append(',');
			b.append(conferenceProposalBean.getAuditorium());
			b.append(',');
			b.append(conferenceProposalBean.getSeminarRoom());
			b.append(',');
			b.append(conferenceProposalBean.getParticipants());
			b.append(',');
			String prefferedCampus="";
			if(conferenceProposalBean.getPrefferedCampus()==1)
				prefferedCampus = "גבעת רם";
			else if(conferenceProposalBean.getPrefferedCampus()==2)
				prefferedCampus = "הר הצופים";
			else if(conferenceProposalBean.getPrefferedCampus()==3)
				prefferedCampus = "עין כרם";
			else if(conferenceProposalBean.getPrefferedCampus()==4)
				prefferedCampus = "רחובות";
			b.append(prefferedCampus);
			b.append(',');
			if(conferenceProposalBean.getOrganizingCompanyName().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getOrganizingCompanyName());
			b.append(',');
			if(conferenceProposalBean.getOrganizingCompanyPhone().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getOrganizingCompanyPhone());
			b.append(',');
			if(conferenceProposalBean.getOrganizingCompanyFax().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getOrganizingCompanyFax());
			b.append(',');
			if(conferenceProposalBean.getOrganizingCompanyEmail().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getOrganizingCompanyEmail());
			b.append(',');
			if(conferenceProposalBean.getContactPerson().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPerson());
			b.append(',');
			if(conferenceProposalBean.getContactPersonRole().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonRole());
			b.append(',');
			if(conferenceProposalBean.getContactPersonPhone().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonPhone());
			b.append(',');
			if(conferenceProposalBean.getContactPersonFax().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonFax());
			b.append(',');
			if(conferenceProposalBean.getContactPersonEmail().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonEmail());
			b.append(',');
			if(conferenceProposalBean.getRemarks().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getRemarks().replace(",", " ").trim());
			b.append(',');
			b.append(conferenceProposalBean.getSubmitted());
			b.append(',');
			if(conferenceProposalBean.getSubmissionDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getSubmissionDate()));
			else
				b.append(" ");
			b.append(',');
			if(conferenceProposalBean.getApproverEvaluation().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApproverEvaluation());
			b.append(',');
			b.append(conferenceProposalBean.getGrade());
			b.append(',');
			String adminRemarks = " ";
			if (!conferenceProposalBean.getAdminRemarks().equals(""));
				adminRemarks = conferenceProposalBean.getAdminRemarks().replace(",", " ").trim();
			b.append(adminRemarks);
			b.append(',');
			if(conferenceProposalBean.getDeadline()>1000)
				b.append(formatter.format(conferenceProposalBean.getDeadline()));
			else
				b.append(" ");
			b.append(',');
			String deadlineRemarks = " ";
			if (!conferenceProposalBean.getDeadlineRemarks().equals(""));
				deadlineRemarks = conferenceProposalBean.getDeadlineRemarks().replace(",", " ").trim();
			b.append(deadlineRemarks);
			b.append(',');
			b.append(conferenceProposalBean.getIsInsideDeadline());
			b.append(',');
			String committeeRemarks = " ";
			if (!conferenceProposalBean.getCommitteeRemarks().equals(""));
				committeeRemarks = conferenceProposalBean.getCommitteeRemarks().replace(",", " ").trim();
			b.append(committeeRemarks);
			b.append('\n');
			bean = ApplicationContextProvider.getContext().getBean("conferenceProposalService");
			ConferenceProposalService conferenceProposalService = (ConferenceProposalService) bean;
			ConferenceProposalBean cp = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
			List<FinancialSupport> fromAssosiates = cp.getFromAssosiate();
			for(FinancialSupport financialSupport: fromAssosiates){
				if (!financialSupport.isEmpty()){
					b.append(conferenceProposalBean.getId());
					b.append(',');
					b.append("הכנסות משותפים");
					b.append(',');
					b.append(financialSupport.getName());
					b.append(',');
					b.append(financialSupport.getSum());
					b.append(',');
					b.append(financialSupport.getCurrency().equals("1")?"שקל":"דולר");
					b.append('\n');
				}
			}
			List<FinancialSupport> fromExternals = cp.getFromExternal();
			for(FinancialSupport financialSupport: fromExternals){
				if (!financialSupport.isEmpty()){
					b.append(conferenceProposalBean.getId());
					b.append(',');
					b.append("הכנסות מגוף חיצוני");
					b.append(',');
					b.append(financialSupport.getName());
					b.append(',');
					b.append(financialSupport.getSum());
					b.append(',');
					b.append(financialSupport.getCurrency().equals("1")?"שקל":"דולר");
					b.append('\n');
				}
			}
			List<FinancialSupport> fromAdmitanceFee = cp.getFromAdmitanceFee();
			for(FinancialSupport financialSupport: fromAdmitanceFee){
				if (!financialSupport.isEmpty()){
					b.append(conferenceProposalBean.getId());
					b.append(',');
					b.append("הכנסות מדמי רישום");
					b.append(',');
					b.append(financialSupport.getName());
					b.append(',');
					b.append(financialSupport.getSum());
					b.append(',');
					b.append(financialSupport.getCurrency());
					b.append('\n');
				}
			}
			List<Committee> ScientificCommittees = cp.getScientificCommittees();
			for (Committee committee: ScientificCommittees){
				if (!committee.isEmpty()){
					b.append(conferenceProposalBean.getId());
					b.append(',');
					b.append("ועדה מדעית");
					b.append(',');
					b.append(committee.getName());
					b.append(',');
					b.append(committee.getInstitute());
					b.append(',');
					b.append(committee.getInstituteRole());
					b.append(',');
					b.append(committee.getCommitteeRole());
					b.append('\n');
				}
			}
			List<Committee> OperationalCommittees = cp.getOperationalCommittees();
			for (Committee committee: OperationalCommittees){
				if (!committee.isEmpty()){
					b.append(conferenceProposalBean.getId());
					b.append(',');
					b.append("ועדה מבצעת");
					b.append(',');
					b.append(committee.getName());
					b.append(',');
					b.append(committee.getInstitute());
					b.append(',');
					b.append(committee.getInstituteRole());
					b.append(',');
					b.append(committee.getCommitteeRole());
					b.append('\n');
				}
			}
		}
		return b;
	}
}
