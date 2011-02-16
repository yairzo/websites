package huard.iws.web;

import huard.iws.bean.InstituteBean;
import huard.iws.bean.PersonBean;
import huard.iws.service.InstituteService;
import huard.iws.service.UniverseService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditInstituteController extends GeneralFormController {
	//private static final Logger logger = Logger.getLogger(EditPersonController.class);


	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		InstituteBean instituteBean = (InstituteBean) command;
		instituteService.updateInstitute(instituteBean.toInstitute());
		Map newModel = new HashMap();
		newModel.put("id", instituteBean.getId());
		return new ModelAndView(new RedirectView("institute.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{

		InstituteBean instituteBean = (InstituteBean) model.get("command");
		model.put("continents",universeService.getContinents());
		model.put("countries",universeService.getCountries(instituteBean.getContinentId()));
		return new ModelAndView(getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		InstituteBean instituteBean = new InstituteBean();
		int id = request.getIntParameter("id", 0);
		if (id > 0)
			instituteBean = new InstituteBean(instituteService.getInstitute(id));
		return instituteBean;
	}

	private InstituteService instituteService;

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	private UniverseService universeService;

	public void setUniverseService(UniverseService universeService) {
		this.universeService = universeService;
	}

}
