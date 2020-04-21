package de.themorpheus.edu.taskservice.controller;

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
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
		ControllerResult<LectureModel> lectureResult = this.lectureController.getLectureByLectureId(dto.getLectureId());
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByDifficultyId(dto.getDifficultyId());
		if (difficultyResult.isResultNotPresent()) return ControllerResult.ret(difficultyResult);

		List<ControllerResult<TaskModel>> taskResults = new ArrayList<>();
		for (int taskId : dto.getTaskIds()) taskResults.add(this.taskController.getTaskByTaskId(taskId));
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

		TaskGroupModel taskGroup = optionalTaskGroup.get();
		ControllerResult<LectureModel> lectureResult = this.lectureController
				.getLectureByLectureId(dto.getLectureId());
		ControllerResult<DifficultyModel> difficultyResult = this.difficultyController
				.getDifficultyByDifficultyId(dto.getDifficultyId());

		if (Validation.validateNotNullOrEmpty(dto.getNameKey())) taskGroup.setNameKey(dto.getNameKey());
		if (lectureResult.isResultPresent()) taskGroup.setLectureId(lectureResult.getResult());
		if (difficultyResult.isResultPresent()) taskGroup.setDifficultyId(difficultyResult.getResult());

		taskGroup = this.taskGroupRepository.save(taskGroup);

		return ControllerResult.of(this.getTaskGroupResponseDTO(taskGroup));
	}

	public ControllerResult<TaskGroupResponseDTO> getTaskGroup(int taskGroupId) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(taskGroupId);

		return optionalTaskGroup.map(taskGroupModel -> ControllerResult.of(
				this.getTaskGroupResponseDTO(taskGroupModel)))
				.orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	private TaskGroupResponseDTO getTaskGroupResponseDTO(TaskGroupModel taskGroup) {
		List<TaskGroupTaskModel> taskGroupTasks = this.taskGroupTaskRepository.findAllByTaskGroupId(taskGroup);
		if (taskGroupTasks.isEmpty()) return taskGroup.toResponseDTO(new int[0]);

		int[] taskIds = new int[taskGroupTasks.size()];
		for (int i = 0; i < taskIds.length; i++)
			taskIds[i] = taskGroupTasks.get(i).getTaskId().getTaskId();

		return taskGroup.toResponseDTO(taskIds);
	}

	public ControllerResult<GetAllTaskGroupsResponseDTO> getTaskGroupsByUser(UUID userId) {
		List<TaskGroupModel> taskGroups = this.taskGroupRepository.findAllByAuthorId(userId);
		if (taskGroups.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		taskGroups.forEach(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));
	}

	public ControllerResult<GetAllTaskGroupsResponseDTO> getTaskGroupsByLecture(int lectureId) {
		ControllerResult<LectureModel> lectureResult = this.lectureController.getLectureByLectureId(lectureId);
		if (lectureResult.isResultNotPresent()) return ControllerResult.ret(lectureResult);

		List<TaskGroupModel> taskGroups = this.taskGroupRepository.findAllByLectureId(lectureResult.getResult());
		if (taskGroups.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<TaskGroupResponseDTO> responseDTOs = new ArrayList<>();
		taskGroups.forEach(taskGroup -> responseDTOs.add(this.getTaskGroupResponseDTO(taskGroup)));

		return ControllerResult.of(new GetAllTaskGroupsResponseDTO(responseDTOs));
	}

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

		ControllerResult<TaskModel> taskResult = this.taskController.getTaskByTaskId(dto.getTaskId());
		if (taskResult.isResultNotPresent()) return ControllerResult.ret(taskResult);

		if (this.taskGroupTaskRepository.existsByTaskGroupIdAndTaskId(optionalTaskGroup.get(), taskResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		this.taskGroupTaskRepository.save(new TaskGroupTaskModel(-1, optionalTaskGroup.get(), taskResult.getResult()));

		return ControllerResult.empty();
	}

	public ControllerResult<TaskGroupModel> removeTaskFromTaskGroup(int taskGroupId, int taskId) {
		Optional<TaskGroupModel> optionalTaskGroup = this.taskGroupRepository.findById(taskGroupId);
		if (!optionalTaskGroup.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ControllerResult<TaskModel> taskResult = this.taskController.getTaskByTaskId(taskId);
		if (taskResult.isResultNotPresent()) return ControllerResult.ret(taskResult);

		this.taskGroupTaskRepository.deleteByTaskGroupIdAndTaskId(optionalTaskGroup.get(), taskResult.getResult());

		return ControllerResult.empty();
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.taskGroupRepository.findAllByAuthorId(userId).forEach(taskGroup -> {
			taskGroup.setAuthorId(Constants.UserId.EMPTY_UUID);
			this.taskGroupRepository.save(taskGroup);
		});
	}

	@Override
	public Object getUserData(UUID userId) {
		return this.taskGroupRepository.findAllByAuthorId(userId);
	}

}
