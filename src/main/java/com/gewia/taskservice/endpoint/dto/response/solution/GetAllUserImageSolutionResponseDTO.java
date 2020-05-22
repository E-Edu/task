package com.gewia.taskservice.endpoint.dto.response.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUserImageSolutionResponseDTO {

	@Size(min = 1)
	private List<GetAllUserImageSolutionsResponseDTOModel> userSolutions;

}
