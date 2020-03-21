package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class TaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int taskId;

	private String task;

	public TaskModel(String task) {
		this.task = task;
	}

}
