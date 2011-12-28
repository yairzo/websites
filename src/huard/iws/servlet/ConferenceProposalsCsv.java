package huard.iws.servlet;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;

import java.io.IOException;
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
			String deadline = requestWrapper.getParameter("deadline", "");
			StringBuffer cpb = generateCsvFileBuffer(deadline);

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

	private static StringBuffer generateCsvFileBuffer(String deadline) {
		StringBuffer b = new StringBuffer();
		b.append("id");
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
		b.append('\n');

		// get data
		Object bean = ApplicationContextProvider.getContext().getBean("conferenceProposalListService");
		ConferenceProposalListService conferenceProposalListService = (ConferenceProposalListService) bean;
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsByDate(deadline);
		for (ConferenceProposal conferenceProposal : conferenceProposals) {
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			b.append(conferenceProposalBean.getId());
			b.append(',');
			b.append(conferenceProposalBean.getOpenDate());
			b.append(',');
			b.append(conferenceProposalBean.getSubject().trim());
			b.append(',');
			String desc = " ";
			if (!conferenceProposalBean.getDescription().equals(""));
				conferenceProposalBean.getDescription().replace(",", " ").trim();
			b.append(desc);
			b.append(',');
			b.append(conferenceProposalBean.getResearcher().getDegreeFullNameHebrew());
			b.append(',');
			if(	conferenceProposalBean.getApproverId()==0)
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApprover().getDegreeFullNameHebrew());
			b.append(',');
			b.append(conferenceProposalBean.getFromDate());
			b.append(',');
			b.append(conferenceProposalBean.getToDate());
			b.append(',');
			b.append(conferenceProposalBean.getLocation());
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
			b.append(conferenceProposalBean.getInitiatingBodyRole());
			b.append(',');
			b.append(conferenceProposalBean.getTotalCost());
			b.append(',');
			b.append(conferenceProposalBean.getTotalCostCurrency());
			b.append(',');
			b.append(conferenceProposalBean.getSupportSum());
			b.append(',');
			b.append(conferenceProposalBean.getSupportCurrency());
			b.append(',');
			b.append(conferenceProposalBean.getAuditorium());
			b.append(',');
			b.append(conferenceProposalBean.getSeminarRoom());
			b.append(',');
			b.append(conferenceProposalBean.getParticipants());
			b.append(',');
			b.append(conferenceProposalBean.getPrefferedCampus());
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
				b.append(conferenceProposalBean.getRemarks());
			b.append(',');
			b.append(conferenceProposalBean.getSubmitted());
			b.append(',');
			b.append(conferenceProposalBean.getSubmissionDate());
			b.append(',');
			if(conferenceProposalBean.getApproverEvaluation().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApproverEvaluation());
			b.append(',');
			b.append(conferenceProposalBean.getGrade());
			b.append(',');
			if(conferenceProposalBean.getAdminRemarks().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getAdminRemarks());
			b.append(',');
			b.append(conferenceProposalBean.getDeadline());
			b.append('\n');
		}
		return b;
	}
}
