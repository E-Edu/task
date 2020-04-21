package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.repository.LectureRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateLectureRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllLecturesResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Lecture.NAME_KEY;

@Component
public class LectureController {

	@Autowired private LectureRepository lectureRepository;

	@Autowired private ModuleController moduleController;

	public ControllerResult<LectureModel> createLecture(CreateLectureRequestDTO dto) {
		ControllerResult<ModuleModel> module = this.moduleController.getModuleByNameKey(dto.getModuleNameKey());
		if (module.isResultNotPresent()) return ControllerResult.ret(module);

		if (this.lectureRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.lectureRepository.save(
				new LectureModel(-1, module.getResult(), dto.getNameKey())));
	}

	public ControllerResult<LectureModel> getLectureByNameKey(String nameKey) {
		LectureModel lecture = this.lectureRepository.getLectureByNameKeyIgnoreCase(nameKey);
		if (lecture == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(lecture);
	}

	public ControllerResult<List<LectureModel>> getAllLectures() {
		List<LectureModel> lectureModels = this.lectureRepository.findAll();
		if (lectureModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(lectureModels);
	}

	public ControllerResult<LectureModel> deleteLecture(String nameKey) {
		if (!this.lectureRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.lectureRepository.deleteLectureByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<GetAllLecturesResponseDTO> getAllLecturesFromModule(String moduleNameKey) {
		ControllerResult<ModuleModel> moduleResult = this.moduleController.getModuleByNameKey(moduleNameKey);
		if (moduleResult.isResultNotPresent()) return ControllerResult.ret(moduleResult);

		List<LectureModel> lectures = this.lectureRepository.getLecturesByModuleId(moduleResult.getResult());
		if (lectures.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllLecturesResponseDTO(lectures));
	}

}
