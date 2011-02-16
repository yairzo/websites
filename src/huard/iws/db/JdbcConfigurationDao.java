package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcConfigurationDao extends SimpleJdbcDaoSupport implements ConfigurationDao{

	@SuppressWarnings("unused")
	public Map<String, String> getConfiguration(){
		String query = "select * from configuration;";
		final Map<String,String> configuration = new HashMap<String,String>();
		List <Void> nothing = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				configuration.put(rs.getString(1), rs.getString(2));
				return null;
			}
		});

		return configuration;
	}
}