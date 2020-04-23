package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskGroupRequestDTO {

	@NotBlank
	private String nameKey;

	private String lectureNameKey;
	private int lectureId;
	private String difficultyNameKey;
	private int difficultyId;

	@Size(min = 1)
	private int[] taskIds;

}
