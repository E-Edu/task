package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSimpleEquationSolutionResponseDTOModel {

	@Min(1)
	private int simpleEquationSolutionId;

	@NotBlank
	private String step;

}
