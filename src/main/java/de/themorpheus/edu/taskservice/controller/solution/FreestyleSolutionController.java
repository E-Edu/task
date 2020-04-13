package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.FreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionFreestyleRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetFreestyleSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.themorpheus.edu.taskservice.util.Constants.Solution.Freestyle.NAME_KEY;

@Component
public class FreestyleSolutionController implements Solution {

	@Autowired
	private SolutionFreestyleRepository solutionFreestyleRepository;

	@Autowired
	private SolutionController solutionController;

	public ControllerResult<FreestyleSolutionModel> createSolutionFreestyle(CreateFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.solutionFreestyleRepository.existsById(dto.getTaskId()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.solutionFreestyleRepository.save(
				new FreestyleSolutionModel(-1, solutionResult.getResult(), dto.getSolution())
				)
		);
	}

	public ControllerResult<FreestyleSolutionModel> checkSolutionFreestyle(CheckFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<FreestyleSolutionModel> optionalFreestyleSolution = this.solutionFreestyleRepository.findById(solutionResult.getResult().getSolutionId());
		if (!optionalFreestyleSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (!optionalFreestyleSolution.get().getSolution().equalsIgnoreCase(dto.getSolution()))
			return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);

		return ControllerResult.empty();
	}

	public ControllerResult<FreestyleSolutionModel> updateSolutionFreestyle(UpdateFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<FreestyleSolutionModel> optionalFreestyleSolution = this.solutionFreestyleRepository
				.findById(solutionResult.getResult().getSolutionId());
		if (!optionalFreestyleSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		FreestyleSolutionModel freestyleSolution = optionalFreestyleSolution.get();
		freestyleSolution.setSolution(dto.getSolution());
		return ControllerResult.of(this.solutionFreestyleRepository.save(freestyleSolution));
	}

	public ControllerResult<FreestyleSolutionModel> deleteSolutionFreestyle(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.solutionFreestyleRepository.existsById(solutionResult.getResult().getSolutionId()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.solutionFreestyleRepository.deleteById(solutionResult.getResult().getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<GetFreestyleSolutionResponseDTO> getSolutionFreestyle(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<FreestyleSolutionModel> optionalFreestyleSolution = this.solutionFreestyleRepository.findById(
				solutionResult.getResult().getSolutionId());
		if (!optionalFreestyleSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		GetFreestyleSolutionResponseDTO dto = new GetFreestyleSolutionResponseDTO(optionalFreestyleSolution.get().getSolution());

		return ControllerResult.of(dto);
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteSolutionFreestyle(taskId);
	}

}
