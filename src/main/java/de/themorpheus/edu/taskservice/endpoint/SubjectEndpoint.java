package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.SubjectController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateSubjectRequestDTO;
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
public class SubjectEndpoint {

	@Autowired private SubjectController subjectController;

	@PostMapping("/subject")
	public Object createSubject(@RequestBody @Valid CreateSubjectRequestDTO dto) {
		return this.subjectController.createSubject(dto).getHttpResponse();
	}

	@GetMapping("/subject/{subjectId:[0-9]+}")
	public Object getSubject(@PathVariable @Min(1) int subjectId) {
		return this.subjectController.getSubject(subjectId).getHttpResponse();
	}

	@GetMapping("/subject/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object getSubjectByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.subjectController.getSubjectByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/subject")
	public Object getSubjects() {
		return this.subjectController.getAllSubjects().getHttpResponse();
	}

	@DeleteMapping("/subject/{subjectId:[0-9]+}")
	public Object deleteSubject(@PathVariable @Min(1) int subjectId) {
			return this.subjectController.deleteSubject(subjectId).getHttpResponse();
	}

	@DeleteMapping("/subject/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object deleteSubjectByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.subjectController.deleteSubjectByNameKey(nameKey).getHttpResponse();
	}

}
