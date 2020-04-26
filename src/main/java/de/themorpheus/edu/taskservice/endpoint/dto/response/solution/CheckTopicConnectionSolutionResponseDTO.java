package de.themorpheus.edu.taskservice.endpoint.dto.response.solution;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTopicConnectionSolutionResponseDTO {

	private List<CheckTopicConnectionSolutionResponseDTOModel> correct;
	private List<CheckTopicConnectionSolutionResponseDTOModel> wrong;
	private List<CheckTopicConnectionSolutionResponseDTOModel> missing;

}
