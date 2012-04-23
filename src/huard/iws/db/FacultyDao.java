package huard.iws.db;

import huard.iws.model.Faculty;

import java.util.List;

public interface FacultyDao {

	public Faculty getFaculty(int id);
	
	public int getFacultyByNameHebrew(String nameHebrew);


	public List<Faculty> getFaculties();

}
