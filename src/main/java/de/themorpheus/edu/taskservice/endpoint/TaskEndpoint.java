package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskEndpoint {

	@Autowired
	private TaskController taskController;

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createTask() {
		this.taskController.createTask("TestTask");
		return null;
	}

	@GetMapping("/task")
	public List<TaskModel> getAllTasks(
		@RequestParam String subject,
		@RequestParam String module,
		@RequestParam String lecture
	) {
		return this.taskController.getAllTasks(subject, module, lecture);
	}

}
