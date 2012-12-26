package huard.iws.service;

import java.util.Map;


public interface ConfigurationService {

	public String getConfigurationString(String module, String key, String defaultValue);
	
	public String getConfigurationString(String module, String key);

	public int getConfigurationInt (String module, String key, int defaultValue);
	
	public int getConfigurationInt(String module, String key);
	 
	public boolean getConfigurationBoolean (String module, String key, boolean defaultValue);
	
	public boolean getConfigurationBoolean(String module, String key);
	
	public Map<String, String> getConfigurationMap(String module);

}
