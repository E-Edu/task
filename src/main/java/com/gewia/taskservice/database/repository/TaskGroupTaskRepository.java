package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.TaskGroupModel;
import com.gewia.taskservice.database.model.TaskGroupTaskModel;
import com.gewia.taskservice.database.model.TaskModel;
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
