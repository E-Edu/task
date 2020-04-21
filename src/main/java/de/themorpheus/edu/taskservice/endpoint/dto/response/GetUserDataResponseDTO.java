package de.themorpheus.edu.taskservice.endpoint.dto.response;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDataResponseDTO {

	@NotNull
	private UUID userId;

	private List<GetUserDataResponseDTOModel> data;

}
