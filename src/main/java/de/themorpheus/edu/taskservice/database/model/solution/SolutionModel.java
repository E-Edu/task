package de.themorpheus.edu.taskservice.database.model.solution;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class SolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int solutionId;

	@OneToOne
	private TaskModel taskId;
	@NotBlank
	private String solutionType;

}
