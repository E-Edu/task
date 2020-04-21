package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUserFreestyleSolutionsResponseDTO {

	@Size(min = 1)
	private List<GetAllUserFreestyleSolutionsResponseDTOModel> userSolutions;

}
