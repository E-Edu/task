package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.MultipleChoiceSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckMultipleChoiceSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateMultipleChoiceSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckMultipleChoiceSolutionsResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckMultipleChoiceSolutionsResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateMultipleChoiceSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetMultipleChoiceSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetMultipleChoiceSolutionResponseDTOModel;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.MultipleChoice.NAME_KEY;

@Component
public class MultipleChoiceSolutionController implements Solution {

	@Autowired private MultipleChoiceSolutionRepository multipleChoiceSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<CreateMultipleChoiceSolutionResponseDTO> createMultipleChoiceSolution(CreateMultipleChoiceSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.multipleChoiceSolutionRepository.existsBySolution(dto.getSolution()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		MultipleChoiceSolutionModel multipleChoiceSolution = this.multipleChoiceSolutionRepository
				.save(new MultipleChoiceSolutionModel(-1, solutionResult.getResult(), dto.getSolution(), dto.isCorrect()));


		return ControllerResult.of(new CreateMultipleChoiceSolutionResponseDTO(
				multipleChoiceSolution.getMultipleChoiceSolutionId(),
				multipleChoiceSolution.getSolution(),
				multipleChoiceSolution.isCorrect()
				)
		);
	}

	public ControllerResult<CheckMultipleChoiceSolutionsResponseDTO> checkMultipleChoiceSolution(CheckMultipleChoiceSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderByMultipleChoiceSolutionId(solutionResult.getResult());
		if (multipleChoiceSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);
		if (multipleChoiceSolutions.size() != dto.getSolutions().length) return ControllerResult.of(Error.INVALID_PARAM, NAME_KEY);

		List<CheckMultipleChoiceSolutionsResponseDTOModel> checkMultipleChoiceSolutionsResponseDTOs = new ArrayList<>();
		for (int i = 0; i < multipleChoiceSolutions.size(); i++) {
			MultipleChoiceSolutionModel multipleChoiceSolution = multipleChoiceSolutions.get(i);
			checkMultipleChoiceSolutionsResponseDTOs.add(new CheckMultipleChoiceSolutionsResponseDTOModel(
						multipleChoiceSolution.getMultipleChoiceSolutionId(),
						multipleChoiceSolution.isCorrect() == dto.getSolutions()[i]
					)
			);
		}

		return ControllerResult.of(new CheckMultipleChoiceSolutionsResponseDTO(checkMultipleChoiceSolutionsResponseDTOs));
	}

	@Transactional
	public ControllerResult<MultipleChoiceSolutionModel> deleteMultipleChoiceSolution(int taskId, String solution) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.multipleChoiceSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.multipleChoiceSolutionRepository.deleteMultipleChoiceSolutionBySolutionIdAndSolution(solutionResult.getResult(), solution);
		this.deleteSolutionIdIfDatabaseIsEmpty(solutionResult.getResult());

		return ControllerResult.empty();
	}

	public ControllerResult<GetMultipleChoiceSolutionResponseDTO> getMultipleChoiceSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
			.findAllMultipleChoiceSolutionsBySolutionIdOrderByMultipleChoiceSolutionId(solutionResult.getResult());
		if (multipleChoiceSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetMultipleChoiceSolutionResponseDTOModel> getMultipleChoiceSolutionResponseDTOs = new ArrayList<>();
		for (MultipleChoiceSolutionModel multipleChoiceSolution : multipleChoiceSolutions) {
			getMultipleChoiceSolutionResponseDTOs.add(new GetMultipleChoiceSolutionResponseDTOModel(
						multipleChoiceSolution.getMultipleChoiceSolutionId(),
						multipleChoiceSolution.getSolution()
					)
			);
		}
		Collections.shuffle(getMultipleChoiceSolutionResponseDTOs);

		return ControllerResult.of(new GetMultipleChoiceSolutionResponseDTO(getMultipleChoiceSolutionResponseDTOs));
	}

	@Override
	public void deleteAll(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return;

		SolutionModel solutionId = solutionResult.getResult();
		if (!this.multipleChoiceSolutionRepository.existsBySolutionId(solutionId)) return;

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
				.findAllMultipleChoiceSolutionsBySolutionIdOrderByMultipleChoiceSolutionId(solutionId);
		if (multipleChoiceSolutions.isEmpty()) return;

		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsBySolutionId(solutionId);
		this.solutionController.deleteSolution(solutionId);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.multipleChoiceSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
