package huard.iws.servlet;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.model.Committee;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.ConfigurationService;
import huard.iws.service.FacultyService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;
import huard.iws.util.UserPersonUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import java.io.*;
public class ConferenceProposalsCsv extends HttpServlet {
	private static final long serialVersionUID = -1;

	// private static final Logger logger = Logger.getLogger(ImageViewer.class);
	
	private boolean isAuthorized(HttpServletRequest request){
		ApplicationContext context = ApplicationContextProvider.getContext();
		Object obj = context.getBean("personService");
		PersonService personService = (PersonService)obj;
		PersonBean userPersonBean = UserPersonUtils.getUserAsPersonBean(request, personService);
		if (userPersonBean.isAuthorized("WEBSITE", "ADMIN")) 
			return true;
		else
			return false;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (! isAuthorized(request)) return;

		ServletOutputStream out = null;
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType ("application/octet-stream");
			//response.setContentType ("text/plain");
			response.setHeader("Content-disposition","attachment; filename=proposals.csv");
			//response.setHeader("Content-disposition","attachment; filename=proposals.odt");
			
			Object obj = ApplicationContextProvider.getContext().getBean("configurationService");
			ConfigurationService configurationService = (ConfigurationService)obj;
			String prevdeadline = configurationService.getConfigurationString("conferenceProposalPrevDeadline");
			String server = configurationService.getConfigurationString("server");
			StringBuffer cpb = generateCsvFileBuffer(prevdeadline,server);

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

	private static StringBuffer generateCsvFileBuffer(String prevdeadline, String server) {
		StringBuffer b = new StringBuffer();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		b.append("line type");
		b.append('~');
		b.append("id");
		b.append('~');
		b.append("internal no.");
		b.append('~');
		b.append("open date");
		b.append('~');
		b.append("researcher name");
		b.append('~');
		b.append("researcher department");
		b.append('~');
		b.append("researcher faculty");
		b.append('~');
		b.append("researcher phone");
		b.append('~');
		b.append("researcher civilId");
		b.append('~');
		b.append("researcher email");
		b.append('~');
		b.append("initiating body");
		b.append('~');
		b.append("initiating body role");
		b.append('~');
		b.append("researcher cell phone");
		b.append('~');
		b.append("subject");
		b.append('~');
		b.append("from Date");
		b.append('~');
		b.append("to Date");
		b.append('~');
		b.append("location");
		b.append('~');
		b.append("location details");
		b.append('~');
		b.append("no. of foreign lecturers");
		b.append('~');
		b.append("no. of foreign guests");
		b.append('~');
		b.append("no. of foreign audience");
		b.append('~');
		b.append("no. of local lecturers");
		b.append('~');
		b.append("no. of local guests");
		b.append('~');
		b.append("no. of local audience");
		b.append('~');
		b.append("guests file");
		b.append('~');
		b.append("program file");
		b.append('~');
		b.append("description");
		b.append('~');
		b.append("organizing company name");
		b.append('~');
		b.append("organizing company file");
		b.append('~');
		b.append("contact person name");
		b.append('~');
		b.append("contact person role");
		b.append('~');
		b.append("contact person phone");
		b.append('~');
		b.append("contact person email");
		b.append('~');
		b.append("total cost");
		b.append('~');
		b.append("total cost currency");
		b.append('~');
		b.append("budget file");
		b.append('~');
		b.append("international flag");
		b.append('~');
		b.append("requested support sum");
		b.append('~');
		b.append("requested support sum currency");
		b.append('~');
		b.append("free room flag");
		b.append('~');
		b.append("no. participants");
		b.append('~');
		b.append("preffered campus");
		b.append('~');
		b.append("remarks");
		b.append('~');
		b.append("approver name");
		b.append('~');
		b.append("grade");
		b.append('~');
		b.append("out of");
		b.append('~');
		b.append("approver evaluation");
		b.append('~');
		b.append("admin remarks");
		b.append('~');

		/*b.append("created by");
		b.append('~');
		b.append("submitted");
		b.append('~');
		b.append("submission date");
		b.append('~');
		b.append("deadline");
		b.append('~');
		b.append("deadline remarks");
		b.append('~');
		b.append("is inside current deadline");
		b.append('~');
		b.append("committee remarks");*/
		b.append('\n');

		// get data
		Object bean = ApplicationContextProvider.getContext().getBean("conferenceProposalListService");
		ConferenceProposalListService conferenceProposalListService = (ConferenceProposalListService) bean;
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsByDate(prevdeadline);
		for (ConferenceProposal conferenceProposal : conferenceProposals) {
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			b.append("1");
			b.append('~');
			b.append(conferenceProposalBean.getId());
			b.append('~');
			b.append(conferenceProposalBean.getInternalId());
			b.append('~');
			b.append(formatter.format(conferenceProposalBean.getOpenDate()));
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getDegreeFullNameHebrew());
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getDepartment());
			b.append('~');
			FacultyService facultyService = (FacultyService)ApplicationContextProvider.getContext().getBean("facultyService");
			b.append(facultyService.getFaculty(conferenceProposalBean.getResearcher().getFacultyId()).getNameHebrew());
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getPhone());
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getCivilId());
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getEmail());
			b.append('~');
			if(conferenceProposalBean.getInitiatingBody().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getInitiatingBody());
			b.append('~');
			String initiatingBodyRole="";
			if(conferenceProposalBean.getInitiatingBodyRole()==1)
				initiatingBodyRole = "ראש/מנהל הגוף";
			else if(conferenceProposalBean.getInitiatingBodyRole()==2)
				initiatingBodyRole = "עובד/חבר בגוף";
			b.append(initiatingBodyRole);
			b.append('~');
			b.append(conferenceProposalBean.getResearcher().getCellPhone());
			b.append('~');
			String subject = " ";
			if (!conferenceProposalBean.getSubject().equals("")){
				subject = conferenceProposalBean.getSubject().trim();
				subject = subject.replace('\n', ' ');
				subject = subject.trim().replaceAll("\\s+", " ");
			}
			b.append(subject);
			b.append('~');
			if(conferenceProposalBean.getFromDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getFromDate()));
			else
				b.append(" ");
			b.append('~');
			if(conferenceProposalBean.getToDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getToDate()));
			else
				b.append(" ");
			b.append('~');
			String location="";
			if(conferenceProposalBean.getLocation().equals("1"))
				location = "בקמפוס בהר הצופים";
			else if(conferenceProposalBean.getLocation().equals("2"))
				location = "בקמפוס בגבעת רם";
			else if(conferenceProposalBean.getLocation().equals("3"))
				location = "בקמפוס בעין כרם";
			else if(conferenceProposalBean.getLocation().equals("4"))
				location = "בקמפוס ברחובות";
			else if(conferenceProposalBean.getLocation().equals("5"))
				location = "בקמפוס באילת";
			else if(conferenceProposalBean.getLocation().equals("6"))
				location = "במקום אחר";
			b.append(location);
			b.append('~');
			if(conferenceProposalBean.getLocationDetail().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getLocationDetail());
			b.append('~');
			b.append(conferenceProposalBean.getForeignLecturers());
			b.append('~');
			b.append(conferenceProposalBean.getForeignGuests());
			b.append('~');
			b.append(conferenceProposalBean.getForeignAudience());
			b.append('~');
			b.append(conferenceProposalBean.getLocalLecturers());
			b.append('~');
			b.append(conferenceProposalBean.getLocalGuests());
			b.append('~');
			b.append(conferenceProposalBean.getLocalAudience());
			b.append('~');
			if(conferenceProposalBean.getGuestsAttach().length>0)
				b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&attachFile=guestsAttach&attachmentId=1");
			else 
				b.append(" ");
			b.append('~');
			if(conferenceProposalBean.getProgramAttach().length>0)
				b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&attachFile=programAttach&attachmentId=1");
			else 
				b.append(" ");
			b.append('~');
			String desc = " ";
			if (!conferenceProposalBean.getDescription().equals("")){
				desc = conferenceProposalBean.getDescription().trim();
				desc = desc.replace('\n', ' ');
				desc = desc.trim().replaceAll("\\s+", " ");
			}
			b.append(desc);
			b.append('~');
			if(conferenceProposalBean.getOrganizingCompanyName().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getOrganizingCompanyName());
			b.append('~');
			if(conferenceProposalBean.getCompanyAttach().length>0)
				b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&attachFile=companyAttach&attachmentId=1");
			else 
				b.append(" ");
			b.append('~');
			if(conferenceProposalBean.getContactPerson().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPerson());
			b.append('~');
			if(conferenceProposalBean.getContactPersonRole().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonRole());
			b.append('~');
			if(conferenceProposalBean.getContactPersonPhone().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonPhone());
			b.append('~');
			if(conferenceProposalBean.getContactPersonEmail().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getContactPersonEmail());
			b.append('~');
			b.append(conferenceProposalBean.getTotalCost());
			b.append('~');
			b.append("דולר");
			b.append('~');
			if(conferenceProposalBean.getFinancialAttach().length>0)
				b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&attachFile=financialAttach&attachmentId=1");
			else 
				b.append(" ");
			b.append('~');
			b.append(conferenceProposalBean.getAuditorium());
			b.append('~');
			b.append(conferenceProposalBean.getSupportSum());
			b.append('~');
			b.append("דולר");
			b.append('~');
			b.append(conferenceProposalBean.getSeminarRoom());
			b.append('~');
			b.append(conferenceProposalBean.getParticipants());
			b.append('~');
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
			b.append('~');
			if(conferenceProposalBean.getRemarks().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getRemarks().trim());
			b.append('~');
			if(	conferenceProposalBean.getApproverId()==0)
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApprover().getDegreeFullNameHebrew());
			b.append('~');
			b.append(conferenceProposalBean.getGrade());
			b.append('~');
			bean = ApplicationContextProvider.getContext().getBean("conferenceProposalService");
			ConferenceProposalService conferenceProposalService = (ConferenceProposalService) bean;
			int max = conferenceProposalService.getMaxGrade(conferenceProposalBean.getApproverId(),prevdeadline);
			b.append(max);
			b.append('~');
			if(conferenceProposalBean.getApproverEvaluation().equals(""))
				b.append(" ");
			else
				b.append(conferenceProposalBean.getApproverEvaluation().trim());
			b.append('~');
			String adminRemarks = " ";
			if (!conferenceProposalBean.getAdminRemarks().equals("")){
				adminRemarks = conferenceProposalBean.getAdminRemarks().trim();
				adminRemarks = adminRemarks.replace('\n', ' ');
				adminRemarks = adminRemarks.trim().replaceAll("\\s+", " ");
			}
			b.append(adminRemarks);
			b.append('~');

			/*
			b.append(conferenceProposalBean.getCreator().getDegreeFullNameHebrew());
			b.append('~');
			b.append(conferenceProposalBean.getSubmitted());
			b.append('~');
			if(conferenceProposalBean.getSubmissionDate()>1000)
				b.append(formatter.format(conferenceProposalBean.getSubmissionDate()));
			else
				b.append(" ");
			b.append('~');
			if(conferenceProposalBean.getDeadline()>1000)
				b.append(formatter.format(conferenceProposalBean.getDeadline()));
			else
				b.append(" ");
			b.append('~');
			String deadlineRemarks = " ";
			if (!conferenceProposalBean.getDeadlineRemarks().equals(""));
				deadlineRemarks = conferenceProposalBean.getDeadlineRemarks().trim();
			b.append(deadlineRemarks);
			b.append('~');
			b.append(conferenceProposalBean.getIsInsideDeadline());
			b.append('~');
			 */
			b.append('\n');
			
			ConferenceProposalBean cp = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(conferenceProposalBean.getId()));
			List<Committee> ScientificCommittees = cp.getScientificCommittees();
			for (Committee committee: ScientificCommittees){
				if (!committee.isEmpty()){
					b.append("2");
					b.append('~');
					b.append(conferenceProposalBean.getId());
					b.append('~');
					b.append(conferenceProposalBean.getInternalId());
					b.append('~');
					b.append(formatter.format(conferenceProposalBean.getOpenDate()));
					b.append('~');
					b.append(committee.getName());
					b.append('~');
					b.append(committee.getInstitute());
					b.append('~');
					b.append(committee.getInstituteRole());
					b.append('~');
					String committeeRole="";
					if(committee.getCommitteeRole().equals("1"))
						committeeRole = "חבר";
					else if(committee.getCommitteeRole().equals("2"))
						committeeRole = "יושב ראש";
					else if(committee.getCommitteeRole().equals("3"))
						committeeRole = "לא שותף";
					b.append(committeeRole);
					b.append('~');
					String committeeRoleOrganizing="";
					if(committee.getCommitteeRoleOrganizing().equals("1"))
						committeeRoleOrganizing = "חבר";
					else if(committee.getCommitteeRoleOrganizing().equals("2"))
						committeeRoleOrganizing = "יושב ראש";
					else if(committee.getCommitteeRoleOrganizing().equals("3"))
						committeeRoleOrganizing = "לא שותף";
					b.append(committeeRoleOrganizing);
					b.append('\n');
				}
			}
			List<FinancialSupport> fromAssosiates = cp.getFromAssosiate();
			int j=0;
			for(FinancialSupport financialSupport: fromAssosiates){
				if (!financialSupport.isEmpty()){
					b.append("3");
					b.append('~');
					b.append(conferenceProposalBean.getId());
					b.append('~');
					b.append(conferenceProposalBean.getInternalId());
					b.append('~');
					b.append(formatter.format(conferenceProposalBean.getOpenDate()));
					b.append('~');
					b.append("הכנסות משותפים");
					b.append('~');
					b.append(financialSupport.getName());
					b.append('~');
					b.append(" ");
					b.append('~');
					b.append(" ");
					b.append('~');
					b.append(financialSupport.getSum());
					b.append('~');
					if(financialSupport.getReferenceFile().length>0)
						b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&assosiateId=" +j +"&attachFile=assosiateAttach&attachmentId=1");
					else
						b.append("  ");
					b.append('\n');
					j++;
				}
			}
			List<FinancialSupport> fromExternals = cp.getFromExternal();
			j=0;
			for(FinancialSupport financialSupport: fromExternals){
				if (!financialSupport.isEmpty()){
					b.append("3");
					b.append('~');
					b.append(conferenceProposalBean.getId());
					b.append('~');
					b.append(conferenceProposalBean.getInternalId());
					b.append('~');
					b.append(formatter.format(conferenceProposalBean.getOpenDate()));
					b.append('~');
					b.append("הכנסות ממממן חיצוני");
					b.append('~');
					b.append(financialSupport.getName());
					b.append('~');
					b.append(" ");
					b.append('~');
					b.append(" ");
					b.append('~');
					b.append(financialSupport.getSum());
					b.append('~');
					if(financialSupport.getReferenceFile().length>0)
						b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&externalId=" +j +"&attachFile=externalAttach&attachmentId=1");
					else
						b.append("  ");
					b.append('\n');
					j++;
				}
			}
			List<FinancialSupport> fromAdmitanceFee = cp.getFromAdmitanceFee();
			j=0;
			for(FinancialSupport financialSupport: fromAdmitanceFee){
				if (!financialSupport.isEmpty()){
					b.append("3");
					b.append('~');
					b.append(conferenceProposalBean.getId());
					b.append('~');
					b.append(conferenceProposalBean.getInternalId());
					b.append('~');
					b.append(formatter.format(conferenceProposalBean.getOpenDate()));
					b.append('~');
					b.append("הכנסות מדמי הרשמה");
					b.append('~');
					b.append("הכנסות מדמי הרשמה");
					b.append('~');
					b.append(financialSupport.getSumPerson());
					b.append('~');
					b.append(financialSupport.getName());
					b.append('~');
					b.append(financialSupport.getSum());
					b.append('~');
					if(financialSupport.getReferenceFile().length>0)
						b.append("http://"+ server +"/iws/fileViewer?conferenceProposalId="+ conferenceProposalBean.getId()+ "&admitanceFeeId=" +j +"&attachFile=admitanceFeeAttach&attachmentId=1");
					else
						b.append("  ");
					b.append('\n');
					j++;
				}
			}
			if (!conferenceProposalBean.getCommitteeRemarks().equals("")){
				StringTokenizer tk = new StringTokenizer(conferenceProposalBean.getCommitteeRemarks(),"\n");
				while (tk.hasMoreTokens()){
					String committeeRemarks=tk.nextToken();
					committeeRemarks = committeeRemarks.trim().replaceAll("\\s+", " ");
					b.append("4");
					b.append('~');
					b.append(conferenceProposalBean.getId());
					b.append('~');
					b.append(conferenceProposalBean.getInternalId());
					b.append('~');
					b.append(formatter.format(conferenceProposalBean.getOpenDate()));
					b.append('~');
					b.append(committeeRemarks.substring(0,committeeRemarks.indexOf(",")));
					b.append('~');
					b.append(committeeRemarks.substring(committeeRemarks.indexOf(",")+1,committeeRemarks.indexOf("-")));
					b.append('~');
					b.append(committeeRemarks.substring(committeeRemarks.indexOf("-")+1));
					b.append('~');
					b.append('\n');
				}
			}
		}
		return b;
	}
}
