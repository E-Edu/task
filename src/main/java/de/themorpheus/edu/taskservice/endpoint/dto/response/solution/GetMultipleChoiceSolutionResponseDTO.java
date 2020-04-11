package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMultipleChoiceSolutionResponseDTO {

	@Min(0) private int taskId;

	@NotNull
	@NotEmpty
	@Size(min = 1)
	private String[] solutions;

}
