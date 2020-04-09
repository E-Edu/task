package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskTypeController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskTypeDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import de.themorpheus.edu.taskservice.util.Validation;
import io.micrometer.core.annotation.Timed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Timed
@RestController
public class TaskTypeEndpoint {

	@Autowired private TaskTypeController taskTypeController;

	@PostMapping(value = "/task_type", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createTaskType(@RequestBody @Valid CreateTaskTypeDTO dto) {
		return this.taskTypeController.createTaskType(dto.getNameKey()).getHttpResponse();
	}

	@GetMapping("/task_type/{nameKey}")
	public Object getTaskType(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		if (Validation.nullOrEmpty(nameKey)) return Error.INVALID_PARAM;

		return this.taskTypeController.getTaskTypeByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/task_types")
	public Object getTaskTypes() {
		return this.taskTypeController.getAllTaskTypes().getHttpResponse();
	}

	@DeleteMapping("/task_type/{nameKey}")
	public Object deleteTaskType(@PathVariable @NotNull @NotEmpty @NotBlank String nameKey) {
		this.taskTypeController.deleteTaskType(nameKey);
		return ControllerResult.empty();
	}

}
