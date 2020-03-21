package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	public void createTask(String task) {
		this.taskRepository.save(new TaskModel(task));
	}

}
