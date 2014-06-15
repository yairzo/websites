package huard.iws.db;

import java.util.HashMap;
import java.util.Map;

public class QueryElementsMap {
	
	public static enum QueryValidKey { additionalConditions, joinPhrase, orderPhrase, limitPhrase}
	
	private Map<QueryValidKey, String> queryElemetsMap;
	
	
	public QueryElementsMap(){
		queryElemetsMap = new HashMap<>();
	}
	
	
	public void put (QueryValidKey key, String value){
		queryElemetsMap.put(key, value);
	}
	
	public String get (QueryValidKey key){
		return queryElemetsMap.get(key);
	}

}
