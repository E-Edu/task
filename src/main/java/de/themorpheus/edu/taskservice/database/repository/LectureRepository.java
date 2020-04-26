package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureModel, Integer> {

	List<LectureModel> findAllByModuleId(ModuleModel moduleId);

	boolean existsByNameKeyIgnoreCase(String nameKey);

	boolean existsByModuleId(ModuleModel moduleId);

	Optional<LectureModel> findByNameKeyIgnoreCase(String nameKey);

}
