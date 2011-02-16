package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.AList;
import huard.iws.util.ListView;
import huard.iws.util.NearPage;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ListListService {

	public List<AList> getListsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage);

	public boolean isLastPage (ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage);

	public List<NearPage> getNearPageNums(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage);

	public int getLastPageNum(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage);

	public int getNumOfListInstructions(AList aList);

	public List<AList> getLists(ListView lv, SearchCreteria search, PersonBean userPersonBean);

	public List<AList> getLists(PersonBean userPersonBean);

	public List<AList>getLists();

	public List<AList> getLists(int typeId);

	public List<AList> getBasicLists();

	public List<AList> getUnboundLists(AList aList);

	public List<AList> getSublists(AList aList);

	public void prepareListView(ListView listView,  SearchCreteria searchCreteria,
			PersonBean userPersonBean, int itemsInPage);

}
