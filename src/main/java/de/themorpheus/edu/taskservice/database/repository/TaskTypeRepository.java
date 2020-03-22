package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskTypeModel, Integer> {

	TaskTypeModel getTaskTypeByTaskTypeId(int taskTypeId);

	TaskTypeModel getTaskTypeByDisplayNameIgnoreCase(String displayName);

}
