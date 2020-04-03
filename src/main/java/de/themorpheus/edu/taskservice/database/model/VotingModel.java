package de.themorpheus.edu.taskservice.database.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class VotingModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int votingModelId;

	@OneToOne
	private TaskModel taskId;

	private UUID userId;

	private int value;

}
