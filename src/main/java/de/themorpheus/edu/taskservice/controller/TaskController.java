package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

	@Autowired private TaskRepository taskRepository;

	@Autowired private LectureController lectureController;
	@Autowired private TaskTypeController taskTypeController;
	@Autowired private DifficultyController difficultyController;

	public TaskModel createTask(
		String task, UUID authorId,
		int necessaryPoints,
		String lectureDisplayName,
		String taskTypeDisplayName,
		String difficultyDisplayName
	) {
		LectureModel lectureModel = this.lectureController.getLectureByDisplayName(lectureDisplayName);
		DifficultyModel difficultyModel = this.difficultyController.getDifficultyByDisplayName(difficultyDisplayName);
		TaskTypeModel taskTypeModel = this.taskTypeController.getTaskTypeByDisplayName(taskTypeDisplayName);

		if (Validation.validateNull(lectureModel, difficultyModel, taskTypeModel)) return null;

		TaskModel taskModel = new TaskModel(
			-1,
			authorId,
			task,
			necessaryPoints,
			false,
			lectureModel,
			taskTypeModel,
			difficultyModel
		);

		return this.taskRepository.save(taskModel);
	}

	public Object deleteTask(int taskId) {
		this.taskRepository.deleteById(taskId);
		//TODO: delete solution
		return null;
	}

	public List<TaskModel> getAllTasks() {
		return this.taskRepository.findAll();
	}

	public List<TaskModel> getTasksFromLecture(String lectureDisplayName) {
		LectureModel lectureModel = this.lectureController.getLectureByDisplayName(lectureDisplayName);
		return this.taskRepository.getAllTasksByLectureId(lectureModel);
	}

	public TaskModel verifyTask(String task) {
		return this.verifyTask(this.taskRepository.getTaskByTaskIgnoreCase(task));
	}

	public TaskModel verifyTask(int taskId) {
		return this.verifyTask(this.taskRepository.getTaskByTaskId(taskId));
	}

	public TaskModel verifyTask(TaskModel task) {
		if (task == null) return null;

		task.setVerified(true); //TODO: Single property update
		return this.taskRepository.save(task);
	}

}
