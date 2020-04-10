package de.themorpheus.edu.taskservice.endpoint.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNextTaskRequestDTO {

	@NotNull @Size(min = 1)
	private List<Integer> lastTaskIds;

}
