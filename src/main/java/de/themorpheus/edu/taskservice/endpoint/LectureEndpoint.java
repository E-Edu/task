package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.LectureController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateLectureDTO;
<<<<<<< HEAD
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import io.micrometer.core.annotation.Timed;
=======
>>>>>>> Added MultipleChoice / Modified Wordsalad -> now using solutionIds (#128)
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Timed
@RestController
public class LectureEndpoint {

	@Autowired private LectureController lectureController;

	@PostMapping(value = "/lecture")
	public Object createLecture(@RequestBody @Valid CreateLectureDTO dto) {
<<<<<<< HEAD
		if (Validation.nullOrEmpty(dto.getNameKey(), dto.getModuleNameKey())) return Error.INVALID_PARAM;

		return this.lectureController.createLecture(dto.getNameKey(), dto.getModuleNameKey()).getHttpResponse();
	}

	@GetMapping("/lecture/{nameKey}")
	public Object getLecture(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		if (Validation.nullOrEmpty(nameKey)) return Error.INVALID_PARAM;

		return this.lectureController.getLectureByNameKey(nameKey).getHttpResponse();
=======
		return this.lectureController.createLecture(dto.getDisplayName(), dto.getModuleDisplayName()).getHttpResponse();
	}

	@GetMapping("/lecture/{displayName}")
	public Object getLecture(@PathVariable @NotNull @NotEmpty @NotBlank String displayName) {
		return this.lectureController.getLectureByDisplayName(displayName).getHttpResponse();
>>>>>>> Added MultipleChoice / Modified Wordsalad -> now using solutionIds (#128)
	}

	@GetMapping("/lecture")
	public Object getLectures() {
		return this.lectureController.getAllLectures().getHttpResponse();
	}

<<<<<<< HEAD
	@GetMapping("/module/{moduleNameKey}/lecture")
	public Object getLecturesFromModule(@PathVariable @NotNull @NotEmpty @NotBlank String moduleNameKey) {
		if (Validation.nullOrEmpty(moduleNameKey)) return Error.INVALID_PARAM;

		return this.lectureController.getAllLecturesFromModule(moduleNameKey).getHttpResponse();
	}

	@DeleteMapping("/lecture/{nameKey}")
	public Object deleteLecture(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		this.lectureController.deleteLecture(nameKey);
		return ControllerResult.empty();
=======
	@GetMapping("/module/{moduleDisplayName}/lecture")
	public Object getLecturesFromModule(@PathVariable @NotNull @NotEmpty @NotBlank String moduleDisplayName) {
		return this.lectureController.getAllLecturesFromModule(moduleDisplayName).getHttpResponse();
	}

	@DeleteMapping("/lecture/{displayName}")
	public Object deleteLecture(@PathVariable @NotNull @NotEmpty @NotBlank String displayName) {
		return this.lectureController.deleteLecture(displayName);
>>>>>>> Added MultipleChoice / Modified Wordsalad -> now using solutionIds (#128)
	}
}
