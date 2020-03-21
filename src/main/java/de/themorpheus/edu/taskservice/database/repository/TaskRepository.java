package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskModel, Integer> {

	TaskModel getTaskByTaskId(int taskId);

	int createTask(TaskModel taskModel);

}
