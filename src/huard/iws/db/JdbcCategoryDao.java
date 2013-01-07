package huard.iws.db;

import huard.iws.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCategoryDao extends SimpleJdbcDaoSupport implements CategoryDao {

	public Category getRootCategory(){
		String query = "select * from websiteCategory where parentId<0 limit 1;";
		logger.info(query);
		Category category = getSimpleJdbcTemplate().queryForObject(query,categoryRowMapper);
		return category;
	}
	
	public Category getCategory(int id){
		String query = "select * from websiteCategory where id=?;";
		logger.info(query);
		Category category = getSimpleJdbcTemplate().queryForObject(query,categoryRowMapper,id);
		return category;
	}
	
	private ParameterizedRowMapper<Category> categoryRowMapper = new ParameterizedRowMapper<Category>(){
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException{
			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setName(rs.getString("name"));
			category.setParentId(rs.getInt("parentId"));
			category.setCategoryOrder(rs.getInt("categoryOrder"));
			category.setUrl(rs.getString("url"));
			return category;
		}
	};
	
	public List<Category> getCategories(int parentCategoryId){
		String query = "select * from websiteCategory where parentId=? order by categoryOrder;";
		logger.info(query);
		logger.info("parentId:" + parentCategoryId);
		
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
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				category.getName(),
				category.getParentId(),
				category.getCategoryOrder(),
				category.getUrl(),
				category.getId());
	}
	public int insertCategory(int parentId){
		final String categoryInsert = "insert websiteCategory set parentId = ?, name = ?, categoryOrder = ?;"; 
		final int categoryOrder = getCategories(parentId).size() + 1;
		final int aParentId = parentId;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(categoryInsert, new String[] {"id"});
		            ps.setInt(1, aParentId);
		            ps.setString(2, "קטגוריה חדשה");
		            ps.setInt(3, categoryOrder);
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
