package huard.iws.service;

import huard.iws.model.Faculty;

import java.util.List;

public interface FacultyService {

	public Faculty getFaculty(int id);

	public List<Faculty> getFaculties();

}
