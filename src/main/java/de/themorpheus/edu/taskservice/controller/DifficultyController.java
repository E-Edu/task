package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.repository.DifficultyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DifficultyController {

	@Autowired private DifficultyRepository difficultyRepository;

	public DifficultyModel createDifficulty(String displayName) {
		return this.difficultyRepository.save(new DifficultyModel(-1, displayName));
	}

	public DifficultyModel getDifficultyByDisplayName(String displayName) {
		return this.difficultyRepository.getDifficultyByDisplayNameIgnoreCase(displayName);
	}

	public List<DifficultyModel> getAllDifficulties() {
		return this.difficultyRepository.findAll();
	}

}
