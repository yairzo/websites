package huard.iws.util;

public class SearchCreteria {
	private String searchPhrase;
	private String searchField;
	private String roleFilter;
	private String whereClause;


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
