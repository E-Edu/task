package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "solution")
public class SolutionModel {

	@Id
	private int solutionId;

	private String solutionType;

}
