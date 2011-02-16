package huard.iws.service;
import java.util.List;

public interface TableDescriptionService {

	public List<String> getColumnsList(String tableName);

	public boolean isTableExists(String tableName);

}
