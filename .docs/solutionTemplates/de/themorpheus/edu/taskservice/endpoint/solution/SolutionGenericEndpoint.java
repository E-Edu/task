package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.SolutionGenericController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CheckSolutionGenericDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CreateSolutionGenericDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.UpdateSolutionGenericDTO;
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
public class SolutionGenericEndpoint {

	@Autowired
	SolutionGenericController solutionGenericController;

	@PostMapping("/solution/NAME")
	public Object create(@RequestBody @Valid CreateSolutionGenericDTO createSolutionGenericDTO) {
		return this.solutionGenericController.create(createSolutionGenericDTO);
	}

	@PostMapping("/solution/NAME/check")
	public Object check(@RequestBody @Valid CheckSolutionGenericDTO checkSolutionGenericDTO) {
		return this.solutionGenericController.check(checkSolutionGenericDTO);
	}

	@GetMapping("/solution/NAME/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.solutionGenericController.get(taskId);
	}

	@PutMapping("/solution/NAME")
	public Object update(@RequestBody @Valid UpdateSolutionGenericDTO updateSolutionGenericDTO) {
		return this.solutionGenericController.update(updateSolutionGenericDTO);
	}

	@DeleteMapping("/solution/NAME/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.solutionGenericController.delete(taskId);
	}

}
