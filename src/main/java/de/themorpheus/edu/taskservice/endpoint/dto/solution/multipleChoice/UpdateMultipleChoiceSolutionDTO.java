package de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMultipleChoiceSolutionDTO {

	@Min(0)
	private int solutionId;

	@UniqueElements
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int multipleChoiceSolutionId;

	@NotNull @NotEmpty @NotBlank
	private String solution;

	private boolean isCorrect;

}
