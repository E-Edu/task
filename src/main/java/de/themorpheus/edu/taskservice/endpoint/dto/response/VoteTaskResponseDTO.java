package de.themorpheus.edu.taskservice.endpoint.dto.response;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTaskResponseDTO {

	@Min(1)
	private int votingModelId;

	@NotNull
	private UUID userId;
	private int value;

}
