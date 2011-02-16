package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.AList;
import huard.iws.service.ListService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeleteListController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeleteListController.class);

	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(Object command, Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean) throws ServletException {
		AList aList = (AList)command;
		listService.deleteList(aList.getId());
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deleteList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception
	{
		AList aList = new AList();
		int aId = request.getIntParameter("id", 0);
		if (aId > 0){
			aList = listService.getList("list", aId, userPersonBean.getUsername());
		}
		return aList;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}
}
