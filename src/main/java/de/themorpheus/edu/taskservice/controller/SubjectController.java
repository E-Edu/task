package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectController {

	private static final String NAME_KEY = "subject";

	@Autowired private SubjectRepository subjectRepository;

	public ControllerResult<SubjectModel> createSubject(String nameKey, String description) {
		if (this.doesSubjectExist(nameKey)) return ControllerResult.of(Error.ALREADY_EXISTS);

		return ControllerResult.of(this.subjectRepository.save(new SubjectModel(-1, nameKey, description)));
	}

	public ControllerResult<SubjectModel> getSubjectByNameKey(String nameKey) {
		return ControllerResult.of(this.subjectRepository.getSubjectByNameKeyIgnoreCase(nameKey));
	}

	public ControllerResult<List<SubjectModel>> getAllSubjects() {
		return ControllerResult.of(this.subjectRepository.findAll());
	}

	public ControllerResult<SubjectModel> deleteSubject(String nameKey) {
		if (!this.subjectRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.subjectRepository.deleteByNameKey(nameKey);
		return ControllerResult.empty();
	}

	public boolean doesSubjectExist(String nameKey) {
		SubjectModel subjectModel = this.subjectRepository.getSubjectByNameKeyIgnoreCase(nameKey);
		return subjectModel != null;
	}
}
