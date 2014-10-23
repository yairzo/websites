package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.MopDesk;
import huard.iws.service.MopDeskServiceImpl.Title;

import java.util.List;

public interface MopDeskService {

	public List<MopDesk> getMopDesks();

	public List<MopDesk> getPublishingMopDesks();

	public MopDesk getMopDesk(int mopDeskId);

	public MopDesk getMopDesk(String mopDeskId);

	public List<PersonBean> getPersonsList(int deskId, int titleType);

	public List<PersonBean> getPersonsListEnglish(int deskId, int titleType);

	public List<PersonBean> getDeskCoordinators(int deskId);

	public List<Title> getTitles();

	public int getPersonDeskId(int personId);



}
