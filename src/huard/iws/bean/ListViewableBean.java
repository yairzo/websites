package huard.iws.bean;

import huard.iws.constant.Constants;
import huard.iws.model.AListColumnInstruction;
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




	public ListViewableBean(){
		helperTableService = (HelperTableService) ApplicationContextProvider.getContext().getBean("helperTableService");
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
					field.setText("<a href=\"mailto:"+field.getText()+"\" class=\"nounderline\">@</a>");
				}
				if (columnInstruction.isWebAddress()){
					field.setText("<a href=\""+field.getText()+"\" target=\"_new\"/>W</a>");
				}
				String urlColumn;
				if (( urlColumn = columnInstruction.getLinkTargetFromColumn()).length() > 0){
					String target= fieldValueMap.get(urlColumn);
					String prefix = "";
					String suffix = "";
					if (! field.getText().isEmpty()){
						if (target.indexOf('@')!=-1){
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
}
