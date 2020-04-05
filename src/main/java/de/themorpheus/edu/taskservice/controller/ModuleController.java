package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.ModuleRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModuleController {

	private static final String NAME_KEY = "module";

	@Autowired private ModuleRepository moduleRepository;

	@Autowired private SubjectController subjectController;

	public ControllerResult<ModuleModel> createModule(String nameKey, String subjectNameKey) {
		ControllerResult<SubjectModel> subjectModelResult = this.subjectController.getSubjectByNameKey(subjectNameKey);
		if (subjectModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "subject");
		return ControllerResult.of(this.moduleRepository.save(
				new ModuleModel(-1, subjectModelResult.getResult(), nameKey)
			)
		);
	}

	public ControllerResult<ModuleModel> getModuleByNameKey(String nameKey) {
		return ControllerResult.of(this.moduleRepository.getModuleByNameKeyIgnoreCase(nameKey));
	}

	public ControllerResult<Object> deleteModule(String nameKey) {
		if (!this.moduleRepository.existsByNameKey(nameKey)) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.moduleRepository.deleteModuleByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<List<ModuleModel>> getAllModules() {
		return ControllerResult.of(this.moduleRepository.findAll());
	}

	public ControllerResult<List<ModuleModel>> getAllModulesFromSubject(String subjectNameKey) {
		ControllerResult<SubjectModel> subjectModelResult = this.subjectController.getSubjectByNameKey(subjectNameKey);
		if (subjectModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "subject");
		return ControllerResult.of(this.moduleRepository.getModulesBySubjectId(subjectModelResult.getResult()));
	}

}
