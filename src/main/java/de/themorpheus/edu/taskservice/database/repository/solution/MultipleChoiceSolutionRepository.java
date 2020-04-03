package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MultipleChoiceSolutionRepository extends JpaRepository<MultipleChoiceSolutionModel, Integer> {

	MultipleChoiceSolutionModel getMultipleChoiceSolutionBySolutionAndSolutionId(String solution, int solutionId);

	void deleteMultipleChoiceSolutionBySolutionIdAndSolution(int solutionId, String solution);

	void deleteAllMultipleChoiceSolutionsBySolutionId(int solutionId);

	boolean existsBySolution(String solution);

	List<MultipleChoiceSolutionModel> findAllMultipleChoiceSolutionsBySolutionIdOrderedBySolution(int solutionId);
}
