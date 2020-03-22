package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.SubjectController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateSubjectDTO;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class SubjectEndpoint {

	@Autowired private SubjectController subjectController;

	@PostMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createSubject(@RequestBody @Valid CreateSubjectDTO dto) {
		String displayName = dto.getDisplayName();

		if (Validation.validateNull(displayName)) return Error.INVALID_PARAM;

		if (this.subjectController.doesSubjectExist(displayName)) return Error.ALREADY_EXISTS;

		return this.subjectController.createSubject(displayName);
	}

	@GetMapping("/subject/{displayName}")
	public Object getSubject(@PathVariable @Valid String displayName) {
		return this.subjectController.getSubjectByDisplayName(displayName).getHttpResponse();
	}

	@GetMapping("/subject")
	public Object getSubjects() {
		return this.subjectController.getAllSubjects().getHttpResponse();
	}

	@DeleteMapping(path = "/subject/{displayName}")
	public void deleteSubject(@PathVariable @Valid String displayName) {
		this.subjectController.deleteSubject(displayName);
	}

}
