package huard.iws.web;

import huard.iws.bean.AListInstructionBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.AListInstruction;
import huard.iws.service.ListInstructionService;
import huard.iws.service.ListService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeleteListInstructionController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeleteListController.class);

	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(Object command, Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean) throws ServletException {
		AListInstructionBean aListInstructionBean = (AListInstructionBean)command;
		listInstructionService.deleteListInstruction(aListInstructionBean.getId());
		Map newModel = new HashMap();
		newModel.put("id", aListInstructionBean.getListId());
		return new ModelAndView(new RedirectView("list.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deleteListInstruction", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception
	{
		AListInstruction aListInstruction = new AListInstruction();
		int aId = request.getIntParameter("id", 0);
		if (aId > 0)
			aListInstruction = listInstructionService.getListInstruction("listInstrcution", aId, userPersonBean.getUsername());
		AListInstructionBean aListInstructionBean = new AListInstructionBean(aListInstruction);
		aListInstructionBean.setList(listService.getList("list",aListInstructionBean.getListId(), userPersonBean.getUsername()));
		return aListInstructionBean;
	}

	private ListInstructionService listInstructionService;

	public ListInstructionService getListInstructionService() {
		return listInstructionService;
	}

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}

}
