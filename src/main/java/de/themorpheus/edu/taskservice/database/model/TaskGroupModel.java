package de.themorpheus.edu.taskservice.database.model;

import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskGroupResponseDTO;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TaskGroupModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskGroupId;

	@NotBlank
	private String nameKey;
	@ManyToOne
	private LectureModel lectureId;
	@ManyToOne
	private DifficultyModel difficultyId;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID authorId;

	public TaskGroupResponseDTO toResponseDTO(int[] taskIds) {
		return new TaskGroupResponseDTO(this.taskGroupId,
				this.nameKey,
				this.lectureId.getLectureId(),
				this.difficultyId.getDifficultyId(),
				this.authorId,
				taskIds
		);
	}

}
