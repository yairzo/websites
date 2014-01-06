package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;
import huard.iws.model.PageBodyImage;
import huard.iws.service.PageBodyImageService;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ImageActionsController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command, Map<String, Object> model,
			RequestWrapper request, PersonBean userPersonBean) throws Exception {
		String action = request.getParameter("action", "");

		int mainImageId = request.getIntParameter("id", 0);

		if (action.equals("cancel")){
			return new ModelAndView( new RedirectView("welcome.html"));
		}
		else if (action != null && action.equals("delete")) {
			String ids = request.getParameter("imageIds","");
			StringTokenizer tk  = new StringTokenizer(ids,",");
			while (tk.hasMoreTokens()){
				String tkn= tk.nextToken();
				if(tkn.isEmpty())
					continue;
				int id = new Integer(tkn).intValue();
				pageBodyImageService.deletePageBodyImage(id);
				if(id==mainImageId)
					mainImageId=pageBodyImageService.getMaxImageId();
			}
		}
		else if (action != null && action.equals("approve")) {
			String ids = request.getParameter("imageIds","");
			StringTokenizer tk  = new StringTokenizer(ids,",");
			while (tk.hasMoreTokens()){
				String tkn= tk.nextToken();
				if(tkn.isEmpty())
					continue;
				int id = new Integer(tkn).intValue();
				pageBodyImageService.approvePageBodyImage(id);
			}
		}
		else if (action != null && action.equals("disapprove")) {
			String ids = request.getParameter("imageIds","");
			StringTokenizer tk  = new StringTokenizer(ids,",");
			while (tk.hasMoreTokens()){
				String tkn= tk.nextToken();
				if(tkn.isEmpty())
					continue;
				int id = new Integer(tkn).intValue();
				pageBodyImageService.disapprovePageBodyImage(id);
			}
		}
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id",mainImageId);

		return new ModelAndView ( new RedirectView("uploadImage.html"), newModel);
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
