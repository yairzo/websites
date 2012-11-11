package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.CallOfProposalBean;
import huard.iws.service.CallOfProposalService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;
import huard.iws.model.CallOfProposal;

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
		//PageListControllerCommand aCommand = (PageListControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		//PageListControllerCommand command = (PageListControllerCommand) model.get("command");

		List<CallOfProposal> callOfProposals = callOfProposalService.getCallsOfProposals();
		List<CallOfProposalBean> callOfProposalBeans = new ArrayList<CallOfProposalBean>();
		for (CallOfProposal callOfProposal: callOfProposals){
			CallOfProposalBean callOfProposalBean = new CallOfProposalBean(callOfProposal,false);
			if(callOfProposalBean.getTitle().startsWith("###"))
				callOfProposalBean.setTitle("");
			callOfProposalBeans.add(callOfProposalBean);
		}
		model.put("callOfProposals", callOfProposalBeans);

		return new ModelAndView ("callOfProposals",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallOfProposalListControllerCommand command = new CallOfProposalListControllerCommand();
		return command;
	}

	public class CallOfProposalListControllerCommand{
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();

		public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}

	}
	
	private CallOfProposalService callOfProposalService;

	public void setCallOfProposalService(CallOfProposalService callOfProposalService) {
		this.callOfProposalService = callOfProposalService;
	}

}
