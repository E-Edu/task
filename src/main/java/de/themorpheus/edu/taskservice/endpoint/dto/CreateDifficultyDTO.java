package de.themorpheus.edu.taskservice.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDifficultyDTO {

	@NotNull @NotBlank @NotEmpty
	private String nameKey;

}
