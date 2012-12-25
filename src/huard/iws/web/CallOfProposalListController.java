package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CallOfProposalBean;
import huard.iws.bean.SubjectBean;
import huard.iws.service.CallOfProposalService;
import huard.iws.service.MopDeskService;
import huard.iws.service.SubjectService;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.model.CallOfProposal;
import huard.iws.model.MopDesk;
import huard.iws.model.Subject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class CallOfProposalListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		CallOfProposalListControllerCommand command = (CallOfProposalListControllerCommand) model.get("command");
		List<CallOfProposal> callOfProposals = callOfProposalService.getCallsOfProposals(command.getSearchCreteria());
		List<CallOfProposalBean> callOfProposalBeans = new ArrayList<CallOfProposalBean>();
		for (CallOfProposal callOfProposal: callOfProposals){
			CallOfProposalBean callOfProposalBean = new CallOfProposalBean(callOfProposal,false);
			if(callOfProposalBean.getTitle().startsWith("###"))
				callOfProposalBean.setTitle("");
			callOfProposalBeans.add(callOfProposalBean);
		}
		model.put("callOfProposals", callOfProposalBeans);
		//desks
		List<MopDesk> mopDesks = mopDeskService.getMopDesks();
		model.put("mopDesks", mopDesks);
		//subjects
		Subject rootSubject = subjectService.getSubject(1, "iw_IL");
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, "iw_IL");
		model.put("rootSubject", rootSubjectBean);

		return new ModelAndView ("callOfProposals",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallOfProposalListControllerCommand command = new CallOfProposalListControllerCommand();
		if (isFormSubmission(request.getRequest())){//on submit
			CallForProposalSearchCreteria searchCreteria = new CallForProposalSearchCreteria();
			String sqlFromDate ="";
			String sqlFromTo ="";
			if(!request.getParameter("submissionDateFrom", "").equals("")){
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("submissionDateFrom", ""));
				formatter=new SimpleDateFormat("yyyy-MM-dd");
				sqlFromDate = formatter.format(formattedDate);
				searchCreteria.setSearchBySubmissionDateFrom(sqlFromDate);
			}
			if(!request.getParameter("submissionDateTo", "").equals("")){
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date formattedDate = (Date)formatter.parse(request.getParameter("submissionDateTo", ""));
				formatter=new SimpleDateFormat("yyyy-MM-dd");
				sqlFromTo = formatter.format(formattedDate);
				searchCreteria.setSearchBySubmissionDateTo(sqlFromTo);
			}
			searchCreteria.setSearchByTemporaryFund(request.getBooleanParameter("temporaryFund", false));
			searchCreteria.setSearchByFund(request.getIntParameter("fundId", 0));
			searchCreteria.setSearchByDesk(request.getIntParameter("deskId", 0));
			searchCreteria.setSearchBySubjectIds(request.getParameter("subjectsIdsString", ""));
			request.getSession().setAttribute("callForProposalSearchCreteria", searchCreteria);
		}
		else{//on show
			CallForProposalSearchCreteria searchCreteria = (CallForProposalSearchCreteria) request.getSession().getAttribute("callForProposalSearchCreteria");
			request.getSession().setAttribute("callForProposalsSearchCreteria", null);
			if (searchCreteria == null){// on first time
				searchCreteria = new CallForProposalSearchCreteria();
			}
			command.setSearchCreteria(searchCreteria);
		}
		return command;
	}

	public class CallOfProposalListControllerCommand{
		private CallForProposalSearchCreteria searchCreteria = new CallForProposalSearchCreteria();
		private ListView listView = new ListView();
		private String subjectsIds ="";
		
		public CallForProposalSearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(CallForProposalSearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
		public String getSubjectsIds() {
			return subjectsIds;
		}
		public void setSubjectsIds(String subjectsIds) {
			this.subjectsIds = subjectsIds;
		}

	}
	
	private CallOfProposalService callOfProposalService;

	public void setCallOfProposalService(CallOfProposalService callOfProposalService) {
		this.callOfProposalService = callOfProposalService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
	
	private SubjectService subjectService;

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	

}
