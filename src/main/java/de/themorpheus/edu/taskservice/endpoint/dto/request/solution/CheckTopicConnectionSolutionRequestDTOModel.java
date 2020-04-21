package de.themorpheus.edu.taskservice.endpoint.dto.request.solution;

import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckTopicConnectionSolutionResponseDTOModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTopicConnectionSolutionRequestDTOModel {

	@NotBlank
	private String pointA;
	@NotBlank
	private String pointB;

	public CheckTopicConnectionSolutionResponseDTOModel toResponseDTOModel(int topicConnectionSolutionId) {
		return new CheckTopicConnectionSolutionResponseDTOModel(topicConnectionSolutionId, this.pointA, this.pointB);
	}

}
