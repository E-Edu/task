package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionWordsaladRepository extends JpaRepository<WordsaladSolutionModel, Integer> {

	boolean existsBySolutionId(SolutionModel solutionId);

	Optional<WordsaladSolutionModel> findBySolutionId(SolutionModel solutionId);

	void deleteBySolutionId(SolutionModel solutionId);

}
