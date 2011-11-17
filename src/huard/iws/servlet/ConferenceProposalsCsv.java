package huard.iws.servlet;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.ConferenceProposalListService;
import huard.iws.util.ApplicationContextProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConferenceProposalsCsv extends HttpServlet {
	private static final long serialVersionUID = -1;

	// private static final Logger logger = Logger.getLogger(ImageViewer.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream out = null;
		try {
			//response.setContentType("application/octet-stream");
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition","attachment;filename=proposals.csv");

			StringBuffer cpb = generateCsvFileBuffer();

			String cps = cpb.toString();
			byte[] file = cps.getBytes();
			int length = cps.length();
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

	private static StringBuffer generateCsvFileBuffer() {
		StringBuffer b = new StringBuffer();

		// get data
		Object bean = ApplicationContextProvider.getContext().getBean("ConferenceProposalListService");
		ConferenceProposalListService conferenceProposalListService = (ConferenceProposalListService) bean;
		List<ConferenceProposal> conferenceProposals = conferenceProposalListService.getConferenceProposals();
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
