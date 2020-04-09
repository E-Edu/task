package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.TaskTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskTypeModel, Integer> {

	void deleteTaskTypeByNameKeyIgnoreCase(String nameKey);

	TaskTypeModel getTaskTypeByTaskTypeId(int taskTypeId);

	TaskTypeModel getTaskTypeByNameKeyIgnoreCase(String nameKey);

	boolean existsByNameKeyIgnoreCase(String nameKey);

}
