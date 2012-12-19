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

public class EditTemporaryFundController extends GeneralFormController {
	private static final Logger logger = Logger.getLogger(EditPersonController.class);



	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		FundBean fundBean = (FundBean) command;

		String action = request.getParameter("action", "");

		if (action.equals("edit")){
			fundService.updateFund(fundBean.toFund());
		}

		Map <String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", fundBean.getId());
		return new ModelAndView(new RedirectView("temporaryFund.html"), newModel );

	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		int id = request.getIntParameter("id", 0);
		if (request.getParameter("action", "").equals("new") || id == 0){
			Fund fund= new Fund();
			fund.setTemporary(true);
			fund.setHtml("");
			int maxId=fundService.getMaxFinancialIdForTemporary();
			System.out.println("1111111111111111:"+maxId);
			fund.setFinancialId(fundService.getMaxFinancialIdForTemporary());
			int temporaryFundId = fundService.insertFund(fund);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",temporaryFundId);
			return new ModelAndView ( new RedirectView("temporaryFund.html"), newModel);
		}
		else{//show edit
			FundBean fundBean = (FundBean) model.get("command");
			//desks
			List<MopDesk> mopDesks = mopDeskService.getMopDesks();
			model.put("mopDesks", mopDesks);
			model.put("id",fundBean.getId());
			return new ModelAndView ( this.getFormView(), model);
		}	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		FundBean fundBean = new FundBean();
		int id = request.getIntParameter("id", 0);
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return fundBean;
		
		fundBean = new FundBean(fundService.getFund(id));
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
