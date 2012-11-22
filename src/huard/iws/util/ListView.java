package huard.iws.util;

import java.util.ArrayList;
import java.util.List;

public class ListView {

	private String orderBy;
	private int id;
	private int page;
	private int lastPage;
	private List<NearPage> nearPages;
	private int rowsInPage;
	private int countRows;


	public void setNearPages(List<NearPage> nearPages) {
		this.nearPages = nearPages;
	}


	public ListView(){

		this.orderBy = "";
		this.page = 1;
		this.lastPage = 1;
		this.rowsInPage=7;
	}


	public boolean isFirstPage() {
		return page == 1;
	}

	public boolean isAtLastPage() {
		return page == lastPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	
	public List<NearPage> getNearPages() {
		/*List<NearPage>  nearPages = new ArrayList<NearPage>();
		int lowEndNearPage = page - 2 > 1 ? page - 2 : 1;
		int highEndNearPage = page + 2 < lastPage ? page + 2 : lastPage;
		for (int i= lowEndNearPage ; i < highEndNearPage; i++ ){
			nearPages.add(new NearPage(""+i, i));
		}*/
		return nearPages;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}
	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

	public int getNumOfPages(){
		if (countRows==0) return 1;
		if (countRows%getRowsInPage() == 0) return countRows/getRowsInPage();
		else return (countRows/getRowsInPage())+1;
	}

	public List<NearPage> getScroll() {
		int first = Math.max(page-2, 1);
		int last = Math.min(page+2, getLastPage());
		List<NearPage> nearPages = new ArrayList<NearPage>();
		if (first != 1) nearPages.add(new NearPage("...",Math.max(1, page-4)));
		for (int i=first ; i<=last ; i++){
			nearPages.add(new NearPage(""+i, i));
		}
		if (last != getLastPage()) nearPages.add(new NearPage("...", Math.min(getLastPage(), page+4)));
		return nearPages;
	}


}
