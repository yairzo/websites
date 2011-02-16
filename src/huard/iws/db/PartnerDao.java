package huard.iws.db;

import huard.iws.model.Partner;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface PartnerDao {

	public int insertPartner(Partner partner);

	public void updatePartner(Partner partner);

	public Partner getPartner(int id);

	public ParameterizedRowMapper<Partner> getPartnerRowMapper();



}
