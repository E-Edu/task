package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.controller.solution.SolutionController;
import de.themorpheus.edu.taskservice.controller.user.UserDataHandler;
import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskDoneModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.TaskDoneRepository;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import de.themorpheus.edu.taskservice.database.repository.UserBanRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllTasksByUserResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetMarkTaskAsDoneResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetSolutionTypeResponseDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Task.NAME_KEY;

@Component
public class TaskController implements UserDataHandler {

	private static final Random RANDOM = new Random();

	@Autowired private TaskRepository taskRepository;
	@Autowired private SolutionController solutionController;

	@Autowired private LectureController lectureController;
	@Autowired private TaskTypeController taskTypeController;
	@Autowired private DifficultyController difficultyController;

	@Autowired private TaskDoneRepository taskDoneRepository;

	@Autowired private UserBanRepository userBanRepository;

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
			Constants.UserId.TEST_UUID,
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
		Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(finishedTaskIds.get(0));
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		LectureModel lecture = optionalTask.get().getLectureId();
		if (lecture == null) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		List<TaskModel> tasks = this.taskRepository.getAllTasksByLectureId(lecture);
		tasks.removeIf(task -> finishedTaskIds.contains(task.getTaskId()) || this.userBanRepository.existsByUserId(Constants.UserId.TEST_UUID)); //TODO: Use SQL query

		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(tasks.get(RANDOM.nextInt(tasks.size())));
	}

	public ControllerResult<SolutionModel> deleteTask(int taskId) {
		if (!this.taskRepository.existsById(taskId)) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<SolutionModel> solutionResult = this.solutionController.deleteAllSolutions(taskId);
		this.taskRepository.deleteById(taskId);

		return solutionResult;
	}

	public ControllerResult<GetSolutionTypeResponseDTO> getSolutionType(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getSolution(taskId);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		return ControllerResult.of(new GetSolutionTypeResponseDTO(solutionResult.getResult().getSolutionType()));
	}

	public ControllerResult<GetAllTasksByUserResponseDTO> getAllTaskByUser(UUID authorId) {
		List<TaskModel> taskModels = this.taskRepository.findAllByAuthorId(authorId);
		if (taskModels.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTasksByUserResponseDTO(taskModels));
	}

	public ControllerResult<List<TaskModel>> getAllTasks() {
		return ControllerResult.of(this.taskRepository.findAll());
	}

	public ControllerResult<List<TaskModel>> getTasksFromLecture(String lectureNameKey, boolean showBanned) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
			.getLectureByNameKey(lectureNameKey);
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		List<TaskModel> tasks = this.taskRepository.getAllTasksByLectureId(lectureResult.getResult());
		tasks.removeIf(task -> this.userBanRepository.existsByUserId(Constants.UserId.TEST_UUID) && !showBanned);
		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(tasks);
	}

	public ControllerResult<TaskModel> verifyTask(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(taskId);
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		optionalTask.get().setVerified(true); //TODO: Single property update
		return ControllerResult.of(this.taskRepository.save(optionalTask.get()));
	}

	public ControllerResult<TaskModel> updateTask(int taskId, UpdateTaskRequestDTO dto) {
		Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(taskId);
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<LectureModel> lectureResult = this.lectureController
			.getLectureByNameKey(dto.getLectureNameKey());
		ControllerResult<TaskTypeModel> taskTypeResult = this.taskTypeController
			.getTaskType(dto.getTaskTypeNameKey());
		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
			.getDifficultyByNameKey(dto.getDifficultyNameKey());

		TaskModel task = optionalTask.get();
		if (lectureResult.isResultPresent()) task.setLectureId(lectureResult.getResult());
		if (taskTypeResult.isResultPresent()) task.setTaskTypeId(taskTypeResult.getResult());
		if (difficultyResult.isResultPresent()) task.setDifficultyId(difficultyResult.getResult());
		if (Validation.validateNotNullOrEmpty(dto.getTask())) task.setTask(dto.getTask());
		if (Validation.greaterZero(dto.getNecessaryPoints())) task.setNecessaryPoints(dto.getNecessaryPoints());

		return ControllerResult.of(this.taskRepository.save(task));
	}

	public ControllerResult<TaskModel> getTaskByTaskId(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(taskId);

		return optionalTask.map(ControllerResult::of).orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.taskRepository.findAllByAuthorId(userId).forEach(task -> {
			task.setAuthorId(Constants.UserId.EMPTY_UUID);
			this.taskRepository.save(task);
		});
	}

	@Override
	public Object getUserData(UUID userId) {
		return this.taskRepository.findAllByAuthorId(userId);
	}

	public ControllerResult<GetMarkTaskAsDoneResponseDTO> markTaskAsDone(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(taskId);
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		TaskModel task = optionalTask.get();
		if (taskDoneRepository.existsByTaskIdAndUserId(task, Constants.UserId.TEST_UUID))
			return ControllerResult.of(Error.ALREADY_EXISTS, Constants.Task.TaskDone.NAME_KEY);

		TaskDoneModel taskDone = taskDoneRepository.save(new TaskDoneModel(
				-1,
				Constants.UserId.TEST_UUID,
				task,
				new Date(System.currentTimeMillis())
			)
		);

		return ControllerResult.of(new GetMarkTaskAsDoneResponseDTO(taskDone.getTaskDoneId(), taskDone.getDate()));
	}

}
