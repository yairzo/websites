package huard.iws.service.articleSources;

import huard.iws.model.Article;
import huard.iws.model.Person;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Source_ieee extends Source{

	@Override
	public List<Article> obtainArticlesByAuthor(Person author) {
		
		String url = "http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=p_Authors:.QT."
				+author.getLastNameEnglish()+"%20"
				+author.getFirstNameEnglish()
				+".QT.&amp;searchWithin=p_Author_Ids:37326991700&amp;newsearch=true";
		
		String jsoupQuery = "body li.noAbstract";
		String publicationDatePatternString = "Publication Year: (\\d+)";
		String pathnamePatternString = "(/stamp/stamp.jsp\\?tp=&arnumber=.*)";
		
		
		List<Article> articles = new ArrayList<Article>();
		
		Elements elements = connectAndQuery(url, jsoupQuery);
		
		String doi;
		String title;
		Timestamp timestamp;
		Pattern patternPublicationDate = Pattern.compile(publicationDatePatternString);
		Pattern patternPathname = Pattern.compile(pathnamePatternString);
		Matcher matcher;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		String pathname;
		
		Elements temp;
		
		for (Element e: elements) {
			//get title
			title = e.select("div.detail h3 a").first().ownText();
			
			//get publication year
			matcher = patternPublicationDate.matcher(e.html());
			matcher.find();
			timestamp = Timestamp.valueOf(
					matcher.group(1)
					+"-01-01 00:00:00");
			
			//get DOI. if this article doesn't have a DOI
			//then get an MD5 hash of a concatenation of the title and timestamp
			temp = e.select("div.detail a[target=blank]");
			if (temp.size() > 0)
				doi = temp.first().ownText();
			else 
				doi = new String(md.digest((title+timestamp.toString()).getBytes())); // TODO fix this to return a pretty number
			
			//get pathname
			temp = e.select("div.detail p.links a[href]");
			pathname = "ieeexplore.ieee.org";
			for (Element f: temp) {
				matcher = patternPathname.matcher(f.attr("href"));
				if (matcher.find())
					pathname+=matcher.group(1);
			}
			
			List<Person> authors = new ArrayList<Person>();
			authors.add(author);
			
			Article article = new Article();
			article.setDoi(doi);
			article.setTitle(title);
			article.setPublicationDate(timestamp);
			article.setPathname(pathname);
			article.setAuthors(authors);
			article.setVisible(true);
			articles.add(article);
		}
		return articles;
	}

	@Override
	public List<Person> obtainAuthorsOfArticle(Article article) {
		/*for (Element f: e.select("div.detail a.prefNameLink")) {
		authors.add(f.ownText());
	}*/
		// TODO Auto-generated method stub
		return null;
	}
}
