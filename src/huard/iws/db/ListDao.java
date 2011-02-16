package huard.iws.db;

import huard.iws.model.AList;
import huard.iws.model.AListDesign;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface ListDao {

	public AList getList(int id);

	public void updateList(AList aList);

	public int insertList(AList aList);

	//public int getListIdByListName(String listName);

	public void deleteList(int id);

	public ParameterizedRowMapper<AList> getRowMapper();

	public void deleteSublist(int listId, int sublistId);

	public List<AList> getSublists(int listId);

	public AList getSublist(int listId, int sublistId);

	public AListDesign getListDesign(int listId, int parentListId);

	public void updateListDesign (AListDesign aListDesign);

	public void deleteListDesign (int listId, int parentListId);

	public void insertListDesign (int listId, int parentListId);

}
