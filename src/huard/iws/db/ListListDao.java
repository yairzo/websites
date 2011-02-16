package huard.iws.db;

import huard.iws.model.AList;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ListListDao {

	public List<AList> getListList(boolean publicOnly);

	public List<AList> getListList(ListView lv, SearchCreteria search,
			boolean publicOnly);

	public List<AList> getListList(ListView lv, boolean publicOnly);

	public int getNumOfListInstructions(AList aList);

}
