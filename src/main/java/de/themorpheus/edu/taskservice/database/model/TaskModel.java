package de.themorpheus.edu.taskservice.database.model;

import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskResponseDTO;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskId;

	@NotBlank
	@Column(unique = true)
	private String task;
	private String description;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID authorId;
	@Min(0)
	private int necessaryPoints;
	private boolean verified;
	@NotBlank
	private String language;

	@ManyToOne
	private LectureModel lectureId;
	@ManyToOne
	private TaskTypeModel taskTypeId;
	@ManyToOne
	private DifficultyModel difficultyId;

	public TaskModel updateTask(String task) {
		if (Validation.validateNotNullOrEmpty(task) && !task.equals(this.task)) this.task = task;

		return this;
	}

	public TaskModel updateDescription(String description) {
		if (Validation.validateNotNullOrEmpty(description) && !description.equals(this.description))
			this.description = description;

		return this;
	}

	public TaskModel updateNecessaryPoints(int necessaryPoints) {
		if (Validation.greaterOne(necessaryPoints) && necessaryPoints != this.necessaryPoints)
			this.necessaryPoints = necessaryPoints;

		return this;
	}

	public TaskModel updateLanguage(String language) {
		if (Validation.validateNotNullOrEmpty(language) && !language.equals(this.language))
			this.language = language;

		return this;
	}

	public TaskModel updateLecture(ControllerResult<LectureModel> lectureResult) {
		if (lectureResult.isResultPresent() && lectureResult.getResult().equals(this.lectureId))
			this.lectureId = lectureResult.getResult();

		return this;
	}

	public TaskModel updateTaskType(ControllerResult<TaskTypeModel> taskTypeResult) {
		if (taskTypeResult.isResultPresent() && taskTypeResult.getResult().equals(this.taskTypeId))
			this.taskTypeId = taskTypeResult.getResult();

		return this;
	}

	public TaskModel updateDifficulty(ControllerResult<DifficultyModel> difficultyResult) {
		if (difficultyResult.isResultPresent() && difficultyResult.getResult().equals(this.difficultyId))
			this.difficultyId = difficultyResult.getResult();

		return this;
	}

	public TaskResponseDTO toResponseDTO() {
		return new TaskResponseDTO(
				this.taskId,
				this.task,
				this.description,
				this.language,
				this.authorId,
				this.necessaryPoints,
				this.verified,
				this.lectureId.toResponseDTO(),
				this.taskTypeId,
				this.difficultyId
		);
	}

	/**
	 * Creates a new {@link TaskModel} with given <i>taskId</i>.
	 * Only call this method if you <b>really</b> want to save performance by not getting a task via
	 * {@link de.themorpheus.edu.taskservice.controller.TaskController}.
	 *
	 * @param taskId the taskId
	 * @return a new {@link TaskModel} instance
	 */
	public static TaskModel create(int taskId) {
		TaskModel taskModel = new TaskModel();
		taskModel.setTaskId(taskId);
		return taskModel;
	}

}
