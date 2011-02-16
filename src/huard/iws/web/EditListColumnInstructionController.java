package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.AListColumnInstruction;
import huard.iws.service.ListColumnInstructionService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditListColumnInstructionController extends GeneralFormController{

	//private static final Logger logger = Logger.getLogger(EditListColumnInstructionController.class);



	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		AListColumnInstruction aListColumnInstruction = (AListColumnInstruction) command;
		Map<String, Object> newModel = new HashMap<String, Object>();

		String action = request.getParameter("action", "");
		if (action.equals("cancel")){
			newModel.put("id", aListColumnInstruction.getListId());
			return new ModelAndView(new RedirectView("list.html"), newModel);
		}



		if (aListColumnInstruction.getId()>0){
			listColumnInstructionService.updateListColumnInstruction(aListColumnInstruction);
		}
		else{
			listColumnInstructionService.insertListColumnInstruction(aListColumnInstruction);
		}

		newModel.put("id", aListColumnInstruction.getId());
		return new ModelAndView(new RedirectView("listColumnInstruction.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{
		return new ModelAndView( "editListColumnInstruction", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		AListColumnInstruction aListColumnInstruction = new AListColumnInstruction();


		if (! isFormSubmission(request.getRequest())){
			int id = request.getIntParameter("id", 0);
			if (id > 0)
				aListColumnInstruction = listColumnInstructionService.getListColumnInstruction("listColumnInstruction",
						id, userPersonBean.getUsername());

			int listId = request.getIntParameter("listId", 0);
			if (listId > 0)
				aListColumnInstruction.setListId(listId);


			boolean hidden = request.getBooleanParameter("hidden", false);
			if (hidden)
				aListColumnInstruction.setHidden(true);
		}
		return aListColumnInstruction;
	}

	private ListColumnInstructionService listColumnInstructionService;


	public void setListColumnInstructionService(ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}

}
