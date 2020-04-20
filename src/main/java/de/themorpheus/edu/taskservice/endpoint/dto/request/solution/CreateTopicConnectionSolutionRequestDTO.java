package de.themorpheus.edu.taskservice.endpoint.dto.request.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTopicConnectionSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@NotBlank
	private String pointA;
	@NotBlank
	private String pointB;

}
