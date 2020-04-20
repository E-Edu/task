package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.repository.DifficultyRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateDifficultyRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllDifficultiesResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Difficulty.NAME_KEY;

@Component
public class DifficultyController {

	@Autowired private DifficultyRepository difficultyRepository;

	public ControllerResult<DifficultyModel> createDifficulty(CreateDifficultyRequestDTO dto) {
		if (this.difficultyRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.difficultyRepository.save(new DifficultyModel(-1, dto.getNameKey())));
	}

	public ControllerResult<DifficultyModel> getDifficultyByNameKey(String nameKey) {
		DifficultyModel difficulty = this.difficultyRepository.getDifficultyByNameKeyIgnoreCase(nameKey);
		if (difficulty == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(difficulty);
	}

	public ControllerResult<DifficultyModel> deleteDifficulty(String nameKey) {
		if (!this.difficultyRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.difficultyRepository.deleteDifficultyByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<GetAllDifficultiesResponseDTO> getAllDifficulties() {
		List<DifficultyModel> difficulties = this.difficultyRepository.findAll();
		if (difficulties.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllDifficultiesResponseDTO(difficulties));
	}

}
