package com.gewia.taskservice.endpoint.dto.response;

import com.gewia.taskservice.database.model.SubjectModel;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSubjectsResponseDTO {

	@Size(min = 1)
	private List<SubjectModel> subjects;

}
