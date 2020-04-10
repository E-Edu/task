package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Subject.NAME_KEY;

@Component
public class SubjectController {

	@Autowired private SubjectRepository subjectRepository;

	public ControllerResult<SubjectModel> createSubject(String nameKey, String description) {
		if (this.subjectRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.subjectRepository.save(new SubjectModel(-1, nameKey, description)));
	}

	public ControllerResult<SubjectModel> getSubjectByNameKey(String nameKey) {
		SubjectModel subject = this.subjectRepository.getSubjectByNameKeyIgnoreCase(nameKey);
		if (subject == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(subject);
	}

	public ControllerResult<List<SubjectModel>> getAllSubjects() {
		List<SubjectModel> subjects = this.subjectRepository.findAll();
		if (subjects.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(subjects);
	}

	public ControllerResult<SubjectModel> deleteSubject(String nameKey) {
		if (!this.subjectRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.subjectRepository.deleteByNameKey(nameKey);
		return ControllerResult.empty();
	}

}
