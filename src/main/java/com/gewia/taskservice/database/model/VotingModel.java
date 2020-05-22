package com.gewia.taskservice.database.model;

import com.gewia.taskservice.endpoint.dto.response.VoteTaskResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.VoteTaskWithTaskIdReponseDTO;
import java.util.UUID;
import javax.persistence.Column;
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

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	private int value;

	public VoteTaskResponseDTO toResponseDTO() {
		return new VoteTaskResponseDTO(this.votingModelId, this.userId, this.value);
	}

	public VoteTaskWithTaskIdReponseDTO toResponseDTOWithTaskId() {
		return new VoteTaskWithTaskIdReponseDTO(this.taskId.getTaskId(), this.votingModelId, this.userId, this.value);
	}

}
