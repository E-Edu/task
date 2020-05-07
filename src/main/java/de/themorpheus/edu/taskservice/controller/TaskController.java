package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.TaskServiceApplication;
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
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllTasksResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetMarkTaskAsDoneResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetSolutionTypeResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskResponseDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.PaginationManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

	public ControllerResult<TaskResponseDTO> createTask(CreateTaskRequestDTO dto) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureByIdOrNameKey(dto.getLectureId(), dto.getLectureNameKey());
		if (lectureResult.isResultPresent()) return ControllerResult.ret(lectureResult);

		ControllerResult<TaskTypeModel> taskTypeResult = this.taskTypeController
				.getTaskTypeByIdOrNameKey(dto.getTaskTypeId(), dto.getTaskTypeNameKey());
		if (taskTypeResult.isResultPresent()) return ControllerResult.ret(taskTypeResult);

		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByIdOrNameKey(dto.getDifficultyId(), dto.getDifficultyNameKey());
		if (difficultyResult.isResultPresent()) return ControllerResult.ret(difficultyResult);

		TaskModel task = new TaskModel(
				-1,
				dto.getDescription(),
				dto.getTask(),
				Constants.UserId.TEST_UUID,
				dto.getNecessaryPoints(),
				false,
				dto.getLanguage(),
				lectureResult.getResult(),
				taskTypeResult.getResult(),
				difficultyResult.getResult()
		);

		return ControllerResult.of(this.taskRepository.save(task).toResponseDTO());
	}

	public ControllerResult<TaskResponseDTO> updateTask(UpdateTaskRequestDTO dto) {
		Optional<TaskModel> optionalTask = this.taskRepository.findById(dto.getTaskId());
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureByIdOrNameKey(dto.getLectureId(), dto.getLectureNameKey());

		ControllerResult<TaskTypeModel> taskTypeResult = this.taskTypeController
				.getTaskTypeByIdOrNameKey(dto.getTaskTypeId(), dto.getTaskTypeNameKey());

		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByIdOrNameKey(dto.getDifficultyId(), dto.getDifficultyNameKey());

		TaskModel task = optionalTask.get();
		task.updateTask(dto.getTask())
				.updateDescription(dto.getDescription())
				.updateLanguage(dto.getLanguage())
				.updateNecessaryPoints(dto.getNecessaryPoints())
				.updateLecture(lectureResult)
				.updateTaskType(taskTypeResult)
				.updateDifficulty(difficultyResult);

		return ControllerResult.of(this.taskRepository.save(task).toResponseDTO());
	}

	public ControllerResult<TaskResponseDTO> getTask(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.findByTaskId(taskId);

		return optionalTask.map(task -> ControllerResult.of(task.toResponseDTO()))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<TaskModel> getTaskRaw(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.findByTaskId(taskId);

		return optionalTask.map(ControllerResult::of)
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<TaskResponseDTO> getNextTask(List<Integer> finishedTaskIds) {
		Optional<TaskModel> optionalTask = this.taskRepository.findByTaskId(finishedTaskIds.get(0));
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		LectureModel lecture = optionalTask.get().getLectureId();
		if (lecture == null) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		List<TaskModel> tasks = this.taskRepository.findAllByLectureId(lecture);
		tasks.removeIf(task -> finishedTaskIds.contains(task.getTaskId()) || this.userBanRepository.existsByUserId(Constants.UserId.TEST_UUID)); //TODO: Use SQL query
		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(tasks.get(RANDOM.nextInt(tasks.size())).toResponseDTO());
	}

	public ControllerResult<GetAllTasksResponseDTO> getTaskByUserId(UUID authorId, int skip, int max) {
		List<TaskModel> tasks = this.taskRepository
				.findAllByAuthorId(authorId, PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_RESULTS)
					)
				);
		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskResponseDTO> responseDTOs = new ArrayList<>();
		tasks.forEach(task -> responseDTOs.add(task.toResponseDTO()));

		return ControllerResult.of(new GetAllTasksResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllTasksResponseDTO> getTasksByLectureId(int lectureId, boolean showBanned, int skip, int max) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureRaw(lectureId);
		if (lectureResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		List<TaskModel> tasks = this.taskRepository
				.findAllByLectureId(lectureResult.getResult(), PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_RESULTS)
					)
				);
		tasks.removeIf(task -> userBanRepository.existsByUserId(Constants.UserId.TEST_UUID) && !showBanned);
		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskResponseDTO> responseDTOs = new ArrayList<>();
		tasks.forEach(task -> responseDTOs.add(task.toResponseDTO()));

		return ControllerResult.of(new GetAllTasksResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllTasksResponseDTO> getTasksByLectureNameKey(String lectureNameKey, boolean showBanned, int skip, int max) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
			.getLectureByNameKeyRaw(lectureNameKey);
		if (lectureResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND, Constants.Lecture.NAME_KEY);

		List<TaskModel> tasks = this.taskRepository
				.findAllByLectureId(lectureResult.getResult(), PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_RESULTS)
					)
				);
		tasks.removeIf(task -> userBanRepository.existsByUserId(Constants.UserId.TEST_UUID) && !showBanned);
		if (tasks.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskResponseDTO> responseDTOs = new ArrayList<>();
		tasks.forEach(task -> responseDTOs.add(task.toResponseDTO()));

		return ControllerResult.of(new GetAllTasksResponseDTO(responseDTOs));
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

	public ControllerResult<TaskResponseDTO> verifyTask(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.findByTaskId(taskId);
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		optionalTask.get().setVerified(true); //TODO: Single property update
		return ControllerResult.of(this.taskRepository.save(optionalTask.get()).toResponseDTO());
	}


	public ControllerResult<GetMarkTaskAsDoneResponseDTO> markTaskAsDone(int taskId) {
		Optional<TaskModel> optionalTask = this.taskRepository.findByTaskId(taskId);
		if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		TaskModel task = optionalTask.get();
		if (taskDoneRepository.existsByTaskIdAndUserId(task, Constants.UserId.TEST_UUID))
			return ControllerResult.of(Error.ALREADY_EXISTS, Constants.Task.Done.NAME_KEY);

		TaskDoneModel taskDone = taskDoneRepository.save(new TaskDoneModel(
				-1,
				Constants.UserId.TEST_UUID,
				task,
				new Date(System.currentTimeMillis())
			)
		);

		return ControllerResult.of(new GetMarkTaskAsDoneResponseDTO(taskDone.getTaskDoneId(), taskDone.getDate()));
	}


	public boolean existsByLectureId(LectureModel lectureId) {
		return this.taskRepository.existsByLectureId(lectureId);
	}

	public boolean existsByDifficultyId(DifficultyModel difficultyId) {
		return this.taskRepository.existsByDifficultyId(difficultyId);
	}

	public boolean existsByTaskTypeId(TaskTypeModel taskTypeId) {
		return this.taskRepository.existsByTaskTypeId(taskTypeId);
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.taskRepository.findAllByAuthorId(userId).forEach(task -> {
			task.setAuthorId(Constants.UserId.EMPTY_UUID);
			this.taskRepository.save(task);
		});
	}

	@Override
	public ControllerResult<Object> getUserData(UUID userId) {
		List<TaskResponseDTO> responseDTOs = new ArrayList<>();
		this.taskRepository.findAllByAuthorId(userId).stream().map(task -> responseDTOs.add(task.toResponseDTO()));
		if (responseDTOs.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTasksResponseDTO(responseDTOs));
	}

}
