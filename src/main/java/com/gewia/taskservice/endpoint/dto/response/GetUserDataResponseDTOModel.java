package com.gewia.taskservice.endpoint.dto.response;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDataResponseDTOModel {

	@NotBlank
	private String nameKey;
	private Object queriedData;

}
