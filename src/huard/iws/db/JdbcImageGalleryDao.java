package huard.iws.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;

public class JdbcImageGalleryDao extends SimpleJdbcDaoSupport implements ImageGalleryDao{
	public static final boolean REVERSE_ORDER = false;

	public List<ImageGalleryItem> getImageGalleryItems(int parentId, PersonBean userBean){
		return imageGalleryMapDao.getImageGalleryMap(userBean).get(parentId).getSubItems();
	}
	
	
	public List<ImageGalleryItem> getImageGalleryItems(int parentId, final boolean reverseOrder, PersonBean userBean){
		logger.info("parent id: " + parentId);
		List<ImageGalleryItem> imageGalleryItems =
				imageGalleryMapDao.getImageGalleryMap(userBean).get(parentId).getSubItems();
		if (reverseOrder){
			Collections.sort(imageGalleryItems, new Comparator<ImageGalleryItem>() {
					@Override
					public int compare(ImageGalleryItem o1, ImageGalleryItem o2) {
						// TODO Auto-generated method stub
						return o2.getPlace() - o1.getPlace();
					}
			});
		}		
		return imageGalleryItems;
	}
	
	
	public List<ImageGalleryItem> getWebsiteImages(int website){
		String query = "select * from imageGallery where websiteId=? and url<>'' order by parentId, place";
		List<ImageGalleryItem> imageGalleryItems =
				getSimpleJdbcTemplate().query(query, rowMapper, website);
		return imageGalleryItems;
	}
	
	public ImageGalleryItem getImageGalleryItem(int id, PersonBean userBean){
		return imageGalleryMapDao.getImageGalleryMap(userBean).get(id);
	}

	ParameterizedRowMapper<ImageGalleryItem> rowMapper = new ParameterizedRowMapper<ImageGalleryItem>(){
		public ImageGalleryItem mapRow(ResultSet rs, int rowNum) throws SQLException{
            ImageGalleryItem imageGalleryItem = new ImageGalleryItem();
            imageGalleryItem.setId(rs.getInt("id"));
            imageGalleryItem.setParentId(rs.getInt("parentId"));
            imageGalleryItem.setUrl(rs.getString("url"));
            imageGalleryItem.setTitle(rs.getString("title"));
            imageGalleryItem.setText(rs.getString("text"));
            imageGalleryItem.setPlace(rs.getInt("place"));
             return imageGalleryItem;
        }
	};

	public void updateImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean){
		String query = "update imageGallery set parentId = ?, url = ?, title = ?, text = ?,place = ? where id = ?";
		getSimpleJdbcTemplate().update(query,
				imageGalleryItem.getParentId(),
				imageGalleryItem.getUrl(),
				imageGalleryItem.getTitle(),
				imageGalleryItem.getText(),
				imageGalleryItem.getPlace(),
				imageGalleryItem.getId()
		);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}

	public int insertImageGalleryItem(int websiteId, int parentId, Locale locale, PersonBean userBean){
		final String query = "insert imageGallery set parentId = ?, title = ?, text = ?, place = ?, websiteId = ?, url = ?";
		final int aParentId = parentId;
		final String title = messageSource.getMessage("website.page.newImageGalleryItem", null, locale);
		final String text = messageSource.getMessage("website.page.newImageGalleryItem", null, locale);
		final int place = getImageGalleryItems(parentId, userBean).size() + 1;
		final int aWebsiteId = websiteId;
		final String url = "/image/questionmark.png"; 
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1, aParentId);
		            ps.setString(2, title);
		            ps.setString(3, text);
		            ps.setInt(4, place);
		            ps.setInt(5, aWebsiteId);
		            ps.setString(6, url);
		            return ps;
		        }
		    },
		    keyHolder);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
		return keyHolder.getKey().intValue();
	}

	public void deleteImageGalleryItem(int id, PersonBean userBean){
		List<ImageGalleryItem> subItems = getImageGalleryItems(id, userBean);
		for (ImageGalleryItem item: subItems)
			deleteImageGalleryItem(item.getId(), userBean);
		final String query = "delete from imageGallery where id=?";
		getSimpleJdbcTemplate().update(query,id);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}
	
	public void rearangeImageGallery(int place, int parentId, PersonBean userBean){
		String query = "update imageGallery set place=place-1 where place>? and parentId=?;";
		getSimpleJdbcTemplate().update(query,place,parentId);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private ImageGalleryMapDao imageGalleryMapDao;

	public void setImageGalleryMapDao(ImageGalleryMapDao imageGalleryMapDao) {
		this.imageGalleryMapDao = imageGalleryMapDao;
	}
	
	
}
