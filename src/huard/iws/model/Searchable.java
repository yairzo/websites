package huard.iws.model;

import huard.iws.util.SearchCreteria;

public interface Searchable {

	public boolean isMatch (SearchCreteria search);

}
