package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectController {

	@Autowired private SubjectRepository subjectRepository;

	public ControllerResult<SubjectModel> createSubject(String displayName) {
		if (doesSubjectExist(displayName)) return ControllerResult.of(Error.ALREADY_EXISTS);

		return ControllerResult.of(this.subjectRepository.save(new SubjectModel(-1, displayName)));
	}

	public ControllerResult<SubjectModel> getSubjectByDisplayName(String displayName) {
		return ControllerResult.of(this.subjectRepository.getSubjectByDisplayNameIgnoreCase(displayName));
	}

	public ControllerResult<List<SubjectModel>> getAllSubjects() {
		return ControllerResult.of(this.subjectRepository.findAll());
	}

	public void deleteSubject(String displayName) {
		SubjectModel subjectModel = this.subjectRepository.getSubjectByDisplayNameIgnoreCase(displayName);

		if (Validation.validateNull(subjectModel)) return;

		this.subjectRepository.deleteById(subjectModel.getSubjectId());
	}

	public boolean doesSubjectExist(String displayName) {
		SubjectModel subjectModel = this.subjectRepository.getSubjectByDisplayNameIgnoreCase(displayName);

		return subjectModel != null;
	}
}
