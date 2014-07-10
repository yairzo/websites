package huard.iws.service;

import huard.iws.model.Article;
import huard.iws.model.Person;

import java.util.List;

/**
 * 
 * @author Leon
 *
 */
public interface ArticleService {
	
	/**
	 * get the author that had the oldest update 
	 * @return author
	 */
	public Person getLeastUpdatedAuthor();
	
	/**
	 * gets a list of articles by an author
	 * @param author
	 * @return list of articles
	 */
	public List<Article> obtainArticlesByAuthor(Person author);
	
	/**
	 * gets a list of authors for a given article
	 * @param article
	 * @return list of authors
	 */
	public List<Person> obtainAuthorsOfArticle(Article article);
	
	/**
	 * check for new articles by the least updated author
	 */
	public void updateLeastUpdatedAuthor();

}
