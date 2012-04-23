package huard.iws.service;

import huard.iws.db.FacultyDao;
import huard.iws.model.Faculty;

import java.util.List;

public class FacultyServiceImpl implements FacultyService{

	public Faculty getFaculty(int id){
		return facultyDao.getFaculty(id);
	}

	public int getFacultyByNameHebrew(String nameHebrew){
		return facultyDao.getFacultyByNameHebrew(nameHebrew);	
	}

	public List<Faculty> getFaculties(){
		return facultyDao.getFaculties();
	}

	private FacultyDao facultyDao;

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

}
