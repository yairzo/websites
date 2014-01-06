package huard.iws.db;

import huard.iws.model.PageBodyImage;
import huard.iws.bean.PersonBean;

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
		pageBodyImage.setTitle("###" + new java.util.Date().getTime() + "###");

		final String pageInsert = "insert image set name = ?, captionHebrew = ?, captionEnglish = ?, image = ?, uploaderPersonId = ?, approved = ?, url=?, urlTitle=?";
		logger.debug(pageInsert);
		final String name = pageBodyImage.getName();
		final String captionHebrew = pageBodyImage.getCaptionHebrew();
		final String captionEnglish = pageBodyImage.getCaptionEnglish();
		final byte [] image = pageBodyImage.getImage();
		final int uploaderPersonId = pageBodyImage.getUploaderPersonId();
		final int approved = pageBodyImage.getApproved();
		final String url = pageBodyImage.getUrl();
		final String title = pageBodyImage.getTitle();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(pageInsert, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setString(2, captionHebrew);
		            ps.setString(3, captionEnglish);
		            ps.setBytes(4, image);
		            ps.setInt(5, uploaderPersonId);
		            ps.setInt(6, approved);
		            ps.setString(7, url);
		            ps.setString(8, title);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println("Int: " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}


	public PageBodyImage getPageBodyImage(int id){
		String query = "select * from image where id=?";
		logger.debug(query);
		PageBodyImage pageBodyImage =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return pageBodyImage;
	}
	
	public PageBodyImage getPageBodyImage(String urlTitle){
		String query = "select * from image where urlTitle=?";
		logger.debug(query);
		PageBodyImage pageBodyImage =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, urlTitle);
			return pageBodyImage;
	}

	public List<PageBodyImage> getPageBodyImages(int page, PersonBean personBean){
		String query = "select * from image";
		if(personBean.isAuthorized("IMAGE","RESEARCHER"))
			query+=" where uploaderPersonId=" + personBean.getId();
		query += " order by creationTime desc limit " + page * NUM_IMAGES_PER_PAGE + "," + NUM_IMAGES_PER_PAGE;
		logger.debug(query);
		List<PageBodyImage> pageBodyImages =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return pageBodyImages;
	}
	


	public List<PageBodyImage> getApprovedPageBodyImages(){
		String query = "select * from image where approved=1 order by creationTime desc";
		logger.debug(query);
		List<PageBodyImage> pageBodyImages =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return pageBodyImages;
	}

	ParameterizedRowMapper<PageBodyImage> rowMapper = new ParameterizedRowMapper<PageBodyImage>(){
		public PageBodyImage mapRow(ResultSet rs, int rowNum) throws SQLException{
            PageBodyImage pageBodyImage = new PageBodyImage();
            pageBodyImage.setId(rs.getInt("id"));
            pageBodyImage.setName(rs.getString("name"));
            pageBodyImage.setCaptionHebrew(rs.getString("captionHebrew"));
            pageBodyImage.setCaptionEnglish(rs.getString("captionEnglish"));
            pageBodyImage.setImage(rs.getBytes("image"));
            pageBodyImage.setUploaderPersonId(rs.getInt("uploaderPersonId"));
            pageBodyImage.setApproved(rs.getInt("approved"));
            pageBodyImage.setUrl(rs.getString("url"));
            pageBodyImage.setTitle(rs.getString("urlTitle"));
            return pageBodyImage;
        }
	};

	public void deletePageBodyImage(int id){
		String query = "delete from image where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	public void approvePageBodyImage(int id){
		String query = "update image set approved=1 where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	public void disapprovePageBodyImage(int id){
		String query = "update image set approved=0 where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}
	
	public int countImages(){
		//String query = "select count(*) from image where websiteId = ?";
		String query = "select count(*) from image";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public void updatePageBodyImage(PageBodyImage pageBodyImage){
		String query = "update image set name=?, captionHebrew = ?, captionEnglish = ?,image = ?,  url=?, urlTitle=?  where id=?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
			pageBodyImage.getName(),
			pageBodyImage.getCaptionHebrew(),
			pageBodyImage.getCaptionEnglish(),
			pageBodyImage.getImage(),
			pageBodyImage.getUrl(),
			pageBodyImage.getTitle(),
			pageBodyImage.getId());
	}

	public int getMaxImageId(){
		//String query = "select count(*) from image where websiteId = ?";
		String query = "select max(id) from image";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
}
