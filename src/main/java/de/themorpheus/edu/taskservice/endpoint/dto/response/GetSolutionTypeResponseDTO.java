package de.themorpheus.edu.taskservice.endpoint.dto.response;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSolutionTypeResponseDTO {

	@NotBlank
	private String solutionType;

}
