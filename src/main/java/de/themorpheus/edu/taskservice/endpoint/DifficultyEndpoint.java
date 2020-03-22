package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.DifficultyController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateDifficultyDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DifficultyEndpoint {

	@Autowired private DifficultyController difficultyController;

	@PostMapping(value = "/difficulty", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createDifficulty(@RequestBody @Valid CreateDifficultyDTO dto) {
		return this.difficultyController.createDifficulty(dto.getDisplayName()).getHttpResponse();
	}

	@GetMapping("/difficulty/{displayName}")
	public Object getDifficulty(@PathVariable String displayName) {
		if (Validation.nullOrEmpty(displayName)) return Error.INVALID_PARAM;

		return this.difficultyController.getDifficultyByDisplayName(displayName).getHttpResponse();
	}

	@GetMapping("/difficulty")
	public Object getDifficulties() {
		return this.difficultyController.getAllDifficulties().getHttpResponse();
	}

	@DeleteMapping("/difficulty/{displayName}")
	public Object deleteDifficulty(@PathVariable @NotNull @NotEmpty @NotBlank String displayName) {
		this.difficultyController.deleteDifficulty(displayName);
		return ControllerResult.empty();
	}

}
