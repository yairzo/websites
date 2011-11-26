package huard.iws.db;

import huard.iws.model.InitiatingBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcInitiatingBodyDao extends SimpleJdbcDaoSupport implements InitiatingBodyDao {

	public InitiatingBody getInitiatingBody(int id){
		String query = "select * from initiatingBody where id=?";
		InitiatingBody initiatingBody =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper,	id);
		return initiatingBody;
	}

	private ParameterizedRowMapper<InitiatingBody> rowMapper = new ParameterizedRowMapper<InitiatingBody>(){
		public InitiatingBody mapRow(ResultSet rs, int rowNum) throws SQLException{
			InitiatingBody initiatingBody = new InitiatingBody();
			initiatingBody.setId(rs.getInt("id"));
			initiatingBody.setNameHebrew(rs.getString("nameHebrew"));
			initiatingBody.setNameEnglish(rs.getString("nameEnglish"));
            return initiatingBody;
        }
	};

	public List<InitiatingBody> getInitiatingBodies(){
		String query = "select * from initiatingBody;";
		List<InitiatingBody> initiatingBodies = getSimpleJdbcTemplate().query(query, rowMapper);
		return initiatingBodies;
	}


}
