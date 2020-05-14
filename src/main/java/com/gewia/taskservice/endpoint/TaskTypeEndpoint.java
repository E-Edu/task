package com.gewia.taskservice.endpoint;

import com.gewia.taskservice.controller.TaskTypeController;
import com.gewia.taskservice.endpoint.dto.request.CreateTaskTypeRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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

	@GetMapping("/task_type/{taskTypeId:[0-9]+}")
	public Object getTaskType(@PathVariable @Min(1) int taskTypeId) {
		return this.taskTypeController.getTaskType(taskTypeId).getHttpResponse();
	}

	@GetMapping("/task_type/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object getTaskTypeByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.taskTypeController.getTaskTypeByNameKey(nameKey).getHttpResponse();
	}

	@GetMapping("/task_type")
	public Object getTaskTypes() {
		return this.taskTypeController.getAllTaskTypes().getHttpResponse();
	}

	@DeleteMapping("/task_type/{taskTypeId:[0-9]+}")
	public Object deleteTaskType(@PathVariable @Min(1) int taskTypeId) {
		return this.taskTypeController.deleteTaskType(taskTypeId).getHttpResponse();
	}

	@DeleteMapping("/task_type/{nameKey:[a-zäöüA-ZÄÖÜ0-9_]*[a-zA-Z][a-zäöüA-ZÄÖÜ0-9_]*}")
	public Object deleteTaskTypeByNameKey(@PathVariable @NotBlank String nameKey) {
		return this.taskTypeController.deleteTaskTypeByNameKey(nameKey).getHttpResponse();
	}

}
