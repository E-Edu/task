package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.TaskDoneModel;
import com.gewia.taskservice.database.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TaskDoneRepository extends JpaRepository<TaskDoneModel, Integer> {

	boolean existsByTaskIdAndUserId(TaskModel taskId, UUID userId);

}
