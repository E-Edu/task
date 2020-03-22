package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import de.themorpheus.edu.taskservice.database.repository.ModuleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModuleController {

	@Autowired private ModuleRepository moduleRepository;

	@Autowired private SubjectController subjectController;

	public ModuleModel createModule(String displayName, String subjectDisplayName) {
		SubjectModel subjectModel = this.subjectController.getSubjectByDisplayName(subjectDisplayName);
		return this.moduleRepository.save(new ModuleModel(-1, subjectModel, displayName));
	}

	public ModuleModel getModuleByDisplayName(String displayName) {
		return this.moduleRepository.getModuleByDisplayNameIgnoreCase(displayName);
	}

	public List<ModuleModel> getAllModules() {
		return this.moduleRepository.findAll();
	}

	public List<ModuleModel> getAllModulesFromSubject(String subjectDisplayName) {
		SubjectModel subjectModel = this.subjectController.getSubjectByDisplayName(subjectDisplayName);
		return this.moduleRepository.getModulesBySubjectId(subjectModel);
	}

}
