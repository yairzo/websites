package huard.iws.db;

import huard.iws.model.SourceDescriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcSourceManagerDao extends SimpleJdbcDaoSupport implements SourceManagerDao {
	
	private static final Logger logger = Logger.getLogger(JdbcSourceManagerDao.class);
	
	private ParameterizedRowMapper<SourceDescriptor> sourceDescriptorRowMapper = new ParameterizedRowMapper<SourceDescriptor>(){
		public SourceDescriptor mapRow(ResultSet rs, int rowNum) throws SQLException{
			SourceDescriptor sourceDescriptor = new SourceDescriptor();
			sourceDescriptor.setId(rs.getInt("id"));
			sourceDescriptor.setName(rs.getString("name"));
			sourceDescriptor.setActive(rs.getBoolean("isActive"));
            return sourceDescriptor;
        }
	};

	@Override
	public List<SourceDescriptor> getActiveSources() {
		String query = "SELECT * FROM sources WHERE isActive = ?";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, sourceDescriptorRowMapper, 1);
	}

	@Override
	public List<SourceDescriptor> getSources() {
		String query = "SELECT * FROM sources";
		logger.debug(query);
		return getSimpleJdbcTemplate().query(query, sourceDescriptorRowMapper);
	}

	@Override
	public void insertSource(SourceDescriptor sourceDescriptor) {
		String query = "INSERT INTO sources (name, isActive) VALUES (?, ?)";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, sourceDescriptor.getName(), sourceDescriptor.isActive());
	}

	@Override
	public void deleteSource(int id) {
		String query = "DELETE FROM sources WHERE id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	@Override
	public void setActive(SourceDescriptor sourceDescriptor) {
		String query = "UPDATE sources SET isActive = 1 WHERE id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, sourceDescriptor.getId());
	}

	@Override
	public void setInactive(SourceDescriptor sourceDescriptor) {
		String query = "UPDATE sources SET isActive = 0 WHERE id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, sourceDescriptor.getId());
	}

}
