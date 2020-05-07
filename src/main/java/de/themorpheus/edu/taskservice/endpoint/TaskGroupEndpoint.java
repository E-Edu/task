package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskGroupController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.AddTaskToTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class TaskGroupEndpoint {

	@Autowired private TaskGroupController taskGroupController;

	@PostMapping("/task_group")
	public Object createTaskGroup(@RequestBody @Valid CreateTaskGroupRequestDTO dto) {
		return this.taskGroupController.createTaskGroup(dto).getHttpResponse();
	}

	@PutMapping("/task_group")
	public Object updateTaskGroup(@RequestBody @Valid UpdateTaskGroupRequestDTO dto) {
		return this.taskGroupController.updateTaskGroup(dto).getHttpResponse();
	}

	@GetMapping("/task_group/{taskGroupId}")
	public Object getTaskGroup(@PathVariable @Min(1) int taskGroupId) {
		return this.taskGroupController.getTaskGroup(taskGroupId).getHttpResponse();
	}

	@GetMapping("/task_group/own")
	public Object getOwnTaskGroups(@RequestParam(required = false, defaultValue = "0") int skip,
									@RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskGroupController.getTaskGroupsByUser(Constants.UserId.TEST_UUID, skip, max).getHttpResponse();
	}

	@GetMapping("/task_group/user/{userId}")
	public Object getTaskGroupsOfUser(@PathVariable @NotNull UUID userId,
									  @RequestParam(required = false, defaultValue = "0") int skip,
									  @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskGroupController.getTaskGroupsByUser(userId, skip, max).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureId:[0-9]+}/task_group")
	public Object getTaskGroupsOfLecture(@PathVariable @NotBlank int lectureId,
										 @RequestParam(required = false, defaultValue = "0") int skip,
										 @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskGroupController.getTaskGroupsByLecture(lectureId, skip, max).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureNameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}/task_group")
	public Object getTaskGroupsOfLectureByLectureNameKey(@PathVariable @NotBlank String lectureNameKey,
														 @RequestParam(required = false, defaultValue = "0") int skip,
														 @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskGroupController.getTaskGroupsByLectureNameKey(lectureNameKey, skip, max).getHttpResponse();
	}

	@DeleteMapping("/task_group/{taskGroupId}")
	public Object deleteTaskGroup(@PathVariable @Min(1) int taskGroupId) {
		return this.taskGroupController.deleteTaskGroup(taskGroupId).getHttpResponse();
	}

	@PostMapping("/task_group/task")
	public Object addTaskToTaskGroup(@RequestBody @Valid AddTaskToTaskGroupRequestDTO dto) {
		return this.taskGroupController.addTaskToTaskGroup(dto).getHttpResponse();
	}

	@DeleteMapping("/task_group/{taskGroupId}/task/{taskId}")
	public Object removeTaskFromTaskGroup(@PathVariable @Min(1) int taskGroupId, @PathVariable @Min(1) int taskId) {
		return this.taskGroupController.removeTaskFromTaskGroup(taskGroupId, taskId).getHttpResponse();
	}

}
