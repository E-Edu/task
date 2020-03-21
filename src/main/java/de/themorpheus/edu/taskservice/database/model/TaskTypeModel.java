package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_taskType")
public class TaskTypeModel {

	@Id
	private int taskTypeId;

	private TaskModel taskId;
	private String displayName;

}