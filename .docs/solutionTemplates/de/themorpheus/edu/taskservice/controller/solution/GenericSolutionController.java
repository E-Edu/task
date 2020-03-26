package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.GenericSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.GenericSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CheckGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.CreateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.Generic.UpdateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericSolutionController {

	@Autowired private GenericSolutionRepository genericSolutionRepository;

	public ControllerResult<GenericSolutionModel> create(CreateGenericSolutionDTO createGenericSolutionDTO) {
		return null;	//TODO: Create solutions for task
	}

	public ControllerResult<GenericSolutionModel> check(CheckGenericSolutionDTO checkGenericSolutionDTO) {
		return null;	//TODO: Check if answer is correct
						//WrongAnswer: ControllerResult.of(Error.WRONG_ANSWER);
						//CorrectAnswer: ControllerResult.empty();
	}

	public ControllerResult<GenericSolutionModel> update(UpdateGenericSolutionDTO updateGenericSolutionDTO) {
		return null;	//TODO: Update all solutions
	}

	public ControllerResult<GenericSolutionModel> delete(int taskId) {
		return ControllerResult.empty();	//TODO: Delete all solutions
	}

	public ControllerResult<GenericSolutionModel> get(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
