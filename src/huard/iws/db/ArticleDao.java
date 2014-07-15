package huard.iws.db;

import huard.iws.model.Article;
import huard.iws.model.Person;

import java.util.List;

/**
 * 
 * @author Leon
 *
 */
public interface ArticleDao {
	
	public Article getArticle(int id);
	
	public List<Article> getArticlesByAuthorId(int id);
	
	public Article getArticleByTitle(String title);
	
	public List<Article> getArticles();
	
	public List<Article> getVisibleArticles();
	
	public List<Article> getHiddenArticles();
	
	public void insertArticle(Article article);

	public void deleteArticle(int id);
	
	/**
	 * get the authors of a an article
	 * @param doi - the DOI of the article
	 * @return list of authors
	 */
	public List<Person> getAuthors(int doi);
	
	/**
	 * returns the author that was least updated
	 * @return the least updated author
	 */
	public Person getLeastUpdatedAuthor();
	
	/**
	 * get the X least updated authors
	 * @param xLastUpdatedAuthors - amount of authors
	 * @return list of authors
	 */
	List<Person> getXLeastUpdatedAuthors(int xLastUpdatedAuthors);
	
	/**
	 * changes from visible to hidden and vice-versa
	 * @param id - the ID of the article
	 */
	public void changeVisibilityState(int id);

	
}
