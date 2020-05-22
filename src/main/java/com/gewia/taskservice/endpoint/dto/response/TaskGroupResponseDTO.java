package com.gewia.taskservice.endpoint.dto.response;

import com.gewia.taskservice.database.model.DifficultyModel;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskGroupResponseDTO {

	@Min(1)
	private int taskGroupId;

	@NotBlank
	private String nameKey;
	@NotBlank
	private String language;

	@NotNull
	private GetLectureResponseDTO lectureId;
	@NotNull
	private DifficultyModel difficultyId;

	@NotNull
	private UUID authorId;

	@Size(min = 1)
	private int[] taskIds;

}
