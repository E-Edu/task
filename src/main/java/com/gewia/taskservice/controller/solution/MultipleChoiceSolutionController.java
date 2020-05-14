package com.gewia.taskservice.controller.solution;

import com.gewia.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import com.gewia.taskservice.database.model.solution.SolutionModel;
import com.gewia.taskservice.database.repository.solution.MultipleChoiceSolutionRepository;
import com.gewia.taskservice.endpoint.dto.request.solution.CheckMultipleChoiceSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.CreateMultipleChoiceSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.CheckMultipleChoiceSolutionsResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.CheckMultipleChoiceSolutionsResponseDTOModel;
import com.gewia.taskservice.endpoint.dto.response.solution.CreateMultipleChoiceSolutionResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.GetAllMultipleChoiceSolutionsResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.GetAllMultipleChoiceSolutionsResponseDTOModel;
import com.gewia.taskservice.endpoint.dto.response.solution.GetMultipleChoiceSolutionResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.GetMultipleChoiceSolutionResponseDTOModel;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.Solution.MultipleChoice.NAME_KEY;

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
	public ControllerResult<MultipleChoiceSolutionModel> deleteMultipleChoiceSolution(int multipleChoiceSolutionId) {
		Optional<MultipleChoiceSolutionModel> optionalMultipleChoiceSolution = this.multipleChoiceSolutionRepository
				.findById(multipleChoiceSolutionId);

		if (!optionalMultipleChoiceSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.multipleChoiceSolutionRepository.delete(optionalMultipleChoiceSolution.get());
		this.deleteSolutionIdIfDatabaseIsEmpty(optionalMultipleChoiceSolution.get().getSolutionId());

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

	public ControllerResult<GetAllMultipleChoiceSolutionsResponseDTO> getAllMultipleChoiceSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<MultipleChoiceSolutionModel> multipleChoiceSolutions = this.multipleChoiceSolutionRepository
				.findAllMultipleChoiceSolutionsBySolutionIdOrderByMultipleChoiceSolutionId(solutionResult.getResult());
		if (multipleChoiceSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<GetAllMultipleChoiceSolutionsResponseDTOModel> getAllMultipleChoiceSolutionsResponseDTOs = new ArrayList<>();
		for (MultipleChoiceSolutionModel multipleChoiceSolution : multipleChoiceSolutions) {
			getAllMultipleChoiceSolutionsResponseDTOs.add(new GetAllMultipleChoiceSolutionsResponseDTOModel(
					multipleChoiceSolution.getMultipleChoiceSolutionId(),
					multipleChoiceSolution.getSolution(),
					multipleChoiceSolution.isCorrect()
				)
			);
		}

		return ControllerResult.of(new GetAllMultipleChoiceSolutionsResponseDTO(getAllMultipleChoiceSolutionsResponseDTOs));
	}

	@Override
	public void deleteAll(SolutionModel solution) {
		this.multipleChoiceSolutionRepository.deleteAllMultipleChoiceSolutionsBySolutionId(solution);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.multipleChoiceSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
