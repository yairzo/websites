package huard.iws.web;

import huard.iws.bean.AListBean;
import huard.iws.bean.AListColumnInstructionBean;
import huard.iws.bean.AListDesignBean;
import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.model.AList;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.Person;
import huard.iws.service.ListColumnInstructionService;
import huard.iws.service.ListService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ViewListController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		ViewListControllerCommand aCommand = (ViewListControllerCommand) command;
		Map<String, Object> newModel = new HashMap<String, Object>();


		int printEdition = request.getIntParameter("p", 0);
		if (printEdition ==1){
			newModel.put("p", 1);
		}

		int iframeView = request.getIntParameter("iv", 0);
		if (iframeView ==1){
			newModel.put("iv", 1);
		}

		int editMode = request.getIntParameter("em", 0);
		if (editMode ==1){
			newModel.put("em", 1);
		}

		AList list = listService.getList(aCommand.getId());
		AListBean listBean = new AListBean(list, request);
		if (listBean.isCompound() && aCommand.getSublistId() > 0){
			AList sublist = listService.getList(aCommand.getSublistId());
			// the listBean now turns to be the sublist with column parameters that fits the parent (compound) list
			listBean = new AListBean(sublist, list.getId(), request);
		}

		if (aCommand.getAction().endsWith("Column")){

			AListColumnInstructionBean columnInstructionBean = listBean.getColumnBeans().get(aCommand.getColumnIndex());

			if (aCommand.getAction().indexOf("bold") == 0)
				columnInstructionBean.setBold(!columnInstructionBean.isBold());
			else if (aCommand.getAction().indexOf("nobr") == 0){
				columnInstructionBean.setNoBr(!columnInstructionBean.isNoBr());
			}
			else if (aCommand.getAction().indexOf("align") == 0){
				columnInstructionBean.advanceNextAlignmentMode();
			}

			else{
				int widthChange = aCommand.getAction().indexOf("wide") == 0 ? 2 : 0;
				if (widthChange == 0)
					widthChange = aCommand.getAction().indexOf("narrow") == 0
					&& columnInstructionBean.getWidth() - 2 > 5 ? -2 : 0;
					columnInstructionBean.setWidth(columnInstructionBean.getWidth() + widthChange);
			}
			if (!list.isCompound())
				listColumnInstructionService.updateListColumnInstruction(columnInstructionBean.toAListColumnInstruction());
			else
				listColumnInstructionService.updateListColumnInstruction(
						columnInstructionBean.toAListColumnInstruction(), list.getId());
		}
		else if (aCommand.getAction().equals("alignDisplayName")){

			AListDesignBean listDesignBean = listBean.getListDesign();
			listDesignBean.advanceNextAlignmentMode();
			listService.updateListDesign(listDesignBean.toListDesign());
		}
		else if (aCommand.getAction().endsWith("BottomPadding")){
			AListDesignBean listDesignBean = listBean.getListDesign();
			int changeAmount = aCommand.getAction().startsWith("increase") ? 10 : -10;
			listDesignBean.setBottomPadding(Math.max(listDesignBean.getBottomPadding() + changeAmount, 5));
			listService.updateListDesign(listDesignBean.toListDesign());
		}
		else if (aCommand.getAction().equals("showTableHeader")){
			AListDesignBean listDesignBean = listBean.getListDesign();
			listDesignBean.setShowTableHeader(! listDesignBean.isShowTableHeader());
			listService.updateListDesign(listDesignBean.toListDesign());
		}
		else if (aCommand.getAction().equals("addEntity")){
			String addedEntityDetails = aCommand.getAddedEntity();
			if (list.getListTypeId() == Constants.getListTypesInv().get("person")){
				Person person = personService.getPerson(addedEntityDetails, new String [] { "lastNameHebrew", "firstNameHebrew", "email" } );
				newModel.put("personId", person.getId());
				newModel.put("listId", listBean.getId());
				newModel.put("cp", "viewList.html");
				newModel.put("cpoi", listBean.getId());
				return new ModelAndView( new RedirectView ("personAttribution.html"), newModel);
			}
			if (list.getListTypeId() == Constants.getListTypesInv().get("organization unit")){
				OrganizationUnit organizationUnit = organizationUnitService.getOrganizationUnit(addedEntityDetails);
				OrganizationUnitAttribution organizationUnitAttribution = new OrganizationUnitAttribution();
				organizationUnitAttribution.setOrganizationUnitId(organizationUnit.getId());
				organizationUnitAttribution.setListId(list.getId());
				organizationUnitService.insertOrganizationUnitAttribution(organizationUnitAttribution);
				newModel.put("id", list.getId());
				return new ModelAndView( new RedirectView ("viewList.html"), newModel);

			}





		}
		else if (aCommand.getAction().equals("updatePreface")){
			String preface = aCommand.getPreface();
			listBean.setPreface(preface);
			listService.updateList(listBean.toAList());
		}

		else if (aCommand.getAction().equals("updateFooter")){
			String footer = aCommand.getFooter();
			listBean.setFooter(footer);
			listService.updateList(listBean.toAList());
		}

		newModel.put("id", aCommand.getId());
		return new ModelAndView(new RedirectView("viewListAdmin.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		int id = request.getIntParameter("id", 0);
		AList list = listService.getList(id);

		LanguageUtils.applyLanguage(model, request, response, list.getName());

		if (! ( userPersonBean.isAuthorized("LISTS", "ADMIN")
				|| userPersonBean.isAuthorized("LISTS", "EDITOR")
				|| userPersonBean.isAuthorized("LISTS", "MOP")
				|| userPersonBean.isAuthorized("LISTS", "READER")
				|| ( userPersonBean.isAuthorized("LISTS", "ANONYMOUS") && list.isOpen())))
			return new ModelAndView(new RedirectView("login.html?login_error=2"));

		AListBean listBean = new AListBean(list, request);

		int orderColumn;
		if (! list.isCompound()){
			orderColumn = request.getIntParameter("oc", -1);
			if (listBean.getListTypeId() == Constants.getListTypesInv().get("person")){
				listBean.initPersonAttributionBeans(orderColumn,0);
				model.put("listType", "person");
			}
			else if (listBean.getListTypeId() == Constants.getListTypesInv().get("organization unit")){
				listBean.initOrganizationalUnitBeans(orderColumn,0,"");
				model.put("listType", "organization unit");
			}
			listBean.initColumnsInstructionBeans(0);
			LanguageUtils.applyLanguage(model, request, response, listBean.getDisplayName());
		}

		if (listBean.isCompound()){
			model.put("list", listBean);
			model.put("aCompoundView", true);
		}
		else{
			model.put("listBean", listBean);
		}

		int printEdition = request.getIntParameter("p", 0);
		model.put("print", printEdition == 1);

		int iframeView = request.getIntParameter("iv", 0);
		model.put("iframeView", iframeView ==1 || userPersonBean.isAuthorized("LISTS", "ANONYMOUS"));

		int ajaxView = request.getIntParameter("a", 0);
		model.put("ajaxView", ajaxView == 1);

		int anEditMode = request.getIntParameter("em", 0);
		boolean editModeAuthorized = (userPersonBean.isAuthorized("LISTS", "ADMIN") ||
				userPersonBean.isAuthorized("LISTS", "EDITOR"));
		model.put("editModeAuthorized", editModeAuthorized);
		boolean editMode =  editModeAuthorized && anEditMode==1;
		model.put("editMode", editMode);

		return new ModelAndView("viewList", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		ViewListControllerCommand command = new ViewListControllerCommand();
		int id = request.getIntParameter("id", 0);
		command.setId(id);
		return command;
	}

	public class ViewListControllerCommand{
		private int id;
		private int sublistId;
		private String action;
		private int columnIndex;
		private String addedEntity;
		private String preface;
		private String footer;


		public String getFooter() {
			return footer;
		}
		public void setFooter(String footer) {
			this.footer = footer;
		}
		public String getPreface() {
			return preface;
		}
		public void setPreface(String preface) {
			this.preface = preface;
		}
		public String getAddedEntity() {
			return addedEntity;
		}
		public void setAddedEntity(String addedEntity) {
			this.addedEntity = addedEntity;
		}
		public int getColumnIndex() {
			return columnIndex;
		}
		public void setColumnIndex(int columnIndex) {
			this.columnIndex = columnIndex;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public int getSublistId() {
			return sublistId;
		}
		public void setSublistId(int sublistId) {
			this.sublistId = sublistId;
		}

	}

	private ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}

	private ListColumnInstructionService listColumnInstructionService;

	public void setListColumnInstructionService(
			ListColumnInstructionService listColumnInstructionService) {
		this.listColumnInstructionService = listColumnInstructionService;
	}

	private OrganizationUnitService organizationUnitService;

	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}





}
