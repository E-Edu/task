package com.gewia.taskservice.endpoint.dto.request.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckMultipleChoiceSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@Size(min = 1)
	private boolean[] solutions;

}
