package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.FreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.UserFreestyleSolutionModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFreestyleSolutionRepository extends JpaRepository<UserFreestyleSolutionModel, Integer> {

	List<UserFreestyleSolutionModel> findAllByUserId(UUID userId);

	void deleteAllByUserId(UUID userId);

	List<UserFreestyleSolutionModel> findAllByFreestyleSolutionId(FreestyleSolutionModel freestyleSolutionId);

}
