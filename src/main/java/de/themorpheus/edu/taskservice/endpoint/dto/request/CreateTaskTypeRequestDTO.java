package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskTypeRequestDTO {

	@NotBlank
	private String nameKey;

}
