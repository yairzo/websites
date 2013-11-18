package huard.iws.db;

import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;
import huard.iws.model.OrganizationUnit.OrganizationUnitType;
import huard.iws.util.ListView;
import huard.iws.util.SQLUtils;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcOrganizationUnitDao extends SimpleJdbcDaoSupport implements OrganizationUnitDao{

	private static final Logger logger = Logger.getLogger(JdbcOrganizationUnitDao.class);

	public List<OrganizationUnit> getOrganizationUnits(){
		String query = "select * from organizationUnit order by nameHebrew;";
			List<OrganizationUnit> organizationUnits =
				getSimpleJdbcTemplate().query(query, rowMapper);
			return organizationUnits;
	}

	public List<OrganizationUnit> getOrganizationUnits(int listId, String orderStatement){
		String query = "select * from organizationUnit, organizationUnitAttribution "+
			"where organizationUnit.id = organizationUnitAttribution.organizationUnitId " +
			" and organizationUnitAttribution.listId= ? order by "+orderStatement+";";
			List<OrganizationUnit> organizationUnits =
				getSimpleJdbcTemplate().query(query, rowMapper, listId);
			return organizationUnits;
	}

	public List<OrganizationUnit> getOrganizationUnits(ListView lv, SearchCreteria search){
		StringBuilder query = new StringBuilder("select * from organizationUnit where id > 0");
		if (search != null){
			query.append(" and " +search.getSearchField()+" like ?");
		}
		query.append(" order by nameHebrew;");
		List<OrganizationUnit> organizationUnits = getSimpleJdbcTemplate().query(query.toString(), rowMapper,"%"+search.getSearchPhrase()+"%");
		return organizationUnits;
	}

	public OrganizationUnit getOrganizationUnit (int id){
		String query = "select * from organizationUnit where id = ?";
		OrganizationUnit organizationUnit = getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		return organizationUnit;
	}

	public OrganizationUnit getOrganizationUnit (String name){
		String query = "select * from organizationUnit where nameHebrew = ? or nameEnglish = ?";
		OrganizationUnit organizationUnit = getSimpleJdbcTemplate().queryForObject(query, rowMapper, name, name);
		return organizationUnit;
	}

	public void updateOrganizationUnit (OrganizationUnit organizationUnit){
		String query = "update organizationUnit set typeId = ?, nameHebrew = ?, nameEnglish = ?," +
				" email = ?, websiteUrl = ?, phone = ?, fax = ?, address = ?, contact = ?," +
				" facultyId = ? where id = ?";
		getSimpleJdbcTemplate().update(query,
				organizationUnit.getTypeId(),
				organizationUnit.getNameHebrew(),
				organizationUnit.getNameEnglish(),
				organizationUnit.getEmail(),
				organizationUnit.getWebsiteUrl(),
				organizationUnit.getPhone(),
				organizationUnit.getFax(),
				organizationUnit.getAddress(),
				organizationUnit.getContact(),
				organizationUnit.getFacultyId(),
				organizationUnit.getId() );
	}

	public int insertOrganizationUnit(){
		final String query = "insert organizationUnit set nameHebrew=''";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		           return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deleteOrganizationUnit(int id){
		String query = "delete from organizationUnit where id = ?";
		getSimpleJdbcTemplate().update(query, id);
	}


	ParameterizedRowMapper<OrganizationUnit> rowMapper = new ParameterizedRowMapper<OrganizationUnit>(){

		public OrganizationUnit mapRow(ResultSet rs, int rowNum) throws SQLException{
			OrganizationUnit organizationUnit = new OrganizationUnit();
			organizationUnit.setId(rs.getInt("id"));
			organizationUnit.setTypeId(rs.getInt("typeId"));
			organizationUnit.setNameHebrew(rs.getString("nameHebrew"));
			organizationUnit.setNameEnglish(rs.getString("nameEnglish"));
			organizationUnit.setEmail(rs.getString("email"));
			organizationUnit.setWebsiteUrl(rs.getString("websiteUrl"));
			organizationUnit.setPhone(rs.getString("phone"));
			organizationUnit.setFax(rs.getString("fax"));
			organizationUnit.setAddress(rs.getString("address"));
			organizationUnit.setContact(rs.getString("contact"));
			organizationUnit.setPlaceInList(rs.getInt("placeInList"));
			organizationUnit.setFacultyId(rs.getInt("facultyId"));

			organizationUnit.prepareForView();
			return organizationUnit;
		}
	};

	public List<OrganizationUnitType> getOrganizationUnitTypes(){
		String query = "select * from organizationUnitType;";
		List<OrganizationUnitType> organizationUnitTypes = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<OrganizationUnitType>(){
			public OrganizationUnitType mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrganizationUnit organizationUnit = new OrganizationUnit();
				OrganizationUnitType organizationUnitType =organizationUnit.new OrganizationUnitType();
				organizationUnitType.setId(rs.getInt("id"));
				organizationUnitType.setNameHebrew(rs.getString("nameHebrew"));
				organizationUnitType.setNameEnglish(rs.getString("nameEnglish"));
				return organizationUnitType;
			}

		});
		return organizationUnitTypes;
	}

	public List<OrganizationUnitAttribution> getOrganizationUnitAttributions(int organizationUnitId){
		String query = "select * from organizationUnitAttribution where organizationUnitId = ?";
		List<OrganizationUnitAttribution>  organiztionUnitAttributions = getSimpleJdbcTemplate().query(query,
				new ParameterizedRowMapper<OrganizationUnitAttribution>(){
					public OrganizationUnitAttribution mapRow(ResultSet rs, int rowNum) throws SQLException{
						OrganizationUnitAttribution organizationUnitAttribution = new OrganizationUnitAttribution();
						organizationUnitAttribution.setId(rs.getInt("id"));
						organizationUnitAttribution.setOrganizationUnitId(rs.getInt("organizationUnitId"));
						organizationUnitAttribution.setListId(rs.getInt("listId"));
						organizationUnitAttribution.setLastUpdateTime(rs.getTimestamp("lastUpdateTime"));
						return organizationUnitAttribution;
					}
		}, organizationUnitId);
		return organiztionUnitAttributions;
	}

	public void deleteOrganizationUnitAttribution (int organizationUnitAttributionId){
		String query = "delete from organizationUnitAttribution where id = ?";
		getSimpleJdbcTemplate().update(query, organizationUnitAttributionId);
	}

	public void insertOrganizationUnitAttribution (OrganizationUnitAttribution organizationUnitAttribution){
		String query = "insert organizationUnitAttribution set organizationUnitId = ?, listId = ?";
		logger.info(query + " " + organizationUnitAttribution.getOrganizationUnitId() + " " + organizationUnitAttribution.getListId());
		getSimpleJdbcTemplate().update(query, organizationUnitAttribution.getOrganizationUnitId(), organizationUnitAttribution.getListId());
	}
}
