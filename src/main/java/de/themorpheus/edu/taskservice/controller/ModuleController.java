package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.ModuleRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateModuleRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllModulesResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetModuleResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Module.NAME_KEY;

@Component
public class ModuleController {

	@Autowired private ModuleRepository moduleRepository;

	@Autowired private SubjectController subjectController;
	@Autowired private LectureController lectureController;

	public ControllerResult<GetModuleResponseDTO> createModule(CreateModuleRequestDTO dto) {
		ControllerResult<SubjectModel> subjectResult;
		if (Validation.greaterZero(dto.getSubjectId()))
			subjectResult = this.subjectController.getSubject(dto.getSubjectId());
		else if (Validation.validateNotNullOrEmpty(dto.getSubjectNameKey()))
			subjectResult = this.subjectController.getSubjectByNameKey(dto.getSubjectNameKey());
		else return ControllerResult.of(Error.MISSING_PARAM, NAME_KEY);

		if (subjectResult.isResultNotPresent()) return ControllerResult.ret(subjectResult);

		SubjectModel subject = subjectResult.getResult();
		if (this.moduleRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.moduleRepository.save(
				new ModuleModel(-1, dto.getNameKey(), subject)).toResponseDTO());
	}

	public ControllerResult<GetModuleResponseDTO> getModule(int moduleId) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findById(moduleId);

		return optionalModule.map(module -> ControllerResult.of(optionalModule.get().toResponseDTO()))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetModuleResponseDTO> getModuleByNameKey(String nameKey) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalModule.map(module -> ControllerResult.of(optionalModule.get().toResponseDTO()))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	ControllerResult<ModuleModel> getModuleRaw(int moduleId) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findById(moduleId);

		return optionalModule.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	ControllerResult<ModuleModel> getModuleByNameKeyRaw(String nameKey) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalModule.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllModulesResponseDTO> getAllModules() {
		List<ModuleModel> modules = this.moduleRepository.findAll();
		if (modules.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetModuleResponseDTO> responseDTOs = new ArrayList<>();
		modules.forEach(module -> responseDTOs.add(module.toResponseDTO()));

		return ControllerResult.of(new GetAllModulesResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllModulesResponseDTO> getAllModulesSubjectId(int subjectId) {
		ControllerResult<SubjectModel> subjectResult = this.subjectController.getSubject(subjectId);
		if (subjectResult.isResultNotPresent()) return ControllerResult.ret(subjectResult);

		List<ModuleModel> modules = this.moduleRepository.findAllBySubjectId(subjectResult.getResult());
		if (modules.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetModuleResponseDTO> responseDTOs = new ArrayList<>();
		modules.forEach(module -> responseDTOs.add(module.toResponseDTO()));

		return ControllerResult.of(new GetAllModulesResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllModulesResponseDTO> getAllModulesBySubjectNameKey(String subjectNameKey) {
		ControllerResult<SubjectModel> subjectResult = this.subjectController.getSubjectByNameKey(subjectNameKey);
		if (subjectResult.isResultNotPresent()) return ControllerResult.ret(subjectResult);

		List<ModuleModel> modules = this.moduleRepository.findAllBySubjectId(subjectResult.getResult());
		if (modules.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetModuleResponseDTO> responseDTOs = new ArrayList<>();
		modules.forEach(module -> responseDTOs.add(module.toResponseDTO()));

		return ControllerResult.of(new GetAllModulesResponseDTO(responseDTOs));
	}

	@Transactional
	public ControllerResult<ModuleModel> deleteModule(int moduleId) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findById(moduleId);
		if (!optionalModule.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ModuleModel module = optionalModule.get();
		if (this.lectureController.existsByModule(module))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.moduleRepository.delete(module);

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<ModuleModel> deleteModuleByNameKey(String nameKey) {
		Optional<ModuleModel> optionalModule = this.moduleRepository.findByNameKeyIgnoreCase(nameKey);
		if (!optionalModule.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ModuleModel module = optionalModule.get();
		if (this.lectureController.existsByModule(module))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.moduleRepository.delete(module);

		return ControllerResult.empty();
	}

	boolean existsBySubject(SubjectModel subject) {
		return this.moduleRepository.existsBySubjectId(subject);
	}

}
