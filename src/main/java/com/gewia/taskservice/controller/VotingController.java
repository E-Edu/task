package com.gewia.taskservice.controller;

import com.gewia.taskservice.controller.user.UserDataHandler;
import com.gewia.taskservice.database.model.TaskModel;
import com.gewia.taskservice.database.model.VotingModel;
import com.gewia.taskservice.database.repository.VotingRepository;
import com.gewia.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.VoteTaskResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.VoteTaskWithTaskIdReponseDTO;
import com.gewia.taskservice.util.Constants;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.Task.Voting.NAME_KEY;

@Component
public class VotingController implements UserDataHandler {

	@Autowired private VotingRepository votingRepository;

	@Autowired private TaskController taskController;

	public ControllerResult<VoteTaskResponseDTO> voteTask(int taskId, VoteTaskRequestDTO dto, UUID userId) {
		ControllerResult<TaskModel> taskResult = this.taskController.getTaskRaw(taskId);
		if (taskResult.isResultNotPresent()) return ControllerResult.ret(taskResult);

		VotingModel voting = new VotingModel(
			-1,
			taskResult.getResult(),
			userId,
			dto.getVote()
		);

		return ControllerResult.of(this.votingRepository.save(voting).toResponseDTO());
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.votingRepository.findAllByUserId(userId).forEach(votingModel -> {
			votingModel.setUserId(Constants.UserId.EMPTY_UUID);
			this.votingRepository.save(votingModel);
		});
	}

	@Override
	public ControllerResult<Object> getUserData(UUID userId) {
		List<VotingModel> votings = this.votingRepository.findAllByUserId(userId);
		if (votings.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<VoteTaskWithTaskIdReponseDTO> responseDTOs = new ArrayList<>();
		votings.forEach(voting -> responseDTOs.add(voting.toResponseDTOWithTaskId()));

		return ControllerResult.of(responseDTOs);
	}

}
