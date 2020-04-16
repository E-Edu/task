package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetImageSolutionResponseDTO {

	@NotNull
	@NotEmpty
	@NotBlank
	private String url;

}
