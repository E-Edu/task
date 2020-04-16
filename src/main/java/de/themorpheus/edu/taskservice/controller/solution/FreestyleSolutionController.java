package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.FreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.UserFreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.FreestyleSolutionRepository;
import de.themorpheus.edu.taskservice.database.repository.solution.UserFreestyleSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateFreestyleSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckFreestyleSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateFreestyleSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.UpdateFreestyleSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.Freestyle.NAME_KEY;

@Component
public class FreestyleSolutionController implements Solution {

	@Autowired private FreestyleSolutionRepository freestyleSolutionRepository;

	@Autowired private UserFreestyleSolutionRepository userFreestyleSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<CreateFreestyleSolutionResponseDTO> createFreestyleSolution(CreateFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.freestyleSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		FreestyleSolutionModel freestyleSolution = this.freestyleSolutionRepository.save(
				new FreestyleSolutionModel(-1, solutionResult.getResult(), dto.getSolution()));

		return ControllerResult.of(new CreateFreestyleSolutionResponseDTO(
				freestyleSolution.getFreestyleSolutionId(),
				freestyleSolution.getSolution()
				)
		);
	}

	public ControllerResult<CheckFreestyleSolutionResponseDTO> checkFreestyleSolution(CheckFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<FreestyleSolutionModel> optionalFreestyleSolution = this.freestyleSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalFreestyleSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		userFreestyleSolutionRepository.save(new UserFreestyleSolutionModel(
				-1,
				optionalFreestyleSolution.get(),
				dto.getSolution(),
				UUID.randomUUID()
			)
		);

		//TODO: PubSubService publish new Freestyle for teacher

		return ControllerResult.of(new CheckFreestyleSolutionResponseDTO(optionalFreestyleSolution.get().getSolution()));
	}

	public ControllerResult<UpdateFreestyleSolutionResponseDTO> updateFreestyleSolution(UpdateFreestyleSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<FreestyleSolutionModel> optionalFreestyleSolution = this.freestyleSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalFreestyleSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		FreestyleSolutionModel freestyleSolution = optionalFreestyleSolution.get();
		freestyleSolution.setSolution(dto.getSolution());
		freestyleSolution = this.freestyleSolutionRepository.save(freestyleSolution);

		return ControllerResult.of(new UpdateFreestyleSolutionResponseDTO(
				freestyleSolution.getFreestyleSolutionId(),
				freestyleSolution.getSolution()
				)
		);
	}

	@Transactional
	public ControllerResult<FreestyleSolutionModel> deleteFreestyleSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.freestyleSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.freestyleSolutionRepository.deleteBySolutionId(solutionResult.getResult());
		this.deleteSolutionIdIfDatabaseIsEmpty(solutionResult.getResult());

		return ControllerResult.empty();
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteFreestyleSolution(taskId);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.freestyleSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
