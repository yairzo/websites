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
			String fromDate = requestWrapper.getParameter("fromDate", "");

			StringBuffer cpb = generateCsvFileBuffer(fromDate);

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

	private static StringBuffer generateCsvFileBuffer(String fromDate) {
		StringBuffer b = new StringBuffer();
		b.append("Subject");
		b.append(',');
		b.append("Description");
		b.append(',');
		b.append("Location");
		b.append('\n');

		// get data
		Object bean = ApplicationContextProvider.getContext().getBean("conferenceProposalListService");
		ConferenceProposalListService conferenceProposalListService = (ConferenceProposalListService) bean;
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposalsByDate(fromDate);
		for (ConferenceProposal conferenceProposal : conferenceProposals) {
			ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposal);
			b.append(conferenceProposalBean.getSubject());
			b.append(',');
			b.append(conferenceProposalBean.getDescription());
			b.append(',');
			b.append(conferenceProposalBean.getLocation());
			b.append('\n');
		}
		return b;
	}
}
