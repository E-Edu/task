package de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.ret;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckedMultipleChoiceSolutionsDTO {

	@NotNull
	@NotEmpty
	@Size(min = 1)
	private boolean[] solutions;

}
