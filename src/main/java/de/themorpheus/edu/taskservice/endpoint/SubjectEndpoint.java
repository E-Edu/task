package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.SubjectController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateSubjectRequestDTO;
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
public class SubjectEndpoint {

	@Autowired private SubjectController subjectController;

	@PostMapping("/subject")
	public Object createSubject(@RequestBody @Valid CreateSubjectRequestDTO dto) {
		return this.subjectController.createSubject(dto).getHttpResponse();
	}

	@GetMapping("/subject/{nameKey}")
	public Object getSubjectByNameKey(@PathVariable @NotBlank String nameKey) {
		try {
			int subjectId = Integer.parseInt(nameKey);
			return this.subjectController.getSubject(subjectId).getHttpResponse();
		} catch (NumberFormatException ignored) {
			return this.subjectController.getSubjectByNameKey(nameKey).getHttpResponse();
		}
	}

	@GetMapping("/subject")
	public Object getSubjects() {
		return this.subjectController.getAllSubjects().getHttpResponse();
	}

	@DeleteMapping("/subject/{nameKey}")
	public Object deleteSubject(@PathVariable @NotBlank String nameKey) {
		try {
			int subjectId = Integer.parseInt(nameKey);
			return this.subjectController.deleteSubject(subjectId).getHttpResponse();
		} catch (NumberFormatException ignored) {
			return this.subjectController.deleteSubjectByNameKey(nameKey).getHttpResponse();
		}
	}

}
