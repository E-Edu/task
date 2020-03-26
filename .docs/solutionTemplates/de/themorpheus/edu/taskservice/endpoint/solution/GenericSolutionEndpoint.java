package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.GenericSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CheckGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CreateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.UpdateGenericSolutionDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericSolutionEndpoint {

	@Autowired
	GenericSolutionController genericSolutionController;

	@PostMapping("/solution/Generic")
	public Object create(@RequestBody @Valid CreateGenericSolutionDTO createGenericSolutionDTO) {
		return this.genericSolutionController.create(createGenericSolutionDTO);
	}

	@PostMapping("/solution/Generic/check")
	public Object check(@RequestBody @Valid CheckGenericSolutionDTO checkGenericSolutionDTO) {
		return this.genericSolutionController.check(checkGenericSolutionDTO);
	}

	@GetMapping("/solution/Generic/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.genericSolutionController.get(taskId);
	}

	@PutMapping("/solution/Generic")
	public Object update(@RequestBody @Valid UpdateGenericSolutionDTO updateGenericSolutionDTO) {
		return this.genericSolutionController.update(updateGenericSolutionDTO);
	}

	@DeleteMapping("/solution/Generic/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.genericSolutionController.delete(taskId);
	}

}
