package huard.iws.db;

import huard.iws.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCategoryDao extends SimpleJdbcDaoSupport implements CategoryDao {

	public Category getRootCategory(String localeId){
		String query = "select * from websiteCategory where parentId = 0 and localeId=?;";
		//logger.info(query);
		Category category = getSimpleJdbcTemplate().queryForObject(query,categoryRowMapper,localeId);
		return category;
	}
	
	public Category getCategory(int id){
		String query = "select * from websiteCategory where id=?;";
		//logger.info(query);
		Category category = getSimpleJdbcTemplate().queryForObject(query,categoryRowMapper,id);
		return category;
	}
	public Category getCategoryByUrl(String url){
		String query = "select * from websiteCategory where url=? limit 1;";
		//logger.info(query);
		Category category = new Category();
		try{
			category = getSimpleJdbcTemplate().queryForObject(query,categoryRowMapper,url);
			return category;
		}
		catch(Exception e){
			return category;
		}
	}
	
	public int getCategoryIdByName(String name){
		try{
			String query = "select id from websiteCategory where name='" + name+"' limit 1;";
			logger.info(query);
			return getSimpleJdbcTemplate().queryForInt(query);
		}
		catch(Exception e){
			return 0;
		}
	}
	
	private ParameterizedRowMapper<Category> categoryRowMapper = new ParameterizedRowMapper<Category>(){
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException{
			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setName(rs.getString("name"));
			category.setParentId(rs.getInt("parentId"));
			category.setCategoryOrder(rs.getInt("categoryOrder"));
			category.setUrl(rs.getString("url"));
			category.setLocaleId(rs.getString("localeId"));
			return category;
		}
	};
	
	public List<Category> getCategories(int parentCategoryId){
		String query = "select * from websiteCategory where parentId=? order by categoryOrder;";
		//logger.info(query);
		//logger.info("parentId:" + parentCategoryId);
		
		List<Category> categories = getSimpleJdbcTemplate().query(query,categoryRowMapper,parentCategoryId);
		return categories;
	}
	
	public void updateCategory(Category category){
		String query = "update websiteCategory set " +
				" name = ?" +
				", parentId = ?" +
				", categoryOrder = ?" +
				", url = ?" +
			" where id = ?;";
		//logger.info(query);
		getSimpleJdbcTemplate().update(query,
				category.getName(),
				category.getParentId(),
				category.getCategoryOrder(),
				category.getUrl(),
				category.getId());
	}
	public int insertCategory(int parentId){
		final String categoryInsert = "insert websiteCategory set parentId = ?, name = ?, categoryOrder = ?,localeId=?;"; 
		final int categoryOrder = getCategories(parentId).size() + 1;
		final int aParentId = parentId;
		final String locaelId = getCategory(parentId).getLocaleId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(categoryInsert, new String[] {"id"});
		            ps.setInt(1, aParentId);
		            ps.setString(2, "קטגוריה חדשה");
		            ps.setInt(3, categoryOrder);
		            ps.setString(4, locaelId);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}
	public void deleteCategory(int id){
		final String categoryDelete = "delete from websiteCategory where id=?";
		getSimpleJdbcTemplate().update(categoryDelete,id);

	}

}
