package huard.iws.db;

import huard.iws.model.Article;
import huard.iws.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * 
 * @author Leon
 *
 */
public class JdbcArticleDao extends SimpleJdbcDaoSupport implements ArticleDao {
	
	private static final Logger logger = Logger.getLogger(JdbcArticleDao.class);

	private PersonDao personDao;
	
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	private ParameterizedRowMapper<Person> personRowMapper = new PersonRowMapper();
	
	private ParameterizedRowMapper<Article> articleRowMapper = new ParameterizedRowMapper<Article>(){
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException{
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setDoi(rs.getString("doi"));
            article.setFakeDoi(rs.getBoolean("fakeDoi"));
            article.setPublicationDate(rs.getTimestamp("publicationDate"));
            article.setPathname(rs.getString("pathname"));
            article.setVisible(rs.getBoolean("visible"));
            return article;
        }
	};
	
	public Article getArticle(int id) {
		String query = "SELECT * FROM article WHERE id=?";
		logger.debug(query);
		Article article = getSimpleJdbcTemplate().queryForObject(query, articleRowMapper,	id);
		article.setAuthors(getAuthors(article.getId()));
		return article;
	}
	
	public List<Article> getArticlesByAuthorId(int id) {
		String query = "SELECT * FROM article WHERE personId = ?";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, articleRowMapper, id);
	}

	public Article getArticleByTitle(String title) {
		String query = "SELECT * FROM article WHERE title = ?";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForObject(query, articleRowMapper, title);
	}

	public List<Article> getArticles() {
		String query = "SELECT * FROM article";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, articleRowMapper);
	}

	public List<Article> getVisibleArticles() {
		String query = "SELECT * FROM article WHERE visible = ?";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, articleRowMapper, "1");
	}

	public void insertArticle(Article article) {
		String query = "INSERT IGNORE INTO article (doi, title, publicationDate, personId, pathname, visible) VALUES (?, ?, ?, ?, ?, ?)";
		logger.debug(query);
		for (Person author : article.getAuthors())
			getSimpleJdbcTemplate().update(query, article.getDoi() ,article.getTitle(), article.getPublicationDate(), author.getId(), article.getPathname(), article.isVisible());
	}

	public void deleteArticle(int id) {
		String query = "DELETE FROM article WHERE id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	private ParameterizedRowMapper<Integer> idParser = new ParameterizedRowMapper<Integer>() {
		public Integer mapRow(ResultSet rs, int arg) throws SQLException {
			return rs.getInt("id");
		}
	};
		
	
	public List<Person> getAuthors(int doi) {
		String query = "SELECT personId FROM article WHERE doi = ?";
		logger.debug(query);
		List<Person> authors = new ArrayList<Person>();
		for (Integer id: getSimpleJdbcTemplate().query(query, idParser, doi))
			authors.add(personDao.getPerson(id));
		return authors;
	}

	public Person getLeastUpdatedAuthor() {
		List<Person> persons = getXLeastUpdatedAuthors(1);
		if (persons.size() == 0)
			return null;
		return getXLeastUpdatedAuthors(1).get(0);
	}

	public List<Person> getXLeastUpdatedAuthors(int xLastUpdatedAuthors) {
		if (xLastUpdatedAuthors == 0) 
			return new ArrayList<Person>();
		String query = "SELECT * FROM person WHERE collectPublications=1 ORDER BY lastSync ASC LIMIT ?";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, personRowMapper, xLastUpdatedAuthors);
	}

}
