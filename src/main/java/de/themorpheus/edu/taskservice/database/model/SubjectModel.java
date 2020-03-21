package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class SubjectModel {

	@Id
	private int subjectId;

	private TaskModel taskId;
	private String displayName;

}
