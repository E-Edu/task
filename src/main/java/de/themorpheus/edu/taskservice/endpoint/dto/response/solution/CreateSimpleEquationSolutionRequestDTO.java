package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSimpleEquationSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@NotBlank
	private String result;
	@Size(min = 1)
	private String[] steps;

}
