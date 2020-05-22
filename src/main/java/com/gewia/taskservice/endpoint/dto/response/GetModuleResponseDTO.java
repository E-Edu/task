package com.gewia.taskservice.endpoint.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetModuleResponseDTO {

	@Min(1)
	private int moduleId;

	@NotBlank
	private String nameKey;
	@Min(1)
	private int subjectId;

}
