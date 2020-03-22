package de.themorpheus.edu.taskservice.endpoint.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {

	@NotNull @NotBlank @NotEmpty
	private String task;
	@Min(0)
	private int necessaryPoints;

	@NotNull @NotBlank @NotEmpty
	private String lectureDisplayName;
	@NotNull @NotBlank @NotEmpty
	private String taskTypeDisplayName;
	@NotNull @NotBlank @NotEmpty
	private String difficultyDisplayName;

}
