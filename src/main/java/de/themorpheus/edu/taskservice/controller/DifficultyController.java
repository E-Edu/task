package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.repository.DifficultyRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DifficultyController {

	@Autowired private DifficultyRepository difficultyRepository;

	public ControllerResult<DifficultyModel> createDifficulty(String displayName) {
		return ControllerResult.of(this.difficultyRepository.save(new DifficultyModel(-1, displayName)));
	}

	public ControllerResult<DifficultyModel> getDifficultyByDisplayName(String displayName) {
		return ControllerResult.of(this.difficultyRepository.getDifficultyByDisplayNameIgnoreCase(displayName));
	}

	public ControllerResult<List<DifficultyModel>> getAllDifficulties() {
		return ControllerResult.of(this.difficultyRepository.findAll());
	}

}
