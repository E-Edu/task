package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.ModuleRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Module.NAME_KEY;

@Component
public class ModuleController {

	@Autowired private ModuleRepository moduleRepository;

	@Autowired private SubjectController subjectController;

	public ControllerResult<ModuleModel> createModule(String nameKey, String subjectNameKey) {
		ControllerResult<SubjectModel> subjectResult = this.subjectController.getSubjectByNameKey(subjectNameKey);
		if (subjectResult.isResultNotPresent()) return ControllerResult.ret(subjectResult);

		return ControllerResult.of(this.moduleRepository.save(
				new ModuleModel(-1, subjectResult.getResult(), nameKey)
			)
		);
	}

	public ControllerResult<ModuleModel> getModuleByNameKey(String nameKey) {
		ModuleModel module = this.moduleRepository.getModuleByNameKeyIgnoreCase(nameKey);
		if (module == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(module);
	}

	public ControllerResult<ModuleModel> deleteModule(String nameKey) {
		if (!this.moduleRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.moduleRepository.deleteModuleByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<List<ModuleModel>> getAllModules() {
		List<ModuleModel> modules = this.moduleRepository.findAll();
		if (modules.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(modules);
	}

	public ControllerResult<List<ModuleModel>> getAllModulesFromSubject(String subjectNameKey) {
		ControllerResult<SubjectModel> subjectResults = this.subjectController.getSubjectByNameKey(subjectNameKey);
		if (subjectResults.isResultNotPresent()) return ControllerResult.ret(subjectResults);

		List<ModuleModel> modules = this.moduleRepository.getModulesBySubjectId(subjectResults.getResult());
		if (modules.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(modules);
	}

}
