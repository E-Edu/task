package com.gewia.taskservice.endpoint.dto.response;

import com.gewia.taskservice.database.model.DifficultyModel;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllDifficultiesResponseDTO {

	@Size(min = 1)
	private List<DifficultyModel> difficulties;

}
