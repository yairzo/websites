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


	public int insertPageBodyImage( PageBodyImage pageBodyImage){
		if(pageBodyImage.getTitle().isEmpty())
			pageBodyImage.setTitle("###" + new java.util.Date().getTime() + "###");

		final String pageInsert = "insert image set name = ?, captionHebrew = '', captionEnglish = '', image = ?, uploaderPersonId = ?, approved = ?, url=?, urlTitle=?";
		logger.debug(pageInsert);
		final String name = pageBodyImage.getName();
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
		            ps.setBytes(2, image);
		            ps.setInt(3, uploaderPersonId);
		            ps.setInt(4, approved);
		            ps.setString(5, url);
		            ps.setString(6, title);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println("Int: " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}


	public PageBodyImage getPageBodyImage(int id){
		PageBodyImage pageBodyImage =new PageBodyImage();
		String query = "select * from image where id=?";
		logger.debug(query);
		try{
		 pageBodyImage =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		 	return pageBodyImage;
		}
		catch(Exception e){
			return pageBodyImage;
		}
			
	}
	
	public PageBodyImage getPageBodyImage(String urlTitle){
		PageBodyImage pageBodyImage =new PageBodyImage();
		String query = "select * from image where urlTitle=?";
		logger.debug(query);
		try{
		 pageBodyImage =	getSimpleJdbcTemplate().queryForObject(query, rowMapper, urlTitle);
			return pageBodyImage;
		}
		catch(Exception e){
			return pageBodyImage;
		}
	}

	public List<PageBodyImage> getPageBodyImages(int imgsPerPage, int page, PersonBean personBean){
		String query = "select * from image";
		if(personBean.isAuthorized("IMAGE","RESEARCHER"))
			query+=" where uploaderPersonId=" + personBean.getId();
		query += " order by creationTime desc limit " + page * imgsPerPage + "," + imgsPerPage;
		logger.debug(query);
		List<PageBodyImage> pageBodyImages =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return pageBodyImages;
	}
	
	public List<PageBodyImage> getFilteredPageBodyImages(String term){
		String query = "select * from image where name like '%"+term+"%' order by creationTime desc ";
		logger.info(query);
		List<PageBodyImage> pageBodyImages =getSimpleJdbcTemplate().query(query, rowMapper);
		return pageBodyImages;
	}


	public List<PageBodyImage> getApprovedPageBodyImages(String localeId){
		String query = "select * from image where approved=1 order by creationTime";
		if(localeId.equals("en_US"))
			query+=" asc";
		else
			query+=" desc";
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

	public int countImagePages(int imgsPerPage){
		int images= countImages();
		return (int)Math.ceil(images/imgsPerPage-1);
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
