package com.gewia.taskservice.database.model.solution;

import com.gewia.taskservice.endpoint.dto.response.solution.CheckSimpleEquationSolutionResponseDTOModel;
import com.gewia.taskservice.endpoint.dto.response.solution.CreateSimpleEquationSolutionResponseDTOModel;
import com.gewia.taskservice.endpoint.dto.response.solution.UpdateSimpleEquationSolutionResponseDTOModel;
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
public class SimpleEquationSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int simpleEquationSolutionId;

	@ManyToOne
	private SolutionModel solutionId;
	@NotBlank
	private String step;

	public CreateSimpleEquationSolutionResponseDTOModel toCreateResponseDTOModel() {
		return new CreateSimpleEquationSolutionResponseDTOModel(this.simpleEquationSolutionId, this.step);
	}

	public UpdateSimpleEquationSolutionResponseDTOModel toUpdateResponseDTOModel() {
		return new UpdateSimpleEquationSolutionResponseDTOModel(this.simpleEquationSolutionId, this.step);
	}

	public CheckSimpleEquationSolutionResponseDTOModel toCheckResponseDTOModel() {
		return new CheckSimpleEquationSolutionResponseDTOModel(this.simpleEquationSolutionId, this.step);
	}
}
