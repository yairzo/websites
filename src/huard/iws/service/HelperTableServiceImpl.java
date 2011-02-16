package huard.iws.service;

import huard.iws.db.HelperTableDao;

import java.util.Map;

public class HelperTableServiceImpl implements HelperTableService{

	public Map<String, String> getDisplayNamesMap(String tableName, String displayColumnName){
		return helperTableDao.getDisplayNamesMap(tableName, displayColumnName);
	}

	private HelperTableDao helperTableDao;

	public void setHelperTableDao(HelperTableDao helperTableDao) {
		this.helperTableDao = helperTableDao;
	}

}
