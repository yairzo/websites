package huard.iws.service;

import huard.iws.db.TableDescriptionDao;

import java.util.List;

public class TableDescriptionServiceImpl implements TableDescriptionService{
	//private static final Logger logger = Logger.getLogger(TableDescriptionServiceImpl.class);

	public List<String> getColumnsList(String tableName){
		return tableDescriptionDao.getColumnsList (tableName);
	}

	public boolean isTableExists(String tableName){
		return tableDescriptionDao.isTableExists(tableName);
	}

	TableDescriptionDao tableDescriptionDao;

	public void setTableDescriptionDao(TableDescriptionDao tableDescriptionDao) {
		this.tableDescriptionDao = tableDescriptionDao;
	}


}
