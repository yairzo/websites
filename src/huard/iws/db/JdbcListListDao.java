package huard.iws.db;


import huard.iws.model.AList;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcListListDao  extends SimpleJdbcDaoSupport implements ListListDao{
		//private static final Logger logger = Logger.getLogger(JdbcListListDao.class);


		public List<AList> getListList(boolean publicOnly) {
			StringBuilder query = new StringBuilder("select * from list");
			if (publicOnly)
				query.append(" where isPublic = ?");
			query.append(" order by name");
			logger.debug(query);
			List<AList> lists ;
			if (publicOnly)
				lists = getSimpleJdbcTemplate().query(query.toString(), listDao.getRowMapper(), publicOnly ? 1 : 0);
			else
				lists = getSimpleJdbcTemplate().query(query.toString(), listDao.getRowMapper());
			return lists;
	    }

		public List<AList> getListList(ListView lv, SearchCreteria search,
				boolean publicOnly) {
			StringBuilder query = new StringBuilder("select * from list where id > 0");
			Object[] searchParam={""};
			if (publicOnly){
				query.append(" and  isPublic = 1");
			}
			if (search != null){
				query.append(" and " +search.getSearchField()+" like ?");
				searchParam[0]=new String ("%"+search.getSearchPhrase()+"%");
			}
			else
				searchParam=new String [0];

			query.append(" order by name;");
			logger.debug(query);
			List<AList> lists = getSimpleJdbcTemplate().query(query.toString(), listDao.getRowMapper(),searchParam);
			return lists;
	    }

		public List<AList> getListList(ListView lv,	boolean publicOnly){
			return getListList(lv, null, publicOnly);
		}


		public int getNumOfListInstructions(AList aList){
			Object [] params = {aList.getId()};
			int r = getSimpleJdbcTemplate().queryForInt("select count(*) from listInstruction where listId = ? ", params );
			return r;
		}

		private ListDao listDao;

		public void setListDao(ListDao listDao) {
			this.listDao = listDao;
		}

}
