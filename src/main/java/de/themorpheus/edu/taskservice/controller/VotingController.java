package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.controller.user.UserDataHandler;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.VotingModel;
import de.themorpheus.edu.taskservice.database.repository.VotingRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.VoteTaskResponseDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotingController implements UserDataHandler {

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

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.votingRepository.findAllByUserId(userId).forEach(votingModel -> {
			votingModel.setUserId(Constants.UserId.EMPTY_UUID);
			this.votingRepository.save(votingModel);
		});
	}

	@Override
	public Object getUserData(UUID userId) {
		return this.votingRepository.findAllByUserId(userId);
	}

}
