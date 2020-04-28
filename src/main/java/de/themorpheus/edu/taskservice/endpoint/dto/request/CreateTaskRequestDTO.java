package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequestDTO {

	@NotBlank
	private String task;
	@Min(1)
	private int necessaryPoints;
	private String description;
	@NotBlank
	private String language;

	private String lectureNameKey;
	private int lectureId;
	private String taskTypeNameKey;
	private int taskTypeId;
	private String difficultyNameKey;
	private int difficultyId;

}
