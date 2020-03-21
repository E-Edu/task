package de.themorpheus.edu.taskservice.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_lecture")
public class LectureModel {

	@Id
	private int lectureId;

	private ModuleModel moduleId;
	private String displayName;

}
