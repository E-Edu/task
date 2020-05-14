package com.gewia.taskservice.endpoint.dto.response.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMultipleChoiceSolutionResponseDTOModel {

	@Min(1)
	private int multipleChoiceSolutionId;

	@NotBlank
	private String solution;

}
