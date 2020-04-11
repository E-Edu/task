package de.themorpheus.edu.taskservice.endpoint.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleRequestDTO {

	@NotNull @NotBlank @NotEmpty
	private String nameKey;
	@NotNull @NotBlank @NotEmpty
	private String subjectNameKey;

}
