package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.ModuleController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateModuleDTO;
import de.themorpheus.edu.taskservice.util.Validation;
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

@RestController
public class ModuleEndpoint {

	@Autowired private ModuleController moduleController;

	@PostMapping(value = "/module", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createModule(@RequestBody @Valid CreateModuleDTO dto) {
		if (Validation.nullOrEmpty(dto.getDisplayName(), dto.getSubjectDisplayName())) return Error.INVALID_PARAM;

		return this.moduleController.createModule(dto.getDisplayName(), dto.getSubjectDisplayName());
	}

	@GetMapping("/module/{displayName}")
	public Object getModule(@PathVariable String displayName) {
		if (Validation.nullOrEmpty(displayName)) return Error.INVALID_PARAM;

		return this.moduleController.getModuleByDisplayName(displayName);
	}

	@GetMapping("/module")
	public Object getModules() {
		return this.moduleController.getAllModules();
	}

	@GetMapping("/subject/{subjectDisplayName}/module")
	public Object getModulesFromSubject(@PathVariable String subjectDisplayName) {
		if (Validation.nullOrEmpty(subjectDisplayName)) return Error.INVALID_PARAM;

		return this.moduleController.getAllModulesFromSubject(subjectDisplayName);
	}

	@DeleteMapping("/module/{displayName}")
	public Object deleteModule(@PathVariable @NotNull @NotEmpty @NotBlank String displayName) {
		this.moduleController.deleteModule(displayName);
		return null;
	}

}
