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
		Faculty faculty =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper,	id);
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
		List<Faculty> faculties = getSimpleJdbcTemplate().query(query, rowMapper);
		return faculties;
	}


}
