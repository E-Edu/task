package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskTypeController;
import de.themorpheus.edu.taskservice.endpoint.dto.CreateTaskTypeDTO;
import de.themorpheus.edu.taskservice.util.Validation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskTypeEndpoint {

	@Autowired private TaskTypeController taskTypeController;

	@PostMapping(value = "/taskType", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object createTaskType(@RequestBody @Valid CreateTaskTypeDTO dto) {
		return this.taskTypeController.createTaskType(dto.getDisplayName());
	}

	@GetMapping("/taskType/{displayName}")
	public Object getTaskType(@PathVariable String displayName) {
		if (Validation.nullOrEmpty(displayName)) return Error.INVALID_PARAM;

		return this.taskTypeController.getTaskTypeByDisplayName(displayName);
	}

	@GetMapping("/taskTypes")
	public Object getTaskTypes() {
		return this.taskTypeController.getAllTaskTypes();
	}

}
