package de.themorpheus.edu.taskservice.endpoint.dto.response;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

	@Min(1)
	private int taskId;

	@NotBlank
	private String task;
	private String description;

	@NotNull
	private UUID authorId;

	@Min(0)
	private int necessaryPoints;
	private boolean verified;

	@NotNull
	private GetLectureResponseDTO lecture;
	@NotNull
	private TaskTypeModel taskTypeId;
	@NotNull
	private DifficultyModel difficultyId;

}
