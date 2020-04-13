package de.themorpheus.edu.taskservice.endpoint.dto.request.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckImageSolutionRequestDTO {

	@Min(0)
	private int taskId;

	@NotNull
	@NotEmpty
	@NotBlank
	private String solution;

}
