package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "task_task")
public class TaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int taskId;

	private UUID authorId;
	private String task;
	private int necessaryPoints;
	private boolean verified;

	public TaskModel(String task) {
		this.task = task;
	}

}
