package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.SubjectController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateSubjectDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@PostMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createSubject(@RequestBody @Valid CreateSubjectDTO dto) {
		return this.subjectController.createSubject(dto.getNameKey(), dto.getDescriptionKey()).getHttpResponse();
	}

	@GetMapping("/subject/{nameKey}")
	public Object getSubject(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		return this.subjectController.getSubjectByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/subject")
	public Object getSubjects() {
		return this.subjectController.getAllSubjects().getHttpResponse();
	}

	@DeleteMapping(path = "/subject/{nameKey}")
	public Object deleteSubject(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		return this.subjectController.deleteSubject(nameKey).getHttpResponse();
	}

}
