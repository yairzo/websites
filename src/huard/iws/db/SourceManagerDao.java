package huard.iws.db;

import huard.iws.model.SourceDescriptor;

import java.util.List;

/**
 * 
 * @author Leon
 *
 */
public interface SourceManagerDao {
	
	public List<SourceDescriptor> getActiveSources();
	
	public List<SourceDescriptor> getSources();
	
	public void insertSource(SourceDescriptor sourceDescriptor);
	
	public void deleteSource(int id);
	
	public void setActive(SourceDescriptor sourceDescriptor);
	
	public void setInactive(SourceDescriptor sourceDescriptor);

}
