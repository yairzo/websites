package huard.iws.db;

import huard.iws.model.MopDesk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class JdbcMopDeskDao extends SimpleJdbcDaoSupport implements MopDeskDao {

	public List<MopDesk> getMopDesks (){
		String queryString = "select * from desk order by appearence";
		List<MopDesk> mopDesks =
			getSimpleJdbcTemplate().query(queryString, getRowMapper());
		return mopDesks;
	}

	ParameterizedRowMapper<MopDesk> rowMapper = new ParameterizedRowMapper<MopDesk>(){
		public MopDesk mapRow(ResultSet rs, int rowNum) throws SQLException{
            MopDesk mopDesk = new MopDesk();
            mopDesk.setId(rs.getInt("id"));
            mopDesk.setDeskId(rs.getString("deskId"));
            mopDesk.setHebrewName(rs.getString("hebrewName"));
            mopDesk.setEnglishName(rs.getString("englishName"));
            mopDesk.setAppearence(rs.getInt("appearence"));
            mopDesk.setPersonsListId(rs.getInt("personsListId"));
            mopDesk.setPersonsListIdEnglish(rs.getInt("personsListIdEnglish"));
            return mopDesk;
        }
	};

	public MopDesk getMopDesk(int mopDeskId){
		try{
			String queryString = "select * from desk where id = ?";
			MopDesk mopDesk =getSimpleJdbcTemplate().queryForObject(queryString, getRowMapper(), mopDeskId);
			return mopDesk;
		}
		catch(Exception e){
			return new MopDesk();
		}
	}

	public MopDesk getMopDesk(String mopDeskId){
		try{
			String queryString = "select * from desk where deskId = ?";
			MopDesk mopDesk =	getSimpleJdbcTemplate().queryForObject(queryString, getRowMapper(), mopDeskId);
			return mopDesk;
		}
		catch(Exception e){
			return new MopDesk();
		}
	}


	public ParameterizedRowMapper<MopDesk> getRowMapper() {
		return rowMapper;
	}

}
