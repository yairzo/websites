package huard.iws.util;

public class SearchCreteria {
	protected String searchPhrase;
	protected String searchField;
	protected String roleFilter;
	protected String whereClause;


	public String getRoleFilter() {
		return roleFilter;
	}

	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
	}

	public SearchCreteria(){
		searchField = "";
		searchPhrase = "";
		roleFilter = "";
		whereClause = "";
	}
	
	public String getFullWhereCluase(){
		String whereCluase = "";
		if (whereCluase.isEmpty() && searchPhrase.isEmpty())
			return "";
		whereCluase += "where";
		if (! this.whereClause.isEmpty())
			whereCluase += this.whereClause;
		if (! searchPhrase.isEmpty()){
			if (! whereCluase.endsWith("where"))
				whereCluase += " and";
			whereCluase += searchField + " = " + searchPhrase;
		}
		return whereCluase;
	}

	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchPhrase() {
		return searchPhrase;
	}
	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}
	public String getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	public boolean isValid(){
		return (! "".equals(searchField) && ! "".equals(searchPhrase));
	}

}
