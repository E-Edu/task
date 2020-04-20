package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

	Optional<TaskModel> getTaskByTaskId(int taskId);

	TaskModel getTaskByTaskIgnoreCase(String task);

	List<TaskModel> getAllTasksByLectureId(LectureModel lectureId);

}
