package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject")
public class SubjectModel {

	@Id
	private int subjectId;

	private String displayName;

}
