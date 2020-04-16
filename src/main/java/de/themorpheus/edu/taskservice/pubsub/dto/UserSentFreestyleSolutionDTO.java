package de.themorpheus.edu.taskservice.pubsub.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@PubSubDTO("task.solution.freestyle")
public class UserSentFreestyleSolutionDTO {

	private UUID authorId;
	private UUID userId;
	private String solution;

}
