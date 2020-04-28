package de.themorpheus.edu.taskservice.database.model;

import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskResponseDTO;
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
		//TODO: ObjectPool
		TaskModel taskModel = new TaskModel();
		taskModel.setTaskId(taskId);
		return taskModel;
	}

}
