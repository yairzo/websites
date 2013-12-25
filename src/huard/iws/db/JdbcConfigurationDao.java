package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcConfigurationDao extends SimpleJdbcDaoSupport implements ConfigurationDao{

	private static final Logger logger = Logger.getLogger(JdbcConfigurationDao.class);	
	
	public Map<String, String> getConfiguration(){
		String query = "select * from configuration";
		logger.debug(query);
		final Map<String,String> configuration = new HashMap<String,String>();
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				String key = rs.getString("module") + "_" + rs.getString("key_name");
				configuration.put(key, rs.getString("value"));
				return null;
			}
		});
		return configuration;
	}	
	
	public Map<String, String> getConfiguration(String module){
		String query = "select * from configuration where module = ?";
		logger.debug(query);
		final Map<String,String> configuration = new HashMap<String,String>();
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				String key = rs.getString("module") + "_" + rs.getString("key_name");
				configuration.put(key, rs.getString("value"));
				return null;
			}
		}, module);

		return configuration;
	}
}