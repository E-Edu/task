package de.themorpheus.edu.taskservice.endpoint.dto.response;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSubjectsResponseDTO {

	@NotNull @Size(min = 1)
	private List<SubjectModel> subjects;

}
