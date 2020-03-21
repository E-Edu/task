package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_difficulty")
public class DifficultyModel {

	@Id
	private int difficultyId;

	private TaskModel taskId;
	private String displayName;

}