package de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSolutionNAMEDTO {

	@Min(0)
	private int taskId;

	@NotNull @NotEmpty @NotBlank
	private String solution;

}
