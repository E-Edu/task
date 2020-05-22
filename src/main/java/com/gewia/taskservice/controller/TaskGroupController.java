package com.gewia.taskservice.controller;

import com.gewia.taskservice.TaskServiceApplication;
import com.gewia.taskservice.controller.user.UserDataHandler;
import com.gewia.taskservice.database.model.DifficultyModel;
import com.gewia.taskservice.database.model.LectureModel;
import com.gewia.taskservice.database.model.TaskGroupModel;
import com.gewia.taskservice.database.model.TaskGroupTaskModel;
import com.gewia.taskservice.database.model.TaskModel;
import com.gewia.taskservice.database.repository.TaskGroupRepository;
import com.gewia.taskservice.database.repository.TaskGroupTaskRepository;
import com.gewia.taskservice.endpoint.dto.request.AddTaskToTaskGroupRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.CreateTaskGroupRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.UpdateTaskGroupRequestDTO;
import com.gewia.taskservice.endpoint.dto.response.GetAllTaskGroupsResponseDTO;
import com.gewia.taskservice.endpoint.dto.response.TaskGroupResponseDTO;
import com.gewia.taskservice.util.Constants;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
import com.gewia.taskservice.util.PaginationManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import static com.gewia.taskservice.util.Constants.TaskGroup.NAME_KEY;

@Component
public class TaskGroupController implements UserDataHandler {

	@Autowired private TaskGroupRepository taskGroupRepository;
	@Autowired private TaskGroupTaskRepository taskGroupTaskRepository;

	@Autowired private TaskController taskController;
	@Autowired private LectureController lectureController;
	@Autowired private DifficultyController difficultyController;

	public ControllerResult<TaskGroupResponseDTO> createTaskGroup(CreateTaskGroupRequestDTO dto) {
		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureByIdOrNameKey(dto.getLectureId(), dto.getLectureNameKey());
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByIdOrNameKey(dto.getDifficultyId(), dto.getDifficultyNameKey());
		if (difficultyResult.isResultNotPresent()) return ControllerResult.ret(difficultyResult);

		List<ControllerResult<TaskModel>> taskResults = new ArrayList<>();
		for (int taskId : dto.getTaskIds()) taskResults.add(this.taskController.getTaskRaw(taskId));
		taskResults.removeIf(ControllerResult::isResultNotPresent);
		if (taskResults.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, Constants.Task.NAME_KEY);

		TaskGroupModel taskGroup = this.taskGroupRepository.save(new TaskGroupModel(
				-1,
				dto.getNameKey(),
				dto.getLanguage(),
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

		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureByIdOrNameKey(dto.getLectureId(), dto.getLectureNameKey());

		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByIdOrNameKey(dto.getDifficultyId(), dto.getDifficultyNameKey());

		TaskGroupModel taskGroup = optionalTaskGroup.get();
		taskGroup.updateNameKey(dto.getNameKey())
				.updateLanguage(dto.getLanguage())
				.updateLecture(lectureResult)
				.updateDifficulty(difficultyResult);

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
