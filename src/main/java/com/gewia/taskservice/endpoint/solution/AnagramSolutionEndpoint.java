package com.gewia.taskservice.endpoint.solution;

import com.gewia.taskservice.controller.solution.AnagramSolutionController;
import com.gewia.taskservice.endpoint.dto.request.solution.CheckAnagramSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.CreateAnagramSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.UpdateAnagramSolutionRequestDTO;
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
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class AnagramSolutionEndpoint {

	@Autowired private AnagramSolutionController anagramSolutionController;

	@PostMapping("/solution/anagram")
	public Object createAnagramSolution(@RequestBody @Valid CreateAnagramSolutionRequestDTO dto) {
		return this.anagramSolutionController.createAnagramSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/anagram/check")
	public Object checkAnagramSolution(@RequestBody @Valid CheckAnagramSolutionRequestDTO dto) {
		return this.anagramSolutionController.checkAnagramSolution(dto).getHttpResponse();
	}

	@GetMapping("/solution/anagram/{taskId}")
	public Object getAnagramSolution(@PathVariable @Min(1) int taskId) {
		return this.anagramSolutionController.getAnagramSolution(taskId).getHttpResponse();
	}

	@PutMapping("/solution/anagram")
	public Object updateAnagramSolution(@RequestBody @Valid UpdateAnagramSolutionRequestDTO dto) {
		return this.anagramSolutionController.updateAnagramSolution(dto).getHttpResponse();
	}

	@DeleteMapping("/solution/anagram/{taskId}")
	public Object deleteAnagramSolution(@PathVariable @Min(1) int taskId) {
		return this.anagramSolutionController.deleteAnagramSolution(taskId).getHttpResponse();
	}

}
