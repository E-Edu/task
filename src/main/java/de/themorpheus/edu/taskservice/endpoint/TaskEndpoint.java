package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.util.Validation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PatchMapping(value = "/task/verify/{taskId}")
	public String verifyTask(@PathVariable("taskId") int taskId) {
		if (Validation.greaterZero(taskId)) {
			return this.taskController.verifyTask(taskId);
		} else {
			return "{\"error\": \"TaskID not greater than 0\"}";
		}
	}

}
