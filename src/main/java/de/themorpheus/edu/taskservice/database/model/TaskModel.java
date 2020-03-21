package de.themorpheus.edu.taskservice.database.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task_task")
public class TaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskId;

	private UUID authorId;
	private String task;
	private int necessaryPoints;
	private boolean verified;

	public TaskModel(String task) {
		this.task = task;
	}

}
