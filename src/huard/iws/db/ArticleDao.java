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
	 * @return
	 */
	public Person getLeastUpdatedAuthor();
}
