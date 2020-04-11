package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.MultipleChoiceSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckMultipleChoiceSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateMultipleChoiceSolutionRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class MultipleChoiceSolutionEndpoint {

	@Autowired private MultipleChoiceSolutionController multipleChoiceSolutionController;

	@PostMapping("/solution/multiple_choice")
	public Object createMultipleChoiceSolution(@RequestBody @Valid CreateMultipleChoiceSolutionRequestDTO dto) {
		return this.multipleChoiceSolutionController.createMultipleChoiceSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/multiple_choice/check")
	public Object checkMultipleChoiceSolution(@RequestBody @Valid CheckMultipleChoiceSolutionRequestDTO dto) {
		return this.multipleChoiceSolutionController.checkMultipleChoiceSolution(dto).getHttpResponse();
	}

	@GetMapping("/solution/multiple_choice/{taskId}")
	public Object getMultipleChoiceSolution(@PathVariable @Min(0) int taskId) {
		return this.multipleChoiceSolutionController.getMultipleChoiceSolution(taskId).getHttpResponse();
	}

	@DeleteMapping("/solution/multiple_choice/{taskId}/{solution}")
	public Object deleteMultipleChoiceSolution(@PathVariable @Min(0) int taskId, @PathVariable @NotNull @NotEmpty @NotBlank String solution) {
		return this.multipleChoiceSolutionController.deleteMultipleChoiceSolution(taskId, solution).getHttpResponse();
	}

}
