package de.themorpheus.edu.taskservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "module")
public class ModuleModel {

	@Id
	private int moduleId;

	private String displayName;

}
