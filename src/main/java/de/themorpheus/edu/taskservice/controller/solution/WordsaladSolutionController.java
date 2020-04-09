package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionWordsaladRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.CheckWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.CreateWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.get.UpdateWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.ret.GetWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordsaladSolutionController implements Solution {

	private static final String NAME_KEY = "wordsalad_solution";

	@Autowired private SolutionWordsaladRepository solutionWordsaladRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<WordsaladSolutionModel> createSolutionWordsalad(CreateWordsaladSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		if (this.solutionWordsaladRepository.existsById(dto.getTaskId())) return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.solutionWordsaladRepository.save(new WordsaladSolutionModel(-1, dto.getSolution())));
	}

	public ControllerResult<WordsaladSolutionModel> checkSolutionWordsalad(CheckWordsaladSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		Optional<WordsaladSolutionModel> wordsaladSolutionModel = this.solutionWordsaladRepository.findById(
				optionalSolution.getResult().getSolutionId());
		if (!wordsaladSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (wordsaladSolutionModel.get().getSolution().equalsIgnoreCase(dto.getSolution())) return ControllerResult.empty();
		else return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);
	}

	public ControllerResult<WordsaladSolutionModel> updateSolutionWordsalad(UpdateWordsaladSolutionDTO dto) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(dto.getTaskId(), NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		if (!this.solutionWordsaladRepository.existsById(dto.getTaskId())) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		Optional<WordsaladSolutionModel> optionalWordsaladSolutionModel = this.solutionWordsaladRepository.findById(optionalSolution.getResult().getSolutionId());
		if (!optionalWordsaladSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		WordsaladSolutionModel wordsaladSolutionModel = optionalWordsaladSolutionModel.get();
		wordsaladSolutionModel.setSolution(dto.getSolution());
		return ControllerResult.of(this.solutionWordsaladRepository.save(wordsaladSolutionModel));
	}

	public ControllerResult<WordsaladSolutionModel> deleteSolutionWordsalad(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		this.solutionWordsaladRepository.deleteById(optionalSolution.getResult().getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<GetWordsaladSolutionDTO> getSolutionWordsalad(int taskId) {
		ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(taskId, NAME_KEY);
		if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

		Optional<WordsaladSolutionModel> wordsaladSolutionModel = this.solutionWordsaladRepository.findById(
				optionalSolution.getResult().getSolutionId());
		if (!wordsaladSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<Character> characters = new ArrayList<>(wordsaladSolutionModel.get().getSolution().length());
		for (char c : wordsaladSolutionModel.get().getSolution().toCharArray()) characters.add(c);

		StringBuilder output = new StringBuilder(wordsaladSolutionModel.get().getSolution().length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}

		return ControllerResult.of(new GetWordsaladSolutionDTO(output.toString()));
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteSolutionWordsalad(taskId);
	}

}
