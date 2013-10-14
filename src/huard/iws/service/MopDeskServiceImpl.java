package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.constant.Constants;
import huard.iws.db.MopDeskDao;
import huard.iws.model.MopDesk;

import java.util.ArrayList;
import java.util.List;

public class MopDeskServiceImpl implements MopDeskService{

	//private static final Logger logger = Logger.getLogger(MopDeskServiceImpl.class);
	public List<MopDesk> getMopDesks(){
		return mopDeskDao.getMopDesks();
	}

	public List<MopDesk> getPublishingMopDesks(){
		return mopDeskDao.getPublishingMopDesks();
	}

	public MopDesk getMopDesk(int mopDeskId){
		return mopDeskDao.getMopDesk(mopDeskId);
	}

	public MopDesk getMopDesk(String mopDeskId){
		return mopDeskDao.getMopDesk(mopDeskId);
	}

	public List<Title> getTitles(){
		List<Title> titles = new ArrayList<Title>();
		for (String titleName: Constants.getMopTitlesIds().keySet()){
			Title title = new Title (titleName, Constants.getMopTitleId(titleName));
			titles.add(title);
		}
		return titles;
	}

	public List<PersonBean> getPersonsList(int deskId, int titleId){
		List<PersonBean> titleIdPersonBeans = new ArrayList<PersonBean>();
		for (PersonBean personBean: personListService.getPersonsList( getMopDesk(deskId).getPersonsListId())){
			if (personBean.getTitleId() == titleId){
				titleIdPersonBeans.add(personBean);
			}
		}
		return titleIdPersonBeans;
	}
	public List<PersonBean> getPersonsListEnglish(int deskId, int titleId){
		List<PersonBean> titleIdPersonBeans = new ArrayList<PersonBean>();
		for (PersonBean personBean: personListService.getPersonsList( getMopDesk(deskId).getPersonsListIdEnglish())){
			if (personBean.getTitleId() == titleId){
				titleIdPersonBeans.add(personBean);
			}
		}
		return titleIdPersonBeans;
	}

	public int getPersonDeskId(int personId){
		for (MopDesk mopDesk: getMopDesks()){
			if (mopDesk.getPersonsListId() > 0
					&& personListService.getPersonsListIds(mopDesk.getPersonsListId()).contains(personId))
				return mopDesk.getId();
		}
		return 0;
	}


	public class Title {
		private int id;
		private String name;
		public Title(){

		}

		public Title(String name, int id){
			this.name = name;
			this.id = id;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}


	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}


	private MopDeskDao mopDeskDao;

	public void setMopDeskDao(MopDeskDao mopDeskDao) {
		this.mopDeskDao = mopDeskDao;
	}







}
