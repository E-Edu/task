package de.themorpheus.edu.taskservice.endpoint.dto.request.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckMultipleChoiceSolutionRequestDTO {

	@Min(0)
	private int taskId;

	@NotNull @Size(min = 1)
	private boolean[] solutions;

}
