package huard.iws.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import huard.iws.db.PersonAttributionDao;

public class PersonDeskServiceImpl implements PersonDeskService{
	public Map<Integer, Integer> personDeskMap = new HashMap<Integer, Integer>();
	private static final Logger logger = Logger.getLogger(PersonDeskServiceImpl.class);

	public void init(){
		this.personDeskMap=new HashMap<Integer, Integer>();
		this.personDeskMap=personAttributionDao.getPersonDeskMap();
	}
	
	public Map<Integer,Integer> getPersonDeskMap(){
		return this.personDeskMap;
	}


PersonAttributionDao personAttributionDao;

public void setPersonAttributionDao(
		PersonAttributionDao personAttributionDao) {
	this.personAttributionDao = personAttributionDao;
}

}
