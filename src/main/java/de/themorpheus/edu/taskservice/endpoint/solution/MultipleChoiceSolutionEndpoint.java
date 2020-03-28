package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.MultipleChoiceSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.CheckMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.CreateMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.UpdateMultipleChoiceSolutionDTO;
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
public class MultipleChoiceSolutionEndpoint {

	@Autowired
	MultipleChoiceSolutionController multipleChoiceSolutionController;

	@PostMapping("/solution/multiple_choice")
	public Object create(@RequestBody @Valid CreateMultipleChoiceSolutionDTO createMultipleChoiceSolutionDTO) {
		return this.multipleChoiceSolutionController.create(createMultipleChoiceSolutionDTO);
	}

	@PostMapping("/solution/multiple_choice/check")
	public Object check(@RequestBody @Valid CheckMultipleChoiceSolutionDTO checkMultipleChoiceSolutionDTO) {
		return this.multipleChoiceSolutionController.check(checkMultipleChoiceSolutionDTO);
	}

	@GetMapping("/solution/multiple_choice/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.multipleChoiceSolutionController.get(taskId);
	}

	@PutMapping("/solution/multiple_choice")
	public Object update(@RequestBody @Valid UpdateMultipleChoiceSolutionDTO updateMultipleChoiceSolutionDTO) {
		return this.multipleChoiceSolutionController.update(updateMultipleChoiceSolutionDTO);
	}

	@DeleteMapping("/solution/multiple_choice/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.multipleChoiceSolutionController.delete(null); //TODO
	}

}
