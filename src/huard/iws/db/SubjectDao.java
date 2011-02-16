package huard.iws.db;

import huard.iws.model.Language;
import huard.iws.model.Subject;

import java.util.List;



public interface SubjectDao {

	public Subject getSubject(int id);

	public List<Subject> getSubjects(int parentId);

	public List<Subject> getSubjects(int parentId, Language language);

}
