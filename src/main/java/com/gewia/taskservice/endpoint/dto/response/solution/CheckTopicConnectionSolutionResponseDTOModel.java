package com.gewia.taskservice.endpoint.dto.response.solution;

import com.gewia.taskservice.endpoint.dto.request.solution.CheckTopicConnectionSolutionRequestDTOModel;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTopicConnectionSolutionResponseDTOModel {

	@Min(1)
	private int topicConnectionSolutionId;

	@NotBlank
	private String pointA;
	@NotBlank
	private String pointB;

	public CheckTopicConnectionSolutionRequestDTOModel toRequestDTOModel() {
		return new CheckTopicConnectionSolutionRequestDTOModel(this.pointA, this.pointB);
	}

	public CheckTopicConnectionSolutionResponseDTOModel toResponseDTOModel() {
		return new CheckTopicConnectionSolutionResponseDTOModel(this.topicConnectionSolutionId, this.pointA, this.pointB);
	}

}
