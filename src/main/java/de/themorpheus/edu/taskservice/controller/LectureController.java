package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LectureController {

	@Autowired private LectureRepository lectureRepository;

	@Autowired private ModuleController moduleController;

	public LectureModel createLecture(String displayName, String moduleDisplayName) {
		ModuleModel moduleModel = this.moduleController.getModuleByDisplayName(moduleDisplayName);
		return this.lectureRepository.save(new LectureModel(-1, moduleModel, displayName));
	}

	public LectureModel getLectureByDisplayName(String displayName) {
		return this.lectureRepository.getLectureByDisplayNameIgnoreCase(displayName);
	}

	public List<LectureModel> getAllLectures() {
		return this.lectureRepository.findAll();
	}

	public List<LectureModel> getAllLecturesFromModule(String moduleDisplayName) {
		ModuleModel moduleModel = this.moduleController.getModuleByDisplayName(moduleDisplayName);
		return this.lectureRepository.getLecturesByModuleId(moduleModel);
	}

}
