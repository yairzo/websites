package huard.iws.db;

import huard.iws.model.Partner;
import huard.iws.model.PartnerInstitute;
import huard.iws.util.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcPartnerDao extends SimpleJdbcDaoSupport implements PartnerDao {

	//private static final Logger logger = Logger.getLogger(JdbcPartnerDao.class);


	public Partner getPartner(int id){
		String query = "select * from partner where id=?";
		List<Partner> partners  =
			getSimpleJdbcTemplate().query(query, partnerRowMapper,	id);
		return partners.get(0);
	}

	public void updatePartner(Partner partner){
		String query = "update partner set degree = ?, name = ?, instituteId = ? where id = ?";
		getSimpleJdbcTemplate().update(query,
				partner.getDegree(),
				partner.getName(),
				partner.getInstituteId(),
				partner.getId()
		);
	}


	public int insertPartner(Partner partner){
		final String proposalInsert = "insert partner set degree = ?, name = ?, instituteId = ?, creationDate = ? ;";
		final String degree = partner.getDegree();
		final String name = partner.getName();
		final int instituteId = partner.getInstituteId();
		final String creationDate = DateUtils.formatTimestampWithoutMillis(new java.util.Date().getTime());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setString(1, degree);
		            ps.setString(2, name);
		            ps.setInt(3, instituteId);
		            ps.setString(4, creationDate);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	ParameterizedRowMapper<Partner> partnerRowMapper = new ParameterizedRowMapper<Partner>(){
		public Partner mapRow(ResultSet rs, int rowNum) throws SQLException{
            Partner partner = new Partner();
            partner.setId(rs.getInt("id"));
            partner.setDegree(rs.getString("degree"));
            partner.setName(rs.getString("name"));
            partner.setInstituteId(rs.getInt("instituteId"));
            return partner;
        }
	};

	public ParameterizedRowMapper<Partner> getPartnerRowMapper() {
		return partnerRowMapper;
	}


	public PartnerInstitute getPartnerInstitute(int id){
		String query = "select * from partnerInstitute where id=?";
		List<PartnerInstitute> partnerInstitutes  =
			getSimpleJdbcTemplate().query(query, partnerInstituteRowMapper,	id);
		return partnerInstitutes.get(0);
	}


	public int insertPartnerInstitute(PartnerInstitute partnerInstitute){
		final String proposalInsert = "insert partnerInstitute set name = ?, creationDate = ? ;";
		final String name = partnerInstitute.getName();
		final String creationDate = DateUtils.formatTimestampWithoutMillis(new java.util.Date().getTime());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setString(2, creationDate);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	ParameterizedRowMapper<PartnerInstitute> partnerInstituteRowMapper = new ParameterizedRowMapper<PartnerInstitute>(){
		public PartnerInstitute mapRow(ResultSet rs, int rowNum) throws SQLException{
            PartnerInstitute partnerInstitute = new PartnerInstitute();
            partnerInstitute.setId(rs.getInt("id"));
            partnerInstitute.setName(rs.getString("name"));
            return partnerInstitute;
        }
	};

	public ParameterizedRowMapper<PartnerInstitute> getPartnerInstituteRowMapper() {
		return partnerInstituteRowMapper;
	}
}
