package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_taskType")
public class TaskTypeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskTypeId;

	private TaskModel taskId;
	private String displayName;

}
