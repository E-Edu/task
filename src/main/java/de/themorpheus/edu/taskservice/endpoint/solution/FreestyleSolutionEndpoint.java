package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.FreestyleSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CheckFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CreateFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.UpdateFreestyleSolutionDTO;

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
public class FreestyleSolutionEndpoint {

	@Autowired
	private FreestyleSolutionController freestyleSolutionController;

	@PostMapping("/solution/freestyle")
	public Object createFreestyleSolution(@RequestBody @Valid CreateFreestyleSolutionDTO createFreestyleSolutionDTO) {
		return this.freestyleSolutionController.createSolutionFreestyle(createFreestyleSolutionDTO).getHttpResponse();
	}

	@PostMapping("/solution/freestyle/check")
	public Object checkFreestyleSolution(@RequestBody @Valid CheckFreestyleSolutionDTO checkFreestyleSolutionDTO) {
		return this.freestyleSolutionController.checkSolutionFreestyle(checkFreestyleSolutionDTO).getHttpResponse();
	}

	@GetMapping("/solution/freestyle/{taskId}")
	public Object getFreestyleSolution(@PathVariable @Min(0) int taskId) {
		return this.freestyleSolutionController.getSolutionFreestyle(taskId).getHttpResponse();
	}

	@PutMapping("/solution/freestyle")
	public Object updateFreestyleSolution(@RequestBody @Valid UpdateFreestyleSolutionDTO updateFreestyleSolutionDTO) {
		return this.freestyleSolutionController.updateSolutionFreestyle(updateFreestyleSolutionDTO).getHttpResponse();
	}

	@DeleteMapping("/solution/freestyle/{taskId}")
	public Object deleteFreestyleSolution(@PathVariable @Min(0) int taskId) {
		return this.freestyleSolutionController.deleteSolutionFreestyle(taskId).getHttpResponse();
	}

}
