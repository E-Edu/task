package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskGroupController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.AddTaskToTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskGroupRequestDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class TaskGroupEndpoint {

	@Autowired private TaskGroupController taskGroupController;

	@PostMapping("/task_group")
	public Object createTaskGroup(CreateTaskGroupRequestDTO dto) {
		return this.taskGroupController.createTaskGroup(dto).getHttpResponse();
	}

	@PutMapping("/task_group")
	public Object updateTaskGroup(UpdateTaskGroupRequestDTO dto) {
		return this.taskGroupController.updateTaskGroup(dto).getHttpResponse();
	}

	@GetMapping("/task_group/{taskGroupId}")
	public Object getTaskGroup(@PathVariable @Min(1) int taskGroupId) {
		return this.taskGroupController.getTaskGroup(taskGroupId).getHttpResponse();
	}

	@GetMapping("/task_group/own")
	public Object getOwnTaskGroups() {
		return this.taskGroupController.getTaskGroupsByUser(Constants.UserId.TEST_UUID).getHttpResponse();
	}

	@GetMapping("/task_group/user/{userId}")
	public Object getTaskGroupsOfUser(@PathVariable @NotNull UUID userId) {
		return this.taskGroupController.getTaskGroupsByUser(userId).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureId}/task_group")
	public Object getTaskGroupsOfLecture(@PathVariable @Min(1) int lectureId) {
		return this.taskGroupController.getTaskGroupsByLecture(lectureId).getHttpResponse();
	}

	@DeleteMapping("/task_group/{taskGroupId}")
	public Object deleteTaskGroup(@PathVariable @Min(1) int taskGroupId) {
		return this.taskGroupController.deleteTaskGroup(taskGroupId).getHttpResponse();
	}

	@PostMapping("/task_group/task")
	public Object addTaskToTaskGroup(AddTaskToTaskGroupRequestDTO dto) {
		return this.taskGroupController.addTaskToTaskGroup(dto).getHttpResponse();
	}

	@DeleteMapping("/task_group/{taskGroupId}/task/{taskId}")
	public Object removeTaskFromTaskGroup(@PathVariable @Min(1) int taskGroupId, @PathVariable @Min(1) int taskId) {
		return this.taskGroupController.removeTaskFromTaskGroup(taskGroupId, taskId).getHttpResponse();
	}

}
