package com.gewia.taskservice.endpoint;

import com.gewia.taskservice.controller.DifficultyController;
import com.gewia.taskservice.endpoint.dto.request.CreateDifficultyRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class DifficultyEndpoint {

	@Autowired private DifficultyController difficultyController;

	@PostMapping("/difficulty")
	public Object createDifficulty(@RequestBody @Valid CreateDifficultyRequestDTO dto) {
		return this.difficultyController.createDifficulty(dto).getHttpResponse();
	}

	@GetMapping("/difficulty/{difficultyId:[0-9]+}")
	public Object getDifficulty(@PathVariable @Min(1) int difficultyId) {
		return this.difficultyController.getDifficulty(difficultyId).getHttpResponse();
	}

	@GetMapping("/difficulty/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object getDifficultyByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.difficultyController.getDifficultyByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/difficulty")
	public Object getDifficulties() {
		return this.difficultyController.getAllDifficulties().getHttpResponse();
	}

	@DeleteMapping("/difficulty/{difficultyId:[0-9]+}")
	public Object deleteDifficulty(@PathVariable @Min(1) int difficultyId) {
		return this.difficultyController.deleteDifficulty(difficultyId).getHttpResponse();
	}

	@DeleteMapping("/difficulty/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object deleteDifficultyByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.difficultyController.deleteDifficultyByNameKey(nameKey).getHttpResponse();
	}

}
