package com.gewia.taskservice.endpoint.dto.response;

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
	private List<GetLectureResponseDTO> lectures;

}
