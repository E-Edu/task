package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskDTO;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskEndpoint {

	@Autowired private TaskController taskController;

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createTask(@RequestBody CreateTaskDTO dto) {
		if (Validation.validateNull(
			dto.getTask(),
			dto.getLectureDisplayName(),
			dto.getTaskTypeDisplayName(),
			dto.getDifficultyDisplayName()
		)
			|| Validation.lowerZero(dto.getNecessaryPoints())
		) return Error.INVALID_PARAM;

		return this.taskController.createTask(
			dto.getTask(),
			UUID.randomUUID(), //TODO
			dto.getNecessaryPoints(),
			dto.getLectureDisplayName(),
			dto.getTaskTypeDisplayName(),
			dto.getDifficultyDisplayName()
		);
	}

	@GetMapping("/lecture/{lectureDisplayName}/task")
	public Object getAllTasksFromLecture(@PathVariable String lectureDisplayName) {
		if (Validation.nullOrEmpty(lectureDisplayName)) return Error.INVALID_PARAM;

		return this.taskController.getTasksFromLecture(lectureDisplayName);
	}

	@PatchMapping(value = "/task/verify/{taskId}")
	public Object verifyTask(@PathVariable("taskId") int taskId) {
		if (Validation.lowerZero(taskId)) return Error.INVALID_PARAM;

		return this.taskController.verifyTask(taskId);
	}

	@DeleteMapping("/task/{taskId}")
	public Object deleteTask(@PathVariable("taskId") int taskId) {
		if (Validation.lowerZero(taskId)) return Error.INVALID_PARAM;

		return this.taskController.deleteTask(taskId);
	}

}
