package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.UpdateTaskDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
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

	public ControllerResult<TaskModel> createTask(CreateTaskDTO dto) {
		ControllerResult<LectureModel> lectureModelResult = this.lectureController.getLectureByNameKey(dto.getLectureNameKey());
		ControllerResult<DifficultyModel> difficultyModelResult = this.difficultyController.getDifficultyByNameKey(dto.getDifficultyNameKey());
		ControllerResult<TaskTypeModel> taskTypeModelResult = this.taskTypeController.getTaskTypeByNameKey(dto.getTaskTypeNameKey());

		if (lectureModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "lecture");
		if (difficultyModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "difficulty");
		if (taskTypeModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "taskType");

		TaskModel taskModel = new TaskModel(
			-1,
			dto.getDescription(),
			UUID.randomUUID(), //TODO
			dto.getTask(),
			dto.getNecessaryPoints(),
			false,
			lectureModelResult.getResult(),
			taskTypeModelResult.getResult(),
			difficultyModelResult.getResult()
		);

		return ControllerResult.of(this.taskRepository.save(taskModel));
	}

	public ControllerResult<TaskModel> getNextTask(List<Integer> finishedTaskIds) {
		TaskModel taskModel = this.taskRepository.getTaskByTaskId(finishedTaskIds.get(0));
		if (taskModel == null) return ControllerResult.of(Error.NOT_FOUND, "task");

		LectureModel lectureModel = taskModel.getLectureId();
		if (lectureModel == null) return ControllerResult.of(Error.NOT_FOUND, "lecture");

		List<TaskModel> taskModels = this.taskRepository.getAllTasksByLectureId(lectureModel);
		taskModels.removeIf(tm -> finishedTaskIds.contains(tm.getTaskId())); //TODO: Use SQL query
		if (taskModels.isEmpty()) return ControllerResult.empty(); //TODO: return all tasks done (9xx)

		return ControllerResult.of(taskModels.get(RANDOM.nextInt(taskModels.size())));
	}

	public void deleteTask(int taskId) {
		this.taskRepository.deleteById(taskId);
		//TODO delete solution
	}

	public ControllerResult<List<TaskModel>> getAllTasks() {
		return ControllerResult.of(this.taskRepository.findAll());
	}

	public ControllerResult<List<TaskModel>> getTasksFromLecture(String lectureNameKey) {
		ControllerResult<LectureModel> lectureModelResult = this.lectureController
			.getLectureByNameKey(lectureNameKey);
		if (lectureModelResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, "lecture");
		return ControllerResult.of(this.taskRepository.getAllTasksByLectureId(lectureModelResult.getResult()));
	}

	public ControllerResult<TaskModel> verifyTask(String task) {
		return this.verifyTask(this.taskRepository.getTaskByTaskIgnoreCase(task));
	}

	public ControllerResult<TaskModel> verifyTask(int taskId) {
		return this.verifyTask(this.taskRepository.getTaskByTaskId(taskId));
	}

	public ControllerResult<TaskModel> verifyTask(TaskModel task) {
		if (task == null) return ControllerResult.of(Error.NOT_FOUND, "task");

		task.setVerified(true); //TODO: Single property update
		return ControllerResult.of(this.taskRepository.save(task));
	}

	public ControllerResult<TaskModel> updateTask(int taskId, UpdateTaskDTO dto) {
		TaskModel taskModel = this.taskRepository.getTaskByTaskId(taskId);
		if (taskModel == null) return ControllerResult.of(Error.NOT_FOUND, "task");

		ControllerResult<LectureModel> lectureModelResult = this.lectureController
			.getLectureByNameKey(dto.getLectureNameKey());
		ControllerResult<TaskTypeModel> taskTypeModelResult = this.taskTypeController
			.getTaskTypeByNameKey(dto.getTaskTypeNameKey());
		ControllerResult<DifficultyModel> difficultyModelResult = this.difficultyController
			.getDifficultyByNameKey(dto.getDifficultyNameKey());

		if (lectureModelResult.isResultPresent()) taskModel.setLectureId(lectureModelResult.getResult());
		if (taskTypeModelResult.isResultPresent()) taskModel.setTaskTypeId(taskTypeModelResult.getResult());
		if (difficultyModelResult.isResultPresent()) taskModel.setDifficultyId(difficultyModelResult.getResult());
		if (Validation.validateNotNullOrEmpty(dto.getTask())) taskModel.setTask(dto.getTask());
		if (Validation.greaterZero(dto.getNecessaryPoints())) taskModel.setNecessaryPoints(dto.getNecessaryPoints());

		return ControllerResult.of(this.taskRepository.save(taskModel));
	}

	public TaskModel getTaskByTaskId(int taskId) {
		return this.taskRepository.getTaskByTaskId(taskId);
	}
}
