package huard.iws.web;

import huard.iws.bean.CategoryBean;
import huard.iws.bean.CallOfProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.service.CallOfProposalService;
import huard.iws.service.CategoryService;
import huard.iws.service.FundService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class CallForProposalController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		CallOfProposalBean callOfProposalBean = (CallOfProposalBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callOfProposalBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		CallOfProposalBean callOfProposalBean = (CallOfProposalBean) model.get("command");

		//top categories
		Category rootCategory = categoryService.getRootCategory();
		List <Category> languageRootCategories = categoryService.getCategories(rootCategory.getId());
		List <CategoryBean> languageRootCategoryBeans = new ArrayList<CategoryBean>();
		for (Category category: languageRootCategories){
			languageRootCategoryBeans.add( new CategoryBean (category));
		}
		model.put("languageRootCategories", languageRootCategoryBeans);
		//category
		model.put("category",categoryService.getCategory(rootCategory.getId()));
		//language
		LanguageUtils.applyLanguage(model, request, response,callOfProposalBean.getLocaleId());
		LanguageUtils.applyLanguages(model);
		//page title
		model.put("pageTitle", callOfProposalBean.getTitle());

		//dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (callOfProposalBean.getPublicationTime()==0)
			model.put("publicationTime", "");
		else
			model.put("publicationTime", formatter.format(callOfProposalBean.getPublicationTime()));
		if (callOfProposalBean.getFinalSubmissionTime()==0)
			model.put("finalSubmissionTime", "");
		else
			model.put("finalSubmissionTime", formatter.format(callOfProposalBean.getFinalSubmissionTime()));
		if (callOfProposalBean.getUpdateTime()==0)
			model.put("updateTime", "");
		else
			model.put("updateTime", formatter.format(callOfProposalBean.getUpdateTime()));

		//extra submission dates
		Date tmpDate = new Date();
		int i=1;
		if(callOfProposalBean.getSubmissionDates()!=null){
			for(Long submissionDate: callOfProposalBean.getSubmissionDates()){
				tmpDate = new Date(submissionDate);
				String submissionName="submissionDate" + i;
				model.put(submissionName, formatter.format(tmpDate));
				i++;
			}
		}
		//funds
		String selectedFund="";
		if(callOfProposalBean.getFundId()>0){
			Fund fund = fundService.getFundByFinancialId(callOfProposalBean.getFundId());
			if (fund!=null)
				selectedFund=fund.getName();
		}
		model.put("selectedFund", selectedFund);
		//stripped fields
		String stripped =callOfProposalBean.getFundingPeriod().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedFundingPeriod", stripped);
		stripped =callOfProposalBean.getAmountOfGrant().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedAmountOfGrant", stripped);
		stripped =callOfProposalBean.getEligibilityRequirements().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedEligibilityRequirements", stripped);
		stripped =callOfProposalBean.getActivityLocation().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedActivityLocation()", stripped);
		stripped =callOfProposalBean.getPossibleCollaboration().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedPossibleCollaboration()", stripped);

		model.put("id",callOfProposalBean.getId());
		return new ModelAndView ( this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallOfProposalBean callOfProposalBean = new CallOfProposalBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
			
		if ( isFormSubmission(request.getRequest()) 
				|| id == 0)
			return callOfProposalBean;
		
		callOfProposalBean = new CallOfProposalBean(callOfProposalService.getCallOfProposalOnline(id),true);
		logger.info("callOfProposalBean id: " + callOfProposalBean.getId());
		
		return callOfProposalBean;
	}

	
	private CallOfProposalService callOfProposalService;

	public void setCallOfProposalService(CallOfProposalService callOfProposalService) {
		this.callOfProposalService = callOfProposalService;
	}
	

	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

}
