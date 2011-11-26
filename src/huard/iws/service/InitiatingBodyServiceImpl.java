package huard.iws.service;

import huard.iws.db.InitiatingBodyDao;
import huard.iws.model.InitiatingBody;
import java.util.List;

public class InitiatingBodyServiceImpl implements InitiatingBodyService{

	public InitiatingBody getInitiatingBody(int id){
		return initiatingBodyDao.getInitiatingBody(id);
	}

	public List<InitiatingBody> getInitiatingBodies(){
		return initiatingBodyDao.getInitiatingBodies();
	}

	private InitiatingBodyDao initiatingBodyDao;

	public void setInitiatingBodyDao(InitiatingBodyDao initiatingBodyDao) {
		this.initiatingBodyDao = initiatingBodyDao;
	}

}
