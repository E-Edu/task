package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubjectRequestDTO {

	@NotBlank
	private String nameKey;
	private String descriptionKey;

}
