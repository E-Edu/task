package de.themorpheus.edu.taskservice.endpoint.dto.response;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllLecturesResponseDTO {

	@Size(min = 1)
	private List<LectureModel> lectures;

}
