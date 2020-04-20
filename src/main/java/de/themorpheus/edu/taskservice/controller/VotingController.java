package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.VotingModel;
import de.themorpheus.edu.taskservice.database.repository.VotingRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.VoteTaskResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotingController {

	@Autowired private VotingRepository votingRepository;

	@Autowired private TaskController taskController;

	public ControllerResult<VoteTaskResponseDTO> voteTask(int taskId, VoteTaskRequestDTO dto, UUID userId) {
		ControllerResult<TaskModel> task = this.taskController.getTaskByTaskId(taskId);
		if (task.isResultNotPresent()) return ControllerResult.ret(task);

		VotingModel voting = new VotingModel(
			-1,
			task.getResult(),
			userId,
			dto.getVote()
		);
		voting = this.votingRepository.save(voting);

		return ControllerResult.of(new VoteTaskResponseDTO(voting.getVotingModelId(), voting.getValue(), voting.getUserId()));
	}

}
