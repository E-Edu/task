package com.gewia.taskservice.endpoint.dto.response.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserFreestyleSolutionsResponseDTOModel {

	@NotNull
	private UUID userId;

	@NotBlank
	private String solution;

}
