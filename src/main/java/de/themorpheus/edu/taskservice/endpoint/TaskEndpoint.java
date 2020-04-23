package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.controller.VotingController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.GetNextTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class TaskEndpoint {

	@Autowired private TaskController taskController;
	@Autowired private VotingController votingController;

	@PostMapping("/task")
	public Object createTask(@RequestBody @Valid CreateTaskRequestDTO dto) {
		return this.taskController.createTask(dto).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureNameKey}/task")
	public Object getAllTasksFromLecture(@PathVariable @NotBlank String lectureNameKey, @RequestParam(required = false) boolean showBanned) {
		try {
			int lectureId = Integer.parseInt(lectureNameKey);
			return this.taskController.getTasksByLectureId(lectureId, showBanned).getHttpResponse();
		} catch (NumberFormatException ignored) {
			return this.taskController.getTasksByLectureNameKey(lectureNameKey, showBanned).getHttpResponse();
		}

	}

	@PatchMapping("/task/verify/{taskId}")
	public Object verifyTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.verifyTask(taskId).getHttpResponse();
	}

	@PutMapping("/task")
	public Object updateTask(@RequestBody @Valid UpdateTaskRequestDTO dto) {
		return this.taskController.updateTask(dto).getHttpResponse();
	}

	@PutMapping("/task/vote/{taskId}")
	public Object voteTask(@PathVariable @Min(1) int taskId, @RequestBody @Valid VoteTaskRequestDTO dto) {
		return this.votingController.voteTask(taskId, dto, Constants.UserId.TEST_UUID).getHttpResponse();
	}

	@PostMapping("/task/next")
	public Object nextTask(@RequestBody @Valid GetNextTaskRequestDTO dto) {
		return this.taskController.getNextTask(dto.getLastTaskIds()).getHttpResponse();
	}

	@DeleteMapping("/task/{taskId}")
	public Object deleteTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.deleteTask(taskId).getHttpResponse();
	}

	@PostMapping("/task/solution_type/{taskId}")
	public Object getSolutionTypeOfTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.getSolutionType(taskId).getHttpResponse();
	}

	@GetMapping("/task/{taskId}")
	public Object getTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.getTask(taskId).getHttpResponse();
	}

	@GetMapping("/task/user/{userId}")
	public Object getTaskByUser(@PathVariable @NotNull UUID userId) {
		return this.taskController.getAllTaskByUserId(userId).getHttpResponse();
	}

	@GetMapping("/task/own")
	public Object getOwnTasks() {
		return this.taskController.getAllTaskByUserId(Constants.UserId.TEST_UUID).getHttpResponse();
	}

	@PatchMapping("task/done/{taskId}")
	public Object markTaskAsDone(@PathVariable @Min(1) int taskId) {
		return this.taskController.markTaskAsDone(taskId).getHttpResponse();
	}

}
