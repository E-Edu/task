package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMultipleChoiceSolutionResponseDTO {

	@Size(min = 1)
	private List<GetMultipleChoiceSolutionResponseDTOModel> solutions;

}
