package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionWordsaladRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateWordsaladSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetWordsaladSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.Wordsalad.NAME_KEY;

@Component
public class WordsaladSolutionController implements Solution {

	@Autowired private SolutionWordsaladRepository solutionWordsaladRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<WordsaladSolutionModel> createSolutionWordsalad(CreateWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.solutionWordsaladRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.solutionWordsaladRepository.save(
				new WordsaladSolutionModel(-1, solutionResult.getResult(), dto.getSolution())
			)
		);
	}

	public ControllerResult<WordsaladSolutionModel> checkSolutionWordsalad(CheckWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.solutionWordsaladRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalWordsaladSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (!optionalWordsaladSolution.get().getSolution().equalsIgnoreCase(dto.getSolution()))
			return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);

		return ControllerResult.empty();
	}

	public ControllerResult<WordsaladSolutionModel> updateSolutionWordsalad(UpdateWordsaladSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.solutionWordsaladRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalWordsaladSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		WordsaladSolutionModel wordsaladSolution = optionalWordsaladSolution.get();
		wordsaladSolution.setSolution(dto.getSolution());
		return ControllerResult.of(this.solutionWordsaladRepository.save(wordsaladSolution));
	}

	public ControllerResult<WordsaladSolutionModel> deleteSolutionWordsalad(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.solutionWordsaladRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.solutionWordsaladRepository.deleteBySolutionId(solutionResult.getResult());
		return ControllerResult.empty();
	}

	public ControllerResult<GetWordsaladSolutionResponseDTO> getSolutionWordsalad(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<WordsaladSolutionModel> optionalWordsaladSolution = this.solutionWordsaladRepository
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
		this.deleteSolutionWordsalad(taskId);
	}

}
