package huard.iws.service;

import huard.iws.model.InitiatingBody;

import java.util.List;

public interface InitiatingBodyService {

	public  InitiatingBody getInitiatingBody(int id);

	public List<InitiatingBody> getInitiatingBodies();

}
