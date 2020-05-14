package com.gewia.taskservice.controller;

import com.gewia.taskservice.database.model.DifficultyModel;
import com.gewia.taskservice.database.repository.DifficultyRepository;
import com.gewia.taskservice.endpoint.dto.request.CreateDifficultyRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.GetAllDifficultiesResponseDTO;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import com.gewia.taskservice.util.Validation;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.Difficulty.NAME_KEY;

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

	public ControllerResult<DifficultyModel> getDifficultyByIdOrNameKey(int id, String nameKey) {
		if (Validation.greaterZero(id)) return this.getDifficulty(id);
		if (Validation.validateNotNullOrEmpty(nameKey)) return this.getDifficultyByNameKey(nameKey);

		return ControllerResult.of(Error.MISSING_PARAM, NAME_KEY);
	}

}
