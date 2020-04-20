package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskTypeController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.CreateTaskTypeRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class TaskTypeEndpoint {

	@Autowired private TaskTypeController taskTypeController;

	@PostMapping("/task_type")
	public Object createTaskType(@RequestBody @Valid CreateTaskTypeRequestDTO dto) {
		return this.taskTypeController.createTaskType(dto).getHttpResponse();
	}

	@GetMapping("/task_type/{nameKey}")
	public Object getTaskType(@PathVariable @NotBlank String nameKey) {
		return this.taskTypeController.getTaskType(nameKey).getHttpResponse();
	}

	@GetMapping("/task_type")
	public Object getTaskTypes() {
		return this.taskTypeController.getAllTaskTypes().getHttpResponse();
	}

	@DeleteMapping("/task_type/{nameKey}")
	public Object deleteTaskType(@PathVariable @NotBlank String nameKey) {
		return this.taskTypeController.deleteTaskType(nameKey).getHttpResponse();
	}

}
