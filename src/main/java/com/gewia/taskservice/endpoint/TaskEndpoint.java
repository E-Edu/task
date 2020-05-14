package com.gewia.taskservice.endpoint;

import com.gewia.taskservice.controller.TaskController;
import com.gewia.taskservice.controller.VotingController;
import com.gewia.taskservice.endpoint.dto.request.CreateTaskRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.GetNextTaskRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.UpdateTaskRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.VoteTaskRequestDTO;
import com.gewia.taskservice.util.Constants;
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

	@GetMapping("/lecture/{lectureId:[0-9]+}/task")
	public Object getAllTasksFromLecture(@PathVariable @Min(1) int lectureId,
										 @RequestParam(required = false) boolean showBanned,
										 @RequestParam(required = false, defaultValue = "0") int skip,
										 @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskController.getTasksByLectureId(lectureId, showBanned, skip, max).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureNameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}/task")
	public Object getAllTasksFromLectureByLectureNameKey(@PathVariable @NotBlank String lectureNameKey,
														 @RequestParam(required = false) boolean showBanned,
														 @RequestParam(required = false, defaultValue = "0") int skip,
														 @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskController.getTasksByLectureNameKey(lectureNameKey, showBanned, skip, max).getHttpResponse();
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
	public Object getTaskByUser(@PathVariable @NotNull UUID userId,
								@RequestParam(required = false, defaultValue = "0") int skip,
								@RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskController.getTaskByUserId(userId, skip, max).getHttpResponse();
	}

	@GetMapping("/task/own")
	public Object getOwnTasks(@RequestParam(required = false, defaultValue = "0") int skip,
							  @RequestParam(required = false, defaultValue = "0") int max) {
		return this.taskController.getTaskByUserId(Constants.UserId.TEST_UUID, skip, max).getHttpResponse();
	}

	@PatchMapping("task/done/{taskId}")
	public Object markTaskAsDone(@PathVariable @Min(1) int taskId) {
		return this.taskController.markTaskAsDone(taskId).getHttpResponse();
	}

}
