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

	@Autowired private MultipleChoiceSolutionRepository multipleChoiceSolutionRepository;

	public ControllerResult<MultipleChoiceSolutionModel> create(CreateMultipleChoiceSolutionDTO createMultipleChoiceSolutionDTO) {
		return ControllerResult.of(this.multipleChoiceSolutionRepository.save(new MultipleChoiceSolutionModel(
				createMultipleChoiceSolutionDTO.getTaskId(),
				createMultipleChoiceSolutionDTO.getSolution(),
				createMultipleChoiceSolutionDTO.isCorrect())));
	}

	public ControllerResult<MultipleChoiceSolutionModel> check(CheckMultipleChoiceSolutionDTO checkMultipleChoiceSolutionDTO) {
		MultipleChoiceSolutionModel multipleChoiceSolutionModel = this.multipleChoiceSolutionRepository.getMultipleChoiceSolutionBySolutionAndTaskId(
				checkMultipleChoiceSolutionDTO.getSolution(),
				checkMultipleChoiceSolutionDTO.getTaskId());
		if (multipleChoiceSolutionModel == null) return ControllerResult.of(Error.NOT_FOUND);
		if (multipleChoiceSolutionModel.isCorrect()) {
			return ControllerResult.empty();
		} else {
			return ControllerResult.of(Error.WRONG_ANSWER);
		}
	}

	public ControllerResult<MultipleChoiceSolutionModel> update(UpdateMultipleChoiceSolutionDTO updateMultipleChoiceSolutionDTO) {
		return null;
	}

	public ControllerResult<MultipleChoiceSolutionModel> delete(DeleteMultipleChoiceSolutionDTO deleteMultipleChoiceSolutionDTO) {
		this.multipleChoiceSolutionRepository.deleteMultipleChoiceSolutionByTaskIdAndSolutionId(
				deleteMultipleChoiceSolutionDTO.getTaskId(),
				deleteMultipleChoiceSolutionDTO.getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<MultipleChoiceSolutionController> deleteAll(int taskId) {
		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsByTaskId(taskId);
		return ControllerResult.empty();
	}

	public ControllerResult<MultipleChoiceSolutionModel> get(int taskId) {
		return null;	//TODO: Get possible solutions
	}
}
