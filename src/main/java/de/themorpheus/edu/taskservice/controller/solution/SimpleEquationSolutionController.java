package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SimpleEquationSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SimpleEquationSolutionResultModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SimpleEquationSolutionRepository;
import de.themorpheus.edu.taskservice.database.repository.solution.SimpleEquationSolutionResultRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckSimpleEquationSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateSimpleEquationSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateSimpleEquationSolutionRequestDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckSimpleEquationSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckSimpleEquationSolutionResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateSimpleEquationSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateSimpleEquationSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateSimpleEquationSolutionResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.UpdateSimpleEquationSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.SimpleEquation.NAME_KEY;

@Component
public class SimpleEquationSolutionController implements Solution {

	@Autowired private SolutionController solutionController;

	@Autowired private SimpleEquationSolutionRepository simpleEquationSolutionRepository;
	@Autowired private SimpleEquationSolutionResultRepository simpleEquationSolutionResultRepository;

	public ControllerResult<CreateSimpleEquationSolutionResponseDTO> createSimpleEquationSolution(CreateSimpleEquationSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		SolutionModel solution = solutionResult.getResult();
		if (this.simpleEquationSolutionRepository.existsBySolutionId(solution)
				|| this.simpleEquationSolutionResultRepository.existsBySolutionId(solution))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		CreateSimpleEquationSolutionResponseDTO responseDTO = new CreateSimpleEquationSolutionResponseDTO();
		List<CreateSimpleEquationSolutionResponseDTOModel> createSimpleEquationSolutionResponseDTOs = new ArrayList<>();
		for (String step : dto.getSteps()) createSimpleEquationSolutionResponseDTOs.add(
				this.simpleEquationSolutionRepository.save(new SimpleEquationSolutionModel(
						-1,
						solution,
						step
						)
				).toCreateResponseDTOModel());

		responseDTO.getSteps().addAll(createSimpleEquationSolutionResponseDTOs);
		responseDTO.setResult(this.simpleEquationSolutionResultRepository.save(
				new SimpleEquationSolutionResultModel(-1, solution, dto.getResult())).getResult());

		return ControllerResult.of(responseDTO);
	}

	public ControllerResult<UpdateSimpleEquationSolutionResponseDTO> updateSimpleEquationSolution(UpdateSimpleEquationSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		SolutionModel solution = solutionResult.getResult();
		if (!this.simpleEquationSolutionRepository.existsBySolutionId(solution)
				&& !this.simpleEquationSolutionResultRepository.existsBySolutionId(solution))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		UpdateSimpleEquationSolutionResponseDTO responseDTO = new UpdateSimpleEquationSolutionResponseDTO();
		List<SimpleEquationSolutionModel> simpleEquationSolutions = new ArrayList<>();
		for (UpdateSimpleEquationSolutionRequestDTOModel updateSimpleEquationSolutionRequestDTO : dto.getSteps())
			simpleEquationSolutions.add(this.simpleEquationSolutionRepository.save(
					updateSimpleEquationSolutionRequestDTO.toModel(solution)));
		for (SimpleEquationSolutionModel simpleEquationSolution : simpleEquationSolutions)
			responseDTO.getSteps().add(simpleEquationSolution.toUpdateResponseDTOModel());

		Optional<SimpleEquationSolutionResultModel> simpleEquationSolutionResult = this.simpleEquationSolutionResultRepository
				.findBySolutionId(solution);
		if (!simpleEquationSolutionResult.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		simpleEquationSolutionResult.get().setResult(dto.getResult());
		responseDTO.setResult(this.simpleEquationSolutionResultRepository.save(simpleEquationSolutionResult.get()).getResult());

		return ControllerResult.of(responseDTO);
	}

	public ControllerResult<CheckSimpleEquationSolutionResponseDTO> checkSimpleEquationSolution(CheckSimpleEquationSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		SolutionModel solution = solutionResult.getResult();
		if (!this.simpleEquationSolutionRepository.existsBySolutionId(solution)
				&& !this.simpleEquationSolutionResultRepository.existsBySolutionId(solution))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		Optional<SimpleEquationSolutionResultModel> optionalSimpleEquationSolutionResult = this.simpleEquationSolutionResultRepository
				.findBySolutionId(solution);
		if (!optionalSimpleEquationSolutionResult.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		SimpleEquationSolutionResultModel simpleEquationSolutionResult = optionalSimpleEquationSolutionResult.get();
		CheckSimpleEquationSolutionResponseDTO responseDTO = new CheckSimpleEquationSolutionResponseDTO();
		responseDTO.setCorrect(simpleEquationSolutionResult.getResult().equalsIgnoreCase(dto.getResult().trim()));
		responseDTO.setResult(simpleEquationSolutionResult.getResult());

		List<CheckSimpleEquationSolutionResponseDTOModel> checkSimpleEquationSolutionResponseDTOs = new ArrayList<>();
		List<SimpleEquationSolutionModel> simpleEquationSolutions = this.simpleEquationSolutionRepository
				.findAllBySolutionId(solution);
		if (simpleEquationSolutions.isEmpty()) return ControllerResult.of(responseDTO);

		for (SimpleEquationSolutionModel simpleEquationSolution : simpleEquationSolutions)
			checkSimpleEquationSolutionResponseDTOs.add(simpleEquationSolution.toCheckResponseDTOModel());
		responseDTO.setSteps(checkSimpleEquationSolutionResponseDTOs);

		return ControllerResult.of(responseDTO);
	}

	public ControllerResult<SimpleEquationSolutionModel> deleteSimpleEquationSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		SolutionModel solution = solutionResult.getResult();
		if (!this.simpleEquationSolutionRepository.existsBySolutionId(solution)
				&& !this.simpleEquationSolutionResultRepository.existsBySolutionId(solution))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.simpleEquationSolutionRepository.deleteAllBySolutionId(solution);
		this.simpleEquationSolutionResultRepository.deleteAllBySolutionId(solution);
		this.deleteSolutionIdIfDatabaseIsEmpty(solution);

		return ControllerResult.empty();
	}

	@Override
	public void deleteAll(SolutionModel solution) {
		this.simpleEquationSolutionRepository.deleteAllBySolutionId(solution);
		this.simpleEquationSolutionResultRepository.deleteAllBySolutionId(solution);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.simpleEquationSolutionResultRepository.existsBySolutionId(solutionId)
				&& !this.simpleEquationSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}
}
