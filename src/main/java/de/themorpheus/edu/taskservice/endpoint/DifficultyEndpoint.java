package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.DifficultyController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateDifficultyRequestDTO;
import javax.validation.Valid;
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
public class DifficultyEndpoint {

	@Autowired private DifficultyController difficultyController;

	@PostMapping(value = "/difficulty")
	public Object createDifficulty(@RequestBody @Valid CreateDifficultyRequestDTO dto) {
		return this.difficultyController.createDifficulty(dto.getNameKey()).getHttpResponse();
	}

	@GetMapping("/difficulty/{nameKey}")
	public Object getDifficulty(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		return this.difficultyController.getDifficultyByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/difficulty")
	public Object getDifficulties() {
		return this.difficultyController.getAllDifficulties().getHttpResponse();
	}

	@DeleteMapping("/difficulty/{nameKey}")
	public Object deleteDifficulty(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		return this.difficultyController.deleteDifficulty(nameKey).getHttpResponse();
	}

}
