package huard.iws.db;

import huard.iws.model.MopDesk;

import java.util.List;

public interface MopDeskDao {

	public List<MopDesk> getMopDesks();

	public MopDesk getMopDesk(int mopDeskId);

	public MopDesk getMopDesk(String mopDeskId);

}
