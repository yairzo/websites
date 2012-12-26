package huard.iws.service;

import huard.iws.db.ConfigurationDao;

import java.util.Map;

public class ConfigurationServiceImpl implements ConfigurationService{

	//private static final Logger logger = Logger.getLogger(ConfigurationServiceImpl.class);

	private Map<String,String> getConfigurationMap(){
		return configurationDao.getConfiguration();
	}

	public String getConfigurationString(String module, String key, String defaultValue){
		String sValue = defaultValue;
		String value = getConfigurationMap().get(buildKey(module, key));
		if (value != null)
			sValue = value;
		return sValue;		
	}
	
	public String getConfigurationString(String module, String key){
		return getConfigurationString(module, key, "");
	}
	
	public int getConfigurationInt(String module, String key, int defaultValue){
		String value =  getConfigurationMap().get(buildKey(module, key));
		int intValue = defaultValue;
		if (value != null)
			intValue = Integer.parseInt(value);
		return intValue;
	}
	
	public int getConfigurationInt(String module, String key){
		return getConfigurationInt(module, key, 0);
	}
	
	public boolean getConfigurationBoolean(String module, String key, boolean defaultValue){
		String value =  getConfigurationMap().get(buildKey(module, key));
		boolean booleanValue = false;
		if (value != null )
			booleanValue = Boolean.parseBoolean(value);
		return booleanValue;
	}
	
	public boolean getConfigurationBoolean(String module, String key){
		return getConfigurationBoolean(module, key, false);
	}
	
	public Map<String, String> getConfigurationMap(String module){
		return getConfigurationMap(module);
	}
	
	private String buildKey(String module, String key){
		String moduleKey = module + "_" + key;
		return moduleKey;
	}

	private ConfigurationDao configurationDao;

	public void setConfigurationDao(ConfigurationDao configurationDao) {
		this.configurationDao = configurationDao;
	}

}
