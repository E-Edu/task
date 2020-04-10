package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.MultipleChoiceSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckMultipleChoiceSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateMultipleChoiceSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckedMultipleChoiceSolutionsResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetMultipleChoiceSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.MultipleChoice.NAME_KEY;

@Component
public class MultipleChoiceSolutionController implements Solution {

	@Autowired private MultipleChoiceSolutionRepository multipleChoiceSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<MultipleChoiceSolutionModel> createMultipleChoiceSolution(CreateMultipleChoiceSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		return ControllerResult.of(this.multipleChoiceSolutionRepository.save(new MultipleChoiceSolutionModel(
				-1, solutionResult.getResult(), dto.getSolution(), dto.isCorrect())
			)
		);
	}

	public ControllerResult<CheckedMultipleChoiceSolutionsResponseDTO> checkMultipleChoiceSolution(CheckMultipleChoiceSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderBySolutionDesc(
				solutionResult.getResult().getSolutionId()
			);
		if (multipleChoiceSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		boolean[] checkedSolutions = new boolean[dto.getSolutions().length];
		for (int i = 0; i < checkedSolutions.length; i++) {
			checkedSolutions[i] = multipleChoiceSolutions.get(i).getSolution().equalsIgnoreCase(dto.getSolutions()[i]);
		}

		return ControllerResult.of(new CheckedMultipleChoiceSolutionsResponseDTO(checkedSolutions));
	}

	public ControllerResult<MultipleChoiceSolutionModel> deleteMultipleChoiceSolution(int taskId, String solution) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.multipleChoiceSolutionRepository.existsById(solutionResult.getResult().getSolutionId()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.multipleChoiceSolutionRepository.deleteMultipleChoiceSolutionBySolutionIdAndSolution(taskId, solution);
		return ControllerResult.empty();
	}

	public ControllerResult<GetMultipleChoiceSolutionResponseDTO> getMultipleChoiceSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderBySolutionDesc(
				solutionResult.getResult().getSolutionId()
			);
		if (multipleChoiceSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		GetMultipleChoiceSolutionResponseDTO dto = new GetMultipleChoiceSolutionResponseDTO(
				taskId,
				multipleChoiceSolutions.stream().map(MultipleChoiceSolutionModel::getSolution).toArray(String[]::new)
		);

		return ControllerResult.of(dto);
	}

	public void deleteAll(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return;

		int solutionId = solutionResult.getResult().getSolutionId();
		if (!this.multipleChoiceSolutionRepository.existsById(solutionId)) return;

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
				.findAllMultipleChoiceSolutionsBySolutionIdOrderBySolutionDesc(solutionId);
		if (multipleChoiceSolutions.isEmpty()) return;

		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsBySolutionId(solutionId);
	}

}
