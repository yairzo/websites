package huard.iws.service;

import huard.iws.db.ArticleDao;
import huard.iws.db.PersonDao;
import huard.iws.model.Article;
import huard.iws.model.Person;
import huard.iws.service.articleSources.Source;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author Leon
 *
 */
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = Logger.getLogger(ArticleService.class);
	
	private ArticleDao articleDao;
	
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	private SourceManagerService sourceManagerService;
	
	public void setSourceManagerService(SourceManagerService sourceManagerService) {
		this.sourceManagerService = sourceManagerService;
	}

	private PersonDao personDao;

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public Person getLeastUpdatedAuthor() {
		return articleDao.getLeastUpdatedAuthor();
	}

	public List<Article> obtainArticlesByAuthor(Person author) {
		List<Article> articles = new ArrayList<Article>();
		for (Source source: sourceManagerService.getActiveSources())
			articles.addAll(source.obtainArticlesByAuthor(author));
		return articles;
	}

	public List<Person> obtainAuthorsOfArticle(Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateLeastUpdatedAuthor() {
		Person author = getLeastUpdatedAuthor();
		if (author != null) {
			List<Article> articles = obtainArticlesByAuthor(author);
			for (Article article: articles)
				articleDao.insertArticle(article);
			author.setLastSync(new Timestamp(System.currentTimeMillis()));
			
			logger.info("Added "+articles.size()+" articles for: "+author.getFirstNameEnglish()+" "+author.getLastNameEnglish());
			personDao.updatePerson(author);
		}
	}

}
