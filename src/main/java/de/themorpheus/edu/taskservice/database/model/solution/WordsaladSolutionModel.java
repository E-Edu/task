package de.themorpheus.edu.taskservice.database.model.solution;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class WordsaladSolutionModel {

	@Id
	private int taskId;

	private String solution;

}
