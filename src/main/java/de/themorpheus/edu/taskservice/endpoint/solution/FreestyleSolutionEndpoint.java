package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.FreestyleSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateFreestyleSolutionRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class FreestyleSolutionEndpoint {

	@Autowired private FreestyleSolutionController freestyleSolutionController;

	@PostMapping("/solution/freestyle")
	public Object createFreestyleSolution(@RequestBody @Valid CreateFreestyleSolutionRequestDTO dto) {
		return this.freestyleSolutionController.createFreestyleSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/freestyle/check")
	public Object checkFreestyleSolution(@RequestBody @Valid CheckFreestyleSolutionRequestDTO dto) {
		return this.freestyleSolutionController.checkFreestyleSolution(dto).getHttpResponse();
	}

	@PutMapping("/solution/freestyle")
	public Object updateFreestyleSolution(@RequestBody @Valid UpdateFreestyleSolutionRequestDTO dto) {
		return this.freestyleSolutionController.updateFreestyleSolution(dto).getHttpResponse();
	}

	@DeleteMapping("/solution/freestyle/{taskId}")
	public Object deleteFreestyleSolution(@PathVariable @Min(1) int taskId) {
		return this.freestyleSolutionController.deleteFreestyleSolution(taskId).getHttpResponse();
	}

}
