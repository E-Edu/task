package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskTypeRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeController {

	@Autowired private TaskTypeRepository taskTypeRepository;

	public ControllerResult<TaskTypeModel> createTaskType(String displayName) {
		return ControllerResult.of(this.taskTypeRepository.save(new TaskTypeModel(-1, displayName)));
	}

	public ControllerResult<TaskTypeModel> getTaskTypeByDisplayName(String displayName) {
		return ControllerResult.of(this.taskTypeRepository.getTaskTypeByDisplayNameIgnoreCase(displayName));
	}

	public ControllerResult<List<TaskTypeModel>> getAllTaskTypes() {
		return ControllerResult.of(this.taskTypeRepository.findAll());
	}

}
