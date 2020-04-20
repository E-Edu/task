package de.themorpheus.edu.taskservice.endpoint.dto.response;

import java.util.UUID;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTaskResponseDTO {

	@Min(1)
	private int votingModelId;

	private int value;
	private UUID userId;

}
