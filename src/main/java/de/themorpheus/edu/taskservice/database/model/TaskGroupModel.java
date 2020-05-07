package de.themorpheus.edu.taskservice.database.model;

import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskGroupResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Validation;
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
	@NotBlank
	private String language;

	@ManyToOne
	private LectureModel lectureId;
	@ManyToOne
	private DifficultyModel difficultyId;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID authorId;

	public TaskGroupModel updateNameKey(String nameKey) {
		if (Validation.validateNotNullOrEmpty(nameKey) && !nameKey.equals(this.nameKey)) this.nameKey = nameKey;

		return this;
	}

	public TaskGroupModel updateLanguage(String language) {
		if (Validation.validateNotNullOrEmpty(language) && !language.equals(this.language)) this.language = language;

		return this;
	}

	public TaskGroupModel updateLecture(ControllerResult<LectureModel> lectureResult) {
		if (lectureResult.isResultPresent() && !lectureResult.getResult().equals(this.lectureId))
			this.lectureId = lectureResult.getResult();

		return this;
	}

	public TaskGroupModel updateDifficulty(ControllerResult<DifficultyModel> difficultyResult) {
		if (difficultyResult.isResultPresent() && !difficultyResult.getResult().equals(this.difficultyId))
			this.difficultyId = difficultyResult.getResult();

		return this;
	}

	public TaskGroupResponseDTO toResponseDTO(int[] taskIds) {
		return new TaskGroupResponseDTO(this.taskGroupId,
				this.nameKey,
				this.language,
				this.lectureId.toResponseDTO(),
				this.difficultyId,
				this.authorId,
				taskIds
		);
	}

}
