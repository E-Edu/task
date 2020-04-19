package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.LectureController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateLectureRequestDTO;
import javax.validation.Valid;
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

	@GetMapping("/lecture/{nameKey}")
	public Object getLecture(@PathVariable @NotBlank String nameKey) {
		return this.lectureController.getLectureByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/lecture")
	public Object getLectures() {
		return this.lectureController.getAllLectures().getHttpResponse();
	}

	@GetMapping("/module/{moduleNameKey}/lecture")
	public Object getLecturesFromModule(@PathVariable @NotBlank String moduleNameKey) {
		return this.lectureController.getAllLecturesFromModule(moduleNameKey).getHttpResponse();
	}

	@DeleteMapping("/lecture/{nameKey}")
	public Object deleteLecture(@PathVariable @NotBlank String nameKey) {
		return this.lectureController.deleteLecture(nameKey).getHttpResponse();
	}

}
