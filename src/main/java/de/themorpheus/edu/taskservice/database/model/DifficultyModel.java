package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class DifficultyModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int difficultyId;

	private String displayName;

}
