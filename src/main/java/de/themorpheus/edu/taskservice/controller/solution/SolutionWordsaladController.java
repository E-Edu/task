package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionWordsaladModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionWordsaladRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CheckSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.CreateSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.GetSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.wordsalad.UpdateSolutionWordsaladDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionWordsaladController {

	@Autowired private SolutionWordsaladRepository solutionWordsaladRepository;

	public ControllerResult<SolutionWordsaladModel> create(CreateSolutionWordsaladDTO createSolutionWordsaladDTO) {
		SolutionWordsaladModel solutionWordsaladModel = new SolutionWordsaladModel();
		solutionWordsaladModel.setSolution(createSolutionWordsaladDTO.getSolution());
		solutionWordsaladModel.setTaskId(createSolutionWordsaladDTO.getTaskId());
		return ControllerResult.empty();
	}

	public ControllerResult<SolutionWordsaladModel> check(CheckSolutionWordsaladDTO checkSolutionWordsaladDTO) {
		Optional<SolutionWordsaladModel> solutionWordsaladModels = this.solutionWordsaladRepository.findById(checkSolutionWordsaladDTO.getTaskId());
		if (!solutionWordsaladModels.isPresent()) return ControllerResult.of(Error.NOT_FOUND);

		SolutionWordsaladModel solutionWordsaladModel = solutionWordsaladModels.get();
		if (solutionWordsaladModel.getSolution().equals(checkSolutionWordsaladDTO.getSolution())) {
			return ControllerResult.empty();
		} else {
			return ControllerResult.of(Error.WRONG_ANSWER);
		}
	}

	public ControllerResult<SolutionWordsaladModel> update(UpdateSolutionWordsaladDTO updateSolutionWordsaladDTO) {
		SolutionWordsaladModel solutionWordsaladModel = new SolutionWordsaladModel();
		solutionWordsaladModel.setSolution(updateSolutionWordsaladDTO.getSolution());
		solutionWordsaladModel.setTaskId(updateSolutionWordsaladDTO.getTaskId());
		return ControllerResult.empty();
	}

	public ControllerResult<SolutionWordsaladModel> delete(int taskId) {
		this.solutionWordsaladRepository.deleteById(taskId);
		return ControllerResult.empty();
	}

	public ControllerResult<GetSolutionWordsaladDTO> get(int taskId) {
		Optional<SolutionWordsaladModel> solutionWordsaladModels = this.solutionWordsaladRepository.findById(taskId);
		if (!solutionWordsaladModels.isPresent()) return ControllerResult.of(Error.NOT_FOUND);

		SolutionWordsaladModel solutionWordsaladModel = solutionWordsaladModels.get();
		List<Character> characters = new ArrayList<>();
		for (char c : solutionWordsaladModel.getSolution().toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(solutionWordsaladModel.getSolution().length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}

		return ControllerResult.of(new GetSolutionWordsaladDTO(output.toString()));
	}
}
