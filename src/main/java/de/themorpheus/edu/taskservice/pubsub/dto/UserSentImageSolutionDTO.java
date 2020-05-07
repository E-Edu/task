package de.themorpheus.edu.taskservice.pubsub.dto;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@PubSubDTO("task.task.solution.image.user")
public class UserSentImageSolutionDTO {

	@Min(1)
	private int taskId;

	@NotNull
	private UUID authorId;
	@NotNull
	private UUID userId;
	@NotBlank
	private String url;

}
