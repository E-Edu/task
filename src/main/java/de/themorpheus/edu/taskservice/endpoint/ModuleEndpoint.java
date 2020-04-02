package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.ModuleController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateModuleDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import io.micrometer.core.annotation.Timed;
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

@Timed
@RestController
public class ModuleEndpoint {

	@Autowired private ModuleController moduleController;

	@PostMapping(value = "/module", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createModule(@RequestBody @Valid CreateModuleDTO dto) {
		if (Validation.nullOrEmpty(dto.getNameKey(), dto.getSubjectNameKey())) return Error.INVALID_PARAM;

		return this.moduleController.createModule(dto.getNameKey(), dto.getSubjectNameKey()).getHttpResponse();
	}

	@GetMapping("/module/{nameKey}")
	public Object getModule(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		if (Validation.nullOrEmpty(nameKey)) return Error.INVALID_PARAM;

		return this.moduleController.getModuleByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/module")
	public Object getModules() {
		return this.moduleController.getAllModules().getHttpResponse();
	}

	@GetMapping("/subject/{subjectNameKey}/module")
	public Object getModulesFromSubject(@PathVariable @NotNull @NotEmpty @NotBlank String subjectNameKey) {
		if (Validation.nullOrEmpty(subjectNameKey)) return Error.INVALID_PARAM;

		return this.moduleController.getAllModulesFromSubject(subjectNameKey).getHttpResponse();
	}

	@DeleteMapping("/module/{nameKey}")
	public Object deleteModule(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		this.moduleController.deleteModule(nameKey);
		return ControllerResult.empty().getHttpResponse();
	}

}
