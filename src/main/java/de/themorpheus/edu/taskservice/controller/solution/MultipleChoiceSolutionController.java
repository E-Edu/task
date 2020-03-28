package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.MultipleChoiceSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.CheckMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.CreateMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.DeleteMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.UpdateMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceSolutionController {

	private static final String NAME_KEY = "multipleChoice";

	@Autowired private MultipleChoiceSolutionRepository multipleChoiceSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<MultipleChoiceSolutionModel> create(CreateMultipleChoiceSolutionDTO dto) {
		ControllerResult<Integer> controllerResult = this.solutionController.getSolutionIdByTaskIdAndNameKey(dto.getTaskId(), NAME_KEY);
		if (controllerResult.isErrorPresent()) return ControllerResult.of(controllerResult.getError(), controllerResult.getExtra());

		return ControllerResult.of(this.multipleChoiceSolutionRepository.save(new MultipleChoiceSolutionModel(
				controllerResult.getResult(),
				dto.getSolution(),
				dto.isCorrect())));
	}

	public ControllerResult<MultipleChoiceSolutionModel> check(CheckMultipleChoiceSolutionDTO dto) {
		MultipleChoiceSolutionModel multipleChoiceSolutionModel = this.multipleChoiceSolutionRepository.getMultipleChoiceSolutionBySolutionAndSolutionId(
				dto.getSolution(),
				dto.getTaskId());
		if (multipleChoiceSolutionModel == null) return ControllerResult.of(Error.NOT_FOUND, "multipleChoiceSolution");
		if (multipleChoiceSolutionModel.isCorrect()) {
			return ControllerResult.empty();
		} else {
			return ControllerResult.of(Error.WRONG_ANSWER, "multipleChoiceSolution");
		}
	}

	public ControllerResult<MultipleChoiceSolutionModel> update(UpdateMultipleChoiceSolutionDTO dto) {
		return null;
	}

	public ControllerResult<MultipleChoiceSolutionModel> delete(DeleteMultipleChoiceSolutionDTO dto) {
		//this.multipleChoiceSolutionRepository.deleteMultipleChoiceSolutionByTaskIdAndSolutionId(
		//		dto.getTaskId(),
		//		dto.getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<MultipleChoiceSolutionController> deleteAll(int taskId) {
		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsBySolutionId(taskId);
		return ControllerResult.empty();
	}

	public ControllerResult<MultipleChoiceSolutionModel> get(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
