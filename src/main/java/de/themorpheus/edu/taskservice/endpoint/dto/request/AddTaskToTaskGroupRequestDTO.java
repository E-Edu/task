package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskToTaskGroupRequestDTO {

	@Min(1)
	private int taskGroupId;

	@Min(1)
	private int taskId;

}
