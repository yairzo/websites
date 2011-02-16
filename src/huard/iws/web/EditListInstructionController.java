package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.AListInstruction;
import huard.iws.service.ListColumnInstructionService;
import huard.iws.service.ListInstructionService;
import huard.iws.service.TableDescriptionService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditListInstructionController extends GeneralFormController{

	private static final Logger logger = Logger.getLogger(EditListInstructionController.class);


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		AListInstruction aListInstruction = (AListInstruction) command;
		String action = request.getParameter("action", "");
		Map<String, Object> newModel = new HashMap<String,Object>();
		if (action.equals("cancel")){
			newModel.put("id",aListInstruction.getListId());
			return new ModelAndView( new RedirectView("list.html"),newModel);
		}
		if (aListInstruction.getId()>0){
			listInstructionService.updateListInstruction(aListInstruction);
		}
		else{
			int id = listInstructionService.insertListInstruction(aListInstruction);
			aListInstruction.setId(id);
		}
		if (aListInstruction.isMaster()){
			StringTokenizer st = new StringTokenizer(aListInstruction.getColumnsSelection(),",");
			while (st.hasMoreTokens()){
				AListColumnInstruction aListColumnInstruction = new AListColumnInstruction();
				aListColumnInstruction.setListId(aListInstruction.getListId());
				aListColumnInstruction.setColumnName(st.nextToken().trim());
				listColumnInstructionService.insertIfNotExistsListColumnInstruction(aListColumnInstruction);
			}
		}
		newModel.put("id", aListInstruction.getId());
		return new ModelAndView(new RedirectView("listInstruction.html"),newModel);
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		AListInstruction aListInstruction = (AListInstruction) model.get("command");

		if (aListInstruction.getId() == 0)
			return new ModelAndView("editListInstruction", model);


		List<String> selectsFromColumnsList = new ArrayList<String>();
		if (aListInstruction.getSelectsFrom() != null){
			String tableName = aListInstruction.getSelectsFrom();
			if (tableDescriptionService.isTableExists(tableName)){
				List<String> columns = tableDescriptionService.getColumnsList(tableName);
				for (String column : columns){
					selectsFromColumnsList.add(tableName+"."+column);
				}
			}
			else{
				model.put("noSuchTable", true);
				model.put("errTableName", tableName);
			}
			if (aListInstruction.getSubTables()!=null){
				StringTokenizer st = new StringTokenizer(aListInstruction.getSubTables(),",");
				while (st.hasMoreElements()){
					String token = st.nextToken().trim()+" ";
					tableName = token.substring(0,token.indexOf(" "));
					if (tableDescriptionService.isTableExists(tableName)){
						List<String> columns = tableDescriptionService.getColumnsList(tableName);
						for (String column : columns){
							selectsFromColumnsList.add(tableName+"."+column);
						}
					}
					else{
						model.put("noSuchTable", true);
						model.put("errSubTableName", tableName);
					}
				}
			}
		}
		model.put("selectsFromColumnsList", selectsFromColumnsList);

		logger.info("listInstructionService is null: " + listInstructionService == null);
		String mastersColumnsSelection = listInstructionService.getMasterListInstruction("listInstruction", aListInstruction.getListId(), userPersonBean.getUsername()).getColumnsSelection();

		StringTokenizer st = new StringTokenizer(mastersColumnsSelection, ",");
		List<String> columns = new ArrayList<String>();
		while (st.hasMoreTokens()){
			columns.add(st.nextToken());
		}
		model.put("mastersColumnsSelection", columns);

		return new ModelAndView ("editListInstruction", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		AListInstruction aListInstruction = new AListInstruction();

		int id = request.getIntParameter("id", 0);
		if (id > 0)
			aListInstruction = listInstructionService.getListInstruction("listInstruction", id, userPersonBean.getUsername());
		else{
			aListInstruction.setListId( request.getIntParameter("listId", 0) );
			aListInstruction.setMaster( request.getBooleanParameter("master", false));
		}
		return aListInstruction;
	}

	private ListInstructionService listInstructionService;


	public void setListInstructionService(ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}

	private ListColumnInstructionService listColumnInstructionService;

	public void setListColumnInstructionService(
			ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}

	private TableDescriptionService tableDescriptionService;


	public void setTableDescriptionService(
			TableDescriptionService tableDescriptionService) {
		this.tableDescriptionService = tableDescriptionService;
	}

}
