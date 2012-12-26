package huard.iws.service;

import java.util.Set;

public interface SphinxSearchService  {
	
	public Set<Long> getMatchedIds (String query, String index);	

}
