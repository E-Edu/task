package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFreestyleSolutionResponseDTO {

	@NotNull
	@NotEmpty
	@NotBlank
	private String solution;

}
