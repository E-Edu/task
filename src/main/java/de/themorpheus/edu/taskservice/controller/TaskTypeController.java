package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskTypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeController {

	@Autowired private TaskTypeRepository taskTypeRepository;

	public TaskTypeModel createTaskType(String displayName) {
		return this.taskTypeRepository.save(new TaskTypeModel(-1, displayName));
	}

	public TaskTypeModel getTaskTypeByDisplayName(String displayName) {
		return this.taskTypeRepository.getTaskTypeByDisplayNameIgnoreCase(displayName);
	}

	public List<TaskTypeModel> getAllTaskTypes() {
		return this.taskTypeRepository.findAll();
	}

}
