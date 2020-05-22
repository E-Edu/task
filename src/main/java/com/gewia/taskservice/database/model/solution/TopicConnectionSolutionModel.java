package com.gewia.taskservice.database.model.solution;

import com.gewia.taskservice.endpoint.dto.response.solution.CheckTopicConnectionSolutionResponseDTOModel;
import com.gewia.taskservice.endpoint.dto.response.solution.GetAllTopicConnectionSolutionsResponseDTOModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TopicConnectionSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int topicConnectionSolutionId;

	@ManyToOne
	private SolutionModel solutionId;
	@NotBlank
	private String pointA;
	@NotBlank
	private String pointB;

	public CheckTopicConnectionSolutionResponseDTOModel toCheckResponseDTOModel() {
		return new CheckTopicConnectionSolutionResponseDTOModel(
				this.topicConnectionSolutionId,
				this.pointA,
				this.pointB
		);
	}

	public GetAllTopicConnectionSolutionsResponseDTOModel toGetAllResponseDTOModel() {
		return new GetAllTopicConnectionSolutionsResponseDTOModel(
				this.topicConnectionSolutionId,
				this.pointA,
				this.pointB
		);
	}

}
