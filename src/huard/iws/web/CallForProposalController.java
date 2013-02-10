package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.CategoryBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Category;
import huard.iws.model.Fund;
import huard.iws.service.CallForProposalService;
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
		CallForProposalBean callForProposalBean = (CallForProposalBean)command;
	
		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", callForProposalBean.getId())	;
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		CallForProposalBean callForProposalBean = (CallForProposalBean) model.get("command");

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
		LanguageUtils.applyLanguage(model, request, response,callForProposalBean.getLocaleId());
		LanguageUtils.applyLanguages(model);
		//page title
		model.put("pageTitle", callForProposalBean.getTitle());

		//dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (callForProposalBean.getPublicationTime()==0)
			model.put("publicationTime", "");
		else
			model.put("publicationTime", formatter.format(callForProposalBean.getPublicationTime()));
		if (callForProposalBean.getFinalSubmissionTime()==0)
			model.put("finalSubmissionTime", "");
		else
			model.put("finalSubmissionTime", formatter.format(callForProposalBean.getFinalSubmissionTime()));
		if (callForProposalBean.getUpdateTime()==0)
			model.put("updateTime", "");
		else
			model.put("updateTime", formatter.format(callForProposalBean.getUpdateTime()));

		//extra submission dates
		Date tmpDate = new Date();
		int i=1;
		if(callForProposalBean.getSubmissionDates()!=null){
			for(Long submissionDate: callForProposalBean.getSubmissionDates()){
				tmpDate = new Date(submissionDate);
				String submissionName="submissionDate" + i;
				model.put(submissionName, formatter.format(tmpDate));
				i++;
			}
		}
		//funds
		String selectedFund="";
		if(callForProposalBean.getFundId()>0){
			Fund fund = fundService.getFundByFinancialId(callForProposalBean.getFundId());
			if (fund!=null)
				selectedFund=fund.getName();
		}
		model.put("selectedFund", selectedFund);
		//stripped fields
		String stripped =callForProposalBean.getFundingPeriod().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedFundingPeriod", stripped);
		stripped =callForProposalBean.getAmountOfGrant().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedAmountOfGrant", stripped);
		stripped =callForProposalBean.getEligibilityRequirements().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedEligibilityRequirements", stripped);
		stripped =callForProposalBean.getActivityLocation().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedActivityLocation()", stripped);
		stripped =callForProposalBean.getPossibleCollaboration().replaceAll("<[/]{0,1}p.*?>","");
		model.put("strippedPossibleCollaboration()", stripped);

		model.put("id",callForProposalBean.getId());
		return new ModelAndView ( this.getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallForProposalBean callForProposalBean = new CallForProposalBean();

		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
			
		if ( isFormSubmission(request.getRequest()) 
				|| id == 0)
			return callForProposalBean;
		
		callForProposalBean = new CallForProposalBean(callForProposalService.getCallForProposalOnline(id),true);
		logger.info("callForProposalBean id: " + callForProposalBean.getId());
		
		return callForProposalBean;
	}

	
	private CallForProposalService callForProposalService;

	public void setCallForProposalService(CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
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
