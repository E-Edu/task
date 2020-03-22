package de.themorpheus.edu.taskservice.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDTO {

	private String task;
	private int necessaryPoints;

	private String taskTypeDisplayName;
	private String subjectDisplayName;
	private String moduleDisplayName;
	private String lectureDisplayName;
	private String difficultyDisplayName;

}
