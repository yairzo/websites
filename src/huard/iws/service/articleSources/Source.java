package huard.iws.service.articleSources;

import huard.iws.model.Article;
import huard.iws.model.Person;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This is a template class for crawlers
 * NAMING CONVENTION: "Source_<name of site in db>"
 * @author Leon
 *
 */
public abstract class Source {
	
	private static final Logger logger = Logger.getLogger(Source.class);
	
	public Elements connectAndQuery(String url, String query) {
		Document doc = null;
		try {
			logger.info("Fetching URL: "+url);
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc.select(query);
	}
	
	public abstract List<Article> obtainArticlesByAuthor(Person author);
	
	public abstract List<Person> obtainAuthorsOfArticle(Article article);
}
