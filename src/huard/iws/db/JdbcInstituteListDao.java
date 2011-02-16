package huard.iws.db;

import huard.iws.model.Institute;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcInstituteListDao extends SimpleJdbcDaoSupport implements InstituteListDao{

	public List<Institute> getInstitutes ( int countryId){
		String queryString = "select * from institute where countryId=? order by name";
		List<Institute> institutes =
			getSimpleJdbcTemplate().query(queryString,
					instituteDao.getRowMapper(), countryId);
		return institutes;


	}
	public List<Institute> getInstitutes (){
		String queryString = "select * from institute order by name";
		List<Institute> institutes =
			getSimpleJdbcTemplate().query(queryString,
					instituteDao.getRowMapper());
		return institutes;


	}

	private InstituteDao instituteDao;

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

}
