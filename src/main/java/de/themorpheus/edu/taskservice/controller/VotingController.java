package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.VotingModel;
import de.themorpheus.edu.taskservice.database.repository.VotingRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VotingController {

	@Autowired private VotingRepository votingRepository;

	@Autowired private TaskController taskController;

	public ControllerResult<Object> voteTask(int taskId, int vote, UUID userId) {
		TaskModel taskModel = this.taskController.getTaskByTaskId(taskId);

		// Check if task exists
		if (Validation.validateNull(taskModel)) return ControllerResult.of(Error.NOT_FOUND);

		VotingModel votingModel = new VotingModel(
			-1,
			taskModel,
			userId,
			vote
		);
		return ControllerResult.of(this.votingRepository.save(votingModel));
	}

}
