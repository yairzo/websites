package huard.iws.web;

import huard.iws.bean.OrganizationUnitAttributionBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.service.ListListService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditOrganizationUnitAttributionController extends GeneralFormController{

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		OrganizationUnitAttributionBean organizationUnitAttributionBean = (OrganizationUnitAttributionBean)command;
		organizationUnitService.insertOrganizationUnitAttribution(organizationUnitAttributionBean.toOrganizationUnitAttribution());

		String callerPage = request.getParameter("cp", "welcome.html");
		Map<String,Object> newModel = new HashMap<String, Object>();
		int callerPageObjectId = request.getIntParameter("cpoi", 0);
		newModel.put("id", callerPageObjectId);
		return new ModelAndView(new RedirectView(callerPage), newModel );

	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		OrganizationUnitAttributionBean organizationUnitAttributionBean = (OrganizationUnitAttributionBean) model.get("command");

		if (organizationUnitAttributionBean.getListId() == 0){
			model.put("aNewAttribution", true);
			model.put("lists", listListService.getLists(Constants.getListTypesInv().get("organization unit")));
		}
		model.put("pageName", messageService.getMessage("iw_IL.websiteInterface.organizationUnitAdd"));

		return new ModelAndView(getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		OrganizationUnitAttributionBean organizationUnitAttributionBean = new OrganizationUnitAttributionBean();
		if (!isFormSubmission(request.getRequest())){
			int organizationUnitId = request.getIntParameter("organizationUnitId", 0);
			organizationUnitAttributionBean.setOrganizationUnitId(organizationUnitId);
			int listId = request.getIntParameter("listId", 0);
			organizationUnitAttributionBean.setListId(listId);
		}
		return organizationUnitAttributionBean;
	}

	private ListListService listListService;

	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}

	private OrganizationUnitService organizationUnitService;

	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}
}
