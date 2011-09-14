package huard.iws.db;

import huard.iws.model.AList;
import huard.iws.model.AListDesign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcListDao extends SimpleJdbcDaoSupport implements ListDao{

	public int test;

	public AList getList(int id){
		String query = "select * from list where id=?";
		AList aList =
			getSimpleJdbcTemplate().queryForObject(query,
					rowMapper,	id);
		aList.setSublists(getSublists(id));
		return aList;
	}

	public List<AList> getSublists (int listId){
		String query = "select listToSublist.location, list.* from listToSublist, list where listToSublist.sublistId = list.id and listToSublist.listId = ? order by location";
		List<AList> sublists = getSimpleJdbcTemplate().query(query, locationRowMapper, listId);
		return sublists;
	}

	ParameterizedRowMapper<AList> rowMapper  = new ParameterizedRowMapper<AList>(){

		public AList mapRow(ResultSet rs, int rowNum) throws SQLException{
            AList aList = new AList();
            aList.setId(rs.getInt("id"));
            aList.setName(rs.getString("name"));
            aList.setDisplayName(rs.getString("displayName"));
            aList.setDisplayNameAligned(rs.getBoolean("displayNameAligned"));
            aList.setOwner(rs.getString("owner"));
            aList.setSendMailToListEnabled(rs.getBoolean("sendMailToListEnabled"));
            aList.setSortEnabled(rs.getBoolean("sortEnabled"));
            aList.setCompound(rs.getBoolean("isCompound"));
            aList.setPublic(rs.getBoolean("isPublic"));
            aList.setOpen(rs.getBoolean("isOpen"));
            aList.setListTypeId(rs.getInt("listTypeId"));
            aList.setPreface(rs.getString("preface"));
            aList.setFooter(rs.getString("footer"));
            aList.setLastUpdate(rs.getTimestamp("lastUpdate").getTime());
            return aList;
        }
	};

	ParameterizedRowMapper<AList> locationRowMapper  = new ParameterizedRowMapper<AList>(){

		public AList mapRow(ResultSet rs, int rowNum) throws SQLException{
            AList aList = new AList();
            aList.setId(rs.getInt("id"));
            aList.setName(rs.getString("name"));
            aList.setDisplayName(rs.getString("displayName"));
            aList.setDisplayNameAligned(rs.getBoolean("displayNameAligned"));
            aList.setOwner(rs.getString("owner"));
            aList.setSendMailToListEnabled(rs.getBoolean("sendMailToListEnabled"));
            aList.setSortEnabled(rs.getBoolean("sortEnabled"));
            aList.setCompound(rs.getBoolean("isCompound"));
            aList.setPublic(rs.getBoolean("isPublic"));
            aList.setListTypeId(rs.getInt("listTypeId"));
            aList.setPreface(rs.getString("preface"));
            aList.setFooter(rs.getString("footer"));
            aList.setLastUpdate(rs.getTimestamp("lastUpdate").getTime());
            aList.setLocation(rs.getInt("listToSublist.location"));
            return aList;
        }
	};

	public void updateList(AList aList){
		String query = "update list set name = ?, displayName = ?, displayNameAligned = ?"+
			", owner = ?, sendMailToListEnabled = ?, sortEnabled = ?, isCompound = ?"+
			", isPublic = ?, isOpen = ?, listTypeId = ?, preface = ?, footer = ?, lastUpdate = ? where id = ?";
		getSimpleJdbcTemplate().update(query,
				aList.getName(),
				aList.getDisplayName(),
				aList.isDisplayNameAligned(),
				aList.getOwner(),
				aList.isSendMailToListEnabled(),
				aList.isSortEnabled(),
				aList.isCompound(),
				aList.isPublic(),
				aList.isOpen(),
				aList.getListTypeId(),
				aList.getPreface(),
				aList.getFooter(),
				new Timestamp(aList.getLastUpdate()),
				aList.getId()
			);
		query = "delete from listToSublist where listId = ?";
		getSimpleJdbcTemplate().update(query, aList.getId()	);
		for (AList sublist: aList.getSublists()){
			query = "insert listToSublist set listId = ?, sublistId = ? , location = ?";
			getSimpleJdbcTemplate().update(query, aList.getId()	, sublist.getId(), sublist.getLocation());
		}
	}

	public int insertList(AList aList){
		final String listInsert = "insert list set name = ?, displayName = ?, displayNameAligned = ?, owner = ?" +
				", sendMailToListEnabled = ?, sortEnabled = ?, isCompound = ?, isPublic = ?, isOpen = ?, listTypeId = ?" +
				", preface = ?, footer = ?, lastUpdate = ?";
		final String name = aList.getName();
		final String displayName = aList.getDisplayName();
		final boolean displayNameAligned = aList.isDisplayNameAligned();
		final String owner = aList.getOwner();
		final boolean sendMailToListEnabled = aList.isSendMailToListEnabled();
		final boolean sortEnabled = aList.isSortEnabled();
		final boolean compound = aList.isCompound();
		final boolean isPublic = aList.isPublic();
		final boolean isOpen = aList.isOpen();
		final int listTypeId = aList.getListTypeId();
		final String preface = aList.getPreface();
		final String footer = aList.getFooter();
		final long lastUpdate = aList.getLastUpdate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(listInsert, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setString(2, displayName);
		            ps.setBoolean(3, displayNameAligned);
		            ps.setString(4, owner);
		            ps.setBoolean(5, sendMailToListEnabled);
		            ps.setBoolean(6, sortEnabled);
		            ps.setBoolean(7, compound);
		            ps.setBoolean(8, isPublic);
		            ps.setBoolean(9, isOpen);
		            ps.setInt(10, listTypeId);
		            ps.setString(11, preface);
		            ps.setString(12, footer);
		            ps.setTimestamp(13, new Timestamp(lastUpdate));
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deleteList(int id){
		String query = "delete from list where id = ?";
		getSimpleJdbcTemplate().update(query, id );
	}

	public void deleteSublist(int listId, int sublistId){
		String query = "delete from listToSublist where listId = ? and sublistId = ?";
		getSimpleJdbcTemplate().update(query, listId, sublistId );
	}

	public AList getSublist(int listId, int sublistId){
		String query = "select * from list, listToSublist where list.id = listToSublist.listId and listId = ? and sublistId = ?";
		AList list = getSimpleJdbcTemplate().queryForObject(query, rowMapper, listId, sublistId );
		return list;
	}

	public AListDesign getListDesign(int listId, int parentListId){
		String query = "select * from listDesign where listId = ? and parentListId = ?";
		AListDesign listDesign = getSimpleJdbcTemplate().queryForObject(query,
				new ParameterizedRowMapper<AListDesign>()
				{
					public AListDesign mapRow(ResultSet rs, int rowNum) throws SQLException{
						AListDesign aListDesign = new AListDesign();
						aListDesign.setId(rs.getInt("id"));
						aListDesign.setListId(rs.getInt("listId"));
						aListDesign.setParentListId(rs.getInt("parentListId"));
						aListDesign.setDisplayNameAlignment(rs.getString("displayNameAlignment"));
						aListDesign.setBottomPadding(rs.getInt("bottomPadding"));
						aListDesign.setShowTableHeader(rs.getBoolean("showTableHeader"));
						return aListDesign;
					}
				}
		, listId, parentListId );
		return listDesign;
	}

	public void updateListDesign (AListDesign aListDesign){
		String query = "update listDesign set listId = ?, parentListId = ?, displayNameAlignment = ?" +
				", bottomPadding = ?, showTableHeader = ? where id = ?";
		getSimpleJdbcTemplate().update(query,
				aListDesign.getListId(),
				aListDesign.getParentListId(),
				aListDesign.getDisplayNameAlignment(),
				aListDesign.getBottomPadding(),
				aListDesign.isShowTableHeader(),
				aListDesign.getId()
		);
	}

	public void deleteListDesign (int listId, int parentListId){
		String query = "delete from listDesign where listId = ? and parentListId = ?";
		getSimpleJdbcTemplate().update(query, listId, parentListId);
	}

	public void insertListDesign (int listId, int parentListId){
		String query = "insert listDesign set listId = ?, parentListId = ?";
		getSimpleJdbcTemplate().update(query, listId, parentListId);
	}


	public ParameterizedRowMapper<AList> getRowMapper() {
		return rowMapper;
	}
	
	
	public void setLastUpdate ( int listId ) {
		String query = "update list set lastUpdate = now() where id = ?";
		getSimpleJdbcTemplate().update(query, listId );
	}

}



