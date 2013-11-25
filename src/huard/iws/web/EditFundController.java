package huard.iws.web;

import huard.iws.bean.FundBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.service.MopDeskService;
import huard.iws.service.FundService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditFundController extends GeneralFormController {
	private static final Logger logger = Logger.getLogger(EditPersonController.class);



	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		FundBean fundBean = (FundBean) command;
		Map <String,Object> newModel = new HashMap<String, Object>();

		String action = request.getParameter("action", "");

		if (action.equals("edit")){
			fundService.updateFund(fundBean.toFund());
		}
		if (action.equals("delete")){
			fundService.deleteFund(fundBean.getFinancialId());
			return new ModelAndView(new RedirectView("funds.html"), newModel );
		}

		newModel.put("id", fundBean.getFinancialId());
		return new ModelAndView(new RedirectView("fund.html"), newModel );

	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		int id = request.getIntParameter("id", 0);
		if (request.getParameter("action", "").equals("new") || id == 0){
			Fund fund= new Fund();
			if(request.getBooleanParameter("temporary", false)){
				fund.setTemporary(true);
				fund.setFinancialId(fundService.getMaxFinancialIdForTemporary());
			}
			int key=fundService.insertFund(fund);
			if(fund.getFinancialId()==0)
				fund.setFinancialId(key);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",fund.getFinancialId());
			return new ModelAndView ( new RedirectView("fund.html"), newModel);
		}
		else{//show edit
			FundBean fundBean = (FundBean) model.get("command");
			//desks
			List<MopDesk> mopDesks = mopDeskService.getMopDesks();
			model.put("mopDesks", mopDesks);
			model.put("id",fundBean.getFinancialId());
			return new ModelAndView ( this.getFormView(), model);
		}
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		FundBean fundBean = new FundBean();
		int id = request.getIntParameter("id", 0);
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return fundBean;

		fundBean = new FundBean(fundService.getFundByFinancialId(id));
		return fundBean;
	}

	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}


}
