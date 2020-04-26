package de.themorpheus.edu.taskservice.endpoint.dto.response;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllTaskGroupsResponseDTO {

	@Size(min = 1)
	private List<TaskGroupResponseDTO> taskGroups;

}
