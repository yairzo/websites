package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcHelperTableDao extends SimpleJdbcDaoSupport implements HelperTableDao{

	@SuppressWarnings("unused")
	public Map<String, String> getDisplayNamesMap(String tableName, String displayColumnName){
		String query = "select * from " + tableName ;
		final String aDisplayColumnName = displayColumnName;
		final Map<String, String> displayNamesMap = new HashMap<String,String>();
		List <Void> nothing = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				displayNamesMap.put("" + rs.getInt("id"), rs.getString(aDisplayColumnName));
				return null;
			}
		});
		return displayNamesMap;
	}

}
