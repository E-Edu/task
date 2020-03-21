package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_module")
public class ModuleModel {

	@Id
	private int moduleId;

	private SubjectModel subjectId;
	private String displayName;

}