package huard.iws.util;

import java.util.ArrayList;
import java.util.List;

public class ListPaginator {
	private List<?> l;
	private int objsInPage;
	//private static final Logger logger = Logger.getLogger(ListPaginator.class);

	public ListPaginator(List<?> l, int objsInPage){
		this.l = l;
		this.objsInPage = objsInPage;
	}

	public List<?> getPage(int page){
		if (l==null) return null;
		if (l.size()==0) return new ArrayList();
		int first = (page - 1) * objsInPage;
		int objsInLastPage = l.size()%objsInPage ==0 ? objsInPage : l.size()%objsInPage ;
		int last;
		if (page == getNumOfPages()) last = first + objsInLastPage ;
		else last = first + objsInPage;
		return l.subList(first, last);
	}

	public List<NearPage> getNearPages(int page){
		int first = Math.max(page-2, 1);
		int last = Math.min(page+2, getNumOfPages());
		List<NearPage> nearPages = new ArrayList<NearPage>();
		if (first != 1) nearPages.add(new NearPage("...",Math.max(1, page-4)));
		for (int i=first ; i<=last ; i++){
			nearPages.add(new NearPage(""+i, i));
		}
		if (last != getNumOfPages()) nearPages.add(new NearPage("...", Math.min(getNumOfPages(), page+4)));
		return nearPages;
	}

	public int getNumOfPages(){
		if (l.size()==0) return 1;
		if (l.size()%objsInPage == 0) return l.size()/objsInPage;
		else return (l.size()/objsInPage)+1;
	}

	public boolean isLastPage(int page){
		return page==getNumOfPages();
	}

}
