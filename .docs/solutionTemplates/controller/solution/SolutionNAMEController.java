package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionNAMEModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionNAMERepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.CheckSolutionNAMEDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.CreateSolutionNAMEDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.NAME.UpdateSolutionNAMEDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionNAMEController {

	@Autowired private SolutionNAMERepository solutionNAMERepository;

	public ControllerResult<SolutionNAMEModel> create(CreateSolutionNAMEDTO createSolutionNAMEDTO) {
		return null;	//TODO: Create solutions for task
	}

	public ControllerResult<SolutionNAMEModel> check(CheckSolutionNAMEDTO checkSolutionNAMEDTO) {
		return null;	//TODO: Check if answer is correct
						//WrongAnswer: ControllerResult.of(Error.WRONG_ANSWER);
						//CorrectAnswer: ControllerResult.empty();
	}

	public ControllerResult<SolutionNAMEModel> update(UpdateSolutionNAMEDTO updateSolutionNAMEDTO) {
		return null;	//TODO: Update all solutions
	}

	public ControllerResult<SolutionNAMEModel> delete(int taskId) {
		return ControllerResult.empty();	//TODO: Delete all solutions
	}

	public ControllerResult<SolutionNAMEModel> get(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
