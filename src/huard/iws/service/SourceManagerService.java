package huard.iws.service;

import huard.iws.service.articleSources.Source;

import java.util.List;

public interface SourceManagerService {
	
	public List<Source> getActiveSources();

}
