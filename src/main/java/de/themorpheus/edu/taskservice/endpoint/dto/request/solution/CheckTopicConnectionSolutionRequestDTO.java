package de.themorpheus.edu.taskservice.endpoint.dto.request.solution;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTopicConnectionSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@Size(min = 1)
	private List<CheckTopicConnectionSolutionRequestDTOModel> connectedPoints;

}
