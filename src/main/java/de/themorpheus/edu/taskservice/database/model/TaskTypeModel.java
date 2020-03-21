package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskType")
public class TaskTypeModel {

	@Id
	private int taskTypeId;

	private String displayName;

}
