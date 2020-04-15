package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.controller.solution.SolutionController;
import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetSolutionTypeResponseDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Task.NAME_KEY;

@Component
public class TaskController {

	private static final Random RANDOM = new Random();

	@Autowired private TaskRepository taskRepository;
	@Autowired private SolutionController solutionController;

	@Autowired private LectureController lectureController;
	@Autowired private TaskTypeController taskTypeController;
	@Autowired private DifficultyController difficultyController;

	public ControllerResult<TaskModel> createTask(CreateTaskRequestDTO dto) {
		ControllerResult<LectureModel> lectureResult = this.lectureController.getLectureByNameKey(dto.getLectureNameKey());
		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController.getDifficultyByNameKey(dto.getDifficultyNameKey());
		ControllerResult<TaskTypeModel> taskTypeResult = this.taskTypeController.getTaskType(dto.getTaskTypeNameKey());

		if (lectureResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);
		if (difficultyResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.Difficulty.NAME_KEY);
		if (taskTypeResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.TaskType.NAME_KEY);

		TaskModel task = new TaskModel(
			-1,
			dto.getDescription(),
			UUID.randomUUID(), //TODO
			dto.getTask(),
			dto.getNecessaryPoints(),
			false,
			lectureResult.getResult(),
			taskTypeResult.getResult(),
			difficultyResult.getResult()
		);

		return ControllerResult.of(this.taskRepository.save(task));
	}

	public ControllerResult<TaskModel> getNextTask(List<Integer> finishedTaskIds) {
		TaskModel task = this.taskRepository.getTaskByTaskId(finishedTaskIds.get(0));
		if (task == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		LectureModel lecture = task.getLectureId();
		if (lecture == null) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		List<TaskModel> tasks = this.taskRepository.getAllTasksByLectureId(lecture);
		tasks.removeIf(tm -> finishedTaskIds.contains(tm.getTaskId())); //TODO: Use SQL query
		if (tasks.isEmpty()) return ControllerResult.of(Error.NO_CONTENT, NAME_KEY);

		return ControllerResult.of(tasks.get(RANDOM.nextInt(tasks.size())));
	}

	public ControllerResult<SolutionModel> deleteTask(int taskId) {
		if (!this.taskRepository.existsById(taskId)) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.taskRepository.deleteById(taskId);
		return this.solutionController.deleteSolutions(taskId);
	}

	public ControllerResult<GetSolutionTypeResponseDTO> getSolutionType(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getSolution(taskId);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		return ControllerResult.of(new GetSolutionTypeResponseDTO(solutionResult.getResult().getSolutionType()));
	}

	public ControllerResult<List<TaskModel>> getAllTasks() {
		return ControllerResult.of(this.taskRepository.findAll());
	}

	public ControllerResult<List<TaskModel>> getTasksFromLecture(String lectureNameKey) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
			.getLectureByNameKey(lectureNameKey);
		if (lectureResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		return ControllerResult.of(this.taskRepository.getAllTasksByLectureId(lectureResult.getResult()));
	}

	public ControllerResult<TaskModel> verifyTask(int taskId) {
		TaskModel task = this.taskRepository.getTaskByTaskId(taskId);
		if (task == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		task.setVerified(true); //TODO: Single property update
		return ControllerResult.of(this.taskRepository.save(task));
	}

	public ControllerResult<TaskModel> updateTask(int taskId, UpdateTaskRequestDTO dto) {
		TaskModel task = this.taskRepository.getTaskByTaskId(taskId);
		if (task == null) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<LectureModel> lectureResult = this.lectureController
			.getLectureByNameKey(dto.getLectureNameKey());
		ControllerResult<TaskTypeModel> taskTypeResult = this.taskTypeController
			.getTaskType(dto.getTaskTypeNameKey());
		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
			.getDifficultyByNameKey(dto.getDifficultyNameKey());

		if (lectureResult.isResultPresent()) task.setLectureId(lectureResult.getResult());
		if (taskTypeResult.isResultPresent()) task.setTaskTypeId(taskTypeResult.getResult());
		if (difficultyResult.isResultPresent()) task.setDifficultyId(difficultyResult.getResult());
		if (Validation.validateNotNullOrEmpty(dto.getTask())) task.setTask(dto.getTask());
		if (Validation.greaterZero(dto.getNecessaryPoints())) task.setNecessaryPoints(dto.getNecessaryPoints());

		return ControllerResult.of(this.taskRepository.save(task));
	}

	public ControllerResult<TaskModel> getTaskByTaskId(int taskId) {
		return ControllerResult.of(this.taskRepository.getTaskByTaskId(taskId));
	}

}
