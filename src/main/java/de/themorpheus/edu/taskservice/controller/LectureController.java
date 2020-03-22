package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.repository.LectureRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LectureController {

	@Autowired private LectureRepository lectureRepository;

	@Autowired private ModuleController moduleController;

	public ControllerResult<LectureModel> createLecture(String displayName, String moduleDisplayName) {
		ControllerResult<ModuleModel> moduleModel = this.moduleController.getModuleByDisplayName(moduleDisplayName);
		if (moduleModel.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "module");
		return ControllerResult.of(this.lectureRepository.save(new LectureModel(-1, moduleModel.getResult(), displayName)));
	}

	public ControllerResult<LectureModel> getLectureByDisplayName(String displayName) {
		return ControllerResult.of(this.lectureRepository.getLectureByDisplayNameIgnoreCase(displayName));
	}

	public ControllerResult<List<LectureModel>> getAllLectures() {
		return ControllerResult.of(this.lectureRepository.findAll());
	}

	public ControllerResult<List<LectureModel>> getAllLecturesFromModule(String moduleDisplayName) {
		ControllerResult<ModuleModel> moduleModel = this.moduleController.getModuleByDisplayName(moduleDisplayName);
		if (moduleModel.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "module");
		return ControllerResult.of(this.lectureRepository.getLecturesByModuleId(moduleModel.getResult()));
	}

}
