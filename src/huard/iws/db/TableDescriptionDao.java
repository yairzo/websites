package huard.iws.db;
import java.util.List;

public interface TableDescriptionDao {

	public List<String> getColumnsList(String tableName);

	public boolean isTableExists(String tableName);

}
