package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "solution")
public class SolutionModel {

	@Id
	private int solutionId;

	private TaskModel taskId;
	private String solutionType;

}
