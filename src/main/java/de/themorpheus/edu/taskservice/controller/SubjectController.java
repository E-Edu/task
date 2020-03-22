package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.List;

import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import jdk.internal.loader.AbstractClassLoaderValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectController {

	@Autowired private SubjectRepository subjectRepository;


	public ControllerResult<SubjectModel> createSubject(String displayName) {
		if (doesSubjectExist(displayName)) return ControllerResult.of(Error.INVALID_PARAM); // TODO: change this

		SubjectModel newModel = this.subjectRepository.save(new SubjectModel(-1, displayName));
		ControllerResult<SubjectModel> returnValue = ControllerResult.of(newModel);

		return returnValue;
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

	private boolean doesSubjectExist(String displayName) {
		SubjectModel subjectModel = this.subjectRepository.getSubjectByDisplayNameIgnoreCase(displayName);

		return subjectModel != null;
	}
}
