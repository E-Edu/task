package com.gewia.taskservice.endpoint.dto.response;

import com.gewia.taskservice.database.model.TaskTypeModel;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllTaskTypesResponseDTO {

	@Size(min = 1)
	private List<TaskTypeModel> taskTypes;

}
