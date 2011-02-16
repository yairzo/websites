package huard.iws.service;

import huard.iws.db.ConfigurationDao;

import java.util.Map;

public class ConfigurationServiceImpl implements ConfigurationService{

	//private static final Logger logger = Logger.getLogger(ConfigurationServiceImpl.class);

	private Map<String,String> getConfigurationMap(){
		return configurationDao.getConfiguration();
	}

	public String getConfigurationString(String key){
		return getConfigurationMap().get(key);
	}
	public int getConfigurationInt(String key){
		String value =  getConfigurationMap().get(key);
		int intValue = 0;
		if (value != null )
			intValue = Integer.parseInt(value);
		return intValue;
	}

	private ConfigurationDao configurationDao;

	public void setConfigurationDao(ConfigurationDao configurationDao) {
		this.configurationDao = configurationDao;
	}

}
