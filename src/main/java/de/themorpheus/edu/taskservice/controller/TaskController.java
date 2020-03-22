package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

	private static final Random RANDOM = new Random();

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

	public TaskModel getNextTask(List<Integer> finishedTaskIds) {
		TaskModel taskModel = this.taskRepository.getTaskByTaskId(finishedTaskIds.get(0));
		if (taskModel == null) return null;

		LectureModel lectureModel = taskModel.getLectureId();
		if (lectureModel == null) return null;

		List<TaskModel> taskModels = this.taskRepository.getAllTasksByLectureId(lectureModel);
		taskModels.removeIf(tm -> finishedTaskIds.contains(tm.getTaskId())); //TODO: Use SQL query
		if (taskModels.isEmpty()) return null; //TODO: return all tasks done (9xx)

		return taskModels.get(RANDOM.nextInt(taskModels.size()));
	}

  public void deleteTask(int taskId) {
		this.taskRepository.deleteById(taskId);
		//TODO delete solution
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
