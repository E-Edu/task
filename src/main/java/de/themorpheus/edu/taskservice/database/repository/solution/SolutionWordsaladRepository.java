package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionWordsaladModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionWordsaladRepository extends JpaRepository<SolutionWordsaladModel, Integer> {

	void deleteSolutionByDisplayNameIgnoreCase(String displayName);

	SolutionModel getSolutionBySolutionId(int solutionId);

	SolutionModel getSolutionByDisplayNameIgnoreCase(String displayName);

}
