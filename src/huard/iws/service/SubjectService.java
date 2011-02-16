package huard.iws.service;

import huard.iws.model.ISubjectRelated;
import huard.iws.model.Subject;

import java.util.List;

public interface SubjectService {

	public Subject getSubject(int subjectId, String localeId);

	public List<Subject> getSubjects(int parentSubjectId, String localeId);

	public List<Subject> getCommonSubjects (ISubjectRelated subjectRelated1, ISubjectRelated subjectRelated2, String localeId);

	public Subject getSubjectFilterSubSubjects(int subjectId, String localeId, List<Integer> relevantSubjectsIds);

}
