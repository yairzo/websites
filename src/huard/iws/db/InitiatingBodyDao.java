package huard.iws.db;

import huard.iws.model.InitiatingBody;

import java.util.List;

public interface InitiatingBodyDao {

	public InitiatingBody getInitiatingBody(int id);

	public List<InitiatingBody> getInitiatingBodies();

}
