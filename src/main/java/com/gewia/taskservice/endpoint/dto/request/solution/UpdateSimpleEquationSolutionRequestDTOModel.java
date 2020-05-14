package com.gewia.taskservice.endpoint.dto.request.solution;

import com.gewia.taskservice.database.model.solution.SimpleEquationSolutionModel;
import com.gewia.taskservice.database.model.solution.SolutionModel;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class UpdateSimpleEquationSolutionRequestDTOModel {

	@Min(1)
	private int simpleEquationSolutionId;

	@NotBlank
	private String step;

	public SimpleEquationSolutionModel toModel(SolutionModel solutionId) {
		return new SimpleEquationSolutionModel(this.simpleEquationSolutionId, solutionId, this.step);
	}

}
