package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.ModuleModel;
import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Integer> {

	void deleteModuleByDisplayNameIgnoreCase(String displayName);

	ModuleModel getModuleByModuleId(int moduleId);

	ModuleModel getModuleByDisplayNameIgnoreCase(String displayName);

	List<ModuleModel> getModulesBySubjectId(SubjectModel subjectId);

}
