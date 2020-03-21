package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_solution")
public class SolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int solutionId;

	@OneToOne
	private TaskModel taskId;
	private String solutionType;

}
