package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.WordsaladSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.CheckWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.CreateWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.UpdateWordsaladSolutionDTO;
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
public class WordsaladSolutionEndpoint {

	@Autowired private WordsaladSolutionController wordsaladSolutionController;

	@PostMapping("/solution/wordsalad")
	public Object create(@RequestBody @Valid CreateWordsaladSolutionDTO createWordsaladSolutionDTO) {
		return this.wordsaladSolutionController.create(createWordsaladSolutionDTO).getHttpResponse();
	}

	@PostMapping("/solution/wordsalad/check")
	public Object check(@RequestBody @Valid CheckWordsaladSolutionDTO checkWordsaladSolutionDTO) {
		return this.wordsaladSolutionController.check(checkWordsaladSolutionDTO).getHttpResponse();
	}

	@GetMapping("/solution/wordsalad/{taskId}")
	public Object get(@PathVariable @Min(0) int taskId) {
		return this.wordsaladSolutionController.get(taskId).getHttpResponse();
	}

	@PutMapping("/solution/wordsalad")
	public Object update(@RequestBody @Valid UpdateWordsaladSolutionDTO updateWordsaladSolutionDTO) {
		return this.wordsaladSolutionController.update(updateWordsaladSolutionDTO).getHttpResponse();
	}

	@DeleteMapping("/solution/wordsalad/{taskId}")
	public Object delete(@PathVariable @Min(0) int taskId) {
		return this.wordsaladSolutionController.delete(taskId).getHttpResponse();
	}
}
