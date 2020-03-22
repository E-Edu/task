package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectController {

	@Autowired private SubjectRepository subjectRepository;

	public SubjectModel createSubject(String displayName) {
		return this.subjectRepository.save(new SubjectModel(-1, displayName));
	}

	public SubjectModel getSubjectByDisplayName(String displayName) {
		return this.subjectRepository.getSubjectByDisplayNameIgnoreCase(displayName);
	}

	public List<SubjectModel> getAllSubjects() {
		return this.subjectRepository.findAll();
	}

}
