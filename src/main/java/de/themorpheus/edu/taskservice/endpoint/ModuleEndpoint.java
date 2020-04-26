package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.ModuleController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateModuleRequestDTO;
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
public class ModuleEndpoint {

	@Autowired private ModuleController moduleController;

	@PostMapping("/module")
	public Object createModule(@RequestBody @Valid CreateModuleRequestDTO dto) {
		return this.moduleController.createModule(dto).getHttpResponse();
	}

	@GetMapping("/module/{moduleId:[0-9]+}")
	public Object getModule(@PathVariable @Min(1) int moduleId) {
		return this.moduleController.getModule(moduleId).getHttpResponse();
	}

	@GetMapping("/module/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object getModuleByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.moduleController.getModuleByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/module")
	public Object getModules() {
		return this.moduleController.getAllModules().getHttpResponse();
	}

	@GetMapping("/subject/{subjectId:[0-9]+}/module")
	public Object getModulesFromSubject(@PathVariable @Min(1) int subjectId) {
		return this.moduleController.getAllModulesSubjectId(subjectId).getHttpResponse();
	}

	@GetMapping("/subject/{subjectNameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}/module")
	public Object getModulesFromSubjectBySubjectNameKey(@PathVariable @NotBlank String subjectNameKey) {
		return this.moduleController.getAllModulesBySubjectNameKey(subjectNameKey).getHttpResponse();
	}

	@DeleteMapping("/module/{moduleId:[0-9]+}")
	public Object deleteModule(@PathVariable @Min(1) int moduleId) {
		return this.moduleController.deleteModule(moduleId).getHttpResponse();
	}

	@DeleteMapping("/module/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object deleteModuleByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.moduleController.deleteModuleByNameKey(nameKey).getHttpResponse();
	}

}
