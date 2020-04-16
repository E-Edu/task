package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.WordsaladSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckWordsaladSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateWordsaladSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetWordsaladSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.UpdateWordsaladSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.Wordsalad.NAME_KEY;

@Component
public class WordsaladSolutionController implements Solution {

	@Autowired private WordsaladSolutionRepository wordsaladSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<CreateWordsaladSolutionResponseDTO> createWordsaladSolution(CreateWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.wordsaladSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		WordsaladSolutionModel wordsaladSolution = this.wordsaladSolutionRepository
				.save(new WordsaladSolutionModel(-1, solutionResult.getResult(), dto.getSolution()));

		return ControllerResult.of(new CreateWordsaladSolutionResponseDTO(
				wordsaladSolution.getWordsaladSolutionId(),
				wordsaladSolution.getSolution()
				)
		);
	}

	public ControllerResult<CheckWordsaladSolutionResponseDTO> checkWordsaladSolution(CheckWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.wordsaladSolutionRepository
				.findBySolutionId(solutionResult.getResult());

		return optionalWordsaladSolution.map(wordsaladSolutionModel -> ControllerResult.of(
				new CheckWordsaladSolutionResponseDTO(dto.getSolution()
						.equalsIgnoreCase(wordsaladSolutionModel.getSolution()), wordsaladSolutionModel.getSolution())))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<UpdateWordsaladSolutionResponseDTO> updateWordsaladSolution(UpdateWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.wordsaladSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalWordsaladSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		WordsaladSolutionModel wordsaladSolution = optionalWordsaladSolution.get();
		wordsaladSolution.setSolution(dto.getSolution());
		this.wordsaladSolutionRepository.save(wordsaladSolution);

		return ControllerResult.of(new UpdateWordsaladSolutionResponseDTO(
				wordsaladSolution.getWordsaladSolutionId(),
				wordsaladSolution.getSolution()
				)
		);
	}

	@Transactional
	public ControllerResult<WordsaladSolutionModel> deleteWordsaladSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.wordsaladSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.wordsaladSolutionRepository.deleteBySolutionId(solutionResult.getResult());
		this.deleteSolutionIdIfDatabaseIsEmpty(solutionResult.getResult());

		return ControllerResult.empty();
	}

	public ControllerResult<GetWordsaladSolutionResponseDTO> getWordsaladSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.wordsaladSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalWordsaladSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<Character> characters = new ArrayList<>(optionalWordsaladSolution.get().getSolution().length());
		for (char c : optionalWordsaladSolution.get().getSolution().toCharArray()) characters.add(c);

		StringBuilder output = new StringBuilder(optionalWordsaladSolution.get().getSolution().length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}

		return ControllerResult.of(new GetWordsaladSolutionResponseDTO(output.toString()));
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteWordsaladSolution(taskId);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.wordsaladSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
