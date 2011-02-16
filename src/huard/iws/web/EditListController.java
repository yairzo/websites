package huard.iws.web;


import huard.iws.bean.AListBean;
import huard.iws.bean.AListColumnInstructionBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.AList;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.AListInstruction;
import huard.iws.service.ListColumnInstructionListService;
import huard.iws.service.ListColumnInstructionService;
import huard.iws.service.ListInstructionListService;
import huard.iws.service.ListInstructionService;
import huard.iws.service.ListListService;
import huard.iws.service.ListService;
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

public class EditListController extends GeneralFormController {

	private static final Logger logger = Logger.getLogger(EditListController.class);

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		AListBean aListBean = (AListBean) command;

		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", aListBean.getId());

		String redirectUrl="list.html";

		int moveDown = request.getIntParameter("moveDown", -1);
		 if (moveDown >= 0 ){
			listService.moveSublistDown(aListBean, moveDown);
			redirectUrl = redirectUrl.concat("#bottom");
		}
		 int moveUp = request.getIntParameter("moveUp", -1);
		 if (moveUp >= 0 ){
			listService.moveSublistUp(aListBean, moveUp);
			//redirectUrl = redirectUrl.concat("#bottom");
		}

		 int delete = request.getIntParameter("delete", -1);
		 if (delete >= 0 ){
			listService.deleteSublist(aListBean, delete, request);
			//redirectUrl = redirectUrl.concat("#bottom");
		}

		String action = request.getParameter("action", "");
		if (action.equals("cancel")){
			return new ModelAndView( new RedirectView("lists.html"),newModel);
		}
		if (action.equals("edit") && aListBean.getListInstructionId()>0){
			newModel.put("id",aListBean.getListInstructionId());
			return new ModelAndView( new RedirectView("listInstruction.html"),newModel);
		}
		else if (action.equals("delete") && aListBean.getListInstructionId()>0){
			newModel.put("id",aListBean.getListInstructionId());
			return new ModelAndView( new RedirectView("deleteListInstruction.html"),newModel);
		}
		else if (action.equals("editColumn") && aListBean.getListColumnInstructionId()>0){
			newModel.put("id",aListBean.getListColumnInstructionId());
			return new ModelAndView( new RedirectView("listColumnInstruction.html"),newModel);
		}
		else if (action.equals("deleteColumn") && aListBean.getListColumnInstructionId()>0){
			newModel.put("id",aListBean.getListColumnInstructionId());
			return new ModelAndView( new RedirectView("deleteListColumnInstruction.html"),newModel);
		}
		else if (action.equals("addSublist") && aListBean.getSublistId() > 0){
			AList addedSublist = listService.getList(aListBean.getSublistId());
			addedSublist.setLocation(aListBean.getSublists().size() +1);
			aListBean.getSublists().add(addedSublist);
			listService.updateList(aListBean.toAList());
			AListBean addedSublistBean = new AListBean(addedSublist, request);
			for (AListColumnInstructionBean columnInstructionBean: addedSublistBean.getColumnBeans()){
				columnInstructionBean.setListId(aListBean.getId());
				listColumnInstructionService.insertListColumnDesign(columnInstructionBean.toAListColumnInstruction());
			}
			listService.insertListDesign(addedSublist.getId(), aListBean.getId());
			return new ModelAndView(new RedirectView(redirectUrl),newModel);
		}

		else{
			if (aListBean.getId()==0) {
				int r = listService.insertList(aListBean.toAList());
				aListBean.setId(r);
				listService.insertListDesign(aListBean.getId(), 0);
				newModel.put("listId",r);
			}
			else{
				listService.updateList(aListBean.toAList());
			}
			if (! aListBean.isCompound() && listInstructionListService.getListInstructions(aListBean.getId()).isEmpty()){
				newModel.put("master", true);
				return new ModelAndView(new RedirectView("listInstruction.html"),newModel);
			}
			return new ModelAndView(new RedirectView(redirectUrl),newModel);
		}
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception{

		AListBean aListBean = (AListBean) model.get("command");
		if (aListBean.getId() > 0 && ! aListBean.isCompound()){
			List<AListInstruction> listInstructions = listInstructionListService.getListInstructions(aListBean.getId());
			model.put("listInstructions", listInstructions);

			List<AListColumnInstruction> listColumnInstructions = listColumnInstructionListService.getListColumnInstructions(aListBean.getId());


			List <AListColumnInstructionBean> listColumnInstructionBeans = new ArrayList<AListColumnInstructionBean>();
			AListInstruction masterListInstruction = listInstructionService.getMasterListInstruction("listInstruction",
					aListBean.getId(), userPersonBean.getUsername());
			for (AListColumnInstruction columnInst: listColumnInstructions){
				AListColumnInstructionBean aListColumnInstructionBean = new AListColumnInstructionBean(columnInst);
				aListColumnInstructionBean.setActual(false);
				StringTokenizer st = new StringTokenizer(masterListInstruction.getColumnsSelection(),",");
				while (st.hasMoreTokens()){
					if (st.nextToken().trim().equals(aListColumnInstructionBean.getColumnName())) aListColumnInstructionBean.setActual(true);
				}
				listColumnInstructionBeans.add(aListColumnInstructionBean);
			}
			for (AListColumnInstructionBean aListColumnInstructionBean: listColumnInstructionBeans){
				logger.info(aListColumnInstructionBean.getColumnName());
			}
			model.put("columnInstructions", listColumnInstructionBeans);
		}
		else if (aListBean.isCompound()){
			List<AList> lists = listListService.getUnboundLists(aListBean.toAList());
			model.put("lists", lists);
		}
		List<AListBean.ListType> listTypes = listService.getListTypes(request);
		model.put("listTypes", listTypes);
		return new ModelAndView("editList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		AList aList = new AList();
		int id = request.getIntParameter("id", 0);
		if (id ==0 )
			id = request.getIntParameter("listId", 0);
		if (id > 0)
			aList = listService.getList("list", id, userPersonBean.getUsername());

		AListBean aListBean  = new AListBean(aList, request);
		return aListBean;
	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}

	private ListListService listListService;

	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}

	private ListInstructionListService listInstructionListService;

	public void setListInstructionListService(ListInstructionListService listInstructionListService) {
		this.listInstructionListService = listInstructionListService;
	}

	private ListColumnInstructionListService listColumnInstructionListService;

	public void setListColumnInstructionListService(
			ListColumnInstructionListService listColumnInstructionListService) {
		this.listColumnInstructionListService = listColumnInstructionListService;
	}

	private ListColumnInstructionService listColumnInstructionService;

	public void setListColumnInstructionService(
			ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}


	private ListInstructionService listInstructionService;

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}


}
