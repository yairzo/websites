package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.FundBean;
import huard.iws.service.FundService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;
import huard.iws.model.Fund;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class TemporaryFundListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{


		List<Fund> temporaryFunds = fundService.getTemporaryFunds();
		List<FundBean> temporaryFundBeans = new ArrayList<FundBean>();
		for (Fund temporaryFund: temporaryFunds){
			FundBean temporaryFundBean = new FundBean(temporaryFund);
			temporaryFundBeans.add(temporaryFundBean);
		}
		model.put("temporaryFunds", temporaryFundBeans);

		return new ModelAndView ("temporaryFunds",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TemporaryFundListControllerCommand command = new TemporaryFundListControllerCommand();
		return command;
	}

	public class TemporaryFundListControllerCommand{
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
	
	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

}
