package com.gewia.taskservice.controller;

import com.gewia.taskservice.database.model.LectureModel;
import com.gewia.taskservice.database.model.ModuleModel;
import com.gewia.taskservice.database.repository.LectureRepository;
import com.gewia.taskservice.endpoint.dto.request.CreateLectureRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.GetAllLecturesResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.GetLectureResponseDTO;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import com.gewia.taskservice.util.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.Lecture.NAME_KEY;

@Component
public class LectureController {

	@Autowired private LectureRepository lectureRepository;

	@Autowired private ModuleController moduleController;
	@Autowired private TaskController taskController;

	public ControllerResult<GetLectureResponseDTO> createLecture(CreateLectureRequestDTO dto) {
		ControllerResult<ModuleModel> moduleResult;
		if (Validation.greaterZero(dto.getModuleId()))
			moduleResult = this.moduleController.getModuleRaw(dto.getModuleId());
		else if (Validation.validateNotNullOrEmpty(dto.getModuleNameKey()))
			moduleResult = this.moduleController.getModuleByNameKeyRaw(dto.getModuleNameKey());
		else return ControllerResult.of(Error.MISSING_PARAM, NAME_KEY);

		if (moduleResult.isResultNotPresent()) return ControllerResult.ret(moduleResult);

		ModuleModel module = moduleResult.getResult();
		if (this.lectureRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.lectureRepository.save(
				new LectureModel(-1, dto.getNameKey(), module)).toResponseDTO());
	}

	public ControllerResult<GetLectureResponseDTO> getLecture(int lectureId) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findById(lectureId);

		return optionalLecture.map(lecture -> ControllerResult.of(lecture.toResponseDTO()))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetLectureResponseDTO> getLectureByNameKey(String nameKey) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalLecture.map(lecture -> ControllerResult.of(lecture.toResponseDTO()))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	ControllerResult<LectureModel> getLectureRaw(int lectureId) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findById(lectureId);

		return optionalLecture.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	ControllerResult<LectureModel> getLectureByNameKeyRaw(String nameKey) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalLecture.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllLecturesResponseDTO> getAllLectures() {
		List<LectureModel> lectures = this.lectureRepository.findAll();
		if (lectures.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetLectureResponseDTO> responseDTOs = new ArrayList<>();
		lectures.forEach(lecture -> responseDTOs.add(lecture.toResponseDTO()));

		return ControllerResult.of(new GetAllLecturesResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllLecturesResponseDTO> getAllLecturesByModuleNameKey(String moduleNameKey) {
		ControllerResult<ModuleModel> moduleResult = this.moduleController.getModuleByNameKeyRaw(moduleNameKey);
		if (moduleResult.isResultNotPresent()) return ControllerResult.ret(moduleResult);

		List<LectureModel> lectures = this.lectureRepository.findAllByModuleId(moduleResult.getResult());
		if (lectures.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetLectureResponseDTO> responseDTOs = new ArrayList<>();
		lectures.forEach(lecture -> responseDTOs.add(lecture.toResponseDTO()));

		return ControllerResult.of(new GetAllLecturesResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllLecturesResponseDTO> getAllLecturesByModuleId(int moduleId) {
		ControllerResult<ModuleModel> moduleResult = this.moduleController.getModuleRaw(moduleId);
		if (moduleResult.isResultNotPresent()) return ControllerResult.ret(moduleResult);

		List<LectureModel> lectures = this.lectureRepository.findAllByModuleId(moduleResult.getResult());
		if (lectures.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetLectureResponseDTO> responseDTOs = new ArrayList<>();
		lectures.forEach(lecture -> responseDTOs.add(lecture.toResponseDTO()));

		return ControllerResult.of(new GetAllLecturesResponseDTO(responseDTOs));
	}

	@Transactional
	public ControllerResult<LectureModel> deleteLectureByNameKey(String nameKey) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findByNameKeyIgnoreCase(nameKey);
		if (!optionalLecture.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		LectureModel lecture = optionalLecture.get();
		if (this.taskController.existsByLectureId(lecture))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.lectureRepository.delete(lecture);

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<LectureModel> deleteLecture(int lectureId) {
		Optional<LectureModel> optionalLecture = this.lectureRepository.findById(lectureId);
		if (!optionalLecture.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		LectureModel lecture = optionalLecture.get();
		if (this.taskController.existsByLectureId(lecture))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.lectureRepository.delete(lecture);

		return ControllerResult.empty();
	}

	public boolean existsByModule(ModuleModel module) {
		return this.lectureRepository.existsByModuleId(module);
	}

	public ControllerResult<LectureModel> getLectureByIdOrNameKey(int id, String nameKey) {
		if (Validation.greaterZero(id)) return this.getLectureRaw(id);
		if (Validation.validateNotNullOrEmpty(nameKey)) return this.getLectureByNameKeyRaw(nameKey);

		return ControllerResult.of(Error.MISSING_PARAM, NAME_KEY);
	}

}
