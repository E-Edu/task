package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.repository.DifficultyRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateDifficultyRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllDifficultiesResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Difficulty.NAME_KEY;

@Component
public class DifficultyController {

	@Autowired private DifficultyRepository difficultyRepository;

	@Autowired private TaskController taskController;

	public ControllerResult<DifficultyModel> createDifficulty(CreateDifficultyRequestDTO dto) {
		if (this.difficultyRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.difficultyRepository.save(new DifficultyModel(-1, dto.getNameKey())));
	}

	public ControllerResult<DifficultyModel> getDifficulty(int difficultyId) {
		Optional<DifficultyModel> optionalDifficulty = this.difficultyRepository.findById(difficultyId);

		return optionalDifficulty.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<DifficultyModel> getDifficultyByNameKey(String nameKey) {
		Optional<DifficultyModel> optionalDifficulty = this.difficultyRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalDifficulty.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllDifficultiesResponseDTO> getAllDifficulties() {
		List<DifficultyModel> difficulties = this.difficultyRepository.findAll();
		if (difficulties.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllDifficultiesResponseDTO(difficulties));
	}

	@Transactional
	public ControllerResult<DifficultyModel> deleteDifficultyByNameKey(String nameKey) {
		Optional<DifficultyModel> optionalDifficulty = this.difficultyRepository.findByNameKeyIgnoreCase(nameKey);
		if (!optionalDifficulty.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		DifficultyModel difficulty = optionalDifficulty.get();
		if (this.taskController.existsByDifficultyId(difficulty))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.difficultyRepository.delete(difficulty);

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<DifficultyModel> deleteDifficulty(int difficultyId) {
		Optional<DifficultyModel> optionalDifficulty = this.difficultyRepository.findById(difficultyId);
		if (!optionalDifficulty.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		DifficultyModel difficulty = optionalDifficulty.get();
		if (this.taskController.existsByDifficultyId(difficulty))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.difficultyRepository.delete(difficulty);

		return ControllerResult.empty();
	}

}
