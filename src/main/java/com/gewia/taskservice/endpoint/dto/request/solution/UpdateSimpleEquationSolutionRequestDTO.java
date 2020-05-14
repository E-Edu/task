package com.gewia.taskservice.endpoint.dto.request.solution;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSimpleEquationSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@NotBlank
	private String result;
	@NotBlank
	private List<UpdateSimpleEquationSolutionRequestDTOModel> steps;

}
