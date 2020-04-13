package de.themorpheus.edu.taskservice.database.model.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ImageSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int imageSolutionId;

	@OneToOne
	private SolutionModel solutionId;
	private String solution;

}
