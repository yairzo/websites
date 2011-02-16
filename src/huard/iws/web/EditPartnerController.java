package huard.iws.web;

import huard.iws.bean.PartnerBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Institute;
import huard.iws.service.InstituteListService;
import huard.iws.service.InstituteService;
import huard.iws.service.PartnerService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditPartnerController extends GeneralFormController {
	//private static final Logger logger = Logger.getLogger(EditPersonController.class);



	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		PartnerBean partnerBean = (PartnerBean) command;
		LinkedHashMap<String,Institute> institutes = instituteListService.getInstitutesMap();
		String instituteName;
		int instituteId = 0;
		boolean needsInstituteEdit = false;
		if (( instituteName = partnerBean.getInstituteName()) !=null){
			Institute institute = institutes.get( instituteName);
			if (institute != null){
				partnerBean.setInstituteId(institute.getId());
			}
			else{
				institute = new Institute();
				institute.setName(partnerBean.getInstituteName());
				instituteId = instituteService.insertInstitute(institute);
				partnerBean.setInstituteId(instituteId);
				needsInstituteEdit = true;
			}
		}
		partnerService.updatePartner(partnerBean.toPartner());
		Map newModel = new HashMap();
		if (needsInstituteEdit){
			model.put("id", instituteId);
			return new ModelAndView(new RedirectView("institute.html"), newModel);
		}
		else{
			model.put("id", partnerBean.getId());
			return new ModelAndView(new RedirectView("partner.html"), newModel );
		}
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		LinkedHashMap institutesNamesMap = instituteListService.getInstitutesMap();
		model.put("institutes", institutesNamesMap);
		return new ModelAndView(getFormView(), model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		PartnerBean partnerBean = new PartnerBean();
		int id = request.getIntParameter("id", 0);
		if (id > 0)
			partnerBean = new PartnerBean(partnerService.getPartner(id));

		if (partnerBean.getInstituteId() > 0){
			partnerBean.setInstituteName(instituteService.getInstitute(partnerBean.getInstituteId()).getName());
		}
		return partnerBean;
	}

	private InstituteListService instituteListService;


	public void setInstituteListService(InstituteListService instituteListService) {
		this.instituteListService = instituteListService;
	}

	private PartnerService partnerService;


	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	private InstituteService instituteService;


	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}



}
