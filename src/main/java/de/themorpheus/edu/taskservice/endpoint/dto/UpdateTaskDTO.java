package de.themorpheus.edu.taskservice.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDTO {

	private String task;
	private int necessaryPoints;

	private String taskTypeNameKey;
	private String subjectNameKey;
	private String moduleNameKey;
	private String lectureNameKey;
	private String difficultyNameKey;

}
