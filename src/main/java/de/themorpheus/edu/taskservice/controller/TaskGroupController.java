package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.TaskServiceApplication;
import de.themorpheus.edu.taskservice.controller.user.UserDataHandler;
import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskGroupModel;
import de.themorpheus.edu.taskservice.database.model.TaskGroupTaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.repository.TaskGroupRepository;
import de.themorpheus.edu.taskservice.database.repository.TaskGroupTaskRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.AddTaskToTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetAllTaskGroupsResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.TaskGroupResponseDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.PaginationManager;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.TaskGroup.NAME_KEY;

@Component
public class TaskGroupController implements UserDataHandler {

	@Autowired private TaskGroupRepository taskGroupRepository;
	@Autowired private TaskGroupTaskRepository taskGroupTaskRepository;

	@Autowired private TaskController taskController;
	@Autowired private LectureController lectureController;
	@Autowired private DifficultyController difficultyController;

	public ControllerResult<TaskGroupResponseDTO> createTaskGroup(CreateTaskGroupRequestDTO dto) {
		ControllerResult<LectureModel> lectureResult;
		if (Validation.greaterZero(dto.getLectureId()))
			lectureResult = this.lectureController.getLectureRaw(dto.getLectureId());
		else if (Validation.validateNotNullOrEmpty(dto.getLectureNameKey()))
			lectureResult = this.lectureController.getLectureByNameKeyRaw(dto.getLectureNameKey());
		else return ControllerResult.of(Error.MISSING_PARAM, Constants.Lecture.NAME_KEY);

		ControllerResult<DifficultyModel> difficultyResult;
		if (Validation.greaterZero(dto.getDifficultyId()))
			difficultyResult = this.difficultyController.getDifficulty(dto.getDifficultyId());
		else if (Validation.validateNotNullOrEmpty(dto.getDifficultyNameKey()))
			difficultyResult = this.difficultyController.getDifficultyByNameKey(dto.getDifficultyNameKey());
		else return ControllerResult.of(Error.MISSING_PARAM, Constants.Difficulty.NAME_KEY);

		List<ControllerResult<TaskModel>> taskResults = new ArrayList<>();
		for (int taskId : dto.getTaskIds()) taskResults.add(this.taskController.getTaskRaw(taskId));
		taskResults.removeIf(ControllerResult::isResultNotPresent);
		if (taskResults.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, Constants.Task.NAME_KEY);

		TaskGroupModel taskGroup = this.taskGroupRepository.save(new TaskGroupModel(
				-1,
				dto.getNameKey(),
				lectureResult.getResult(),
				difficultyResult.getResult(),
				Constants.UserId.TEST_UUID
			)
		);

		List<TaskGroupTaskModel> taskGroupTasks = new ArrayList<>();
		for (ControllerResult<TaskModel> taskResult : taskResults)
			taskGroupTasks.add(this.taskGroupTaskRepository.save(new TaskGroupTaskModel(
					-1,
					taskGroup,
					taskResult.getResult()
				)
			));
		int[] taskIds = new int[taskGroupTasks.size()];
		for (int i = 0; i < taskIds.length; i++)
			taskIds[i] = taskGroupTasks.get(i).getTaskId().getTaskId();

		return ControllerResult.of(taskGroup.toResponseDTO(taskIds));
	}

	public ControllerResult<TaskGroupResponseDTO> updateTaskGroup(UpdateTaskGroupRequestDTO dto) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(dto.getTaskGroupId());
		if (!optionalTaskGroup.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<LectureModel> lectureResult;
		if (Validation.greaterZero(dto.getLectureId()))
			lectureResult = this.lectureController.getLectureRaw(dto.getLectureId());
		else if (Validation.validateNotNullOrEmpty(dto.getLectureNameKey()))
			lectureResult = this.lectureController.getLectureByNameKeyRaw(dto.getLectureNameKey());
		else lectureResult = ControllerResult.of(Error.MISSING_PARAM, Constants.Lecture.NAME_KEY);

		ControllerResult<DifficultyModel> difficultyResult;
		if (Validation.greaterZero(dto.getDifficultyId()))
			difficultyResult = this.difficultyController.getDifficulty(dto.getDifficultyId());
		else if (Validation.validateNotNullOrEmpty(dto.getDifficultyNameKey()))
			difficultyResult = this.difficultyController.getDifficultyByNameKey(dto.getDifficultyNameKey());
		else difficultyResult = ControllerResult.of(Error.MISSING_PARAM, Constants.Difficulty.NAME_KEY);

		TaskGroupModel taskGroup = optionalTaskGroup.get();
		if (Validation.validateNotNullOrEmpty(dto.getNameKey())) taskGroup.setNameKey(dto.getNameKey());
		if (lectureResult.isResultPresent()) taskGroup.setLectureId(lectureResult.getResult());
		if (difficultyResult.isResultPresent()) taskGroup.setDifficultyId(difficultyResult.getResult());

		return ControllerResult.of(this.getTaskGroupResponseDTO(this.taskGroupRepository.save(taskGroup)));
	}

	public ControllerResult<TaskGroupResponseDTO> getTaskGroup(int taskGroupId) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(taskGroupId);

		return optionalTaskGroup.map(taskGroupModel -> ControllerResult.of(
				this.getTaskGroupResponseDTO(taskGroupModel)))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	public ControllerResult<GetAllTaskGroupsResponseDTO> getTaskGroupsByUser(UUID userId, int skip, int max) {
		List<TaskGroupModel> taskGroups = this.taskGroupRepository
				.findAllByAuthorId(userId, PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_GROUP_RESULTS)
					)
				);
		if (taskGroups.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		taskGroups.forEach(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllTaskGroupsResponseDTO> getTaskGroupsByLecture(int lectureId, int skip, int max) {
		ControllerResult<LectureModel> lectureResult = this.lectureController.getLectureRaw(lectureId);
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		List<TaskGroupModel> taskGroups = this.taskGroupRepository
				.findAllByLectureId(lectureResult.getResult(), PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_GROUP_RESULTS)
					)
				);
		if (taskGroups.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		taskGroups.forEach(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));

	}

	public ControllerResult<Object> getTaskGroupsByLectureNameKey(String lectureNameKey, int skip, int max) {
		ControllerResult<LectureModel> lectureResult = this.lectureController.getLectureByNameKeyRaw(lectureNameKey);
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		List<TaskGroupModel> taskGroups = this.taskGroupRepository
				.findAllByLectureId(lectureResult.getResult(), PageRequest.of(
						Math.max(skip, 0),
						PaginationManager.checkPositiveToMaxOrGetMax(max, TaskServiceApplication.MAX_TASK_GROUP_RESULTS)
					)
				);
		if (taskGroups.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		taskGroups.forEach(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));
	}

	@Transactional
	public ControllerResult<TaskGroupModel> deleteTaskGroup(int taskGroupId) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(taskGroupId);
		if (!optionalTaskGroup.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		TaskGroupModel taskGroup = optionalTaskGroup.get();
		this.taskGroupTaskRepository.deleteAllByTaskGroupId(taskGroup);
		this.taskGroupRepository.delete(taskGroup);

		return ControllerResult.empty();
	}

	public ControllerResult<TaskGroupResponseDTO> addTaskToTaskGroup(AddTaskToTaskGroupRequestDTO dto) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(dto.getTaskGroupId());
		if (!optionalTaskGroup.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<TaskModel> taskResult = this.taskController.getTaskRaw(dto.getTaskId());
		if (taskResult.isResultNotPresent()) return ControllerResult.ret(taskResult);

		if (this.taskGroupTaskRepository.existsByTaskGroupIdAndTaskId(optionalTaskGroup.get(), taskResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		this.taskGroupTaskRepository.save(new TaskGroupTaskModel(-1, optionalTaskGroup.get(), taskResult.getResult()));

		return ControllerResult.empty();
	}

	@Transactional
	public ControllerResult<TaskGroupModel> removeTaskFromTaskGroup(int taskGroupId, int taskId) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(taskGroupId);
		if (!optionalTaskGroup.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<TaskModel> taskResult = this.taskController.getTaskRaw(taskId);
		if (taskResult.isResultNotPresent()) return ControllerResult.ret(taskResult);

		this.taskGroupTaskRepository.deleteByTaskGroupIdAndTaskId(optionalTaskGroup.get(), taskResult.getResult());

		return ControllerResult.empty();
	}

	private TaskGroupResponseDTO getTaskGroupResponseDTO(TaskGroupModel taskGroup) {
		List<TaskGroupTaskModel> taskGroupTasks = this.taskGroupTaskRepository.findAllByTaskGroupId(taskGroup);
		if (taskGroupTasks.isEmpty()) return taskGroup.toResponseDTO(new int[0]);

		int[] taskIds = new int[taskGroupTasks.size()];
		for (int i = 0; i < taskIds.length; i++)
			taskIds[i] = taskGroupTasks.get(i).getTaskId().getTaskId();

		return taskGroup.toResponseDTO(taskIds);
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.taskGroupRepository.findAllByAuthorId(userId).forEach(taskGroup -> {
			taskGroup.setAuthorId(Constants.UserId.EMPTY_UUID);
			this.taskGroupRepository.save(taskGroup);
		});
	}

	@Override
	public ControllerResult<Object> getUserData(UUID userId) {
		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		this.taskGroupRepository.findAllByAuthorId(userId).stream()
				.map(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));
		if (responseDTOs.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));
	}

}
