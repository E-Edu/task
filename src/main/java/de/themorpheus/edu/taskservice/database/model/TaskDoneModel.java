package de.themorpheus.edu.taskservice.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TaskDoneModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskDoneId;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;
	@ManyToOne
	private TaskModel taskId;
	private Date date;

}
