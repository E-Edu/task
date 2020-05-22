package com.gewia.taskservice.endpoint.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLectureRequestDTO {

	@NotBlank
	private String nameKey;
	private String moduleNameKey;
	private int moduleId;

}
