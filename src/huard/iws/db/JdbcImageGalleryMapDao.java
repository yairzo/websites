package huard.iws.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcImageGalleryMapDao extends SimpleJdbcDaoSupport implements ImageGalleryMapDao{
	private static final Logger logger = Logger.getLogger(JdbcImageGalleryMapDao.class);
	
	   	
	public void invalidateImageGalleryMap(PersonBean userBean){
		return;
	}
	
	public Map<Integer, ImageGalleryItem> getImageGalleryMap (PersonBean userBean){

		String query = "select * from imageGallery order by place, id";
		logger.info(query);

		Map<Integer, ImageGalleryItem> imageGalleryItemsMap = new HashMap<Integer, ImageGalleryItem>();
		List<ImageGalleryItem> imageGalleryItems =
				getSimpleJdbcTemplate().query(query, rowMapper);
		imageGalleryItemsMap.put(0, new ImageGalleryItem() );
		for (ImageGalleryItem imageGalleryItem: imageGalleryItems){
			imageGalleryItemsMap.put(imageGalleryItem.getId(), imageGalleryItem );
		}
		
		for (ImageGalleryItem imageGalleryItem: imageGalleryItems){
			if (imageGalleryItem.getParentId() > 0){
				ImageGalleryItem parentImageGalleryItem = imageGalleryItemsMap.get(imageGalleryItem.getParentId());
				if (parentImageGalleryItem == null)
					continue;
				parentImageGalleryItem.getSubItems().add(imageGalleryItem);
			}
			else{
				ImageGalleryItem parentImageGalleryItem = imageGalleryItemsMap.get(0);
				if (parentImageGalleryItem == null)
					continue;
				parentImageGalleryItem.getSubItems().add(imageGalleryItem);
				
			}
		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx:"+imageGalleryItemsMap);
		return imageGalleryItemsMap;
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
             return imageGalleryItem;
        }
	};
	
	public void printImageGalleryMap(PersonBean userBean){
		for (int imageGalleryItemId: getImageGalleryMap(userBean).keySet()){
			System.out.println("id: " + imageGalleryItemId);
			for (ImageGalleryItem imageGalleryItem: getImageGalleryMap(userBean).get(imageGalleryItemId).getSubItems())
				System.out.println("sub item id: " + imageGalleryItem.getId());			
		}
	}

}
