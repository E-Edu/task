package com.gewia.taskservice.database.model;

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
public class TaskGroupTaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskGroupTaskId;

	@ManyToOne
	private TaskGroupModel taskGroupId;
	@ManyToOne
	private TaskModel taskId;

}
