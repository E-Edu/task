package com.gewia.taskservice.endpoint.dto.request.solution;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAnagramSolutionRequestDTO {

	@Min(1)
	private int taskId;

	@NotBlank
	private String solution;

}
