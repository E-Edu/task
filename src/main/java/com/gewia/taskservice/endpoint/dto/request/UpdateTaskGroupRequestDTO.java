package com.gewia.taskservice.endpoint.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskGroupRequestDTO {

	@Min(1)
	private int taskGroupId;

	private String nameKey;
	private String language;

	private String lectureNameKey;
	private int lectureId;
	private String difficultyNameKey;
	private int difficultyId;

}
