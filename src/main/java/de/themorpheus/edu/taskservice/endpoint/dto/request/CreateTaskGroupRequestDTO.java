package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.Min;
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
	@Min(1)
	private int lectureId;
	@Min(1)
	private int difficultyId;
	@Size(min = 1)
	private int[] taskIds;

}
