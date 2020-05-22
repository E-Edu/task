package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.ModuleModel;
import com.gewia.taskservice.database.model.SubjectModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Integer> {

	Optional<ModuleModel> findByNameKeyIgnoreCase(String nameKey);

	List<ModuleModel> findAllBySubjectId(SubjectModel subjectId);

	boolean existsBySubjectId(SubjectModel subjectId);

	boolean existsByNameKeyIgnoreCase(String nameKey);

}
