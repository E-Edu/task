package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.MultipleChoiceSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.get.CheckMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.get.CreateMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.ret.CheckedMultipleChoiceSolutionsDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.multipleChoice.ret.GetMultipleChoiceSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceSolutionController implements SolutionInterface{

	private static final String NAME_KEY = "multiple_choice_solution";

	@Autowired private MultipleChoiceSolutionRepository multipleChoiceSolutionRepository;

	@Autowired private SolutionController solutionController;

	public MultipleChoiceSolutionController() {
		this.solutionController.registerSolutionInterface(this);
	}

	public ControllerResult<MultipleChoiceSolutionModel> createMultipleChoiceSolution(CreateMultipleChoiceSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		return ControllerResult.of(this.multipleChoiceSolutionRepository.save(new MultipleChoiceSolutionModel(
				optionalSolution.getResult().getSolutionId(), dto.getSolution(), dto.isCorrect())));
	}

	public ControllerResult<CheckedMultipleChoiceSolutionsDTO> checkMultipleChoiceSolution(CheckMultipleChoiceSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutionModels = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderedBySolution(
				optionalSolution.getResult().getSolutionId()
			);
		if (multipleChoiceSolutionModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		boolean[] checkedSolutions = new boolean[dto.getSolutions().length];
		for (int i = 0; i < checkedSolutions.length; i++) {
			checkedSolutions[i] = multipleChoiceSolutionModels.get(i).getSolution().equalsIgnoreCase(dto.getSolutions()[i]);
		}

		return ControllerResult.of(new CheckedMultipleChoiceSolutionsDTO(checkedSolutions));
	}

	public ControllerResult<MultipleChoiceSolutionModel> deleteMultipleChoiceSolution(int taskId, String solution) {
		this.multipleChoiceSolutionRepository.deleteMultipleChoiceSolutionBySolutionIdAndSolution(taskId, solution);
		return ControllerResult.empty();
	}

	public ControllerResult<GetMultipleChoiceSolutionDTO> getMultipleChoiceSolution(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutionModels = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderedBySolution(
				optionalSolution.getResult().getSolutionId()
			);
		if (multipleChoiceSolutionModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		GetMultipleChoiceSolutionDTO dto = new GetMultipleChoiceSolutionDTO(
				taskId,
				multipleChoiceSolutionModels.stream().map(MultipleChoiceSolutionModel::getSolution).toArray(String[]::new)
		);

		return ControllerResult.of(dto);
	}

	@Override
	public void deleteAll(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getOrCreateSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return;

		List<MultipleChoiceSolutionModel> multipleChoiceSolutionModels = this.multipleChoiceSolutionRepository.findAllById(
				Collections.singleton(optionalSolution.getResult().getSolutionId()));
		if (multipleChoiceSolutionModels.isEmpty()) return;

		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsBySolutionId(optionalSolution.getResult().getSolutionId());
	}

}
