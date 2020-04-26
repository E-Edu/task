package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.SubjectRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateSubjectRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllSubjectsResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Subject.NAME_KEY;

@Component
public class SubjectController {

	@Autowired private SubjectRepository subjectRepository;

	@Autowired private ModuleController moduleController;

	public ControllerResult<SubjectModel> createSubject(CreateSubjectRequestDTO dto) {
		if (this.subjectRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.subjectRepository.save(
				new SubjectModel(-1, dto.getNameKey(), dto.getDescriptionKey())));
	}

	public ControllerResult<SubjectModel> getSubject(int subjectId) {
		Optional<SubjectModel> optionalSubject = this.subjectRepository.findById(subjectId);

		return optionalSubject.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<SubjectModel> getSubjectByNameKey(String nameKey) {
		Optional<SubjectModel> optionalSubject = this.subjectRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalSubject.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllSubjectsResponseDTO> getAllSubjects() {
		List<SubjectModel> subjects = this.subjectRepository.findAll();
		if (subjects.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllSubjectsResponseDTO(subjects));
	}

	@Transactional
	public ControllerResult<SubjectModel> deleteSubject(int subjectId) {
		Optional<SubjectModel> optionalSubject = this.subjectRepository.findById(subjectId);
		if (!optionalSubject.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		SubjectModel subject = optionalSubject.get();
		if (this.moduleController.existsBySubject(subject))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.subjectRepository.delete(subject);

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<SubjectModel> deleteSubjectByNameKey(String nameKey) {
		Optional<SubjectModel> optionalSubject = this.subjectRepository.findByNameKeyIgnoreCase(nameKey);
		if (!optionalSubject.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		SubjectModel subject = optionalSubject.get();
		if (this.moduleController.existsBySubject(subject))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.subjectRepository.delete(subject);

		return ControllerResult.empty();
	}

}
