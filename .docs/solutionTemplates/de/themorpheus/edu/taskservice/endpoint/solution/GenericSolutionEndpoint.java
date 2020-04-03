package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.GenericSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.CheckGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.CreateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.UpdateGenericSolutionDTO;
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

	@Autowired private GenericSolutionController genericSolutionController;

	@PostMapping("/solution/generic")
	public Object createGenericSolution(@RequestBody @Valid CreateGenericSolutionDTO createGenericSolutionDTO) {
		return this.genericSolutionController.create(createGenericSolutionDTO);
	}

	@PostMapping("/solution/generic/check")
	public Object checkGenericSolution(@RequestBody @Valid CheckGenericSolutionDTO checkGenericSolutionDTO) {
		return this.genericSolutionController.check(checkGenericSolutionDTO);
	}

	@GetMapping("/solution/generic/{taskId}")
	public Object getGenericSolution(@PathVariable @Min(0) int taskId) {
		return this.genericSolutionController.get(taskId);
	}

	@PutMapping("/solution/generic")
	public Object updateGenericSolution(@RequestBody @Valid UpdateGenericSolutionDTO updateGenericSolutionDTO) {
		return this.genericSolutionController.update(updateGenericSolutionDTO);
	}

	@DeleteMapping("/solution/generic/{taskId}")
	public Object deleteGenericSolution(@PathVariable @Min(0) int taskId) {
		return this.genericSolutionController.delete(taskId);
	}

}
