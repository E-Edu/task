package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.controller.VotingController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.GetNextTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import de.themorpheus.edu.taskservice.util.Constants;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		return this.taskController.getTasksFromLecture(lectureNameKey, showBanned).getHttpResponse();
	}

	@PatchMapping("/task/verify/{taskId}")
	public Object verifyTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.verifyTask(taskId).getHttpResponse();
	}

	@PutMapping("/task/{taskId}")
	public Object updateTask(@PathVariable @Min(1) int taskId, @RequestBody @Valid UpdateTaskRequestDTO dto) {
		return this.taskController.updateTask(taskId, dto).getHttpResponse();
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

	@PostMapping("/task/solution_type/{task_id}")
	public Object getSolutionTypeOfTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.getSolutionType(taskId).getHttpResponse();
	}

	@GetMapping("/task/{taskId}")
	public Object getTask(@PathVariable @Min(1) int taskId) {
		return this.taskController.getTaskByTaskId(taskId).getHttpResponse();
	}

	@GetMapping("task/my")
	public Object getTasksCreatedByUser() {
		return this.taskController.getAllTaskByUser(Constants.UserId.TEST_UUID).getHttpResponse();
	}

	@PatchMapping("task/done/{taskId}")
	public Object markTaskAsDone(@PathVariable @Min(1) int taskId) {
		return this.taskController.markTaskAsDone(taskId).getHttpResponse();
	}

}
