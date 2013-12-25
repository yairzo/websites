package huard.iws.db;

import huard.iws.model.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcFacultyDao extends SimpleJdbcDaoSupport implements FacultyDao {

	public Faculty getFaculty(int id){
		String query = "select * from faculty where id=?";
		logger.debug(query);
		Faculty faculty =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper,	id);
		return faculty;
	}
	
	public int getFacultyByNameHebrew(String nameHebrew){
		String query = "select id from faculty where nameHebrew=?;";
		logger.debug(query);
		int faculty =
			getSimpleJdbcTemplate().queryForInt(query, nameHebrew);
		return faculty;
	}
	
	private ParameterizedRowMapper<Faculty> rowMapper = new ParameterizedRowMapper<Faculty>(){
		public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException{
            Faculty faculty = new Faculty();
            faculty.setId(rs.getInt("id"));
           faculty.setNameHebrew(rs.getString("nameHebrew"));
           faculty.setNameEnglish(rs.getString("nameEnglish"));
            return faculty;
        }
	};

	public List<Faculty> getFaculties(){
		String query = "select * from faculty;";
		logger.debug(query);
		List<Faculty> faculties = getSimpleJdbcTemplate().query(query, rowMapper);
		return faculties;
	}


}
