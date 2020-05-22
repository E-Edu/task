package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.TaskTypeModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskTypeModel, Integer> {

	Optional<TaskTypeModel> findByNameKeyIgnoreCase(String nameKey);

	boolean existsByNameKeyIgnoreCase(String nameKey);

}
