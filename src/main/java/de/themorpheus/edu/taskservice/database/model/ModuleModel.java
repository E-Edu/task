package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ModuleModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int moduleId;

	@ManyToOne
	private SubjectModel subjectId;
	private String nameKey;

}
