package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionModel, Integer> {

	void deleteSolutionByDisplayNameIgnoreCase(String displayName);

	SolutionModel getSolutionBySolutionId(int solutionId);

	SolutionModel getSolutionByDisplayNameIgnoreCase(String displayName);

}
