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

	@Autowired private ModuleRepository moduleRepository;

	@Autowired private SubjectController subjectController;

	public ControllerResult<ModuleModel> createModule(String displayName, String subjectDisplayName) {
		ControllerResult<SubjectModel> subjectModelResult = this.subjectController.getSubjectByDisplayName(subjectDisplayName);
		if (subjectModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "subject");
		return ControllerResult.of(this.moduleRepository.save(
				new ModuleModel(-1, subjectModelResult.getResult(), displayName)
			)
		);
	}

	public ControllerResult<ModuleModel> getModuleByDisplayName(String displayName) {
		return ControllerResult.of(this.moduleRepository.getModuleByDisplayNameIgnoreCase(displayName));
	}

	public void deleteModule(String displayName) {
		this.moduleRepository.deleteModuleByDisplayNameIgnoreCase(displayName);
	}

	public ControllerResult<List<ModuleModel>> getAllModules() {
		return ControllerResult.of(this.moduleRepository.findAll());
	}

	public ControllerResult<List<ModuleModel>> getAllModulesFromSubject(String subjectDisplayName) {
		ControllerResult<SubjectModel> subjectModelResult = this.subjectController.getSubjectByDisplayName(subjectDisplayName);
		if (subjectModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "subject");
		return ControllerResult.of(this.moduleRepository.getModulesBySubjectId(subjectModelResult.getResult()));
	}

}
