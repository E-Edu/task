package de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.ret;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWordsaladSolutionDTO {

	@NotNull @NotEmpty @NotBlank
	private String solution;

}
