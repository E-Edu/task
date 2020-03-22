package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Integer> {

	ModuleModel getModuleByModuleId(int moduleId);

	ModuleModel getModuleByDisplayNameIgnoreCase(String displayName);

	List<ModuleModel> getModulesBySubjectId(SubjectModel subjectId);

}
