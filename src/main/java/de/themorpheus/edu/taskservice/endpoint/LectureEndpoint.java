package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.LectureController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateLectureDTO;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureEndpoint {

	@Autowired private LectureController lectureController;

	@PostMapping(value = "/lecture", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createLecture(@RequestBody @Valid CreateLectureDTO dto) {
		if (Validation.nullOrEmpty(dto.getDisplayName(), dto.getModuleDisplayName())) return Error.INVALID_PARAM;

		return this.lectureController.createLecture(dto.getDisplayName(), dto.getModuleDisplayName());
	}

	@GetMapping("/lecture/{displayName}")
	public Object getLecture(@PathVariable String displayName) {
		if (Validation.nullOrEmpty(displayName)) return Error.INVALID_PARAM;

		return this.lectureController.getLectureByDisplayName(displayName);
	}

	@GetMapping("/lecture")
	public Object getLectures() {
		return this.lectureController.getAllLectures();
	}

	@GetMapping("/module/{moduleDisplayName}/lecture")
	public Object getLecturesFromModule(@PathVariable String moduleDisplayName) {
		if (Validation.nullOrEmpty(moduleDisplayName)) return Error.INVALID_PARAM;

		return this.lectureController.getAllLecturesFromModule(moduleDisplayName);
	}

}
