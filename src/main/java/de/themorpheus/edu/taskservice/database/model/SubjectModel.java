package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class SubjectModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subjectId;

	@NotBlank
	@Column(unique = true)
	private String nameKey;
	private String descriptionKey;

}
