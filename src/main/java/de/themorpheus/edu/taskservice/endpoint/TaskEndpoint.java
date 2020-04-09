package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.controller.VotingController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.GetNextTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.UpdateTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.VoteTaskDTO;
import java.util.UUID;
import io.micrometer.core.annotation.Timed;
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

@Timed
@RestController
public class TaskEndpoint {

	@Autowired private TaskController taskController;
	@Autowired private VotingController votingController;

	@PostMapping("/task")
	public Object createTask(@RequestBody @Valid CreateTaskDTO dto) {
		return this.taskController.createTask(dto).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureNameKey}/task")
	public Object getAllTasksFromLecture(@PathVariable @NotNull @NotEmpty @NotBlank String lectureNameKey) {
		return this.taskController.getTasksFromLecture(lectureNameKey).getHttpResponse();
	}

	@PatchMapping(value = "/task/verify/{taskId}")
	public Object verifyTask(@PathVariable @Min(0) int taskId) {
		return this.taskController.verifyTask(taskId).getHttpResponse();
	}

	@PutMapping(path = "/task/{taskId}")
	public Object updateTask(@PathVariable @Min(0) int taskId, @RequestBody @Valid UpdateTaskDTO dto) {
		return this.taskController.updateTask(taskId, dto).getHttpResponse();
	}

	@PutMapping("/task/vote/{taskId}")
	public Object voteTask(@PathVariable @Min(0) int taskId, @RequestBody @Valid VoteTaskDTO dto) {
		// TODO: Pass real userId instead of random UUID
		return this.votingController.voteTask(taskId, dto.getVote(), UUID.randomUUID()).getHttpResponse();
	}

	@PostMapping("/task/next")
	public Object nextTask(@RequestBody @Valid GetNextTaskDTO dto) {
		return this.taskController.getNextTask(dto.getLastTaskIds()).getHttpResponse();
	}

	@DeleteMapping("/task/{taskId}")
	public Object deleteTask(@PathVariable @Min(0) int taskId) {
		return this.taskController.deleteTask(taskId).getHttpResponse();
	}

}
