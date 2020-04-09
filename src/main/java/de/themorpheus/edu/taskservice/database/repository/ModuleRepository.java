package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Integer> {

	void deleteModuleByNameKeyIgnoreCase(String nameKey);

	ModuleModel getModuleByModuleId(int moduleId);

	ModuleModel getModuleByNameKeyIgnoreCase(String nameKey);

	List<ModuleModel> getModulesBySubjectId(SubjectModel subjectId);

}
