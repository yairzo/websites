package huard.iws.web;

import huard.iws.bean.OrganizationUnitBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.OrganizationUnit;
import huard.iws.service.OrganizationUnitService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeleteOrganizationUnitController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeleteOrganizationUnitController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		OrganizationUnitBean organizationUnitBean = (OrganizationUnitBean) command;
		organizationUnitService.deleteOrganizationUnit(organizationUnitBean.getId());
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deleteOrganizationUnit", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		OrganizationUnitBean organizationUnitBean = new OrganizationUnitBean();
		int id = request.getIntParameter("id", 0);

		if (id > 0){
			OrganizationUnit organizationUnit = organizationUnitService.getOrganizationUnit(id);
			organizationUnitBean = new OrganizationUnitBean(organizationUnit);
		}
		return organizationUnitBean;
	}

	private OrganizationUnitService organizationUnitService;

	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}
}