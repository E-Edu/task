package de.themorpheus.edu.taskservice.endpoint.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLectureResponseDTO {

	@Min(1)
	private int lectureId;
	@NotBlank
	private String nameKey;
	@Min(1)
	private int moduleId;

}
