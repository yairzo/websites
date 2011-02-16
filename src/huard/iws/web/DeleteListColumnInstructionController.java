package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.AListColumnInstruction;
import huard.iws.service.ListColumnInstructionService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class DeleteListColumnInstructionController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(DeleteListColumnInstructionController.class);

	public ModelAndView onSubmit(Object command, Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean) throws ServletException {
		AListColumnInstruction aListColumnInstruction = (AListColumnInstruction)command;
		listColumnInstructionService.deleteListColumnInstruction(aListColumnInstruction.getId());

		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", aListColumnInstruction.getListId());
		return new ModelAndView(new RedirectView(getSuccessView()), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView ( "deleteListColumnInstruction", model);
	}




	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception
	{
		AListColumnInstruction aListColumnInstruction = new AListColumnInstruction();
		int aId = request.getIntParameter("id", 0);
		if (aId > 0)
			aListColumnInstruction = listColumnInstructionService.getListColumnInstruction("listColumnInstrcution", aId, userPersonBean.getUsername());
		return aListColumnInstruction;
	}

	ListColumnInstructionService listColumnInstructionService;


	public void setListColumnInstructionService(
			ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}

}
