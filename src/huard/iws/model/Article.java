package huard.iws.model;

import huard.iws.util.DateUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Leon
 *
 */
public class Article {

	private int id;
	private String doi;
	private boolean fakeDoi;
	private String title;
	private Timestamp publicationDate;
	private List<Person> authors;
	private String pathname;
	private boolean visible;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public Article() {
		this.authors = new ArrayList<Person>();
		this.visible = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}
	
	public boolean isFakeDoi() {
		return fakeDoi;
	}

	public void setFakeDoi(boolean fakeDoi) {
		this.fakeDoi = fakeDoi;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getPublicationDate() {
		return publicationDate;
	}

	public String getHumanPublicationDate() {
		return DateUtils.formatDate(publicationDate.getTime(),"dd/MM/yyyy");
	}
	
	public void setPublicationDate(Timestamp publicationDate) {
		this.publicationDate = publicationDate;
	}

	public List<Person> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Person> authors) {
		this.authors = authors;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
