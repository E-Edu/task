package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "difficulty")
public class DifficultyModel {

	@Id
	private int difficultyId;

	private String displayName;

}
