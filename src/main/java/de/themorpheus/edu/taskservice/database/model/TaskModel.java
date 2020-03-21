package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class TaskModel {

	@Id
	private int taskId;

	private String task;

	public TaskModel(String task) {
		this.task = task;
	}

}
