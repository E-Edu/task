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

	public ControllerResult<LectureModel> createLecture(String nameKey, String moduleNameKey) {
		ControllerResult<ModuleModel> moduleModel = this.moduleController.getModuleByNameKey(moduleNameKey);
		if (moduleModel.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "module");
		return ControllerResult.of(this.lectureRepository.save(new LectureModel(-1, moduleModel.getResult(), nameKey)));
	}

	public ControllerResult<LectureModel> getLectureByNameKey(String nameKey) {
		return ControllerResult.of(this.lectureRepository.getLectureByNameKeyIgnoreCase(nameKey));
	}

	public ControllerResult<List<LectureModel>> getAllLectures() {
		return ControllerResult.of(this.lectureRepository.findAll());
	}

	public ControllerResult<LectureModel> deleteLecture(String nameKey) {
		this.lectureRepository.deleteLectureByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<List<LectureModel>> getAllLecturesFromModule(String moduleNameKey) {
		ControllerResult<ModuleModel> moduleModel = this.moduleController.getModuleByNameKey(moduleNameKey);
		if (moduleModel.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "module");
		return ControllerResult.of(this.lectureRepository.getLecturesByModuleId(moduleModel.getResult()));
	}

}
