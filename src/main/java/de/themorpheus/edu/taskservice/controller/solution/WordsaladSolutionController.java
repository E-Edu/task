package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionWordsaladRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CheckWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CreateWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.GetWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.UpdateWordsaladSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordsaladSolutionController {

	@Autowired private SolutionWordsaladRepository solutionWordsaladRepository;

	public ControllerResult<WordsaladSolutionModel> create(CreateWordsaladSolutionDTO createWordsaladSolutionDTO) {
		if (this.solutionWordsaladRepository.findById(createWordsaladSolutionDTO.getTaskId()).isPresent()) return ControllerResult.of(Error.ALREADY_EXISTS, "wordsaladSolution");

		WordsaladSolutionModel wordsaladSolutionModel = new WordsaladSolutionModel();
		wordsaladSolutionModel.setSolution(createWordsaladSolutionDTO.getSolution());
		wordsaladSolutionModel.setTaskId(createWordsaladSolutionDTO.getTaskId());
		return ControllerResult.of(this.solutionWordsaladRepository.save(wordsaladSolutionModel));
	}

	public ControllerResult<WordsaladSolutionModel> check(CheckWordsaladSolutionDTO checkWordsaladSolutionDTO) {
		Optional<WordsaladSolutionModel> solutionWordsaladModels = this.solutionWordsaladRepository.findById(checkWordsaladSolutionDTO.getTaskId());
		if (!solutionWordsaladModels.isPresent()) return ControllerResult.of(Error.NOT_FOUND, "wordsaladSolution");

		WordsaladSolutionModel wordsaladSolutionModel = solutionWordsaladModels.get();
		if (wordsaladSolutionModel.getSolution().equals(checkWordsaladSolutionDTO.getSolution())) {
			return ControllerResult.empty();
		} else {
			return ControllerResult.of(Error.WRONG_ANSWER, "wordsaladSolution");
		}
	}

	public ControllerResult<WordsaladSolutionModel> update(UpdateWordsaladSolutionDTO updateWordsaladSolutionDTO) {
		WordsaladSolutionModel wordsaladSolutionModel = new WordsaladSolutionModel();
		wordsaladSolutionModel.setSolution(updateWordsaladSolutionDTO.getSolution());
		wordsaladSolutionModel.setTaskId(updateWordsaladSolutionDTO.getTaskId());
		return ControllerResult.of(wordsaladSolutionModel);
	}

	public ControllerResult<WordsaladSolutionModel> delete(int taskId) {
		if (!this.solutionWordsaladRepository.findById(taskId).isPresent()) return ControllerResult.of(Error.NOT_FOUND, "wordsaladSolution");

		this.solutionWordsaladRepository.deleteById(taskId);
		return ControllerResult.empty();
	}

	public ControllerResult<GetWordsaladSolutionDTO> get(int taskId) {
		Optional<WordsaladSolutionModel> solutionWordsaladModels = this.solutionWordsaladRepository.findById(taskId);
		if (!solutionWordsaladModels.isPresent()) return ControllerResult.of(Error.NOT_FOUND, "wordsaladSolution");

		WordsaladSolutionModel wordsaladSolutionModel = solutionWordsaladModels.get();
		List<Character> characters = new ArrayList<>();
		for (char c : wordsaladSolutionModel.getSolution().toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(wordsaladSolutionModel.getSolution().length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}

		return ControllerResult.of(new GetWordsaladSolutionDTO(output.toString()));
	}
}
