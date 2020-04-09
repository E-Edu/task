package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureModel, Integer> {

	void deleteLectureByNameKeyIgnoreCase(String nameKey);

	LectureModel getLectureByLectureId(int lectureId);

	LectureModel getLectureByNameKeyIgnoreCase(String nameKey);

	List<LectureModel> getLecturesByModuleId(ModuleModel moduleId);

}
