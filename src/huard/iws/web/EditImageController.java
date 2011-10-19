package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;
import huard.iws.service.PageBodyImageService;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditImageController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command, Map<String, Object> model,
			RequestWrapper request, PersonBean userPersonBean) throws Exception {
		String action = request.getParameter("action", "");

		if (action.equals("cancel")){
			return new ModelAndView( new RedirectView("welcome.html"));
		}
		else if (action != null && action.equals("delete")) {
			String id = request.getParameter("imageId", "");
			pageBodyImageService.deletePageBodyImage(Integer.parseInt(id));
		}
		else if (action != null && action.equals("approve")) {
			String id = request.getParameter("imageId", "");
			pageBodyImageService.approvePageBodyImage(Integer.parseInt(id));
		} 
		
		return new ModelAndView(new RedirectView("uploadImage.html"));
	}

	protected ModelAndView onShowForm(RequestWrapper request,
			HttpServletResponse response, PersonBean userPersonBean,
			Map<String, Object> model) throws Exception {

		return new ModelAndView(this.getFormView(), model);
	}

	protected Object getFormBackingObject(RequestWrapper requestWrapper,
			PersonBean userPersonBean) throws Exception {
		PageBodyImageBean pageBodyImageBean = new PageBodyImageBean();

		return pageBodyImageBean;
	}

	private PageBodyImageService pageBodyImageService;

	public void setPageBodyImageService(
			PageBodyImageService pageBodyImageService) {
		this.pageBodyImageService = pageBodyImageService;
	}


}
