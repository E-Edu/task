package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.GenericSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.GenericSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.CheckGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.CreateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.generic.UpdateGenericSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericSolutionController {

	@Autowired private GenericSolutionRepository genericSolutionRepository;

	public ControllerResult<GenericSolutionModel> createGenericSolution(CreateGenericSolutionDTO dto) {
		return null;	//TODO: Create solutions for task
	}

	public ControllerResult<GenericSolutionModel> checkGenericSolution(CheckGenericSolutionDTO dto) {
		return null;	//TODO: Check if answer is correct
						//WrongAnswer: ControllerResult.of(Error.WRONG_ANSWER);
						//CorrectAnswer: ControllerResult.empty();
	}

	public ControllerResult<GenericSolutionModel> updateGenericSolution(UpdateGenericSolutionDTO dto) {
		return null;	//TODO: Update all solutions
	}

	public ControllerResult<GenericSolutionModel> deleteGenericSolution(int taskId) {
		return null;	//TODO: Delete all solutions
	}

	public ControllerResult<GenericSolutionModel> getGenericSolution(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
