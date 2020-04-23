package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequestDTO {

	@Min(1)
	private int taskId;

	private String task;
	private String description;
	private int necessaryPoints;

	private String lectureNameKey;
	private int lectureId;
	private String taskTypeNameKey;
	private int taskTypeId;
	private String difficultyNameKey;
	private int difficultyId;

}
