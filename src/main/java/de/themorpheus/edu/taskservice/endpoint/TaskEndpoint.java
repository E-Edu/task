package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.TaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskEndpoint {

	@Autowired
	private TaskController taskController;

	@GetMapping(value = "/task")
	public String getTasks(Model model) {
		return null;
	}

	@GetMapping(value = "/task/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTask(int taskId) {
		return null;
	}

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createTask() {
		this.taskController.createTask("TestTask");
		return null;
	}

	public String getAllTask(Model model) {
		return null;
	}

}
