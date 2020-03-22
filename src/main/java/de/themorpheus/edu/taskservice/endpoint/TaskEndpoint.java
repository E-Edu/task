package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.GetNextTaskDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.UpdateTaskDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskEndpoint {

	@Autowired private TaskController taskController;

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)

	public Object createTask(@RequestBody @Valid CreateTaskDTO dto) {

		return this.taskController.createTask(
			dto.getTask(),
			UUID.randomUUID(), //TODO
			dto.getNecessaryPoints(),
			dto.getLectureDisplayName(),
			dto.getTaskTypeDisplayName(),
			dto.getDifficultyDisplayName()
		).getHttpResponse();
	}

	@GetMapping("/lecture/{lectureDisplayName}/task")
	public Object getAllTasksFromLecture(@PathVariable @NotNull @NotEmpty @NotBlank String lectureDisplayName) {
		if (Validation.nullOrEmpty(lectureDisplayName)) return Error.INVALID_PARAM;

		return this.taskController.getTasksFromLecture(lectureDisplayName).getHttpResponse();
	}

	@PatchMapping(value = "/task/verify/{taskId}")
	public Object verifyTask(@PathVariable @Min(0) int taskId) {
		return this.taskController.verifyTask(taskId).getHttpResponse();
	}

	@PutMapping(path = "/task/{taskId}")
	public Object updateTask(@PathVariable @Min(0) int taskId, @RequestBody @Valid UpdateTaskDTO updateTaskDTO) {
		return this.taskController.updateTask(
			taskId,
			updateTaskDTO.getTask(),
			updateTaskDTO.getNecessaryPoints(),
			updateTaskDTO.getTaskTypeDisplayName(),
			updateTaskDTO.getLectureDisplayName(),
			updateTaskDTO.getDifficultyDisplayName()
		).getHttpResponse();
	}

	@PostMapping("/task/next")
	public Object nextTask(@RequestBody @Valid GetNextTaskDTO dto) {
		return this.taskController.getNextTask(dto.getLastTaskIds()).getHttpResponse();
	}

	@DeleteMapping("/task/{taskId}")
	public Object deleteTask(@PathVariable @Min(0) int taskId) {
		this.taskController.deleteTask(taskId);
		return ControllerResult.empty().getHttpResponse();
	}

}
