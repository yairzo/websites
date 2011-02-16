package huard.iws.db;

import huard.iws.model.Partner;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPartnerListDao extends SimpleJdbcDaoSupport implements PartnerListDao{

	public List<Partner> getPartners (){
		String queryString = "select * from partner order by name";
		List<Partner> partners =
			getSimpleJdbcTemplate().query(queryString,
					partnerDao.getPartnerRowMapper());
		return partners;
	}



	private PartnerDao partnerDao;

	public void setPartnerDao(PartnerDao partnerDao) {
		this.partnerDao = partnerDao;
	}

}
