package huard.iws.bean;

import huard.iws.constant.Constants;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.Faculty;
import huard.iws.service.FacultyService;
import huard.iws.service.HelperTableService;
import huard.iws.util.ApplicationContextProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class ListViewableBean implements IListViewableBean{

	protected int id;
	protected List<AListColumnInstruction> columnInstructions;
	protected Map<String,String> fieldValueMap;
	private HelperTableService helperTableService;
	private FacultyService facultyService;




	public ListViewableBean(){
		helperTableService = (HelperTableService) ApplicationContextProvider.getContext().getBean("helperTableService");
		facultyService = (FacultyService) ApplicationContextProvider.getContext().getBean("facultyService");
	}

	public int getBeanTypeId(){
		if (this instanceof PersonListAttributionBean){
			return Constants.getListTypesInv().get("person");
		}
		else if (this instanceof OrganizationUnitBean){
			return Constants.getListTypesInv().get("organization unit");
		}
		return 0;
	}

	public List<Field> getFields(){
		List<Field> data = new ArrayList<Field>();

		for (AListColumnInstruction columnInstruction: columnInstructions){
			if (! columnInstruction.isHidden()){
				StringTokenizer st = new StringTokenizer(columnInstruction.getColumnName(), " ");
				Field field = new Field();
				while (st.hasMoreTokens()){
					String token = st.nextToken();
					field.setText(field.getText().concat(fieldValueMap.get(token) + " "));
				}
				field.truncate(1);
				field.setWidth(""+columnInstruction.getWidth());

				if (columnInstruction.isMailAddress()){
					field.setIsEmailAddress(true);
					field.setPrefix("<a href=\"mailto:");
					field.setSuffix("\" class=\"nounderline\">@</a>");
					
					//field.setText("<a href=\"mailto:"+field.getText()+"\" class=\"nounderline\">@</a>");
				}
				if (columnInstruction.isWebAddress()){
					field.setIsWebAddress(true);
					if(!field.getText().trim().isEmpty())
						field.setText("<a href=\""+field.getText()+"\" target=\"_new\"/>W</a>");
				}
				if (columnInstruction.isImage()){
					field.setIsImage(true);
					//if(!field.getText().trim().isEmpty())
					//	field.setText("<img src=\"/imageViewer?urlTitle="+field.getText()+"&amp;attachType=bodyImage\" height=\"100px\" width=\"100px\"/>");
				}
				String urlColumn;
				if (( urlColumn = columnInstruction.getLinkTargetFromColumn()).length() > 0){
					String target= fieldValueMap.get(urlColumn);
					String prefix = "";
					String suffix = "";
					if (! field.getText().isEmpty()){
						if (field.getIsEmailAddress() || target.indexOf('@')!=-1){
							prefix = "<a href=\"mailto:"+target+"\">";
							suffix = "</a>";
						}
						else{
							if (! target.startsWith("http://"))
								target = "http://"+field;
							prefix = "<a href=\""+target+"\" target=\"_new\" class=\"nounderline\">";
							suffix = "</a>";
						}
					}
					field.setPrefix(prefix);
					field.setSuffix(suffix);
				}
				if (columnInstruction.isUseHelperTable()){

					if (field.getText().length() > 0){
						String text = helperTableService.getDisplayNamesMap(columnInstruction.getHelperTableName(), columnInstruction.getHelperTableDisplayColumnName()).get(field.getText());
						field.setText(text);
					}
				}
				if (columnInstruction.isBold()){
					field.setText("<b>"+field.getText()+"</b>");
				}
				if (columnInstruction.isNoBr()){
					field.setText("<nobr>"+field.getText()+"</nobr>");
				}
				if (columnInstruction.getAlign()!=null && columnInstruction.getAlign().length()>0){
					field.setAlign(columnInstruction.getAlign());
				}
				data.add(field);
			}
		}
		return data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, String> getFieldValueMap() {
		return fieldValueMap;
	}
	
	public String getDegreeEnglish(){
		return fieldValueMap.get("person.degreeEnglish");
	}
	public String getFirstNameEnglish(){
		return fieldValueMap.get("person.firstNameEnglish");
	}
	public String getLastNameEnglish(){
		return fieldValueMap.get("person.lastNameEnglish");
	}
	public String getDepartment(){
		return fieldValueMap.get("person.department");
	}	
	public String getAcademicTitle(){
		return fieldValueMap.get("person.academicTitle");
	}
	public String getTitle(){
		return fieldValueMap.get("personAttribution.title");
	}
	public String getRoomNumber(){
		return fieldValueMap.get("person.roomNumber");
	}
	public String getFacultyName(){
		int facultyId= new Integer(fieldValueMap.get("person.facultyId"));
		Faculty faculty =  new Faculty();
		try{
			 faculty =facultyService.getFaculty(facultyId);
			 return faculty.getNameEnglish();
		}
		catch(Exception e){
			return "";
		}
	}
	public String getWebsiteUrl(){
		return fieldValueMap.get("person.websiteUrl");
	}
	public String getDescriptionSummary(){
		return fieldValueMap.get("personAttribution.descriptionSummary");
	}
	public String getDescription(){
		return fieldValueMap.get("personAttribution.description");
	}
	public String getEmail(){
		return fieldValueMap.get("personAttribution.email");
	}
	public String getPhone(){
		return fieldValueMap.get("personAttribution.phone");
	}
	public String getImageUrl(){
		return fieldValueMap.get("personAttribution.imageUrl");
	}
	public String getOrganizationImageUrl(){
		return fieldValueMap.get("organizationUnit.imageUrl");
	}
	public String getOrganizationUnitNameEnglish(){
		return fieldValueMap.get("organizationUnit.nameEnglish");
	}
	public String getOrganizationUnitShortName(){
		return fieldValueMap.get("organizationUnit.shortName");
	}
	public String getOrganizationUnitContact(){
		return fieldValueMap.get("organizationUnit.contact");
	}
}
