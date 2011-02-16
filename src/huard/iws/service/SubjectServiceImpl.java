package huard.iws.service;



import huard.iws.db.SubjectDao;
import huard.iws.model.ISubjectRelated;
import huard.iws.model.Language;
import huard.iws.model.Subject;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectServiceImpl implements SubjectService{

	//private static final Logger logger = Logger.getLogger(SubjectServiceImpl.class);

	public Subject getSubject(int id, String localeId){
		Subject subject = subjectDao.getSubject(id);
		subject.setSubSubjects(getSubjects(id, localeId));
		return subject;
	}

	public Subject getSubjectFilterSubSubjects(int subjectId, String localeId, List<Integer> relevantSubjectsIds){
		Subject subject = subjectDao.getSubject(subjectId);
		subject.setSubSubjects(getSubjects(subjectId, localeId, relevantSubjectsIds));
		return subject;
	}

	public List<Subject> getSubjects(int parentSubjectId, String localeId){
		Language language = LanguageUtils.getLanguage(localeId);
		List<Subject> subjects = subjectDao.getSubjects(parentSubjectId, language);
		for (Subject subject : subjects){
			subject.setSubSubjects(getSubjects(subject.getId(), localeId));
		}
		return subjects;
	}

	public List<Subject> getSubjects(int parentSubjectId, String localeId, List<Integer> relevantSubjectsIds){
		Language language = LanguageUtils.getLanguage(localeId);
		List<Subject> subjects = subjectDao.getSubjects(parentSubjectId, language);
		List<Subject> removedSubjects = new ArrayList<Subject>();
		for (Subject subject : subjects){
			List<Subject> subSubjects = new ArrayList<Subject>();
			if (relevantSubjectsIds.contains(subject.getId())
					|| (subject.getParentId() ==1
							&& !(subSubjects = getSubjects(subject.getId(), localeId, relevantSubjectsIds)).isEmpty())){
				subject.setSubSubjects(subSubjects);
			}
			else{
				removedSubjects.add(subject);
			}
		}
		subjects.removeAll(removedSubjects);
		return subjects;
	}

	public List<Subject> getCommonSubjects (ISubjectRelated subjectRelated1, ISubjectRelated subjectRelated2, String localeId){
		Set<Integer> commonSubjectsIds = new HashSet<Integer>();
		for (int id1: subjectRelated1.getSubjectsIds()){
			for (int id2: subjectRelated2.getSubjectsIds()){
				if (id1 == id2){
					commonSubjectsIds.add(id1);
				}
			}
		}
		List<Subject> commonSubjects = new ArrayList<Subject>();
		for (int id: commonSubjectsIds){
			commonSubjects.add(getSubject(id, localeId));
		}
		return commonSubjects;
	}

	private SubjectDao subjectDao;

	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

}
