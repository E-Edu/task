package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskTypeRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskTypeRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllTaskTypesResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.TaskType.NAME_KEY;

@Component
public class TaskTypeController {

	@Autowired private TaskTypeRepository taskTypeRepository;

	public ControllerResult<TaskTypeModel> createTaskType(CreateTaskTypeRequestDTO dto) {
		if (this.taskTypeRepository.existsByNameKeyIgnoreCase(dto.getNameKey()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.taskTypeRepository.save(new TaskTypeModel(-1, dto.getNameKey())));
	}

	public ControllerResult<TaskTypeModel> getTaskType(String nameKey) {
		TaskTypeModel taskType = this.taskTypeRepository.getTaskTypeByNameKeyIgnoreCase(nameKey);
		if (taskType == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(taskType);
	}

	public ControllerResult<TaskTypeModel> deleteTaskType(String nameKey) {
		if (!this.taskTypeRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.taskTypeRepository.deleteTaskTypeByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<GetAllTaskTypesResponseDTO> getAllTaskTypes() {
		List<TaskTypeModel> taskTypeModels = this.taskTypeRepository.findAll();
		if (taskTypeModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTaskTypesResponseDTO(taskTypeModels));
	}

}
