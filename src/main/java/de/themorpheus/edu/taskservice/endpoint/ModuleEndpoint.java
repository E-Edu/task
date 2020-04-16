package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.ModuleController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateModuleRequestDTO;
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
public class ModuleEndpoint {

	@Autowired private ModuleController moduleController;

	@PostMapping("/module")
	public Object createModule(@RequestBody @Valid CreateModuleRequestDTO dto) {
		return this.moduleController.createModule(dto).getHttpResponse();
	}

	@GetMapping("/module/{nameKey}")
	public Object getModule(@PathVariable @NotBlank String nameKey) {
		return this.moduleController.getModuleByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/module")
	public Object getModules() {
		return this.moduleController.getAllModules().getHttpResponse();
	}

	@GetMapping("/subject/{subjectNameKey}/module")
	public Object getModulesFromSubject(@PathVariable @NotBlank String subjectNameKey) {
		return this.moduleController.getAllModulesFromSubject(subjectNameKey).getHttpResponse();
	}

	@DeleteMapping("/module/{nameKey}")
	public Object deleteModule(@PathVariable @NotBlank String nameKey) {
		return this.moduleController.deleteModule(nameKey).getHttpResponse();
	}

}
