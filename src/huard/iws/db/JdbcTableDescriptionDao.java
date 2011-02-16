package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcTableDescriptionDao extends SimpleJdbcDaoSupport implements TableDescriptionDao {
	//private static final Logger logger = Logger.getLogger(JdbcTableDescriptionDao.class);

	public List<String> getColumnsList(String tableName){
		String queryString = "describe "+tableName;
		List<String> columns =
			getSimpleJdbcTemplate().query(queryString, new ParameterizedRowMapper<String>(){
				public String mapRow(ResultSet rs, int rowNum) throws SQLException{
		            return rs.getString("Field");
		        }
			});
		return columns;
	}

	public boolean isTableExists(String tableName){
		String queryString = "show tables";
		List<String> tables = getSimpleJdbcTemplate().query(queryString,new ParameterizedRowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) throws SQLException{
	            return rs.getString(1);
	        }
		});
		return (tables.contains(tableName));
	}

}
