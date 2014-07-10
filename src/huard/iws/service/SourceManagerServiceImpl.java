package huard.iws.service;

import huard.iws.db.SourceManagerDao;
import huard.iws.model.SourceDescriptor;
import huard.iws.service.articleSources.Source;

import java.util.ArrayList;
import java.util.List;

public class SourceManagerServiceImpl implements SourceManagerService {

	private SourceManagerDao sourceManagerDao;
	
	public void setSourceManagerDao(SourceManagerDao sourceManagerDao) {
		this.sourceManagerDao = sourceManagerDao;
	}

	
	@Override
	public List<Source> getActiveSources() {
		List<Source> sources = new ArrayList<Source>();
		List<SourceDescriptor> sourceDescriptors = sourceManagerDao.getActiveSources();
		
		// Reflection ahead. debug with caution
		
		try {
			for (SourceDescriptor sourceDescriptor : sourceDescriptors) {
				@SuppressWarnings("unchecked")
				Class<Source> c = (Class<Source>) Class.forName("huard.iws.service.articleSources.Source_"+sourceDescriptor.getName());
				sources.add(c.newInstance());
			}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
		
		return sources;
	}

}
