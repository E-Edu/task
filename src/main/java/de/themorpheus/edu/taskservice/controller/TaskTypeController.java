package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskTypeRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeController {

	private static final String NAME_KEY = "task_type";

	@Autowired private TaskTypeRepository taskTypeRepository;

	public ControllerResult<TaskTypeModel> createTaskType(String nameKey) {
		return ControllerResult.of(this.taskTypeRepository.save(new TaskTypeModel(-1, nameKey)));
	}

	public ControllerResult<TaskTypeModel> getTaskType(String nameKey) {
		return ControllerResult.of(this.taskTypeRepository.getTaskTypeByNameKeyIgnoreCase(nameKey));
	}

	public ControllerResult<Object> deleteTaskType(String nameKey) {
		if (!this.taskTypeRepository.existsByNameKeyIgnoreCase(nameKey))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.taskTypeRepository.deleteTaskTypeByNameKeyIgnoreCase(nameKey);
		return ControllerResult.empty();
	}

	public ControllerResult<List<TaskTypeModel>> getAllTaskTypes() {
		return ControllerResult.of(this.taskTypeRepository.findAll());
	}

}
