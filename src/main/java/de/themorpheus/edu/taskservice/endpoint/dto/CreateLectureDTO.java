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
public class CreateLectureDTO {

	@NotNull @NotBlank @NotEmpty
	private String displayName;
	@NotNull @NotBlank @NotEmpty
	private String moduleDisplayName;

}
