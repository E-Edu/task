package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckWordsaladSolutionResponseDTO {

	private boolean correct;

	@NotBlank
	private String solution;

}
