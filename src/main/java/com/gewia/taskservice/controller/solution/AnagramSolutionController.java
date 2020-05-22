package com.gewia.taskservice.controller.solution;

import com.gewia.taskservice.database.model.solution.SolutionModel;
import com.gewia.taskservice.database.model.solution.AnagramSolutionModel;
import com.gewia.taskservice.database.repository.solution.AnagramSolutionRepository;
import com.gewia.taskservice.endpoint.dto.request.solution.CheckAnagramSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.CreateAnagramSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.UpdateAnagramSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.CheckAnagramSolutionResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.CreateAnagramSolutionResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.GetAnagramSolutionResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.solution.UpdateAnagramSolutionResponseDTO;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.Solution.Anagram.NAME_KEY;

@Component
public class AnagramSolutionController implements Solution {

	private static final Random RANDOM = new Random();

	@Autowired private AnagramSolutionRepository anagramSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<CreateAnagramSolutionResponseDTO> createAnagramSolution(CreateAnagramSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.anagramSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		AnagramSolutionModel anagramSolution = this.anagramSolutionRepository
				.save(new AnagramSolutionModel(-1, solutionResult.getResult(), dto.getSolution()));

		return ControllerResult.of(new CreateAnagramSolutionResponseDTO(
				anagramSolution.getAnagramSolutionId(),
				anagramSolution.getSolution()
			)
		);
	}

	public ControllerResult<CheckAnagramSolutionResponseDTO> checkAnagramSolution(CheckAnagramSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<AnagramSolutionModel> optionalAnagramSolution = this.anagramSolutionRepository
				.findBySolutionId(solutionResult.getResult());

		return optionalAnagramSolution.map(anagramSolutionModel -> ControllerResult.of(
				new CheckAnagramSolutionResponseDTO(dto.getSolution()
						.equalsIgnoreCase(anagramSolutionModel.getSolution()), anagramSolutionModel.getSolution())))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<UpdateAnagramSolutionResponseDTO> updateAnagramSolution(UpdateAnagramSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<AnagramSolutionModel> optionalAnagramSolution = this.anagramSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalAnagramSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		AnagramSolutionModel anagramSolution = optionalAnagramSolution.get();
		anagramSolution.setSolution(dto.getSolution());
		this.anagramSolutionRepository.save(anagramSolution);

		return ControllerResult.of(new UpdateAnagramSolutionResponseDTO(
				anagramSolution.getAnagramSolutionId(),
				anagramSolution.getSolution()
			)
		);
	}

	@Transactional
	public ControllerResult<AnagramSolutionModel> deleteAnagramSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.anagramSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.anagramSolutionRepository.deleteBySolutionId(solutionResult.getResult());
		this.deleteSolutionIdIfDatabaseIsEmpty(solutionResult.getResult());

		return ControllerResult.empty();
	}

	public ControllerResult<GetAnagramSolutionResponseDTO> getAnagramSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<AnagramSolutionModel> optionalAnagramSolution = this.anagramSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalAnagramSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<Character> characters = new ArrayList<>(optionalAnagramSolution.get().getSolution().length());
		for (char c : optionalAnagramSolution.get().getSolution().toCharArray()) characters.add(c);

		StringBuilder output = new StringBuilder(optionalAnagramSolution.get().getSolution().length());
		while (characters.size() != 0)
			output.append(characters.remove(RANDOM.nextInt(characters.size())));

		return ControllerResult.of(new GetAnagramSolutionResponseDTO(output.toString()));
	}

	@Override
	public void deleteAll(SolutionModel solution) {
		this.anagramSolutionRepository.deleteBySolutionId(solution);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.anagramSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
