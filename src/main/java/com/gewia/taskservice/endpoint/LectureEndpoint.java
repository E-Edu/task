package com.gewia.taskservice.endpoint;

import com.gewia.taskservice.controller.LectureController;
import com.gewia.taskservice.endpoint.dto.request.CreateLectureRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class LectureEndpoint {

	@Autowired private LectureController lectureController;

	@PostMapping("/lecture")
	public Object createLecture(@RequestBody @Valid CreateLectureRequestDTO dto) {
		return this.lectureController.createLecture(dto).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureId:[0-9]+}")
	public Object getLecture(@PathVariable @Min(1) int lectureId) {
		return this.lectureController.getLecture(lectureId).getHttpResponse();
	}

	@GetMapping("/lecture/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object getLectureByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.lectureController.getLectureByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/lecture")
	public Object getLectures() {
		return this.lectureController.getAllLectures().getHttpResponse();
	}

	@GetMapping("/module/{moduleId:[0-9]+}/lecture")
	public Object getLecturesFromModule(@PathVariable @Min(1) int moduleId) {
		return this.lectureController.getAllLecturesByModuleId(moduleId).getHttpResponse();
	}

	@GetMapping("/module/{moduleNameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}/lecture")
	public Object getLecturesFromModuleByModuleNameKey(@PathVariable @NotBlank String moduleNameKey) {
		return this.lectureController.getAllLecturesByModuleNameKey(moduleNameKey).getHttpResponse();
	}

	@DeleteMapping("/lecture/{lectureId:[0-9]+}")
	public Object deleteLecture(@PathVariable @Min(1) int lectureId) {
		return this.lectureController.deleteLecture(lectureId).getHttpResponse();
	}

	@DeleteMapping("/lecture/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object deleteLectureByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.lectureController.deleteLectureByNameKey(nameKey).getHttpResponse();
	}

}
