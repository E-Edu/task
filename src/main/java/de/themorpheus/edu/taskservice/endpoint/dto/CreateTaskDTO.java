package de.themorpheus.edu.taskservice.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {

	private String task;
	private int necessaryPoints;

	private String lectureDisplayName;
	private String taskTypeDisplayName;
	private String difficultyDisplayName;

}
