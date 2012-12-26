package huard.iws.db;

import java.util.Map;

public interface ConfigurationDao {

	public Map<String, String> getConfiguration();
	
	public Map<String, String> getConfiguration(String module);

}
