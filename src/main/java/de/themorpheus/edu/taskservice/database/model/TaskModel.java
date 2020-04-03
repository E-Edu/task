package de.themorpheus.edu.taskservice.database.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

	private String description;
	private UUID authorId;
	private String task;
	private int necessaryPoints;
	private boolean verified;

	@ManyToOne
	private LectureModel lectureId;
	@ManyToOne
	private TaskTypeModel taskTypeId;
	@ManyToOne
	private DifficultyModel difficultyId;

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
