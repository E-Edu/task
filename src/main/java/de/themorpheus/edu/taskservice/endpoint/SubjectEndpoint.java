package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.SubjectController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateSubjectDTO;
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
public class SubjectEndpoint {

	@Autowired private SubjectController subjectController;

	@PostMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createSubject(@RequestBody @Valid CreateSubjectDTO dto) {
		return this.subjectController.createSubject(dto.getDisplayName());
	}

	@GetMapping("/subject/{displayName}")
	public Object getSubject(@PathVariable String displayName) {
		if (Validation.nullOrEmpty(displayName)) return Error.INVALID_PARAM;

		return this.subjectController.getSubjectByDisplayName(displayName);
	}

	@GetMapping("/subject")
	public Object getSubjects() {
		return this.subjectController.getAllSubjects();
	}

}
