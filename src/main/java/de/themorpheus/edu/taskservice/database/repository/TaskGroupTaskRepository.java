package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.TaskGroupModel;
import de.themorpheus.edu.taskservice.database.model.TaskGroupTaskModel;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupTaskRepository extends JpaRepository<TaskGroupTaskModel, Integer> {

	void deleteByTaskGroupIdAndTaskId(TaskGroupModel taskGroupTaskId, TaskModel taskId);

	boolean existsByTaskGroupIdAndTaskId(TaskGroupModel taskGroupId, TaskModel taskId);

	void deleteAllByTaskGroupId(TaskGroupModel taskGroupId);

	List<TaskGroupTaskModel> findAllByTaskGroupId(TaskGroupModel taskGroupId);

}
