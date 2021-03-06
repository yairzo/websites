package huard.iws.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	
	public  List<ImageGalleryItem> getGalleries(){
		String query = "select * from imageGallery where parentId=0";
		logger.info(query);
		List<ImageGalleryItem> imageGalleryItems =	getSimpleJdbcTemplate().query(query, rowMapper);
		return imageGalleryItems;
	}

	/*public List<ImageGalleryItem> getWebsiteImages(int website){
		String query = "select * from imageGallery where websiteId=? and url<>'' order by parentId, place";
		List<ImageGalleryItem> imageGalleryItems =
				getSimpleJdbcTemplate().query(query, rowMapper, website);
		return imageGalleryItems;
	}*/
	
	public ImageGalleryItem getImageGalleryItem(int id, PersonBean userBean){
		return imageGalleryMapDao.getImageGalleryMap(userBean).get(id);
	}
	public int getCategory (String urlTitle){
		String query = "select id from imageGallery where url = ?";
		return getSimpleJdbcTemplate().queryForInt(query,urlTitle);
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
            imageGalleryItem.setLevel(rs.getInt("level")); 
            imageGalleryItem.setIsLink(rs.getBoolean("isLink"));
            imageGalleryItem.setTextualPageUrlTitle(rs.getString("textualPageUrlTitle"));
            return imageGalleryItem;
        }
	};

	public void updateImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean){
		String query = "update imageGallery set parentId = ?, url = ?, title = ?, text = ?,place = ?,level=?,isLink=?,textualPageUrlTitle=? where id = ?";
		getSimpleJdbcTemplate().update(query,
				imageGalleryItem.getParentId(),
				imageGalleryItem.getUrl(),
				imageGalleryItem.getTitle(),
				imageGalleryItem.getText(),
				imageGalleryItem.getPlace(),
				imageGalleryItem.getLevel(),
				imageGalleryItem.isLink(),
				imageGalleryItem.getTextualPageUrlTitle(),
				imageGalleryItem.getId()
		);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}

	public int insertImageGalleryItem(int parentId, Locale locale, PersonBean userBean){
		final String query = "insert imageGallery set parentId = ?, place = ?";
		final int aParentId = parentId;
		final int place = getImageGalleryItems(parentId, userBean).size() + 1;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1, aParentId);
		            ps.setInt(2, place);
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
	public void insertImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean){
		String query = "insert into imageGallery set parentId=?, place=?, text=?, title=?,level=?,isLink=?, url=?, textualPageUrlTitle=?";
		getSimpleJdbcTemplate().update(query,imageGalleryItem.getParentId(),imageGalleryItem.getPlace(),imageGalleryItem.getText(),imageGalleryItem.getTitle(),imageGalleryItem.getLevel(),imageGalleryItem.isLink(),imageGalleryItem.getUrl(),imageGalleryItem.getTextualPageUrlTitle());
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}

	public void prepareDeleteOldCategoryItems (int categoryId, PersonBean userBean){
		String query = "update imageGallery set deleted=1 where parentId=?;";
		getSimpleJdbcTemplate().update(query,categoryId);
		imageGalleryMapDao.invalidateImageGalleryMap(userBean);
	}

	public void deleteOldCategoryItems (int categoryId, PersonBean userBean){
		String query = "delete from imageGallery where parentId=? and deleted=1 ;";
		getSimpleJdbcTemplate().update(query,categoryId);
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
