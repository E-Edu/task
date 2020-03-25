package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.SolutionNAMEController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.CheckSolutionNAMEDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.CreateSolutionNAMEDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.UpdateSolutionNAMEDTO;
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
public class SolutionNAMEEndpoint {

	@Autowired
	SolutionNAMEController solutionNAMEController;

	@PostMapping("/solution/NAME")
	public Object create(@RequestBody @Valid CreateSolutionNAMEDTO createSolutionNAMEDTO) {
		return this.solutionNAMEController.create(createSolutionNAMEDTO);
	}

	@PostMapping("/solution/NAME/check")
	public Object check(@RequestBody @Valid CheckSolutionNAMEDTO checkSolutionNAMEDTO) {
		return this.solutionNAMEController.check(checkSolutionNAMEDTO);
	}

	@GetMapping("/solution/NAME/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.solutionNAMEController.get(taskId);
	}

	@PutMapping("/solution/NAME")
	public Object update(@RequestBody @Valid UpdateSolutionNAMEDTO updateSolutionNAMEDTO) {
		return this.solutionNAMEController.update(updateSolutionNAMEDTO);
	}

	@DeleteMapping("/solution/NAME/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.solutionNAMEController.delete(taskId);
	}

}
