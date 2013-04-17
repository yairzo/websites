package huard.iws.db;

import huard.iws.model.Language;
import huard.iws.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcSubjectDao extends SimpleJdbcDaoSupport implements SubjectDao {

	///private static final Logger logger = Logger.getLogger(JdbcSubjectDao.class);

	public Subject getSubject(int id){
		String subjectSelect = "select * from subject where id=?";
		Subject subject =
			getSimpleJdbcTemplate().queryForObject(subjectSelect, rowMapper, id);
			return subject;
	}


	ParameterizedRowMapper<Subject> rowMapper = new ParameterizedRowMapper<Subject>(){
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException{
            Subject subject = new Subject();
            subject.setId(rs.getInt("id"));
            subject.setNameHebrew(rs.getString("nameHebrew"));
            subject.setDescriptionHebrew(rs.getString("descriptionHebrew"));
            subject.setNameEnglish(rs.getString("nameEnglish"));
            subject.setDescriptionEnglish(rs.getString("descriptionEnglish"));
            subject.setParentId(rs.getInt("parentId"));
            return subject;
        }
	};


	public List<Subject> getSubjects(int parentSubjectId){
		return getSubjects(parentSubjectId, null);
	}

	public List<Subject> getSubjects(int parentSubjectId, Language language) {
		String mainOrderBy = "";
		if (language != null)
			mainOrderBy = "subjectOrder, name" + language.getName() + ",";
		String query = "select * from subject where parentId=? order by " + mainOrderBy + " id;";
		List<Subject> subjects =
			getSimpleJdbcTemplate().query(query, rowMapper, parentSubjectId);
		return subjects;
    }
	
	public List<Integer> getSubjectsIds(){
		String query = "select id from subject;";
		return getSimpleJdbcTemplate().query(query, subjectIdRowMapper);
	}
	private ParameterizedRowMapper<Integer> subjectIdRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer subjectId = rs.getInt("id");
            return subjectId;
		}
	};

}
