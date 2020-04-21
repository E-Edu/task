package de.themorpheus.edu.taskservice.endpoint.dto.request;

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
	private int lectureId;
	private int difficultyId;

}
