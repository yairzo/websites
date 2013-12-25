package huard.iws.db;

import huard.iws.model.Institute;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcInstituteListDao extends SimpleJdbcDaoSupport implements InstituteListDao{

	public List<Institute> getInstitutes ( int countryId){
		String query = "select * from institute where countryId=? order by name";
		logger.debug(query);
		List<Institute> institutes =
			getSimpleJdbcTemplate().query(query,
					instituteDao.getRowMapper(), countryId);
		return institutes;


	}
	public List<Institute> getInstitutes (){
		String query = "select * from institute order by name";
		logger.debug(query);
		List<Institute> institutes =
			getSimpleJdbcTemplate().query(query,
					instituteDao.getRowMapper());
		return institutes;


	}

	private InstituteDao instituteDao;

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

}
