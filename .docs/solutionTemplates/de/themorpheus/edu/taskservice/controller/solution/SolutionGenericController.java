package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionGenericModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionGenericRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CheckSolutionGenericDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CreateSolutionGenericDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.UpdateSolutionGenericDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionGenericController {

	@Autowired private SolutionGenericRepository solutionGenericRepository;

	public ControllerResult<SolutionGenericModel> create(CreateSolutionGenericDTO createSolutionGenericDTO) {
		return null;	//TODO: Create solutions for task
	}

	public ControllerResult<SolutionGenericModel> check(CheckSolutionGenericDTO checkSolutionGenericDTO) {
		return null;	//TODO: Check if answer is correct
						//WrongAnswer: ControllerResult.of(Error.WRONG_ANSWER);
						//CorrectAnswer: ControllerResult.empty();
	}

	public ControllerResult<SolutionGenericModel> update(UpdateSolutionGenericDTO updateSolutionGenericDTO) {
		return null;	//TODO: Update all solutions
	}

	public ControllerResult<SolutionGenericModel> delete(int taskId) {
		return ControllerResult.empty();	//TODO: Delete all solutions
	}

	public ControllerResult<SolutionGenericModel> get(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
