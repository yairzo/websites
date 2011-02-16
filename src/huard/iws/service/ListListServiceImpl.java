package huard.iws.service;


import huard.iws.bean.PersonBean;
import huard.iws.db.ListListDao;
import huard.iws.model.AList;
import huard.iws.util.ListPaginator;
import huard.iws.util.ListView;
import huard.iws.util.NearPage;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListListServiceImpl implements ListListService{
	//private static final Logger logger = Logger.getLogger(ListListService.class);



	public List<AList> getListsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage) {
		ListPaginator lp = new ListPaginator(getLists(lv, search, userPersonBean), itemsInPage);
		List l = lp.getPage(lv.getPage());
		List<AList> listsPage = new ArrayList<AList>();
		for (Object o : l){
			AList list = (AList) o;
			listsPage.add(list);
		}
		return listsPage;
	}

	public boolean isLastPage (ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage){
		ListPaginator lp = new ListPaginator(getLists(lv, search, userPersonBean), itemsInPage);
		return lp.getNumOfPages() == lv.getPage();
	}

	public List<NearPage> getNearPageNums(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage){
		ListPaginator lp = new ListPaginator(getLists(lv, search, userPersonBean), itemsInPage);
		return lp.getNearPages(lv.getPage());
	}

	public int getLastPageNum(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage){
		ListPaginator lp = new ListPaginator(getLists(lv, search, userPersonBean), itemsInPage);
		return lp.getNumOfPages();
	}

	public void setListListDao(ListListDao listListDao) {
		this.listListDao = listListDao;
	}

	public List<AList> getLists(ListView lv, SearchCreteria search, PersonBean userPersonBean) {
		final boolean PUBLIC_ONLY =  ! (userPersonBean.isAuthorized("LISTS", "EDITOR") || userPersonBean.isAuthorized("LISTS", "ADMIN"));
		if (search != null && search.getSearchField()!=null
				&& search.getSearchPhrase()!=null && ! "".equals(search.getSearchPhrase())){
			return listListDao.getListList(lv, search, PUBLIC_ONLY);
		}
		else {
			return listListDao.getListList(lv, PUBLIC_ONLY);
		}
	}

	public List<AList>getLists(){
		return listListDao.getListList(false);
	}

	public List<AList> getLists(int typeId){
		List<AList> lists = getLists();
		List<AList> listsByType = new ArrayList<AList>();
		for (AList list: lists){
			if (list.getListTypeId() == typeId)
				listsByType.add(list);
		}
		return listsByType;
	}

	public List<AList>getLists(PersonBean userPersonBean){
		final boolean PUBLIC_ONLY =  ! (userPersonBean.isAuthorized("LISTS", "EDITOR") || userPersonBean.isAuthorized("LISTS", "ADMIN"));
		return listListDao.getListList(PUBLIC_ONLY);
	}

	public List<AList> getBasicLists() {
		List<AList> basicLists = new ArrayList<AList>();
		for (AList list : getLists()){
			if (!list.isCompound()) {
				basicLists.add(list);
			}
		}
		return basicLists;
	}

	public Map<Integer,AList> getListsMap(){
		Map<Integer, AList> listsMap = new HashMap<Integer,AList>();
		for (AList list: getLists()){
			listsMap.put(list.getId(), list);
		}
		return listsMap;
	}

	public List<AList> getUnboundLists(AList aList){
		Set<Integer> boundListsIds = new HashSet<Integer>();
		for (AList sublist: aList.getSublists()){
			boundListsIds.add(sublist.getId());
		}
		List <AList> unboundLists = new ArrayList<AList>();
		for (AList list: getLists()){
			if (! boundListsIds.contains(list.getId()) && aList.getId() != list.getId()){
				unboundLists.add(list);
			}
		}
		return unboundLists;
	}

	public List<AList> getSublists(AList aList){
		List <AList> boundLists = new ArrayList<AList>();
		for (AList sublist: aList.getSublists()){
			boundLists.add(getListsMap().get(sublist.getId()));
		}
		return boundLists;
	}

	public void prepareListView(ListView lv, SearchCreteria search, PersonBean userPersonBean, int itemsInPage){
		ListPaginator lp = new ListPaginator(getLists(lv, search, userPersonBean), itemsInPage);
		lv.setLastPage(lp.getNumOfPages());
		lv.setNearPages(lp.getNearPages(lv.getPage()));
	}

	private ListListDao listListDao;

	public int getNumOfListInstructions(AList aList){
		return listListDao.getNumOfListInstructions(aList);
	}
}
