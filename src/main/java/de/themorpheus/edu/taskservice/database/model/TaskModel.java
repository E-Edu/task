package de.themorpheus.edu.taskservice.database.model;

import java.util.UUID;
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
public class TaskModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskId;

	private UUID authorId;
	private String task;
	private int necessaryPoints;
	private boolean verified;

	@ManyToOne
	private LectureModel lectureId;
	@ManyToOne
	private TaskTypeModel taskTypeId;
	@ManyToOne
	private DifficultyModel difficultyId;

}
