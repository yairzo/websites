package huard.iws.web;

import huard.iws.bean.OrganizationUnitAttributionBean;
import huard.iws.bean.OrganizationUnitBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Faculty;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.OrganizationUnit.OrganizationUnitType;
import huard.iws.service.FacultyService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditOrganizationUnitController extends GeneralFormController {
	private static final Logger logger = Logger.getLogger(EditPersonController.class);



	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		OrganizationUnitBean organizationUnitBean = (OrganizationUnitBean) command;

		String action = request.getParameter("action", "");

		if (action.equals("editAttribution")){
			// Do nothing for now, there is nothing to edit on organizationUnitAttribution
		}
		else if (action.equals("deleteAttribution")){
			int organizationUnitAttributionId = request.getIntParameter("organizationUnitAttributionId", 0);
			logger.info("OrganizationAttributionId: " + organizationUnitAttributionId);
			organizationUnitService.deleteOrganizationUnitAttribution(organizationUnitAttributionId);
		}
		else{
			organizationUnitService.updateOrganizationUnit(organizationUnitBean.toOrganizationUnit());
		}

		Map <String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", organizationUnitBean.getId());
		return new ModelAndView(new RedirectView("organizationUnit.html"), newModel );

	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		OrganizationUnitBean organizationUnitBean = (OrganizationUnitBean) model.get("command");

		String action = request.getParameter("action", "");

		model.put("aNewOrganizationUnit", action.equals("open"));

		List<OrganizationUnitType> organizationUnitTypes = organizationUnitService.getOrganizationUnitTypes();
		model.put("organizationUnitTypes", organizationUnitTypes);

		List<Faculty> faculties = facultyService.getFaculties();
		model.put("faculties", faculties);

		List<OrganizationUnitAttribution> organizationUnitsAttributions = organizationUnitService.getOrganizationUnitAttributions(organizationUnitBean.getId());
		List<OrganizationUnitAttributionBean> organizationUnitAttributionsBeans = new ArrayList<OrganizationUnitAttributionBean>();
		for (OrganizationUnitAttribution organizationUnitAttribution: organizationUnitsAttributions){
			organizationUnitAttributionsBeans.add( new OrganizationUnitAttributionBean(organizationUnitAttribution));
		}
		model.put("organizationUnitAttributions", organizationUnitAttributionsBeans);
		return new ModelAndView(getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		OrganizationUnitBean organizationUnitBean = new OrganizationUnitBean();

		if (!isFormSubmission(request.getRequest())){
			int id = request.getIntParameter("id", 0);
			String action = request.getParameter("action", "");
			if (!action.equals("open")) {
				if (id > 0)
					organizationUnitBean = new OrganizationUnitBean(organizationUnitService.getOrganizationUnit(id));
			}
			else{
				id = organizationUnitService.insertOrganizationUnit();
				organizationUnitBean.setId(id);
			}
		}
		return organizationUnitBean;
	}

	private OrganizationUnitService organizationUnitService;

	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}

	private FacultyService facultyService;

	public void setFacultyService(FacultyService facultyService) {
		this.facultyService = facultyService;
	}


}
