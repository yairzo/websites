package huard.iws.util;

import java.util.List;

public class ListView {

	private String orderBy;
	private int id;
	private int page;
	private int lastPage;
	private List<NearPage> nearPages;


	public void setNearPages(List<NearPage> nearPages) {
		this.nearPages = nearPages;
	}


	public ListView(){

		this.orderBy = "";
		this.page = 1;
		this.lastPage = 1;
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




}
