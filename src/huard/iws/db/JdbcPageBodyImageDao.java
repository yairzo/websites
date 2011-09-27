package huard.iws.db;

import huard.iws.model.PageBodyImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcPageBodyImageDao extends SimpleJdbcDaoSupport implements PageBodyImageDao  {

	private final int NUM_IMAGES_PER_PAGE = 4;


	public int insertPageBodyImage( PageBodyImage pageBodyImage){
		final String pageInsert = "insert image set name = ?, image = ?, uploaderPersonId = ?";
		final String name = pageBodyImage.getName();
		final byte [] image = pageBodyImage.getImage();
		final int uploaderPersonId = pageBodyImage.getUploaderPersonId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(pageInsert, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setBytes(2, image);
		            ps.setInt(3, uploaderPersonId);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println("Int: " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}


	public PageBodyImage getPageBodyImage(int id){
		String query = "select * from image where id=?";
		PageBodyImage pageBodyImage =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return pageBodyImage;
	}

	public List<PageBodyImage> getPageBodyImages(int page){
		String query = "select * from image order by creationTime desc limit "
			+ page * NUM_IMAGES_PER_PAGE + "," + NUM_IMAGES_PER_PAGE;
		System.out.println("Before loading images !!!!!");
		List<PageBodyImage> pageBodyImages =
			getSimpleJdbcTemplate().query(query, rowMapper);
		System.out.println("After loading images !!!!!");
			return pageBodyImages;
	}

	ParameterizedRowMapper<PageBodyImage> rowMapper = new ParameterizedRowMapper<PageBodyImage>(){
		public PageBodyImage mapRow(ResultSet rs, int rowNum) throws SQLException{
            PageBodyImage pageBodyImage = new PageBodyImage();
            pageBodyImage.setId(rs.getInt("id"));
            pageBodyImage.setName(rs.getString("name"));
            pageBodyImage.setImage(rs.getBytes("image"));
            return pageBodyImage;
        }
	};

	public void deletePageBodyImage(int id){
		String query = "delete from image where id = ?";
		getSimpleJdbcTemplate().update(query, id);
	}

	public int countImages(){
		//String query = "select count(*) from image where websiteId = ?";
		String query = "select count(*) from image";
		return getSimpleJdbcTemplate().queryForInt(query);
	}


}
