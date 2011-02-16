package huard.iws.db;

import huard.iws.model.Faculty;

import java.util.List;

public interface FacultyDao {

	public Faculty getFaculty(int id);

	public List<Faculty> getFaculties();

}
