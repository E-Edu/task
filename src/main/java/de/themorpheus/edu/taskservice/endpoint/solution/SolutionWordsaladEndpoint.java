package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.SolutionWordsaladController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CheckSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CreateSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.UpdateSolutionWordsaladDTO;
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
public class SolutionWordsaladEndpoint {

	@Autowired
	SolutionWordsaladController solutionWordsaladController;

	@PostMapping("/solution/wordsalad")
	public Object create(@RequestBody @Valid CreateSolutionWordsaladDTO createSolutionWordsaladDTO) {
		return this.solutionWordsaladController.create(createSolutionWordsaladDTO);
	}

	@PostMapping("/solution/wordsalad/check")
	public Object check(@RequestBody @Valid CheckSolutionWordsaladDTO checkSolutionWordsaladDTO) {
		return this.solutionWordsaladController.check(checkSolutionWordsaladDTO);
	}

	@GetMapping("/solution/wordsalad/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.solutionWordsaladController.get(taskId);
	}

	@PutMapping("/solution/wordsalad")
	public Object update(@RequestBody @Valid UpdateSolutionWordsaladDTO updateSolutionWordsaladDTO) {
		return this.solutionWordsaladController.update(updateSolutionWordsaladDTO);
	}

	@DeleteMapping("/solution/wordsalad/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.solutionWordsaladController.delete(taskId);
	}

}
