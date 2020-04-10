package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.FreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionFreestyleRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CheckFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CreateFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.UpdateFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.ret.GetFreestyleSolutionDTO;
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

	public ControllerResult<FreestyleSolutionModel> createSolutionFreestyle(CreateFreestyleSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		if (this.solutionFreestyleRepository.existsById(dto.getTaskId()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.solutionFreestyleRepository.save(new FreestyleSolutionModel(-1, dto.getSolution())));
	}

	public ControllerResult<FreestyleSolutionModel> checkSolutionFreestyle(CheckFreestyleSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		Optional<FreestyleSolutionModel> freestyleSolutionModel = this.solutionFreestyleRepository.findById(
				optionalSolution.getResult().getSolutionId());
		if (!freestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (freestyleSolutionModel.get().getSolution().equalsIgnoreCase(dto.getSolution()))
			return ControllerResult.empty();
		else return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);
	}

	public ControllerResult<FreestyleSolutionModel> updateSolutionFreestyle(UpdateFreestyleSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		if (!this.solutionFreestyleRepository.existsById(dto.getTaskId()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		Optional<FreestyleSolutionModel> optionalFreestyleSolutionModel = this.solutionFreestyleRepository.findById(optionalSolution.getResult().getSolutionId());
		if (!optionalFreestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		FreestyleSolutionModel freestyleSolutionModel = optionalFreestyleSolutionModel.get();
		freestyleSolutionModel.setSolution(dto.getSolution());
		return ControllerResult.of(this.solutionFreestyleRepository.save(freestyleSolutionModel));
	}

	public ControllerResult<FreestyleSolutionModel> deleteSolutionFreestyle(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		if (!this.solutionFreestyleRepository.existsById(optionalSolution.getResult().getSolutionId()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.solutionFreestyleRepository.deleteById(optionalSolution.getResult().getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<GetFreestyleSolutionDTO> getSolutionFreestyle(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		Optional<FreestyleSolutionModel> freestyleSolutionModel = this.solutionFreestyleRepository.findById(
				optionalSolution.getResult().getSolutionId());
		if (!freestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		GetFreestyleSolutionDTO dto = new GetFreestyleSolutionDTO(
				freestyleSolutionModel.get().getSolution().toString()
		);

		return ControllerResult.of(dto);
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteSolutionFreestyle(taskId);
	}

}
