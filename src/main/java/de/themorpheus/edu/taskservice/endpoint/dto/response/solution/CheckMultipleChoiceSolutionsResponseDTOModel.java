package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckMultipleChoiceSolutionsResponseDTOModel {

	@Min(1)
	private int multipleChoiceSolutionId;

	private boolean correct;

}
