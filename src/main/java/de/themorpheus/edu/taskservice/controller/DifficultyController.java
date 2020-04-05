package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.repository.DifficultyRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DifficultyController {

	private static final String NAME_KEY = "difficulty";

	@Autowired private DifficultyRepository difficultyRepository;

	public ControllerResult<DifficultyModel> createDifficulty(String nameKey) {
		return ControllerResult.of(this.difficultyRepository.save(new DifficultyModel(-1, nameKey)));
	}

	public ControllerResult<DifficultyModel> getDifficultyByNameKey(String nameKey) {
		return ControllerResult.of(this.difficultyRepository.getDifficultyByNameKeyIgnoreCase(nameKey));
	}

	public ControllerResult<DifficultyModel> deleteDifficulty(String nameKey) {
		if (!this.difficultyRepository.existsByNameKey(nameKey)) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.difficultyRepository.deleteDifficultyByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<List<DifficultyModel>> getAllDifficulties() {
		return ControllerResult.of(this.difficultyRepository.findAll());
	}

}
