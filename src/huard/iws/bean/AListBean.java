package huard.iws.bean;


import huard.iws.model.AList;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.IListControllerCommand;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListColumnInstructionListService;
import huard.iws.service.ListService;
import huard.iws.service.MessageService;
import huard.iws.service.OrganizationUnitService;
import huard.iws.service.PersonAttributionListService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.LanguageUtils;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;

public class AListBean extends BasicBean implements IListControllerCommand{
	protected static final Logger logger = Logger.getLogger(AListBean.class);
	private int id;
	private String name;
	private String displayName;
	private boolean displayNameAligned;
	private String owner;
	private boolean sendMailToListEnabled;
	private boolean sortEnabled;
	private boolean compound;
	private boolean isPublic;
	private boolean isOpen;
	private int listTypeId;
	private String preface;
	private String footer;
	private Timestamp lastUpdate;

	private int [] listEditableAttributionsTypes = new int [] {1};

	private int listInstructionId;
	private int listColumnInstructionId;

	private MessageService messageService = (MessageService) ApplicationContextProvider.getContext().getBean("messageService");

	private ListService listService = (ListService) ApplicationContextProvider.getContext().getBean("listService");

	private ListColumnInstructionListService columnsService = (ListColumnInstructionListService) ApplicationContextProvider.getContext().getBean("listColumnInstructionListService");
	private List<AListColumnInstructionBean> columnBeans;

	private PersonAttributionListService personAttributionListService = (PersonAttributionListService) ApplicationContextProvider.getContext().getBean("personAttributionListService");
	private OrganizationUnitService organizationUnitService = (OrganizationUnitService) ApplicationContextProvider.getContext().getBean("organizationUnitService");

	private LinkedHashSet<PersonListAttributionBean> personAttribBeans;
	private List<OrganizationUnitBean> organizationUnitBeans;

	private SearchCreteria searchCreteria;
	private ListView listView;


	private int sublistId;

	private List<AList> sublists;
	private List<AListBean> sublistsBeans;

	private AListDesignBean listDesign;
	
	public static int LIST_TYPE_PERSON = 1;
	public static int LIST_TYPE_ORGANIZATION_UNIT = 2;

	// static final Logger logger = Logger.getLogger(AListBean.class);


	public AListBean (RequestWrapper request){
		super (request);
	}

	public AListBean(AList aList, RequestWrapper request){
		this(aList, 0, request);
	}

	public AListBean(AList aList, int parentListId){
		this(aList, parentListId, null);
	}

	public AListBean(AList aList, int parentListId, RequestWrapper request){
		super(request);
		this.id = aList.getId();
		this.name = aList.getName();
		this.displayName = aList.getDisplayName();
		this.displayNameAligned = aList.isDisplayNameAligned();
		this.owner = aList.getOwner();
		this.sendMailToListEnabled = aList.isSendMailToListEnabled();
		this.sortEnabled = aList.isSortEnabled();
		this.compound = aList.isCompound();
		this.isPublic = aList.isPublic();
		this.isOpen = aList.isOpen();
		this.listTypeId = aList.getListTypeId();
		this.sublists = aList.getSublists();
		this.preface = aList.getPreface();
		this.footer = aList.getFooter();
		this.lastUpdate = new Timestamp(aList.getLastUpdate());
		String filter="";
		if(request!=null && request.getSession().getAttribute("filterOrganizationUnit")!=null)
			filter=(String)request.getSession().getAttribute("filterOrganizationUnit");
		init(parentListId,filter);
	}

	public AList toAList(){
		AList aList = new AList();
		aList.setId(id);
		aList.setName(name);
		aList.setDisplayName(displayName);
		aList.setDisplayNameAligned(displayNameAligned);
		aList.setOwner(owner);
		aList.setSendMailToListEnabled(sendMailToListEnabled);
		aList.setSortEnabled(sortEnabled);
		aList.setCompound(compound);
		aList.setPublic(isPublic);
		aList.setOpen(isOpen);
		aList.setListTypeId(listTypeId);
		aList.setSublists(sublists);
		aList.setPreface(preface);
		aList.setFooter(footer);
		aList.setLastUpdate(lastUpdate.getTime());
		return aList;
	}

	public boolean isEditableAttribution(){
		for (Integer i: listEditableAttributionsTypes){
			if (listTypeId == i)
				return true;
		}
		return false;
	}

	public boolean isHasPreface(){
		return preface != null && preface.trim().length() > 0
			&& ! messageService.equals(getLocale() + ".iws.general.addText", preface);
	}

	public boolean isHasFooter(){
		return footer != null && footer.trim().length() > 0
		&& ! messageService.equals(getLocale() + ".iws.general.addText", footer);
	}

	public String getHorizontalDirection(){
		return LanguageUtils.getLanguage(this.name).getDir();
	}


	public String getViewableColumnsCount(){
		int i=0;
		if (columnBeans==null){
			initColumnsInstructionBeans(0);
		}
		for (AListColumnInstructionBean column: columnBeans){
			 if (! column.isHidden()){
				 i++;
			 }
		 }
		return ""+i;
	}

	public String getEmails(){
		if (personAttribBeans==null){
			initPersonAttributionBeans(1, 0,"");
		}
		StringBuilder emails= new StringBuilder();
		int counter = 0;
		for (PersonListAttributionBean personAttribBean: personAttribBeans){
			if (personAttribBean.getEmail().length() > 0) {
				if (counter > 0)
					emails.append(";");
				emails.append(personAttribBean.getEmail().trim());
			}
			else{
				emails.append(personAttribBean.getPerson().getEmail().trim());
			}
			counter++;
		}
		return emails.toString();
	}

	public List<IListViewableBean> getViewableBeans(){
		List<IListViewableBean> viewableBeans = new ArrayList<IListViewableBean>();
		if (this.listTypeId == listService.getListTypeInv("person")){
			viewableBeans.addAll(personAttribBeans);
		}
		else if (this.listTypeId == listService.getListTypeInv("organization unit")){
			viewableBeans.addAll(organizationUnitBeans);
		}
		return viewableBeans;
	}

	public int getMaxViewableColumnIndex(){
		int maxViewableColumnIndex = 0;
		for (int i=0; i < columnBeans.size() ; i++){
			if ( ! columnBeans.get(i).isHidden())
				maxViewableColumnIndex = i ;
		}
		return maxViewableColumnIndex;
	}

	public void init(int parentListId,String filter){
		if (this.listTypeId == listService.getListTypeInv("person"))
			initPersonAttributionBeans(-1,parentListId,filter);
		else if (this.listTypeId == listService.getListTypeInv("organization unit"))
			initOrganizationalUnitBeans(-1,parentListId,filter);
		initColumnsInstructionBeans(parentListId);
		initListDesign(parentListId);
	}

		/*public void initPersonAttributionBeans(int parentListId){
			initPersonAttributionBeans(-1, parentListId);
		}*/

	public void initPersonAttributionBeans(int orderColumn, int parentListId, String filter){
		personAttribBeans = new LinkedHashSet<PersonListAttributionBean>();
		List<PersonListAttribution> personAttribs;
		if (this.isCompound()){
			personAttribs = personAttributionListService.getPersonAttributions(id);
		}
		else{
			if (orderColumn>-1){
				personAttribs = personAttributionListService.getPersonAttributionsByListId(id, orderColumn, filter);
			}
			else{
				personAttribs = personAttributionListService.getPersonAttributionsByListId(id, filter);
			}
		}
		for (PersonListAttribution personAttrib :personAttribs){
			personAttribBeans.add(new PersonListAttributionBean(personAttrib, parentListId));
		}
	}

	public void initOrganizationalUnitBeans(int orderColumn, int parentListId, String filter){
		List<OrganizationUnit> organizationUnits;
		if (orderColumn>-1){
			organizationUnits = organizationUnitService.getOrganizationUnits(this.id, orderColumn,filter);
		}
		else{
			organizationUnits = organizationUnitService.getOrganizationUnits(this.id,filter);
		}
		organizationUnitBeans = new ArrayList<OrganizationUnitBean>();
		for (OrganizationUnit organizationUnit: organizationUnits){
			OrganizationUnitBean organizationUnitBean = new OrganizationUnitBean(organizationUnit, this.id, parentListId);
			organizationUnitBeans.add(organizationUnitBean);
		}
	}

	public void initColumnsInstructionBeans(int parentListId){
		columnBeans = new ArrayList<AListColumnInstructionBean>();
		List<AListColumnInstruction> columns = columnsService.getListColumnInstructions(id, parentListId);
		for (AListColumnInstruction column: columns ){
			columnBeans.add(new AListColumnInstructionBean(column));
		}
	}

	public void initListDesign(int parentListId){
		if (this.id > 0){
			listDesign = new AListDesignBean(listService.getListDesign(this.id, parentListId));
		}
		else
			listDesign = new AListDesignBean();
	}

	public int getListInstructionId() {
		return listInstructionId;
	}

	public void setListInstructionId(int listInstructionId) {
		this.listInstructionId = listInstructionId;
	}

	public boolean isSendMailToListEnabled() {
		return sendMailToListEnabled;
	}

	public void setSendMailToListEnabled(boolean sendMailToListEnabled) {
		this.sendMailToListEnabled = sendMailToListEnabled;
	}

	public boolean isSortEnabled() {
		return sortEnabled;
	}

	public void setSortEnabled(boolean sortEnabled) {
		this.sortEnabled = sortEnabled;
	}

	public boolean isCompound() {
		return compound;
	}

	public void setCompound(boolean compound) {
		this.compound = compound;
	}

	public List<AList> getSublists(){
		return sublists;
	}

	public List<AListBean> getSublistsBeans(){
		if (sublistsBeans == null){
			sublistsBeans = new ArrayList<AListBean>();
			for (AList list: getSublists()){
				sublistsBeans.add(new AListBean(list, this.id));
			}
		}
		return sublistsBeans;
	}

	public void setSublists(List<AList> sublists){
		this.sublists = sublists;
	}


	public List<AListColumnInstructionBean> getColumnBeans() {
		return columnBeans;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isDisplayNameAligned() {
		return displayNameAligned;
	}

	public void setDisplayNameAligned(boolean displayNameAligned) {
		this.displayNameAligned = displayNameAligned;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public SearchCreteria getSearchCreteria() {
		return searchCreteria;
	}

	public void setSearchCreteria(SearchCreteria searchCreteria) {
		this.searchCreteria = searchCreteria;
	}

	public LinkedHashSet<PersonListAttributionBean> getPersonAttribBeans() {
		return personAttribBeans;
	}

	public void setPersonAttribBeans(
			LinkedHashSet<PersonListAttributionBean> personAttribBeans) {
		this.personAttribBeans = personAttribBeans;
	}

	public int getListColumnInstructionId() {
		return listColumnInstructionId;
	}

	public void setListColumnInstructionId(int listColumnInstructionId) {
		this.listColumnInstructionId = listColumnInstructionId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public class ListType {
		private int id;
		private String display;
		public String getDisplay() {
			return display;
		}
		public void setDisplay(String display) {
			this.display = display;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	}

	public int getListTypeId() {
		return listTypeId;
	}

	public void setListTypeId(int listTypeId) {
		this.listTypeId = listTypeId;
	}

	public int getSublistId() {
		return sublistId;
	}

	public void setSublistId(int sublistId) {
		this.sublistId = sublistId;
	}

	public AListDesignBean getListDesign() {
		return listDesign;
	}

	public void setListDesign(AListDesignBean listDesign) {
		this.listDesign = listDesign;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}

	public void setSublistsBeans(List<AListBean> sublistsBeans) {
		this.sublistsBeans = sublistsBeans;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
		

}
