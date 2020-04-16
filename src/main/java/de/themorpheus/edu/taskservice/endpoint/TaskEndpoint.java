package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.controller.VotingController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.GetNextTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Object getAllTasksFromLecture(@PathVariable @NotNull @NotEmpty @NotBlank String lectureNameKey) {
		return this.taskController.getTasksFromLecture(lectureNameKey).getHttpResponse();
	}

	@PatchMapping("/task/verify/{taskId}")
	public Object verifyTask(@PathVariable @Min(0) int taskId) {
		return this.taskController.verifyTask(taskId).getHttpResponse();
	}

	@PutMapping("/task/{taskId}")
	public Object updateTask(@PathVariable @Min(0) int taskId, @RequestBody @Valid UpdateTaskRequestDTO dto) {
		return this.taskController.updateTask(taskId, dto).getHttpResponse();
	}

	@PutMapping("/task/vote/{taskId}")
	public Object voteTask(@PathVariable @Min(0) int taskId, @RequestBody @Valid VoteTaskRequestDTO dto) {
		// TODO: Pass real userId instead of random UUID
		return this.votingController.voteTask(taskId, dto.getVote(), UUID.randomUUID()).getHttpResponse();
	}

	@PostMapping("/task/next")
	public Object nextTask(@RequestBody @Valid GetNextTaskRequestDTO dto) {
		return this.taskController.getNextTask(dto.getLastTaskIds()).getHttpResponse();
	}

	@DeleteMapping("/task/{taskId}")
	public Object deleteTask(@PathVariable @Min(0) int taskId) {
		return this.taskController.deleteTask(taskId).getHttpResponse();
	}

	@GetMapping("/task/{taskId}")
	public Object getTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.getTaskByTaskId(taskId).getHttpResponse();
	}

}
