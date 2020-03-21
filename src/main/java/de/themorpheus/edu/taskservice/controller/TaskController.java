package de.themorpheus.edu.taskservice.controller;

import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.repository.LectureRepository;
import de.themorpheus.edu.taskservice.database.repository.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

	@Autowired private TaskRepository taskRepository;
	@Autowired private LectureRepository lectureRepository;

	public void createTask(String task) {
		this.taskRepository.save(new TaskModel(task));
	}

	public List<TaskModel> getAllTasks(String subject, String module, String lecture) {
		return this.taskRepository.getAllTasksByLectureId(this.lectureRepository.getLectureByLectureId(0));
	}

}
