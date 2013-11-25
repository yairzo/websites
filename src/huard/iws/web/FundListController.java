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

public class FundListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		List<Fund> funds = fundService.getNonTemporaryFunds();
		List<FundBean> fundBeans = new ArrayList<FundBean>();
		for (Fund fund: funds){
			FundBean fundBean = new FundBean(fund);
			fundBeans.add(fundBean);
		}
		model.put("funds", fundBeans);
		List<Fund> temporaryFunds = fundService.getTemporaryFunds();
		List<FundBean> temporaryFundBeans = new ArrayList<FundBean>();
		for (Fund tempfund: temporaryFunds){
			FundBean fundBean = new FundBean(tempfund);
			temporaryFundBeans.add(fundBean);
		}
		model.put("temporaryFunds", temporaryFundBeans);

		return new ModelAndView ("funds",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		FundListControllerCommand command = new FundListControllerCommand();
		return command;
	}

	public class FundListControllerCommand{
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
