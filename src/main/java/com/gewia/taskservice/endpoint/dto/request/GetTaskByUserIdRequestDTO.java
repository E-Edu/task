package com.gewia.taskservice.endpoint.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskByUserIdRequestDTO {

	@NotNull
	private UUID userId;

}
