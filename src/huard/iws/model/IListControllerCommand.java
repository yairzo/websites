package huard.iws.model;

import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

public interface IListControllerCommand {

	public SearchCreteria getSearchCreteria();

	public void setSearchCreteria(SearchCreteria searchCreteria);

	public ListView getListView();

	public void setListView(ListView listView);

}
