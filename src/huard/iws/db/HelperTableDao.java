package huard.iws.db;

import java.util.Map;

public interface HelperTableDao {

	public Map<String, String> getDisplayNamesMap (String tableName, String columnName);

}
