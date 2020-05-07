package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskTypeRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskTypeRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllTaskTypesResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.TaskType.NAME_KEY;

@Component
public class TaskTypeController {

	@Autowired private TaskTypeRepository taskTypeRepository;
	@Autowired private TaskController taskController;

	public ControllerResult<TaskTypeModel> createTaskType(CreateTaskTypeRequestDTO dto) {
		if (this.taskTypeRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.taskTypeRepository.save(new TaskTypeModel(-1, dto.getNameKey())));
	}

	public ControllerResult<TaskTypeModel> getTaskType(int taskTypeId) {
		Optional<TaskTypeModel> optionalTaskType = this.taskTypeRepository.findById(taskTypeId);

		return optionalTaskType.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<TaskTypeModel> getTaskTypeByNameKey(String nameKey) {
		Optional<TaskTypeModel> optionalTaskType = this.taskTypeRepository.findByNameKeyIgnoreCase(nameKey);

		return optionalTaskType.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllTaskTypesResponseDTO> getAllTaskTypes() {
		List<TaskTypeModel> taskTypeModels = this.taskTypeRepository.findAll();
		if (taskTypeModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTaskTypesResponseDTO(taskTypeModels));
	}

	@Transactional
	public ControllerResult<TaskTypeModel> deleteTaskTypeByNameKey(String nameKey) {
		Optional<TaskTypeModel> optionalTaskType = this.taskTypeRepository.findByNameKeyIgnoreCase(nameKey);
		if (!optionalTaskType.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		TaskTypeModel taskType = optionalTaskType.get();
		if (this.taskController.existsByTaskTypeId(taskType))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.taskTypeRepository.delete(taskType);

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<TaskTypeModel> deleteTaskType(int taskTypeId) {
		Optional<TaskTypeModel> optionalTaskType = this.taskTypeRepository.findById(taskTypeId);
		if (!optionalTaskType.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		TaskTypeModel taskType = optionalTaskType.get();
		if (this.taskController.existsByTaskTypeId(taskType))
			return ControllerResult.of(Error.FAILED_DEPENDENCY, NAME_KEY);

		this.taskTypeRepository.delete(taskType);

		return ControllerResult.empty();
	}

	public ControllerResult<TaskTypeModel> getTaskTypeByIdOrNameKey(int id, String nameKey) {
		if (Validation.greaterZero(id)) return this.getTaskType(id);
		if (Validation.validateNotNullOrEmpty(nameKey)) return this.getTaskTypeByNameKey(nameKey);

		return ControllerResult.of(Error.MISSING_PARAM, NAME_KEY);
	}

}
