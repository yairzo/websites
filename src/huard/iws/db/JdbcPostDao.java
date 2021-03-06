package huard.iws.db;

import huard.iws.bean.PersonBean;
import huard.iws.model.Attachment;
import huard.iws.model.Person;
import huard.iws.model.Post;
import huard.iws.model.PostType;
import huard.iws.util.BaseUtils;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcPostDao extends SimpleJdbcDaoSupport implements PostDao {

	//private static final Logger logger = Logger.getLogger(JdbcPostDao.class);

	public Post getPost(int id){
		String query = "select * from post where id=?";
		logger.debug(query);
		Post post =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper,id);
		applySubjectIds(post);
		return post;
	}

	private void applySubjectIds(Post post){
		String query = "select * from subjectToPost where postId = ?";
		logger.debug(query);
		List<Integer> subjectsIds =  getSimpleJdbcTemplate().query(query, subjectToPostRowMapper, post.getId());
		post.setSubjectsIds(subjectsIds);
	}

	private void applySubjectIds(List<Post> posts){
		for (Post post: posts){
			applySubjectIds(post);
		}
	}

	private ParameterizedRowMapper<Post> rowMapper = new ParameterizedRowMapper<Post>(){
		public Post mapRow(ResultSet rs, int rowNum) throws SQLException{
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setCreatorId(rs.getInt("creatorId"));
            post.setSenderId(rs.getInt("senderId"));
            post.setAdditionalAddresses(rs.getString("additionalAddresses"));
            post.setMessageSubject(rs.getString("messageSubject"));
            post.setMessage(rs.getString("message"));
            post.setMessageNew(rs.getString("messageNew"));        
            post.setCreationTime(rs.getTimestamp("creationTime"));
            post.setVerified(rs.getBoolean("isVerified"));
            post.setSent(rs.getBoolean("isSent"));
            post.setSendImmediately(rs.getBoolean("isSendImmediately"));
            post.setSendTime(rs.getTimestamp("sendTime"));
            post.setSelfSend(rs.getBoolean("isSelfSend"));
            post.setSelfSent(rs.getBoolean("isSelfSent"));
            post.setLocaleId(rs.getString("localeId"));
            post.setTypeId(rs.getInt("typeId"));
            post.setAttachments(getAttachments(post.getId()));
            post.setCallForProposalUrlTitle(rs.getString("callForProposalUrlTitle"));
            return post;
        }
	};

	private ParameterizedRowMapper<Integer> subjectToPostRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer subjectId = rs.getInt("subjectId");
            return subjectId;
		}
	};

	public void updatePost(Post post){
		String query = "update post set creatorId = ?, senderId = ?, additionalAddresses = ?," +
				"messageSubject = ?, message = ?, messageNew = ?, isVerified = ?, isSent = ?, isSendImmediately = ?,  sendTime = now() " +
				", isSelfSend = ?, isSelfSent = ?, localeId = ?, typeId = ?,callForProposalUrlTitle=? where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				post.getCreatorId(),
				post.getSenderId(),
				post.getAdditionalAddresses(),
				post.getMessageSubject(),
				post.getMessage(),
				post.getMessageNew(),
				post.isVerified(),
				post.isSent(),
				post.isSendImmediately(),
				post.isSelfSend(),
				post.isSelfSent(),
				post.getLocaleId(),
				post.getTypeId(),
				post.getCallForProposalUrlTitle(),
				post.getId()
			);

		query = "delete from subjectToPost where postId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,post.getId());

		for (Integer subjectId: post.getSubjectsIds()){
			query  = "insert subjectToPost set postId = ?, subjectId = ?";
			if (subjectId != null)
				getSimpleJdbcTemplate().update(query,post.getId(), subjectId);
		}
		this.updateAttachments(post);
	}

	public int insertPost(int creatorId){
		final String query = "insert post set creatorId = ?, senderId = ?, creationTime = now(),localeId='iw_IL'," +
				" message='', messageNew = '', additionalAddresses=''";
		logger.debug(query);
		final int aCreatorId = creatorId;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
							connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, aCreatorId);
						ps.setInt(2, aCreatorId);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deletePost (int id){
		String query = "delete from post where id =?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	public List<Post> getPosts(){
		return getPosts(null);
	}

	public List<Post> getPosts(String additionalCondition){
		String query = "select * from post";
		if (additionalCondition != null)
				query += " where " + additionalCondition;
		query += " order by sendTime desc";
		logger.debug(query);
		List<Post> posts = getSimpleJdbcTemplate().query(query, rowMapper);
		applySubjectIds(posts);
		return posts;
	}

	public List<Post> getPosts(ListView lv, SearchCreteria search,	PersonBean userPersonBean) {

		List<Post> posts;
		String query = "select * from post ";
		if (userPersonBean.isOnlyAuthorized("POST","READER"))
			query += "inner join personToPost on (personToPost.personId = " + userPersonBean.getId() +" and post.id = personToPost.postId)";
		query += getPostsWhereClause(search,userPersonBean);
		query += " order by "+lv.getOrderBy();
		logger.debug(query);
		posts =	getSimpleJdbcTemplate().query(query.toString(), rowMapper);
		return posts;
	}	
	
	public String getPostsWhereClause(SearchCreteria search, PersonBean userPersonBean){
		String whereClause = "";
		if (search != null)
			whereClause = search.getFullWhereCluase();
		if (userPersonBean.isAuthorized("POST","CREATOR") && !userPersonBean.isAuthorized("POST", "ADMIN")){
			if (whereClause.isEmpty())
				whereClause += " where";
			else
				whereClause += " and";
			whereClause += " creatorId = " + userPersonBean.getId() ;
		}		
		return whereClause;
	}




	public List<Post> getPosts(ListView lv, PersonBean userPersonBean) {
		return getPosts(lv, null, userPersonBean);
    }

	public List<Post> getYetSentPosts(){
		String query = "select * from post where isSent=0 order by sendTime;";
		logger.debug(query);
		List<Post> posts = getSimpleJdbcTemplate().query(query, rowMapper);
		applySubjectIds(posts);
		return posts;
	}

	public List<Post> getSelfSendPosts(){
		String query = "select * from post where isSelfSend=1 and isSelfSent = 0;";
		logger.debug(query);
		List<Post> posts = getSimpleJdbcTemplate().query(query, rowMapper);
		applySubjectIds(posts);
		return posts;
	}

	public Set<Integer> getPreparedPostPersons(int postId){
		String query = "select * from personToPost where postId = ? and isSelfSend = 0";
		logger.debug(query);
		List<Integer> personIds = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				Integer personId = new Integer(rs.getInt("personId"));
				return personId;
			}
		}, postId);
		return BaseUtils.toSet(personIds);
	}
	
	public int countNotSentPostPersons(){
		String query = "select count(*) from personToPost where isSent = 0 and creationTime> CURRENT_DATE - INTERVAL 30 DAY";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public int countSentPostPersons(){
		String query = "select count(*) from (select personId,date(creationTime) from personToPost where isSent = 1 and creationTime> CURRENT_DATE - INTERVAL 7 DAY group by personId,date(creationTime) ) as temp;";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public Map<Integer,Set<Integer>> getPreparedPostPersonsMap(){
		String query = "select * from personToPost where isSelfSend = 0";
		logger.debug(query);
		final Map<Integer,Set<Integer>> preparedPostPersonsMap = new HashMap<Integer, Set<Integer>>();
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				int postId = rs.getInt("postId");
				int personId = rs.getInt("personId");
				if (! preparedPostPersonsMap.containsKey(postId))
					preparedPostPersonsMap.put(postId, new HashSet<Integer>());
				preparedPostPersonsMap.get(postId).add(personId);
				return 0;
			}
		});
		return preparedPostPersonsMap;
	}

	public List<Post> getPreparedPersonPosts(int personId){
		String query = "select * from post where id in (select postId from personToPost where isSent=0 and personId = ?) order by typeId, localeId desc;";
		logger.debug(query);
		List<Post> posts = getSimpleJdbcTemplate().query(query , rowMapper, personId);
		for (Post post: posts){
			applySubjectIds(post);
		}
		return posts;
	}

	public void insertPersonPost(int personId, int postId){
		insertPersonPost(personId, postId, false);
	}

	public void insertPersonPost(int personId, int postId, boolean selfSend){
		String query = "insert ignore personToPost set personId = ?, postId = ?, creationTime = now()";
		if (selfSend)
			query += ", isSelfSend = 1";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				personId,
				postId);
	}

	public void deletePersonPost(int personId, int postId){
		String query = "delete from personToPost where personId = ? and postId = ?;";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				personId,
				postId);
	}

	public void markPersonPostAsSent(int personId, int postId){
		String query = "update personToPost set isSent = 1,sendingTime=now() where personId = ? and postId = ?;";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				personId,
				postId);
	}

	public ParameterizedRowMapper<Post> getRowMapper() {
		return rowMapper;
	}

	private List<Attachment> getAttachments (int postId){
		String query = "select * from postAttach where postId = ?";
		logger.debug(query);
		final int aPostId = postId;
		List <Attachment> attachments = getSimpleJdbcTemplate().query (query,
				new ParameterizedRowMapper<Attachment>(){
					public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
						Attachment attachment = new Attachment();
						attachment.setId(rs.getInt("id"));
						attachment.setParentId(aPostId);
						attachment.setTitle(rs.getString("title"));
						attachment.setFile(rs.getBytes("file"));
						attachment.setContentType(rs.getString("contentType"));
						attachment.setPlace(rs.getInt("place"));
						return attachment;
					}

		}, postId);
		return attachments;
	}

	public void updateAttachments(Post post){
		String query = "delete from postAttach where postId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, post.getId());
		query = "insert postAttach set id = ?, postId = ?, title = ?, file = ?, contentType = ?, place = ?";
		logger.debug(query);
		final int postId = post.getId();
		for ( Attachment attachment : post.getAttachments()){
			getSimpleJdbcTemplate().update(query,
					attachment.getId(),
					postId,
					attachment.getTitle(),
					attachment.getFile(),
					attachment.getContentType(),
					attachment.getPlace()
			);
		}
	}

	public List<PostType> getPostTypes(){
		String query ="select * from postType order by id";
		logger.debug(query);
		List<PostType> postTypes = getSimpleJdbcTemplate().query(query,
				new ParameterizedRowMapper<PostType>(){
					public PostType mapRow(ResultSet rs, int rowNum) throws SQLException{
						PostType postType = new PostType();
						postType.setId(rs.getInt("id"));
						postType.setHebrewName(rs.getString("hebrewName"));
						postType.setHebrewDescription(rs.getString("hebrewDescription"));
						postType.setEnglishName(rs.getString("englishName"));
						postType.setEnglishDescription(rs.getString("englishDescription"));
						postType.setColor(rs.getString("color"));
						postType.setRelyOnCallForProposal(rs.getBoolean("isRelyOnCallOfProposal"));
						return postType;
					}
		});
		return postTypes;
	}



	private String createEmailsInQuery(List<Person> persons){
		StringBuilder sb = new StringBuilder("('");
		for (Person person: persons){
			if (sb.length() > 0)
				sb.append("','");
			sb.append(person.getEmail());
		}
		sb.append("')");
		return sb.toString();
	}
	
	public Map<Integer,Integer> getCountPostPersonsToSend(){
		String query = "select post.id, count(distinct(personId)) as count from post inner join subjectToPost on (post.id = subjectToPost.postId) inner join subjectToPerson using (subjectId) where isSent =0 and isVerified=1 group by post.id;";
		logger.debug(query);
		final Map<Integer,Integer> countPostPersonsMap = new HashMap<Integer, Integer>();
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				int postId = rs.getInt("id");
				int count = rs.getInt("count");
				if (! countPostPersonsMap.containsKey(postId))
					countPostPersonsMap.put(postId, count);
				return 0;
			}
		});
		return countPostPersonsMap;
	}
	public Map<Integer,Integer> getCountPostPersonsSent(){
		String query = "select postId, count(personId) as count from personToPost where isSelfSend=0 group By postId";
		logger.debug(query);
		final Map<Integer,Integer> countPostPersonsMap = new HashMap<Integer, Integer>();
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				int postId = rs.getInt("postId");
				int count = rs.getInt("count");
				if (! countPostPersonsMap.containsKey(postId))
					countPostPersonsMap.put(postId, count);
				return 0;
			}
		});
		return countPostPersonsMap;
	}
	
	public Post getPostByMessageSubject(String messageSubject){
		Post post= new Post();
		try{
			String query = "select * from post where messageSubject like '" + messageSubject+ "%' order by id desc limit 1";
			logger.debug(query);
			post = getSimpleJdbcTemplate().queryForObject(query, rowMapper);
			applySubjectIds(post);
			return post;
		}
		catch(Exception e){
			return post;
		}
	}
	public int getPostTypeFromCP(int callForProposalTypeId){
		int type=0;
		try{
			String query = "select postType from callForProposalToPostType where callForProposalType =" + callForProposalTypeId;
			logger.debug(query);
			type = getSimpleJdbcTemplate().queryForInt(query);
			return type;
		}
		catch(Exception e){
			return type;
		}
	}

}
